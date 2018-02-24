package com.vaadin.demo.dashboard.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 
 * @author kylin
 *
 */
public class Account implements Serializable {

	private static final long serialVersionUID = -1509736341367539639L;
	
	private BigDecimal accountID;

	private String customerID; 
	
	private String accountType;
	
	private String accountStatus;
	
	private Date dateOpened;
	
	private Date dateClosed;
	
	public Account() {
		
	}

	public Account(BigDecimal accountID, String customerID, String accountType, String accountStatus, Date dateOpened, Date dateClosed) {
		super();
		this.accountID = accountID;
		this.customerID = customerID;
		this.accountType = accountType;
		this.accountStatus = accountStatus;
		this.dateOpened = dateOpened;
		this.dateClosed = dateClosed;
	}

	public BigDecimal getAccountID() {
		return accountID;
	}

	public void setAccountID(BigDecimal accountID) {
		this.accountID = accountID;
	}

	public String getCustomerID() {
		return customerID;
	}

	public void setCustomerID(String customerID) {
		this.customerID = customerID;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public String getAccountStatus() {
		return accountStatus;
	}

	public void setAccountStatus(String accountStatus) {
		this.accountStatus = accountStatus;
	}

	public Date getDateOpened() {
		return dateOpened;
	}

	public void setDateOpened(Date dateOpened) {
		this.dateOpened = dateOpened;
	}

	public Date getDateClosed() {
		return dateClosed;
	}

	public void setDateClosed(Date dateClosed) {
		this.dateClosed = dateClosed;
	}

	@Override
	public String toString() {
		return "Account [accountID=" + accountID + ", accountType=" + accountType + ", accountStatus=" + accountStatus
				+ ", dateOpened=" + dateOpened + ", dateClosed=" + dateClosed + "]";
	}
}
