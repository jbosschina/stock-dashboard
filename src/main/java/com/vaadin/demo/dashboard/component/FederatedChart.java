package com.vaadin.demo.dashboard.component;

import java.util.ArrayList;
import java.util.List;

import com.vaadin.addon.charts.Chart;
import com.vaadin.addon.charts.model.ChartType;
import com.vaadin.addon.charts.model.Credits;
import com.vaadin.addon.charts.model.ListSeries;
import com.vaadin.addon.charts.model.PlotOptionsBar;
import com.vaadin.addon.charts.model.Series;
import com.vaadin.demo.dashboard.DashboardUI;
import com.vaadin.demo.dashboard.data.dummy.DummyDataGenerator;
import com.vaadin.demo.dashboard.domain.Stock;

/**
 * @author Kylin Soong
 */
public class FederatedChart extends Chart{

    private static final long serialVersionUID = -4300859308337933706L;
    
    public FederatedChart() {
        
        setCaption("Federated View");
        getConfiguration().setTitle("");
        getConfiguration().getChart().setType(ChartType.BAR);
        getConfiguration().getChart().setAnimation(false);
        getConfiguration().getxAxis().getLabels().setEnabled(false);
        getConfiguration().getxAxis().setTickWidth(0);
        getConfiguration().getyAxis().setTitle("");
        setSizeFull();
        
        List<Stock> stocks = new ArrayList<Stock>(DashboardUI.getDataProvider().getStocks());
        
        List<Series> series = new ArrayList<Series>();
        int size = stocks.size();
        if(size > 6) {
            size = 6;
        }
        for (int i = 0; i < size; i++) {
            Stock stock = stocks.get(i);
            PlotOptionsBar opts = new PlotOptionsBar();
            opts.setColor(DummyDataGenerator.chartColors[i]);
            opts.setBorderWidth(0);
            opts.setShadow(false);
            opts.setPointPadding(0.4);
            opts.setAnimation(false);
            ListSeries item = new ListSeries(stock.getCompany_name(), stock.getPrice());
            item.setPlotOptions(opts);
            series.add(item);
        }
        getConfiguration().setSeries(series);

        Credits c = new Credits("");
        getConfiguration().setCredits(c);

        PlotOptionsBar opts = new PlotOptionsBar();
        opts.setGroupPadding(0);
        getConfiguration().setPlotOptions(opts);
    }

}
