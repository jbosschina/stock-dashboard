package com.vaadin.demo.dashboard.event;

import java.util.Collection;

import com.vaadin.demo.dashboard.domain.Product;
import com.vaadin.demo.dashboard.domain.Stock;
import com.vaadin.demo.dashboard.domain.StockPrice;
import com.vaadin.demo.dashboard.domain.Transaction;
import com.vaadin.demo.dashboard.view.DashboardViewType;

/*
 * Event bus events used in Dashboard are listed here as inner classes.
 */
public abstract class DashboardEvent {

    public static final class UserLoginRequestedEvent {
        private final String userName, password;

        public UserLoginRequestedEvent(final String userName,
                final String password) {
            this.userName = userName;
            this.password = password;
        }

        public String getUserName() {
            return userName;
        }

        public String getPassword() {
            return password;
        }
    }

    public static class BrowserResizeEvent {

    }

    public static class UserLoggedOutEvent {

    }

    public static class NotificationsCountUpdatedEvent {
    }

    public static final class ReportsCountUpdatedEvent {
        private final int count;

        public ReportsCountUpdatedEvent(final int count) {
            this.count = count;
        }

        public int getCount() {
            return count;
        }

    }

    public static final class TransactionReportEvent {
        private final Collection<Transaction> transactions;

        public TransactionReportEvent(final Collection<Transaction> transactions) {
            this.transactions = transactions;
        }

        public Collection<Transaction> getTransactions() {
            return transactions;
        }
    }
    
    public static final class ProductReportEvent {
        
        private final Collection<Product> products;
        
        public ProductReportEvent(final Collection<Product> products) {
            this.products = products;
        }

        public Collection<Product> getProducts() {
            return products;
        }
    }
    
    public static final class StockPriceReportEvent {
        
        private final Collection<StockPrice> prices;
        
        public StockPriceReportEvent(final Collection<StockPrice> prices) {
            this.prices = prices;
        }

        public Collection<StockPrice> getPrices() {
            return prices;
        }
    }

    public static final class StockReportEvent {
        
        private final Collection<Stock> stocks;
        
        public StockReportEvent(final Collection<Stock> stocks) {
            this.stocks = stocks;
        }

        public Collection<Stock> getStocks() {
            return stocks;
        }
    }

 

    public static final class PostViewChangeEvent {
        private final DashboardViewType view;

        public PostViewChangeEvent(final DashboardViewType view) {
            this.view = view;
        }

        public DashboardViewType getView() {
            return view;
        }
    }

    public static class CloseOpenWindowsEvent {
    }

    public static class ProfileUpdatedEvent {
    }

}
