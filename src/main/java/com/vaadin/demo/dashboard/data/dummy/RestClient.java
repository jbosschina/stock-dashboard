package com.vaadin.demo.dashboard.data.dummy;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vaadin.demo.dashboard.domain.Product;
import com.vaadin.demo.dashboard.domain.Stock;
import com.vaadin.demo.dashboard.domain.StockPrice;

import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;
import io.fabric8.openshift.api.model.Route;
import io.fabric8.openshift.api.model.RouteList;
import io.fabric8.openshift.client.OpenShiftClient;

@SuppressWarnings("unchecked")
public class RestClient {
    
    private static String BASE = buildBaseURL();
    
    private static String API_PRODUCT = BASE + "/rest/query/account/products";
    private static String API_MARKETDATA = BASE + "/rest/query/marketData/stockPrices";
    private static String API_STOCKS= BASE + "/rest/query/stocks";
    
    private static final String STOCKS_BACKEND_LABEL_K = "type";
    private static final String STOCKS_BACKEND_LABEL_V = "stocks-backend";
    
    
    public static List<Product> loadProduct(final List<Product> items) {
        
        try {
            URL url = new URL(API_PRODUCT);
            InputStream in = url.openStream();
            ObjectMapper mapper = new ObjectMapper();
            List<Map<String, Object>> list = mapper.readValue(in, List.class);
            list.forEach(m -> {
                Integer id = (Integer) m.get("id");
                String symbol = (String) m.get("symbol");
                String company_name = (String) m.get("company_name") ;
                items.add(new Product(id, symbol, company_name));
            });
            in.close();
            System.out.println("Load " + items.size() + " items via " + url);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return items;
    }
    
    private static String buildBaseURL() {
        
        String baseUrl = null;
        
        try {
            KubernetesClient kc = new DefaultKubernetesClient();
            OpenShiftClient client = kc.adapt(OpenShiftClient.class);
            RouteList routes = client.routes().list();
            
            System.out.println("MasterUrl: " + kc.getMasterUrl());
            System.out.println("Project Name: " + kc.getNamespace());
            
            String backendRoute = System.getProperty("BACKEND_ROUTE", "spring-boot-cxf-jaxrs");
            
            List<Route> result = null;
            result = routes.getItems().stream().filter(r -> r.getMetadata().getName().equals(backendRoute)).collect(Collectors.toList());
            
            if(result == null || result.size() == 0) {
                result = routes.getItems().stream().filter(r -> r.getMetadata().getLabels().get(STOCKS_BACKEND_LABEL_K) != null && r.getMetadata().getLabels().get(STOCKS_BACKEND_LABEL_K).equals(STOCKS_BACKEND_LABEL_V)).collect(Collectors.toList());
            }
            
            baseUrl = System.getProperty("REST_PROTOCOL", "http") + "://";
            if(result != null && result.size() > 0) {
                baseUrl += result.get(0).getSpec().getHost();
            } else {
                baseUrl += (System.getProperty("REST_HOST", "spring-boot-cxf-jaxrs") + ":" +  System.getProperty("REST_PORT", "8080"));
            }

            kc.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        if(baseUrl == null) {
            baseUrl = System.getProperty("BACKEND_REST_BASEURL", "http://spring-boot-cxf-jaxrs-petrochina.apps.na1.openshift.opentlc.com");
        }
        
        System.out.println("BaseURL: " + baseUrl);

        return baseUrl;
        
    }

    public static List<StockPrice> loadMarketData(final List<StockPrice> items) {
        try {
            URL url = new URL(API_MARKETDATA);
            InputStream in = url.openStream();
            ObjectMapper mapper = new ObjectMapper();
            List<Map<String, Object>> list = mapper.readValue(in, List.class);
            list.forEach(m -> {
                String symbol = (String) m.get("symbol");
                Double price = (Double) m.get("price") ;
                items.add(new StockPrice(symbol, price));
            });
            in.close();
            System.out.println("Load " + items.size() + " items via " + url);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return items;
    }

    public static List<Stock> loadStocks(final List<Stock> items) {
        try {
            URL url = new URL(API_STOCKS);
            InputStream in = url.openStream();
            ObjectMapper mapper = new ObjectMapper();
            List<Map<String, Object>> list = mapper.readValue(in, List.class);
            list.forEach(m -> {
                Integer id = (Integer) m.get("product_id");
                String symbol = (String) m.get("symbol");
                Double price = (Double) m.get("price") ;
                String company_name = (String) m.get("company_name") ;
                items.add(new Stock(id, symbol, price, company_name));
            });
            in.close();
            System.out.println("Load " + items.size() + " items via " + url);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return items;
    }

    public static void main(String[] args) throws JsonParseException, JsonMappingException, IOException {
        
       System.out.println(RestClient.loadProduct(new ArrayList<Product>()));
       System.out.println(RestClient.loadMarketData(new ArrayList<StockPrice>()));
       System.out.println(RestClient.loadStocks(new ArrayList<Stock>()));
    }
}
