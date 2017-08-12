package com.vaadin.demo.dashboard.view;

import com.vaadin.demo.dashboard.view.dashboard.DashboardView;
import com.vaadin.demo.dashboard.view.reports.ReportsView;
import com.vaadin.demo.dashboard.view.sales.SalesView;
import com.vaadin.demo.dashboard.view.teiid.ProductsView;
import com.vaadin.demo.dashboard.view.teiid.StockPricesChartView;
import com.vaadin.demo.dashboard.view.teiid.StockPricesView;
import com.vaadin.demo.dashboard.view.teiid.StocksView;
import com.vaadin.demo.dashboard.view.transactions.TransactionsView;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.server.Resource;

/**
 * 
 * @author kylin
 *
 */
public enum DashboardViewType {
    
    DASHBOARD("dashboard", DashboardView.class, VaadinIcons.HOME, true), 
    MARKETTRENDS("marketTrends", StockPricesChartView.class, VaadinIcons.CHART, false), 
    PRODUCTS("products", ProductsView.class, VaadinIcons.TABLE, false), 
    PRICES("prices", StockPricesView.class, VaadinIcons.TABLE, false), 
    STOCKS("stocks", StocksView.class, VaadinIcons.TABLE, false), 
    REPORTS("reports", ReportsView.class, VaadinIcons.FILE_TEXT_O, true),
    
    SALES("sales", SalesView.class, VaadinIcons.CHART, false),
    TRANSACTIONS("transactions", TransactionsView.class, VaadinIcons.TABLE, false);
    
    
    /*, SCHEDULE("schedule", ScheduleView.class, FontAwesome.CALENDAR_O, false);*/

    private final String viewName;
    private final Class<? extends View> viewClass;
    private final Resource icon;
    private final boolean stateful;

    private DashboardViewType(final String viewName, final Class<? extends View> viewClass, final Resource icon, final boolean stateful) {
        this.viewName = viewName;
        this.viewClass = viewClass;
        this.icon = icon;
        this.stateful = stateful;
    }

    public boolean isStateful() {
        return stateful;
    }

    public String getViewName() {
        return viewName;
    }

    public Class<? extends View> getViewClass() {
        return viewClass;
    }

    public Resource getIcon() {
        return icon;
    }

    public static DashboardViewType getByViewName(final String viewName) {
        DashboardViewType result = null;
        for (DashboardViewType viewType : values()) {
            if (viewType.getViewName().equals(viewName)) {
                result = viewType;
                break;
            }
        }
        return result;
    }

}
