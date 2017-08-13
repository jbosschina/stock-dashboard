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
public class Product implements Serializable {

    private static final long serialVersionUID = 7415754178658398903L;
    private Integer id;
    private String symbol;
    private String company_name;
    
    public Product() {
    }
    
    public Product(Integer id, String symbol, String company_name) {
        super();
        this.id = id;
        this.symbol = symbol;
        this.company_name = company_name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    @Override
    public String toString() {
        return "[id=" + id + ", symbol=" + symbol + ", company_name=" + company_name + "]";
    }
}
