package com.vaadin.demo.dashboard.view.teiid;

import java.text.DecimalFormat;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

import com.google.common.eventbus.Subscribe;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.demo.dashboard.DashboardUI;
import com.vaadin.demo.dashboard.domain.StockPrice;
import com.vaadin.demo.dashboard.event.DashboardEventBus;
import com.vaadin.demo.dashboard.event.DashboardEvent.BrowserResizeEvent;
import com.vaadin.demo.dashboard.event.DashboardEvent.StockPriceReportEvent;
import com.vaadin.demo.dashboard.view.DashboardViewType;
import com.vaadin.event.ShortcutListener;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Page;
import com.vaadin.server.Responsive;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.SingleSelect;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Grid.Column;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.themes.ValoTheme;

/**
 * @author Kylin Soong
 */
public class StockPricesView extends VerticalLayout implements View {

    private static final long serialVersionUID = 9203154510302527919L;
    
    private final Grid<StockPrice> grid;
    private SingleSelect<StockPrice> singleSelect;
    private Button createReport;
    private String filterValue = "";
    private static final Set<Column<StockPrice, ?>> collapsibleColumns = new LinkedHashSet<>();
    
    private static final DecimalFormat DECIMALFORMAT = new DecimalFormat("#.##");
    
    public StockPricesView() {
        setSizeFull();
        addStyleName("transactions");
        setMargin(false);
        setSpacing(false);
        DashboardEventBus.register(this);
        
        addComponent(buildToolbar());

        grid = buildGrid();
        singleSelect = grid.asSingleSelect();
        addComponent(grid);
        setExpandRatio(grid, 1);
    }
    
    @Override
    public void detach() {
        super.detach();
        // A new instance of TransactionsView is created every time it's
        // navigated to so we'll need to clean up references to it on detach.
        DashboardEventBus.unregister(this);
    }
    
    @Subscribe
    public void browserResized(final BrowserResizeEvent event) {
        // Some columns are collapsed when browser window width gets small
        // enough to make the table fit better.

        if (defaultColumnsVisible()) {
            for (Column<StockPrice, ?> column : collapsibleColumns) {
                column.setHidden(Page.getCurrent().getBrowserWindowWidth() < 800);
            }
        }
    }

    private boolean defaultColumnsVisible() {
        boolean result = true;
        for (Column<StockPrice, ?> column : collapsibleColumns) {
            if (column.isHidden() == Page.getCurrent().getBrowserWindowWidth() < 800) {
                result = false;
            }
        }
        return result;
    }

    private Grid<StockPrice> buildGrid() {
        
        final Grid<StockPrice> grid = new Grid<>();
        grid.setSelectionMode(SelectionMode.SINGLE);
        grid.setSizeFull();
        
   
        collapsibleColumns.add(grid.addColumn(StockPrice::getSymbol).setId("Symbol"));
        
        grid.addColumn(stock -> "$"+ DECIMALFORMAT.format(stock.getPrice())).setId("Price").setHidable(true);
        
        grid.setColumnReorderingAllowed(true);
        
        Collection<StockPrice> prices = DashboardUI.getDataProvider().getStockPrices();
        ListDataProvider<StockPrice> dataProvider = com.vaadin.data.provider.DataProvider.ofCollection(prices);
        dataProvider.addSortComparator(Comparator.comparing(StockPrice::getSymbol).reversed()::compare);
        grid.setDataProvider(dataProvider);
        
        grid.addSelectionListener(event -> createReport.setEnabled(!singleSelect.isEmpty()));
        
        return grid;
    }

    private Component buildToolbar() {
        
        HorizontalLayout header = new HorizontalLayout();
        header.addStyleName("viewheader");
        Responsive.makeResponsive(header);

        Label title = new Label("Market Data");
        title.setSizeUndefined();
        title.addStyleName(ValoTheme.LABEL_H1);
        title.addStyleName(ValoTheme.LABEL_NO_MARGIN);
        header.addComponent(title);

        createReport = buildCreateReport();
        HorizontalLayout tools = new HorizontalLayout(buildFilter(), createReport);
        tools.addStyleName("toolbar");
        header.addComponent(tools);
        
        return header;
    }

    private Component buildFilter() {
        
        final TextField filter = new TextField();

        // TODO use new filtering API
        filter.addValueChangeListener(event -> {
            
            Collection<StockPrice> prices = DashboardUI.getDataProvider().getStockPrices().stream().filter(product -> {
                filterValue = filter.getValue().trim().toLowerCase();
                return passesFilter(product.getSymbol()) ;
            }).collect(Collectors.toList());

            ListDataProvider<StockPrice> dataProvider = com.vaadin.data.provider.DataProvider.ofCollection(prices);
            dataProvider.addSortComparator(Comparator.comparing(StockPrice::getSymbol).reversed()::compare);
            grid.setDataProvider(dataProvider);
        });

        filter.setPlaceholder("Filter");
        filter.setIcon(VaadinIcons.SEARCH);
        filter.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
        filter.addShortcutListener(
                new ShortcutListener("Clear", KeyCode.ESCAPE, null) {
 
                    private static final long serialVersionUID = 7457300408371145676L;

                    @Override
                    public void handleAction(final Object sender, final Object target) {
                        filter.setValue("");
                    }
                });
        return filter;
    }

    private boolean passesFilter(String subject) {
        if (subject == null) {
            return false;
        }
        return subject.trim().toLowerCase().contains(filterValue);
    }

    private Button buildCreateReport() {
        final Button createReport = new Button("Create Report");
        createReport.setDescription("Create a new report from the selected prices data");
        createReport.addClickListener(event -> createNewReportFromSelection());
        createReport.setEnabled(false);
        return createReport;
    }

    void createNewReportFromSelection() {
        if (!singleSelect.isEmpty()) {
            UI.getCurrent().getNavigator().navigateTo(DashboardViewType.REPORTS.getViewName());
            DashboardEventBus.post(new StockPriceReportEvent( Collections.singletonList(singleSelect.getValue())));
        }
    }


    @Override
    public void enter(ViewChangeEvent event) {
        
    }

}
