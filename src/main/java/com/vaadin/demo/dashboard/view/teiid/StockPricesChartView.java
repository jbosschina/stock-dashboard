package com.vaadin.demo.dashboard.view.teiid;

import com.vaadin.addon.charts.model.Configuration;
import com.vaadin.addon.charts.model.DataSeries;
import com.vaadin.addon.charts.model.HorizontalAlign;
import com.vaadin.addon.charts.model.LayoutDirection;
import com.vaadin.addon.charts.model.Legend;
import com.vaadin.addon.charts.model.PlotOptionsColumn;
import com.vaadin.addon.charts.model.PlotOptionsSpline;
import com.vaadin.addon.charts.model.Tooltip;
import com.vaadin.addon.charts.model.VerticalAlign;
import com.vaadin.addon.charts.model.XAxis;
import com.vaadin.addon.charts.model.YAxis;
import com.vaadin.addon.charts.model.ZoomType;
import com.vaadin.addon.charts.model.style.Style;
import com.vaadin.addon.charts.model.style.Color;
import com.vaadin.addon.charts.model.style.SolidColor;
import com.vaadin.addon.charts.model.style.Theme;
import com.vaadin.addon.charts.themes.ValoLightTheme;
import com.vaadin.demo.dashboard.DashboardUI;
import com.vaadin.demo.dashboard.domain.Stock;
import com.vaadin.demo.dashboard.domain.StockPrice;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.vaadin.addon.charts.Chart;
import com.vaadin.addon.charts.ChartOptions;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Responsive;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.themes.ValoTheme;

/**
 * @author Kylin Soong
 */
public class StockPricesChartView extends VerticalLayout implements View {

    private static final long serialVersionUID = -7808672657867498091L;
    
    private Chart federated;
    
    public StockPricesChartView() {
        
        setSizeFull();
        addStyleName("sales");
        setMargin(false);
        setSpacing(false);

        addComponent(buildHeader());
        
        Chart charts = buildStockTrends();
        addComponent(charts);
        setExpandRatio(charts, 1);
    }

    private Chart buildStockTrends() {
        
        Collection<StockPrice> prices = DashboardUI.getDataProvider().getStockPrices();
        
        Chart chart = new Chart();
        
        Configuration conf = chart.getConfiguration();
        conf.getChart().setZoomType(ZoomType.XY);
        conf.setTitle("Stock Prices Comparision Chart");
        
        XAxis x = new XAxis();
        List<String> symbols = new ArrayList<>();
        prices.forEach(p -> symbols.add(p.getSymbol()));
        x.setCategories(symbols.toArray(new String[symbols.size()]));
        conf.addxAxis(x);
        
        YAxis primary = new YAxis();
        primary.setTitle("Trend");
        Style style = new Style();
        style.setColor(getThemeColors()[1]);
        primary.getTitle().setStyle(style);
        conf.addyAxis(primary);
        
        YAxis snd = new YAxis();
        snd.setTitle("Price");
        snd.setOpposite(true);
        style = new Style();
        style.setColor(new SolidColor("#4572A7"));
        snd.getTitle().setStyle(style);
        conf.addyAxis(snd);
        
        Tooltip tooltip = new Tooltip();
        tooltip.setFormatter("this.x +': '+ this.y");
        conf.setTooltip(tooltip);
        
        Legend legend = new Legend();
        legend.setLayout(LayoutDirection.VERTICAL);
        legend.setAlign(HorizontalAlign.LEFT);
        legend.setX(120);
        legend.setVerticalAlign(VerticalAlign.TOP);
        legend.setY(100);
        legend.setFloating(true);
        conf.setLegend(legend);
        
        List<Double> lists = new ArrayList<>();
        prices.forEach(p -> lists.add(p.getPrice()));
        DataSeries series = new DataSeries();
        series.setPlotOptions(new PlotOptionsColumn());
        series.setName("Price");
        series.setData(lists.toArray(new Double[lists.size()]));
        series.setyAxis(1);
        conf.addSeries(series);
        
        series = new DataSeries();
        PlotOptionsSpline plotOptions = new PlotOptionsSpline();
        series.setPlotOptions(plotOptions);
        series.setName("Trend");
        series.setData(lists.toArray(new Double[lists.size()]));
        plotOptions.setColor(getThemeColors()[1]);
        conf.addSeries(series);
        
        return chart;
    }

    private Component buildHeader() {
        HorizontalLayout header = new HorizontalLayout();
        header.addStyleName("viewheader");
        Responsive.makeResponsive(header);

        Label titleLabel = new Label("Markets Trend");
        titleLabel.setSizeUndefined();
        titleLabel.addStyleName(ValoTheme.LABEL_H1);
        titleLabel.addStyleName(ValoTheme.LABEL_NO_MARGIN);
        header.addComponents(titleLabel, buildToolbar());

        return header;
    }

    private Component buildToolbar() {
        
        HorizontalLayout toolbar = new HorizontalLayout();
        toolbar.addStyleName("toolbar");
        
        final Button add = new Button("Add");
        add.setEnabled(false);
        add.addStyleName(ValoTheme.BUTTON_PRIMARY);
        add.addClickListener(new ClickListener() {

            private static final long serialVersionUID = -8526001900877520695L;

            @Override
            public void buttonClick(ClickEvent event) {
                
                federated = buildFederated();
                addComponent(federated);
                setExpandRatio(federated, 2);
            }
        });
        toolbar.addComponent(add);
        
        return toolbar;
    }
    
    private Chart buildFederated() {

        Collection<Stock> stocks = DashboardUI.getDataProvider().getStocks();
        
        Chart chart = new Chart();
        
        Configuration conf = chart.getConfiguration();
        conf.getChart().setZoomType(ZoomType.XY);
        conf.setTitle("Federated Stock Prices Comparision Chart");
        
        XAxis x = new XAxis();
        List<String> names = new ArrayList<>();
        stocks.forEach(s -> names.add(s.getCompany_name()));
        x.setCategories(names.toArray(new String[names.size()]));
        conf.addxAxis(x);
        
        YAxis primary = new YAxis();
        primary.setTitle("Trend");
        Style style = new Style();
        style.setColor(getThemeColors()[1]);
        primary.getTitle().setStyle(style);
        conf.addyAxis(primary);
        
        YAxis snd = new YAxis();
        snd.setTitle("Price");
        snd.setOpposite(true);
        style = new Style();
        style.setColor(new SolidColor("#4572A7"));
        snd.getTitle().setStyle(style);
        conf.addyAxis(snd);
        
        Tooltip tooltip = new Tooltip();
        tooltip.setFormatter("this.x +': '+ this.y");
        conf.setTooltip(tooltip);
        
        Legend legend = new Legend();
        legend.setLayout(LayoutDirection.VERTICAL);
        legend.setAlign(HorizontalAlign.LEFT);
        legend.setX(120);
        legend.setVerticalAlign(VerticalAlign.TOP);
        legend.setY(100);
        legend.setFloating(true);
        conf.setLegend(legend);
        
        List<Double> lists = new ArrayList<>();
        stocks.forEach(p -> lists.add(p.getPrice()));
        DataSeries series = new DataSeries();
        series.setPlotOptions(new PlotOptionsColumn());
        series.setName("Price");
        series.setData(lists.toArray(new Double[lists.size()]));
        series.setyAxis(1);
        conf.addSeries(series);
        
        series = new DataSeries();
        PlotOptionsSpline plotOptions = new PlotOptionsSpline();
        series.setPlotOptions(plotOptions);
        series.setName("Trend");
        series.setData(lists.toArray(new Double[lists.size()]));
        plotOptions.setColor(getThemeColors()[1]);
        conf.addSeries(series);
        
        return chart;
    }
    
    
    protected Color[] getThemeColors() {
        Theme theme = ChartOptions.get().getTheme();
        return (theme != null) ? theme.getColors() : new ValoLightTheme().getColors();
    }

    @Override
    public void enter(ViewChangeEvent event) {
        // TODO Auto-generated method stub
        
    }

}
