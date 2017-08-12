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
import com.vaadin.demo.dashboard.domain.Stock;
import com.vaadin.demo.dashboard.event.DashboardEventBus;
import com.vaadin.demo.dashboard.event.DashboardEvent.BrowserResizeEvent;
import com.vaadin.demo.dashboard.event.DashboardEvent.StockReportEvent;
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
public class StocksView extends VerticalLayout implements View {

    private static final long serialVersionUID = 6591799108162327109L;
    
    private final Grid<Stock> grid;
    private SingleSelect<Stock> singleSelect;
    private Button createReport;
    private String filterValue = "";
    private static final Set<Column<Stock, ?>> collapsibleColumns = new LinkedHashSet<>();
    private static final DecimalFormat DECIMALFORMAT = new DecimalFormat("#.##");
    
    public StocksView() {
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
            for (Column<Stock, ?> column : collapsibleColumns) {
                column.setHidden(Page.getCurrent().getBrowserWindowWidth() < 800);
            }
        }
    }

    private boolean defaultColumnsVisible() {
        boolean result = true;
        for (Column<Stock, ?> column : collapsibleColumns) {
            if (column.isHidden() == Page.getCurrent().getBrowserWindowWidth() < 800) {
                result = false;
            }
        }
        return result;
    }

    private Grid<Stock> buildGrid() {
        
        final Grid<Stock> grid = new Grid<>();
        grid.setSelectionMode(SelectionMode.SINGLE);
        grid.setSizeFull();
        
        collapsibleColumns.add(grid.addColumn(Stock::getProduct_id).setId("ProductId"));
        collapsibleColumns.add(grid.addColumn(Stock::getSymbol).setId("Symbol"));
        collapsibleColumns.add(grid.addColumn(Stock::getCompany_name).setId("CompanyName"));
        
        grid.addColumn(stock -> "$"+ DECIMALFORMAT.format(stock.getPrice())).setId("Price").setHidable(true);
        
        grid.setColumnReorderingAllowed(true);
        
        Collection<Stock> stocks = DashboardUI.getDataProvider().getStocks();
        ListDataProvider<Stock> dataProvider = com.vaadin.data.provider.DataProvider.ofCollection(stocks);
        dataProvider.addSortComparator(Comparator.comparing(Stock::getSymbol).reversed()::compare);
        grid.setDataProvider(dataProvider);
        
        grid.addSelectionListener(event -> createReport.setEnabled(!singleSelect.isEmpty()));
        
        return grid;
    }

    private Component buildToolbar() {
        
        HorizontalLayout header = new HorizontalLayout();
        header.addStyleName("viewheader");
        Responsive.makeResponsive(header);

        Label title = new Label("Stocks");
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
            
            Collection<Stock> stocks = DashboardUI.getDataProvider().getStocks().stream().filter(stock -> {
                filterValue = filter.getValue().trim().toLowerCase();
                return passesFilter(stock.getSymbol()) || passesFilter(stock.getCompany_name()) ;
            }).collect(Collectors.toList());

            ListDataProvider<Stock> dataProvider = com.vaadin.data.provider.DataProvider.ofCollection(stocks);
            dataProvider.addSortComparator(Comparator.comparing(Stock::getProduct_id).reversed()::compare);
            grid.setDataProvider(dataProvider);
        });

        filter.setPlaceholder("Filter");
        filter.setIcon(VaadinIcons.SEARCH);
        filter.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
        filter.addShortcutListener(
                new ShortcutListener("Clear", KeyCode.ESCAPE, null) {
 
                    private static final long serialVersionUID = -2677989821967524088L;

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
        createReport.setDescription("Create a new report from the selected stocks");
        createReport.addClickListener(event -> createNewReportFromSelection());
        createReport.setEnabled(false);
        return createReport;
    }

    void createNewReportFromSelection() {
        if (!singleSelect.isEmpty()) {
            UI.getCurrent().getNavigator().navigateTo(DashboardViewType.REPORTS.getViewName());
            DashboardEventBus.post(new StockReportEvent( Collections.singletonList(singleSelect.getValue())));
        }
    }


    @Override
    public void enter(ViewChangeEvent event) {
        
        
    }

}
