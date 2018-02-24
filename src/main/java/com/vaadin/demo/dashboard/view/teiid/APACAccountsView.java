package com.vaadin.demo.dashboard.view.teiid;

import java.util.Collection;
import java.util.Comparator;

import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.demo.dashboard.DashboardUI;
import com.vaadin.demo.dashboard.domain.Account;
import com.vaadin.ui.Grid;
import com.vaadin.ui.themes.ValoTheme;

/**
 * @author Kylin Soong
 */
public class APACAccountsView extends Grid<Account> {

    private static final long serialVersionUID = -6229335600519692147L;
    
    public APACAccountsView() {
        
    	setCaption("APAC Accounts(total: " + DashboardUI.getDataProvider().getAPACAccount().size() +")");
        addStyleName(ValoTheme.TABLE_BORDERLESS);
        addStyleName(ValoTheme.TABLE_NO_STRIPES);
        addStyleName(ValoTheme.TABLE_NO_VERTICAL_LINES);
        addStyleName(ValoTheme.TABLE_SMALL);
        setSizeFull();
                
        addColumn(Account::getAccountID).setId("AccountID");
        addColumn(Account::getCustomerID).setId("CustomerID");
        addColumn(Account::getAccountType).setId("AccountType");
        addColumn(Account::getAccountStatus).setId("AccountStatus");
        addColumn(Account::getDateOpened).setId("DateOpened");
        addColumn(Account::getDateClosed).setId("DateClosed");
//        setColumnReorderingAllowed(false);
        
        Collection<Account> accounts = DashboardUI.getDataProvider().getAPACAccount();
        ListDataProvider<Account> dataProvider = com.vaadin.data.provider.DataProvider.ofCollection(accounts);
        dataProvider.addSortComparator(Comparator.comparing(Account::getAccountID).reversed()::compare);
        setDataProvider(dataProvider);

    }
    
  

}
