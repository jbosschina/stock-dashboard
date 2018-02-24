package com.vaadin.demo.dashboard.data.dummy;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.olingo.client.api.ODataClient;
import org.apache.olingo.client.api.communication.request.retrieve.ODataEntitySetIteratorRequest;
import org.apache.olingo.client.api.communication.response.ODataRetrieveResponse;
import org.apache.olingo.client.api.domain.ClientEntity;
import org.apache.olingo.client.api.domain.ClientEntitySet;
import org.apache.olingo.client.api.domain.ClientEntitySetIterator;
import org.apache.olingo.client.core.ODataClientFactory;
import org.apache.olingo.client.core.http.BasicAuthHttpClientFactory;

import java.math.BigDecimal;
import java.net.URI;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import com.vaadin.demo.dashboard.domain.Account;
import com.vaadin.demo.dashboard.domain.Product;
import com.vaadin.demo.dashboard.domain.Stock;
import com.vaadin.demo.dashboard.domain.StockPrice;

import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;
import io.fabric8.openshift.api.model.Route;
import io.fabric8.openshift.api.model.RouteList;
import io.fabric8.openshift.client.OpenShiftClient;

public class ODataClientUtils {
	
	private static final String BACKEND_ROUTE_VALUE = "vdb-service-odata";
	private static final String REST_PROTOCOL_HTTP = "http";
	private static final String BACKEND_HOST_URL_VALUE = "vdb-service-odata-teiid-data-service.192.168.42.107.nip.io";
    
    private static String BASE = buildBaseURL();
    
    private static final String URL_US_Customers = "/odata4/Portfolio.1/US_Customers";
    private static final String URL_US_Customers_VBL = "/odata4/Portfolio.1/US_Customers_VBL";
    private static final String URL_APAC_Customers = "/odata4/Portfolio.1/APAC_Customers";
    private static final String URL_APAC_Customers_VBL = "/odata4/Portfolio.1/APAC_Customers_VBL";
    private static final String URL_Customers = "/odata4/Portfolio.1/Customers";
    private static final String T_MARKETDATA = "marketdata";
    private static final String T_ACCOUNT = "account";
    
    private static final String USERNAME = "teiidUser";
    private static final String PASSWORD = "password1!";
	
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    
    static ODataClient client;
    
    static {
    	client = ODataClientFactory.getClient();
    	client.getConfiguration().setHttpClientFactory(new BasicAuthHttpClientFactory(USERNAME, PASSWORD));
    }
    
    
    public static List<StockPrice> loadMarketData(final List<StockPrice> items) {
    	ClientEntitySetIterator<ClientEntitySet, ClientEntity> iterator = odataQuery(BASE + URL_US_Customers, T_MARKETDATA);
    	iterator.forEachRemaining(e -> {
    		String symbol = e.getProperty("SYMBOL").getValue().toString();
    		String price = e.getProperty("PRICE").getValue().toString();
    		items.add(new StockPrice(symbol, Double.valueOf(price)));
    	});
        return items;
    }
    
    public static List<Account> loadUS_CustomersAccount(List<Account> items) {
    	ClientEntitySetIterator<ClientEntitySet, ClientEntity> iterator = odataQuery(BASE + URL_US_Customers, T_ACCOUNT);
    	iterator.forEachRemaining(e -> {
    		String aid = e.getProperty("AccountID").getValue().toString();
    		String cid = e.getProperty("SSN").getValue().toString();
    		String type = e.getProperty("AccountType").getValue().toString();
    		String status = e.getProperty("AccountStatus").getValue().toString();
    		String open = e.getProperty("DATEOPENED").getValue().toString();
    		String close = e.getProperty("DATECLOSED").getValue().toString();
    		Account account = new Account();
    		account.setAccountID(new BigDecimal(aid));
    		account.setCustomerID(cid);
    		account.setAccountType(type);
    		account.setAccountStatus(status);
    		try {
				account.setDateOpened(dateFormat.parse(open));
				if(close.length() > 0) {
					account.setDateClosed(dateFormat.parse(close));
				}
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
    		items.add(account);
    	});
    	return items;
    }
    
    public static List<Account> loadUS_Customers_VBLAccount(List<Account> items) {
    	ClientEntitySetIterator<ClientEntitySet, ClientEntity> iterator = odataQuery(BASE + URL_US_Customers_VBL, T_ACCOUNT);
    	iterator.forEachRemaining(e -> {
    		String aid = e.getProperty("AccountID").getValue().toString();
    		String cid = e.getProperty("SSN").getValue().toString();
    		String type = e.getProperty("AccountType").getValue().toString();
    		String status = e.getProperty("AccountStatus").getValue().toString();
    		String open = e.getProperty("DATEOPENED").getValue().toString();
    		String close = e.getProperty("DATECLOSED").getValue().toString();
    		Account account = new Account();
    		account.setAccountID(new BigDecimal(aid));
    		account.setCustomerID(cid);
    		account.setAccountType(type);
    		account.setAccountStatus(status);
    		try {
				account.setDateOpened(dateFormat.parse(open));
				if(close.length() > 0) {
					account.setDateClosed(dateFormat.parse(close));
				}
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
    		items.add(account);
    	});
    	return items;
    }
    
    public static List<Account> loadAPAC_CustomersAccount(List<Account> items) {
    	ClientEntitySetIterator<ClientEntitySet, ClientEntity> iterator = odataQuery(BASE + URL_APAC_Customers, T_ACCOUNT);
    	iterator.forEachRemaining(e -> {
    		String aid = e.getProperty("AccountId").getValue().toString();
    		String cid = e.getProperty("CustID").getValue().toString();
    		String type = e.getProperty("AccountType").getValue().toString();
    		String status = e.getProperty("AccountStatus").getValue().toString();
    		String open = e.getProperty("DATEOPENED").getValue().toString();
    		String close = e.getProperty("DATECLOSED").getValue().toString();
    		Account account = new Account();
    		account.setAccountID(new BigDecimal(aid));
    		account.setCustomerID(cid);
    		account.setAccountType(type);
    		account.setAccountStatus(status);
    		try {
				account.setDateOpened(dateFormat.parse(open));
				if(close.length() > 0) {
					account.setDateClosed(dateFormat.parse(close));
				}
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
    		items.add(account);
    	});
    	return items;
    }
    
    public static List<Account> loadAPAC_Customers_VBLAccount(List<Account> items) {
    	ClientEntitySetIterator<ClientEntitySet, ClientEntity> iterator = odataQuery(BASE + URL_APAC_Customers_VBL, T_ACCOUNT);
    	iterator.forEachRemaining(e -> {
    		String aid = e.getProperty("AccountId").getValue().toString();
    		String cid = e.getProperty("CustID").getValue().toString();
    		String type = e.getProperty("AccountType").getValue().toString();
    		String status = e.getProperty("AccountStatus").getValue().toString();
    		String open = e.getProperty("DATEOPENED").getValue().toString();
    		String close = e.getProperty("DATECLOSED").getValue().toString();
    		Account account = new Account();
    		account.setAccountID(new BigDecimal(aid));
    		account.setCustomerID(cid);
    		account.setAccountType(type);
    		account.setAccountStatus(status);
    		try {
				account.setDateOpened(dateFormat.parse(open));
				if(close.length() > 0) {
					account.setDateClosed(dateFormat.parse(close));
				}
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
    		items.add(account);
    	});
    	return items;
    }
    
    public static List<Account> loadCustomersAccount(List<Account> items) {
    	ClientEntitySetIterator<ClientEntitySet, ClientEntity> iterator = odataQuery(BASE + URL_Customers, T_ACCOUNT);
    	iterator.forEachRemaining(e -> {
    		String aid = e.getProperty("AccountId").getValue().toString();
    		String cid = e.getProperty("CustID").getValue().toString();
    		String type = e.getProperty("AccountType").getValue().toString();
    		String status = e.getProperty("AccountStatus").getValue().toString();
    		String open = e.getProperty("DATEOPENED").getValue().toString();
    		String close = e.getProperty("DATECLOSED").getValue().toString();
    		Account account = new Account();
    		account.setAccountID(new BigDecimal(aid));
    		account.setCustomerID(cid);
    		account.setAccountType(type);
    		account.setAccountStatus(status);
    		try {
				account.setDateOpened(dateFormat.parse(open));
				if(close.length() > 0) {
					account.setDateClosed(dateFormat.parse(close));
				}
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
    		items.add(account);
    	});
    	return items;
    }
    
    public static List<Stock> loadStocks(final List<Stock> items) {
        return items;
    }
    
    public static List<Product> loadProduct(final List<Product> items) {    
        return items;
    }
    
    private static ClientEntitySetIterator<ClientEntitySet, ClientEntity> odataQuery(String url, String table) {
    	URI uri = client.newURIBuilder(url).appendEntitySetSegment(table).build();
    	ODataEntitySetIteratorRequest<ClientEntitySet, ClientEntity> request = client.getRetrieveRequestFactory().getEntitySetIteratorRequest(uri);
    	request.setAccept("application/json;odata.metadata=minimal");
    	ODataRetrieveResponse<ClientEntitySetIterator<ClientEntitySet, ClientEntity>> response = request.execute();
    	return response.getBody();
    }

    private static String buildBaseURL() {
        
    	String ptotocol = System.getProperty("REST_PROTOCOL", REST_PROTOCOL_HTTP);
        String host = null;
        
        try {
            KubernetesClient kc = new DefaultKubernetesClient();
            OpenShiftClient client = kc.adapt(OpenShiftClient.class);
            RouteList routes = client.routes().list();
            
            System.out.println("MasterUrl: " + kc.getMasterUrl());
            System.out.println("Project Name: " + kc.getNamespace());
            
            String backendRoute = System.getProperty("BACKEND_ROUTE", BACKEND_ROUTE_VALUE);
            
            List<Route> result = routes.getItems().stream().filter(r -> r.getMetadata().getName().equals(backendRoute)).collect(Collectors.toList());
            
            if(result != null && result.size() > 0) {
            	host =  result.get(0).getSpec().getHost();
            } 
            
            kc.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        if(host == null) {
            host = System.getProperty("BACKEND_HOST_URL", BACKEND_HOST_URL_VALUE);
        }
        
        System.out.println("host: " + BACKEND_HOST_URL_VALUE);

        return ptotocol + "://" + host;
        
    }

    

    

    public static void main(String[] args) {
    	
       System.out.println(ODataClientUtils.loadMarketData(new ArrayList<StockPrice>()));        
       System.out.println(ODataClientUtils.loadUS_CustomersAccount(new ArrayList<Account>()));
       System.out.println(ODataClientUtils.loadAPAC_CustomersAccount(new ArrayList<Account>()));
       System.out.println(ODataClientUtils.loadUS_Customers_VBLAccount(new ArrayList<Account>()));
       System.out.println(ODataClientUtils.loadAPAC_Customers_VBLAccount(new ArrayList<Account>()));
       System.out.println(ODataClientUtils.loadCustomersAccount(new ArrayList<Account>()));     
    }
}
