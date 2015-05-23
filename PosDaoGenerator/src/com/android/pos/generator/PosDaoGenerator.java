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
        Schema schema = new Schema(37, "com.android.pos.dao");

        configureDao(schema);

        new DaoGenerator().generateAll(schema, "../Pos/src-gen");
    }

    private static void configureDao(Schema schema) {
        
    	Entity merchant = schema.addEntity("Merchant");
        
    	merchant.addIdProperty();
        merchant.addStringProperty("name").notNull();
        merchant.addStringProperty("type");
        merchant.addStringProperty("address");
        merchant.addStringProperty("telephone");
        merchant.addStringProperty("contactName");
        merchant.addStringProperty("contactTelephone");
        merchant.addStringProperty("contactEmail");
        merchant.addStringProperty("loginId");
        merchant.addStringProperty("printerType");
        merchant.addStringProperty("printerAddress");
        merchant.addStringProperty("printerMiniFont");
        merchant.addIntProperty("printerLineSize");
        merchant.addStringProperty("printerRequired");
        merchant.addStringProperty("password");
        merchant.addDateProperty("periodStart");
        merchant.addDateProperty("periodEnd");
        merchant.addIntProperty("priceTypeCount");
        merchant.addStringProperty("priceLabel1");
        merchant.addStringProperty("priceLabel2");
        merchant.addStringProperty("priceLabel3");
        merchant.addStringProperty("discountType");
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
    	
    	product.addStringProperty("code");
    	Property productName = product.addStringProperty("name").getProperty();
    	product.addStringProperty("type");
    	product.addIntProperty("price1");
    	product.addIntProperty("price2");
    	product.addIntProperty("price3");
    	product.addIntProperty("costPrice");
    	product.addStringProperty("picRequired");
    	product.addIntProperty("commision");
    	product.addIntProperty("promoPrice");
    	product.addDateProperty("promoStart");
        product.addDateProperty("promoEnd");
    	product.addIntProperty("stock");
    	product.addIntProperty("minStock");
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
    	transactions.addStringProperty("orderType");
    	transactions.addStringProperty("orderReference");
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
        
        Property customerId = transactions.addLongProperty("customerId").getProperty();
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
    	transactionItem.addIntProperty("commision");
        transactionItem.addStringProperty("remarks");
    	
    	Property employeeId = transactionItem.addLongProperty("employeeId").getProperty();
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
        
        Entity orders = schema.addEntity("Orders");
    	
        orders.addIdProperty();
    	
    	merchantId = orders.addLongProperty("merchantId").notNull().getProperty();
    	orders.addToOne(merchant, merchantId);
    	
    	orders.addStringProperty("orderNo");
    	trxDate = orders.addDateProperty("orderDate").notNull().getProperty();
    	orders.addStringProperty("orderType");
    	orders.addStringProperty("orderReference");
    	orders.addStringProperty("customerName");
        orders.addStringProperty("status");
    	
    	Entity orderItem = schema.addEntity("OrderItem");
    	
        orderItem.addIdProperty();
    	
        merchantId = orderItem.addLongProperty("merchantId").notNull().getProperty();
        orderItem.addToOne(merchant, merchantId);
        
    	Property orderId = orderItem.addLongProperty("orderId").notNull().getProperty();
    	orderItem.addToOne(orders, orderId);
    	
    	orderItem.addStringProperty("orderNo");
    	
    	productId = orderItem.addLongProperty("productId").notNull().getProperty();
    	orderItem.addToOne(product, productId);
    	
    	productName = orderItem.addStringProperty("productName").getProperty();
    	orderItem.addIntProperty("quantity");
    	
    	orderItem.addStringProperty("remarks");
    	
    	ToMany orderToItem = orders.addToMany(orderItem, orderId);
        orderToItem.orderAsc(productName);
        
        Entity supplier = schema.addEntity("Supplier");
    	
        supplier.addIdProperty();
    	
    	merchantId = supplier.addLongProperty("merchantId").notNull().getProperty();
    	supplier.addToOne(merchant, merchantId);
    	
    	supplier.addStringProperty("name");
    	supplier.addStringProperty("telephone");
    	supplier.addStringProperty("address");
    	supplier.addStringProperty("picName");
    	supplier.addStringProperty("picTelephone");
    	supplier.addStringProperty("remarks");
    	
    	supplier.addStringProperty("status");
    	supplier.addStringProperty("uploadStatus");
    	supplier.addStringProperty("createBy");
    	supplier.addDateProperty("createDate");
    	supplier.addStringProperty("updateBy");
    	supplier.addDateProperty("updateDate");
    	
    	Entity bills = schema.addEntity("Bills");
    	
    	bills.addIdProperty();
    	
    	merchantId = bills.addLongProperty("merchantId").notNull().getProperty();
    	bills.addToOne(merchant, merchantId);
    	
    	bills.addStringProperty("billReferenceNo");
    	bills.addStringProperty("billType");
    	bills.addDateProperty("billDate");
    	bills.addDateProperty("billDueDate");
    	bills.addIntProperty("billAmount");
    	bills.addDateProperty("paymentDate");
    	bills.addIntProperty("payment");
    	
    	Property supplierId = bills.addLongProperty("supplierId").getProperty();
    	bills.addToOne(supplier, supplierId);
    	
    	bills.addStringProperty("supplierName");
    	
    	bills.addDateProperty("deliveryDate");
    	bills.addStringProperty("remarks");
    	
        bills.addStringProperty("status");
    	bills.addStringProperty("uploadStatus");
    	bills.addStringProperty("createBy");
    	bills.addDateProperty("createDate");
    	bills.addStringProperty("updateBy");
    	bills.addDateProperty("updateDate");
    	
    	Entity inventory = schema.addEntity("Inventory");
    	
    	inventory.addIdProperty();
    	
    	merchantId = inventory.addLongProperty("merchantId").notNull().getProperty();
    	inventory.addToOne(merchant, merchantId);
    	
    	productId = inventory.addLongProperty("productId").notNull().getProperty();
    	inventory.addToOne(product, productId);
    	
    	inventory.addStringProperty("productName");
    	inventory.addIntProperty("productCostPrice");
    	inventory.addStringProperty("quantityStr");
    	inventory.addIntProperty("quantity");
    	
    	Property billsId = inventory.addLongProperty("billId").getProperty();
    	inventory.addToOne(bills, billsId);
    	
    	inventory.addStringProperty("billReferenceNo");
    	
    	supplierId = inventory.addLongProperty("supplierId").getProperty();
    	inventory.addToOne(supplier, supplierId);
    	
    	inventory.addStringProperty("supplierName");
    	
    	inventory.addDateProperty("deliveryDate");
    	inventory.addStringProperty("remarks");
    	
    	inventory.addStringProperty("status");
    	inventory.addStringProperty("uploadStatus");
    	inventory.addStringProperty("createBy");
    	inventory.addDateProperty("createDate");
    	inventory.addStringProperty("updateBy");
    	inventory.addDateProperty("updateDate");
    	
    	Entity merchantAccess = schema.addEntity("MerchantAccess");
    	
    	Property merchantAccessId = merchantAccess.addIdProperty().getProperty();
    	
    	merchantId = merchantAccess.addLongProperty("merchantId").notNull().getProperty();
    	merchantAccess.addToOne(merchant, merchantId);
    	
    	merchantAccess.addStringProperty("name");
    	merchantAccess.addStringProperty("code");
    	
    	merchantAccess.addStringProperty("status");
    	merchantAccess.addStringProperty("uploadStatus");
    	merchantAccess.addStringProperty("createBy");
    	merchantAccess.addDateProperty("createDate");
    	merchantAccess.addStringProperty("updateBy");
    	merchantAccess.addDateProperty("updateDate");
    	
    	ToMany merchantToMerchantAccess = merchant.addToMany(merchantAccess, merchantId);
    	merchantToMerchantAccess.orderAsc(merchantAccessId);
    	
    	Entity userAccess = schema.addEntity("UserAccess");
    	
    	Property userAccessId = userAccess.addIdProperty().getProperty();
    	
    	merchantId = userAccess.addLongProperty("merchantId").notNull().getProperty();
    	userAccess.addToOne(merchant, merchantId);
    	
    	Property userId = userAccess.addLongProperty("userId").notNull().getProperty();
    	userAccess.addToOne(user, userId);
    	
    	userAccess.addStringProperty("name");
    	userAccess.addStringProperty("code");
    	
    	userAccess.addStringProperty("status");
    	userAccess.addStringProperty("uploadStatus");
    	userAccess.addStringProperty("createBy");
    	userAccess.addDateProperty("createDate");
    	userAccess.addStringProperty("updateBy");
    	userAccess.addDateProperty("updateDate");
    	
    	ToMany userToUserAccess = user.addToMany(userAccess, userId);
    	userToUserAccess.orderAsc(userAccessId);
    }
}
