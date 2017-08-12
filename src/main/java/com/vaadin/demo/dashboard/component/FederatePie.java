package com.vaadin.demo.dashboard.component;

import java.util.ArrayList;
import java.util.List;

import com.vaadin.addon.charts.Chart;
import com.vaadin.addon.charts.model.ChartType;
import com.vaadin.addon.charts.model.Credits;
import com.vaadin.addon.charts.model.DataSeries;
import com.vaadin.addon.charts.model.DataSeriesItem;
import com.vaadin.addon.charts.model.PlotOptionsPie;
import com.vaadin.demo.dashboard.DashboardUI;
import com.vaadin.demo.dashboard.data.dummy.DummyDataGenerator;
import com.vaadin.demo.dashboard.domain.Stock;

public class FederatePie extends Chart{

    private static final long serialVersionUID = -8293101402829323203L;
    
    public FederatePie() {
        
        super(ChartType.PIE);

        setCaption("Popular Stocks");
        getConfiguration().setTitle("");
        getConfiguration().getChart().setType(ChartType.PIE);
        getConfiguration().getChart().setAnimation(false);
        setWidth("100%");
        setHeight("90%");
        
        DataSeries series = new DataSeries();
        
        List<Stock> stocks = new ArrayList<Stock>(DashboardUI.getDataProvider().getStocks());
        int size = stocks.size();
        if(size > 6) {
            size = 6;
        }
        
        for (int i = 0; i < size; i++) {
            Stock stock = stocks.get(i);
            DataSeriesItem item = new DataSeriesItem(stock.getSymbol(), stock.getPrice());
            item.setColor(DummyDataGenerator.chartColors[i]);
            series.add(item);
        }
        getConfiguration().setSeries(series);
        
        PlotOptionsPie opts = new PlotOptionsPie();
        opts.setBorderWidth(0);
        opts.setShadow(false);
        opts.setAnimation(false);
        getConfiguration().setPlotOptions(opts);

        Credits c = new Credits("");
        getConfiguration().setCredits(c);
    }

}
