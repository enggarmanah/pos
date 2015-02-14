/*
 * Copyright (C) 2011 Markus Junginger, greenrobot (http://greenrobot.de)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.android.pos.generator;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Property;
import de.greenrobot.daogenerator.Schema;
import de.greenrobot.daogenerator.ToMany;

/**
 * Generates entities and DAOs for the example project DaoExample.
 * 
 * Run it as a Java application (not Android).
 * 
 * @author Markus
 */
public class PosDaoGenerator {

    public static void main(String[] args) throws Exception {
        Schema schema = new Schema(16, "com.android.pos.dao");

        configureDao(schema);

        new DaoGenerator().generateAll(schema, "../Pos/src-gen");
    }

    private static void configureDao(Schema schema) {
        
    	Entity merchant = schema.addEntity("Merchant");
        
    	merchant.addIdProperty();
        merchant.addStringProperty("name").notNull();
        merchant.addStringProperty("type");
        merchant.addStringProperty("address");
        merchant.addStringProperty("contactName");
        merchant.addStringProperty("contactTelephone");
        merchant.addStringProperty("loginId");
        merchant.addStringProperty("password");
        merchant.addDateProperty("periodStart");
        merchant.addDateProperty("periodEnd");
        merchant.addIntProperty("taxPercentage");
        merchant.addIntProperty("serviceChargePercentage");
        merchant.addBooleanProperty("isLogin");
        merchant.addStringProperty("status");
        merchant.addStringProperty("uploadStatus");
        merchant.addStringProperty("createBy");
        merchant.addDateProperty("createDate");
        merchant.addStringProperty("updateBy");
        merchant.addDateProperty("updateDate");
        
        Entity productGroup = schema.addEntity("ProductGroup");
        
        productGroup.addIdProperty();
        
        Property merchantId = productGroup.addLongProperty("merchantId").notNull().getProperty();
        productGroup.addToOne(merchant, merchantId);
        
        productGroup.addStringProperty("name").notNull();
        productGroup.addStringProperty("status");
        productGroup.addStringProperty("uploadStatus");
        productGroup.addStringProperty("createBy");
        productGroup.addDateProperty("createDate");
        productGroup.addStringProperty("updateBy");
        productGroup.addDateProperty("updateDate");
        
        Entity product = schema.addEntity("Product");
    	
    	product.addIdProperty();
    	
    	merchantId = product.addLongProperty("merchantId").notNull().getProperty();
    	product.addToOne(merchant, merchantId);
    	
    	Property productGroupId = product.addLongProperty("productGroupId").getProperty();
    	product.addToOne(productGroup, productGroupId);
    	
    	Property productName = product.addStringProperty("name").getProperty();
    	product.addStringProperty("type");
    	product.addIntProperty("price");
    	product.addIntProperty("costPrice");
    	product.addStringProperty("picRequired");
    	product.addIntProperty("commision");
    	product.addIntProperty("promoPrice");
    	product.addDateProperty("promoStart");
        product.addDateProperty("promoEnd");
        product.addStringProperty("status");
        product.addStringProperty("uploadStatus");
        product.addStringProperty("createBy");
        product.addDateProperty("createDate");
        product.addStringProperty("updateBy");
        product.addDateProperty("updateDate");
        
        ToMany merchantToProduct = merchant.addToMany(product, merchantId);
        merchantToProduct.orderAsc(productName);
        
        ToMany productGroupToProduct = productGroup.addToMany(product, productGroupId);
        productGroupToProduct.orderAsc(productName);
        
        Entity customer = schema.addEntity("Customer");
    	
    	customer.addIdProperty();
    	
    	merchantId = customer.addLongProperty("merchantId").notNull().getProperty();
    	customer.addToOne(merchant, merchantId);
    	
    	Property customerName = customer.addStringProperty("name").getProperty();
    	customer.addStringProperty("telephone");
    	customer.addStringProperty("email");
    	customer.addStringProperty("emailStatus");
    	customer.addStringProperty("address");
        customer.addStringProperty("status");
        customer.addStringProperty("uploadStatus");
        customer.addStringProperty("createBy");
        customer.addDateProperty("createDate");
        customer.addStringProperty("updateBy");
        customer.addDateProperty("updateDate");
        
        ToMany merchantToCustomer = merchant.addToMany(customer, merchantId);
        merchantToCustomer.orderAsc(customerName);
        
        Entity employee = schema.addEntity("Employee");
    	
        employee.addIdProperty();
    	
    	merchantId = employee.addLongProperty("merchantId").notNull().getProperty();
    	employee.addToOne(merchant, merchantId);
    	
    	Property employeeName = employee.addStringProperty("name").getProperty();
    	employee.addStringProperty("telephone");
    	employee.addStringProperty("address");
    	employee.addStringProperty("status");
    	employee.addStringProperty("uploadStatus");
    	employee.addStringProperty("createBy");
    	employee.addDateProperty("createDate");
    	employee.addStringProperty("updateBy");
    	employee.addDateProperty("updateDate");
        
        ToMany merchantToEmployee = merchant.addToMany(employee, merchantId);
        merchantToEmployee.orderAsc(employeeName);
        
        Entity user = schema.addEntity("User");
    	
        user.addIdProperty();
    	
    	merchantId = user.addLongProperty("merchantId").notNull().getProperty();
    	user.addToOne(merchant, merchantId);
    	
    	Property userName = user.addStringProperty("name").getProperty();
    	user.addStringProperty("userId");
    	user.addStringProperty("password");
    	user.addStringProperty("role");
        user.addStringProperty("status");
        user.addStringProperty("uploadStatus");
        user.addStringProperty("createBy");
        user.addDateProperty("createDate");
        user.addStringProperty("updateBy");
        user.addDateProperty("updateDate");
        
        ToMany merchantToUser = merchant.addToMany(user, merchantId);
        merchantToUser.orderAsc(userName);
        
        Entity transactions = schema.addEntity("Transactions");
    	
        transactions.addIdProperty();
    	
    	merchantId = transactions.addLongProperty("merchantId").notNull().getProperty();
    	transactions.addToOne(merchant, merchantId);
    	
    	transactions.addStringProperty("transactionNo");
    	Property trxDate = transactions.addDateProperty("transactionDate").notNull().getProperty();
        transactions.addIntProperty("billAmount");
        transactions.addStringProperty("discountName");
        transactions.addIntProperty("discountPercentage");
        transactions.addIntProperty("discountAmount");
        transactions.addIntProperty("taxPercentage");
        transactions.addIntProperty("taxAmount");
        transactions.addIntProperty("serviceChargePercentage");
        transactions.addIntProperty("serviceChargeAmount");
        transactions.addIntProperty("totalAmount");
        transactions.addIntProperty("paymentAmount");
        transactions.addIntProperty("returnAmount");
        transactions.addStringProperty("paymentType");
        
        Property cashierId = transactions.addLongProperty("cashierId").notNull().getProperty();
        transactions.addToOne(user, cashierId);
        
        transactions.addStringProperty("cashierName");
        
        Property customerId = transactions.addLongProperty("customerId").notNull().getProperty();
        transactions.addToOne(customer, customerId);
        
        transactions.addStringProperty("customerName");
        transactions.addStringProperty("uploadStatus");
        transactions.addStringProperty("status");
        
        ToMany cashierToTransaction = user.addToMany(transactions, cashierId);
        cashierToTransaction.orderAsc(trxDate);
        
        ToMany customerToTransaction = customer.addToMany(transactions, customerId);
        customerToTransaction.orderAsc(trxDate);
        
        Entity transactionItem = schema.addEntity("TransactionItem");
    	
        Property transactionItemId = transactionItem.addIdProperty().getProperty();
    	
        merchantId = transactionItem.addLongProperty("merchantId").notNull().getProperty();
        transactionItem.addToOne(merchant, merchantId);
        
    	Property transactionId = transactionItem.addLongProperty("transactionId").notNull().getProperty();
    	transactionItem.addToOne(transactions, transactionId);
    	
    	Property productId = transactionItem.addLongProperty("productId").notNull().getProperty();
    	transactionItem.addToOne(product, productId);
    	
    	productName = transactionItem.addStringProperty("productName").getProperty();
    	transactionItem.addStringProperty("productType");
    	transactionItem.addIntProperty("price");
    	transactionItem.addIntProperty("costPrice");
    	transactionItem.addIntProperty("discount");
    	transactionItem.addIntProperty("quantity");
    	
    	Property employeeId = transactionItem.addLongProperty("employeeId").notNull().getProperty();
    	transactionItem.addToOne(employee, employeeId);
        
        ToMany transactionToItem = transactions.addToMany(transactionItem, transactionId);
        transactionToItem.orderAsc(productName);
        
        transactionItem.addStringProperty("uploadStatus");
        
        ToMany employeeToItem = employee.addToMany(transactionItem, transactionId);
        employeeToItem.orderAsc(transactionItemId);
        
        Entity discount = schema.addEntity("Discount");
        
        discount.addIdProperty();
        
        merchantId = discount.addLongProperty("merchantId").notNull().getProperty();
        discount.addToOne(merchant, merchantId);
        
        discount.addStringProperty("name").notNull();
        discount.addIntProperty("percentage");
        discount.addIntProperty("amount");
        discount.addStringProperty("status");
        discount.addStringProperty("uploadStatus");
        discount.addStringProperty("createBy");
        discount.addDateProperty("createDate");
        discount.addStringProperty("updateBy");
        discount.addDateProperty("updateDate");
    }
}
