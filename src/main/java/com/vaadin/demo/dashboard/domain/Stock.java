/**
 *  Copyright 2005-2017 Red Hat, Inc.
 *
 *  Red Hat licenses this file to you under the Apache License, version
 *  2.0 (the "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 *  implied.  See the License for the specific language governing
 *  permissions and limitations under the License.
 */
package com.vaadin.demo.dashboard.domain;

import java.io.Serializable;

/**
 * @author Kylin Soong
 */
public class Stock implements Serializable {

 
    private static final long serialVersionUID = -1571198948194151785L;
    private Integer product_id;
    private String symbol;
    private Double price;
    private String company_name;
    
    public Stock() {  
    }
    
    public Stock(Integer product_id, String symbol, Double price, String company_name) {
        super();
        this.product_id = product_id;
        this.symbol = symbol;
        this.price = price;
        this.company_name = company_name;
    }

    public Integer getProduct_id() {
        return product_id;
    }

    public void setProduct_id(Integer product_id) {
        this.product_id = product_id;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    @Override
    public String toString() {
        return "[product_id=" + product_id + ", symbol=" + symbol + ", price=" + price + ", company_name=" + company_name + "]";
    }
    
    
}
