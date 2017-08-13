package com.vaadin.demo.dashboard.component;

import java.text.DecimalFormat;
import java.util.Collection;
import java.util.Comparator;

import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.demo.dashboard.DashboardUI;
import com.vaadin.demo.dashboard.domain.StockPrice;
import com.vaadin.ui.Grid;
import com.vaadin.ui.themes.ValoTheme;

/**
 * @author Kylin Soong
 */
public class TopTenStocks extends Grid<StockPrice>{
    
    private static final DecimalFormat DECIMALFORMAT = new DecimalFormat("#.##");

    private static final long serialVersionUID = 1429967856362062762L;
    
    public TopTenStocks() {
        
        setCaption("Top 10 Stocks");
        addStyleName(ValoTheme.TABLE_BORDERLESS);
        addStyleName(ValoTheme.TABLE_NO_STRIPES);
        addStyleName(ValoTheme.TABLE_NO_VERTICAL_LINES);
        addStyleName(ValoTheme.TABLE_SMALL);
        setSizeFull();

        addColumn(StockPrice::getSymbol).setId("Symbol");
        addColumn(stock -> "$"+ DECIMALFORMAT.format(stock.getPrice())).setId("Price");
        setColumnReorderingAllowed(false);
        
        Collection<StockPrice> prices = DashboardUI.getDataProvider().getTop10Stocks();
        ListDataProvider<StockPrice> dataProvider = com.vaadin.data.provider.DataProvider.ofCollection(prices);
        dataProvider.addSortComparator(Comparator.comparing(StockPrice::getPrice).reversed()::compare);
        setDataProvider(dataProvider);
        
    }

}
