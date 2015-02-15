# ************************************************************
# Sequel Pro SQL dump
# Version 4096
#
# http://www.sequelpro.com/
# http://code.google.com/p/sequel-pro/
#
# Host: 127.0.0.1 (MySQL 5.6.22)
# Database: pos
# Generation Time: 2015-02-15 18:11:34 +0000
# ************************************************************


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


# Dump of table customer
# ------------------------------------------------------------

DROP TABLE IF EXISTS `customer`;

CREATE TABLE `customer` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `merchant_id` int(11) DEFAULT NULL,
  `remote_id` int(11) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  `telephone` varchar(50) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `email_status` varchar(1) DEFAULT NULL,
  `address` varchar(50) DEFAULT NULL,
  `status` varchar(1) DEFAULT NULL,
  `create_by` varchar(50) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `update_by` varchar(50) DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `customer` WRITE;
/*!40000 ALTER TABLE `customer` DISABLE KEYS */;

INSERT INTO `customer` (`id`, `merchant_id`, `remote_id`, `name`, `telephone`, `email`, `email_status`, `address`, `status`, `create_by`, `create_date`, `update_by`, `update_date`)
VALUES
	(27,1,2,'Amelia Kurniawan','081215643322','','N','','A','retno','2015-02-07 11:19:55','retno','2015-02-07 11:19:55'),
	(28,1,1,'Anastasia Bello','08121222119','anastasia@yahoo. com','N','','D','retno','2015-02-07 11:37:04','retno','2015-02-07 11:43:28'),
	(29,1,3,'Jihan Mutia','081234322243','jihan.mutia@yahoo.com','N','','A','retno','2015-02-07 11:20:01','retno','2015-02-07 11:20:01'),
	(30,1,4,'Jasmine Anita','','','N','','A','retno','2015-02-07 11:20:57','retno','2015-02-07 11:20:57');

/*!40000 ALTER TABLE `customer` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table device
# ------------------------------------------------------------

DROP TABLE IF EXISTS `device`;

CREATE TABLE `device` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `merchant_id` int(11) DEFAULT NULL,
  `uuid` varchar(50) DEFAULT NULL,
  `last_sync_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `merchant_id_uuid` (`merchant_id`,`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `device` WRITE;
/*!40000 ALTER TABLE `device` DISABLE KEYS */;

INSERT INTO `device` (`id`, `merchant_id`, `uuid`, `last_sync_date`)
VALUES
	(49,1,'d827f0bc-ce62-4da2-a8b4-07c724312da1','2015-02-15 05:48:49'),
	(50,1,'132c0966-cc51-407d-b388-c6e498f260e7','2015-02-15 05:57:17'),
	(51,1,'03f957db-b0c6-44e8-b33d-a72b88a2ec08','2015-02-15 05:55:01');

/*!40000 ALTER TABLE `device` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table discount
# ------------------------------------------------------------

DROP TABLE IF EXISTS `discount`;

CREATE TABLE `discount` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `merchant_id` int(11) DEFAULT NULL,
  `remote_id` int(11) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  `percentage` int(11) DEFAULT NULL,
  `status` varchar(50) DEFAULT NULL,
  `create_by` varchar(50) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `update_by` varchar(50) DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `discount` WRITE;
/*!40000 ALTER TABLE `discount` DISABLE KEYS */;

INSERT INTO `discount` (`id`, `merchant_id`, `remote_id`, `name`, `percentage`, `status`, `create_by`, `create_date`, `update_by`, `update_date`)
VALUES
	(17,1,3,'Diskon Family',100,'A','retno','2015-02-04 15:50:56','retno','2015-02-04 15:50:56'),
	(18,1,2,'Diskon Kerabat',20,'A','retno','2015-02-04 15:51:00','retno','2015-02-04 15:51:00'),
	(19,1,1,'Diskon Member',10,'A','retno','2015-02-04 15:51:04','bram','2015-02-13 15:23:17'),
	(20,1,4,'Diskon Nominal',5,'D','radix','2015-02-04 15:51:04','radix','2015-02-07 01:50:48');

/*!40000 ALTER TABLE `discount` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table employee
# ------------------------------------------------------------

DROP TABLE IF EXISTS `employee`;

CREATE TABLE `employee` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `merchant_id` int(11) DEFAULT NULL,
  `remote_id` int(11) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  `telephone` varchar(50) DEFAULT NULL,
  `address` varchar(50) DEFAULT NULL,
  `status` varchar(1) DEFAULT NULL,
  `create_by` varchar(50) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `update_by` varchar(50) DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `employee` WRITE;
/*!40000 ALTER TABLE `employee` DISABLE KEYS */;

INSERT INTO `employee` (`id`, `merchant_id`, `remote_id`, `name`, `telephone`, `address`, `status`, `create_by`, `create_date`, `update_by`, `update_date`)
VALUES
	(23,1,1,'Budi Setiawan','','','A','retno','2015-02-06 16:52:56','retno','2015-02-07 04:23:21'),
	(24,1,2,'Yani','','','D','retno','2015-02-06 16:55:23','retno','2015-02-07 04:20:43'),
	(25,1,3,'Yulis Tanoto','','','A','retno','2015-02-06 16:55:27','retno','2015-02-07 04:24:36'),
	(26,1,4,'Tulus Prasojo','','','A','retno','2015-02-07 04:06:33','retno','2015-02-07 04:24:14');

/*!40000 ALTER TABLE `employee` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table merchant
# ------------------------------------------------------------

DROP TABLE IF EXISTS `merchant`;

CREATE TABLE `merchant` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `remote_id` int(11) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  `type` varchar(50) DEFAULT NULL,
  `address` varchar(50) DEFAULT NULL,
  `contact_name` varchar(50) DEFAULT NULL,
  `contact_telephone` varchar(50) DEFAULT NULL,
  `login_id` varchar(50) DEFAULT NULL,
  `password` varchar(50) DEFAULT NULL,
  `period_start` datetime DEFAULT NULL,
  `period_end` datetime DEFAULT NULL,
  `tax_percentage` int(11) DEFAULT NULL,
  `service_charge_percentage` int(11) DEFAULT NULL,
  `status` varchar(50) DEFAULT NULL,
  `create_by` varchar(50) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `update_by` varchar(50) DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `merchant` WRITE;
/*!40000 ALTER TABLE `merchant` DISABLE KEYS */;

INSERT INTO `merchant` (`id`, `remote_id`, `name`, `type`, `address`, `contact_name`, `contact_telephone`, `login_id`, `password`, `period_start`, `period_end`, `tax_percentage`, `service_charge_percentage`, `status`, `create_by`, `create_date`, `update_by`, `update_date`)
VALUES
	(14,1,'Warung Pojok','R','Jl. Panjaitan 50 Jakarta Pusat','Agus Mustopo','021 76541234','warjok','warjok','2014-12-22 16:00:00','2015-12-22 16:00:00',0,0,'A','retno','2015-02-07 10:41:59','retno','2015-02-07 18:45:53'),
	(15,2,'Warung Babe Gimin','R','Jl Hasanuddin 31 Jakarta Pusat','Suparman','021 756123','babegimin','12345678','2015-02-06 16:00:00','2016-02-06 16:00:00',0,0,'A','retno','2015-02-07 10:41:55','retno','2015-02-07 18:45:46');

/*!40000 ALTER TABLE `merchant` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table product
# ------------------------------------------------------------

DROP TABLE IF EXISTS `product`;

CREATE TABLE `product` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `merchant_id` int(11) DEFAULT NULL,
  `remote_id` int(11) DEFAULT NULL,
  `product_group_id` int(11) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  `type` varchar(50) DEFAULT NULL,
  `price` int(11) DEFAULT NULL,
  `cost_price` int(11) DEFAULT NULL,
  `pic_required` varchar(50) DEFAULT NULL,
  `commision` int(11) DEFAULT NULL,
  `promo_price` int(11) DEFAULT NULL,
  `promo_start` datetime DEFAULT NULL,
  `promo_end` datetime DEFAULT NULL,
  `status` varchar(50) DEFAULT NULL,
  `create_by` varchar(50) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `update_by` varchar(50) DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `product` WRITE;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;

INSERT INTO `product` (`id`, `merchant_id`, `remote_id`, `product_group_id`, `name`, `type`, `price`, `cost_price`, `pic_required`, `commision`, `promo_price`, `promo_start`, `promo_end`, `status`, `create_by`, `create_date`, `update_by`, `update_date`)
VALUES
	(15,1,14,2,'Ayam Asam Manis','P',7500,6000,'N',NULL,NULL,NULL,NULL,'A','retno','2015-02-12 01:35:27','retno','2015-02-14 10:41:40'),
	(16,1,4,2,'Ayam Bakar Kecap Manis','P',10000,6000,'N',NULL,NULL,NULL,NULL,'I','retno','2015-02-12 01:35:27','retno','2015-02-14 10:42:28'),
	(17,1,13,2,'Ayam Bakar Petis Udang Spesial','P',7500,5000,'N',NULL,NULL,NULL,NULL,'A','retno','2015-02-12 01:35:27','retno','2015-02-14 10:46:10'),
	(18,1,19,2,'Ayam Bakar Spesial','P',10000,NULL,'N',NULL,NULL,NULL,NULL,'A','retno','2015-02-12 01:35:27','retno','2015-02-12 01:34:22'),
	(19,1,10,2,'Ayam Balado Sambal Pete','P',6500,NULL,'N',NULL,4500,'2014-12-15 16:00:00','2014-12-15 16:00:00','A','retno','2015-02-12 01:35:27','retno','2015-02-12 01:34:22'),
	(20,1,3,2,'Ayam Goreng Spesial Jamur','P',6500,NULL,'N',NULL,NULL,NULL,NULL,'A','retno','2015-02-12 01:35:27','retno','2015-02-12 01:34:22'),
	(21,1,2,5,'Bakso Sapi Spesial Jamur Merang','P',8000,NULL,'N',NULL,NULL,NULL,NULL,'A','retno','2015-02-12 01:35:27','bram','2015-02-13 15:15:29'),
	(22,1,7,5,'Bakso Urat Spesial','P',8000,NULL,'N',NULL,NULL,NULL,NULL,'A','retno','2015-02-12 01:35:27','bram','2015-02-13 15:15:34'),
	(23,1,15,2,'Cah Kangkung','P',7500,NULL,'N',NULL,NULL,NULL,NULL,'A','retno','2015-02-12 01:35:27','retno','2015-02-12 01:34:22'),
	(24,1,1,5,'Daging Sapi Asap Spesial','P',10000,NULL,'N',NULL,NULL,NULL,NULL,'A','retno','2015-02-12 01:35:27','bram','2015-02-13 15:15:43'),
	(25,1,20,4,'Facial AH 80','S',50000,NULL,'Y',NULL,NULL,NULL,NULL,'A','retno','2015-02-12 01:35:27','retno','2015-02-12 01:34:22'),
	(26,1,8,2,'Gado-gado','P',5000,NULL,'N',NULL,NULL,NULL,NULL,'A','retno','2015-02-12 01:35:27','retno','2015-02-12 01:34:22'),
	(27,1,18,2,'Sambal Goreng Ati','P',7500,NULL,'N',NULL,NULL,NULL,NULL,'D','retno','2015-02-12 01:35:27','retno','2015-02-12 01:34:22'),
	(28,1,12,3,'Sate Kambing','P',17500,NULL,'N',NULL,NULL,NULL,NULL,'A','retno','2015-02-12 01:35:27','retno','2015-02-12 01:34:22'),
	(29,1,5,3,'Sate Kambing Muda','P',17500,NULL,'N',NULL,NULL,NULL,NULL,'A','retno','2015-02-12 01:35:27','retno','2015-02-12 01:34:22'),
	(30,1,11,2,'Soto Ayam','P',10000,NULL,'N',NULL,NULL,NULL,NULL,'A','retno','2015-02-12 01:35:27','retno','2015-02-12 01:34:22'),
	(31,1,16,3,'Tongseng Kambing','P',25000,NULL,'N',NULL,NULL,NULL,NULL,'A','retno','2015-02-12 01:35:27','retno','2015-02-12 01:34:22'),
	(32,1,6,1,'Udang Bakar Madu','P',15000,NULL,'N',NULL,NULL,NULL,NULL,'A','retno','2015-02-12 01:35:27','retno','2015-02-12 01:34:22');

/*!40000 ALTER TABLE `product` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table product_group
# ------------------------------------------------------------

DROP TABLE IF EXISTS `product_group`;

CREATE TABLE `product_group` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `merchant_id` int(11) DEFAULT NULL,
  `remote_id` int(11) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  `status` varchar(50) DEFAULT NULL,
  `create_by` varchar(50) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `update_by` varchar(50) DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `product_group` WRITE;
/*!40000 ALTER TABLE `product_group` DISABLE KEYS */;

INSERT INTO `product_group` (`id`, `merchant_id`, `remote_id`, `name`, `status`, `create_by`, `create_date`, `update_by`, `update_date`)
VALUES
	(2,1,2,'Menu Ayam','A','retno','2015-02-03 14:57:26','retno','2015-02-07 05:10:51'),
	(3,1,3,'Menu Kambing','A','retno','2015-02-03 14:57:31','retno','2015-02-07 05:10:51'),
	(4,1,1,'Menu Udang','A','retno','2015-02-03 14:57:35','retno','2015-02-07 05:10:51'),
	(5,1,4,'Perawatan Muka','A','retno','2015-02-03 14:57:38','retno','2015-02-07 05:10:51'),
	(14,1,5,'Menu Sapi','A','retno','2015-02-07 05:10:31','retno','2015-02-07 05:15:29');

/*!40000 ALTER TABLE `product_group` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table transaction_item
# ------------------------------------------------------------

DROP TABLE IF EXISTS `transaction_item`;

CREATE TABLE `transaction_item` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `merchant_id` int(11) DEFAULT NULL,
  `remote_id` int(11) DEFAULT NULL,
  `transaction_id` int(11) DEFAULT NULL,
  `product_id` int(11) DEFAULT NULL,
  `product_name` varchar(50) DEFAULT NULL,
  `product_type` varchar(50) DEFAULT NULL,
  `price` int(11) DEFAULT NULL,
  `cost_price` int(11) DEFAULT NULL,
  `discount` int(11) DEFAULT NULL,
  `quantity` int(11) DEFAULT NULL,
  `employee_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `transaction_item` WRITE;
/*!40000 ALTER TABLE `transaction_item` DISABLE KEYS */;

INSERT INTO `transaction_item` (`id`, `merchant_id`, `remote_id`, `transaction_id`, `product_id`, `product_name`, `product_type`, `price`, `cost_price`, `discount`, `quantity`, `employee_id`)
VALUES
	(23,1,1,1,14,'Ayam Asam Manis','P',7500,7500,0,1,0),
	(24,1,2,1,4,'Ayam Bakar Kecap Manis','P',10000,10000,0,1,0),
	(25,1,3,1,13,'Ayam Bakar Petis Udang Spesial','P',7500,7500,0,1,0),
	(26,1,4,2,5,'Sate Kambing Muda','P',17500,17500,0,1,0),
	(27,1,5,2,12,'Sate Kambing','P',17500,17500,0,1,0),
	(28,1,6,2,16,'Tongseng Kambing','P',25000,25000,0,1,0),
	(29,1,7,3,2,'Bakso Sapi Spesial Jamur Merang','P',8000,8000,0,1,0),
	(30,1,8,3,7,'Bakso Urat Spesial','P',8000,8000,0,3,0),
	(31,1,9,4,16,'Tongseng Kambing','P',25000,25000,0,1,0),
	(32,1,10,4,5,'Sate Kambing Muda','P',17500,17500,0,2,0),
	(33,1,11,4,6,'Udang Bakar Madu','P',15000,15000,0,2,0),
	(34,1,12,5,1,'Daging Sapi Asap Spesial','P',10000,10000,0,1,0),
	(35,1,13,5,2,'Bakso Sapi Spesial Jamur Merang','P',8000,8000,0,1,0),
	(36,1,14,5,7,'Bakso Urat Spesial','P',8000,8000,0,1,0),
	(37,1,15,6,7,'Bakso Urat Spesial','P',8000,8000,0,1,0),
	(38,1,16,6,13,'Ayam Bakar Petis Udang Spesial','P',7500,7500,0,1,0),
	(39,1,17,6,8,'Gado-gado','P',5000,5000,0,1,0),
	(40,1,18,7,14,'Ayam Asam Manis','P',7500,7500,0,1,0),
	(41,1,19,7,4,'Ayam Bakar Kecap Manis','P',10000,10000,0,1,0),
	(42,1,20,7,13,'Ayam Bakar Petis Udang Spesial','P',7500,7500,0,1,0),
	(43,1,21,8,13,'Ayam Bakar Petis Udang Spesial','P',7500,7500,0,1,0),
	(44,1,22,8,19,'Ayam Bakar Spesial','P',10000,10000,0,1,0),
	(45,1,23,9,5,'Sate Kambing Muda','P',17500,17500,0,1,0),
	(46,1,24,9,16,'Tongseng Kambing','P',25000,25000,0,1,0),
	(47,1,25,10,14,'Ayam Asam Manis','P',7500,7500,0,1,0),
	(48,1,26,11,16,'Tongseng Kambing','P',25000,25000,0,2,0),
	(49,1,27,11,5,'Sate Kambing Muda','P',17500,17500,0,1,0),
	(50,1,28,12,13,'Ayam Bakar Petis Udang Spesial','P',7500,7500,0,1,0),
	(51,1,29,12,2,'Bakso Sapi Spesial Jamur Merang','P',8000,8000,0,1,0),
	(52,1,30,13,19,'Ayam Bakar Spesial','P',10000,10000,0,1,0),
	(53,1,31,13,10,'Ayam Balado Sambal Pete','P',6500,6500,0,1,0),
	(54,1,32,14,4,'Ayam Bakar Kecap Manis','P',10000,6000,1000,1,0),
	(55,1,33,14,19,'Ayam Bakar Spesial','P',10000,10000,1000,1,0),
	(56,1,34,15,14,'Ayam Asam Manis','P',7500,6000,0,1,0),
	(57,1,35,15,3,'Ayam Goreng Spesial Jamur','P',6500,6500,0,1,0),
	(58,1,36,16,4,'Ayam Bakar Kecap Manis','P',10000,6000,0,1,0),
	(59,1,37,16,3,'Ayam Goreng Spesial Jamur','P',6500,6500,0,1,0),
	(60,1,43,18,19,'Ayam Bakar Spesial','P',10000,NULL,NULL,1,0),
	(61,1,44,18,1,'Daging Sapi Asap Spesial','P',10000,NULL,NULL,2,0),
	(62,1,45,19,5,'Sate Kambing Muda','P',17500,17500,1750,1,0),
	(63,1,46,19,16,'Tongseng Kambing','P',25000,25000,2500,1,0),
	(64,1,47,19,19,'Ayam Bakar Spesial','P',10000,10000,1000,1,0);

/*!40000 ALTER TABLE `transaction_item` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table transactions
# ------------------------------------------------------------

DROP TABLE IF EXISTS `transactions`;

CREATE TABLE `transactions` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `merchant_id` int(11) DEFAULT NULL,
  `remote_id` int(11) DEFAULT NULL,
  `transaction_no` varchar(50) DEFAULT NULL,
  `transaction_date` datetime DEFAULT NULL,
  `bill_amount` int(11) DEFAULT NULL,
  `discount_name` varchar(50) DEFAULT NULL,
  `discount_percentage` int(11) DEFAULT NULL,
  `discount_amount` int(11) DEFAULT NULL,
  `tax_percentage` int(11) DEFAULT NULL,
  `tax_amount` int(11) DEFAULT NULL,
  `service_charge_percentage` int(11) DEFAULT NULL,
  `service_charge_amount` int(11) DEFAULT NULL,
  `total_amount` int(11) DEFAULT NULL,
  `payment_amount` int(11) DEFAULT NULL,
  `return_amount` int(11) DEFAULT NULL,
  `payment_type` varchar(50) DEFAULT NULL,
  `cashier_id` int(11) DEFAULT NULL,
  `cashier_name` varchar(50) DEFAULT NULL,
  `customer_id` int(11) DEFAULT NULL,
  `customer_name` varchar(50) DEFAULT NULL,
  `status` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `transactions` WRITE;
/*!40000 ALTER TABLE `transactions` DISABLE KEYS */;

INSERT INTO `transactions` (`id`, `merchant_id`, `remote_id`, `transaction_no`, `transaction_date`, `bill_amount`, `discount_name`, `discount_percentage`, `discount_amount`, `tax_percentage`, `tax_amount`, `service_charge_percentage`, `service_charge_amount`, `total_amount`, `payment_amount`, `return_amount`, `payment_type`, `cashier_id`, `cashier_name`, `customer_id`, `customer_name`, `status`)
VALUES
	(11,1,1,'20150214002759248','2015-01-01 16:27:59',25000,'Diskon Member',10,2500,0,0,0,0,22500,30000,7500,'CASH',3,'Bram',0,NULL,NULL),
	(12,1,2,'20150214002819591','2015-01-03 16:28:20',60000,'Diskon Member',10,6000,0,0,0,0,54000,60000,6000,'CASH',3,'Bram',0,NULL,NULL),
	(13,1,3,'20150214002833559','2015-02-01 16:28:34',32000,'Diskon Member',10,3200,0,0,0,0,28800,60000,31200,'CASH',3,'Bram',0,NULL,NULL),
	(14,1,4,'20150214002901724','2015-02-03 16:29:02',90000,'Diskon Member',10,9000,0,0,0,0,81000,85000,4000,'CASH',3,'Bram',0,NULL,NULL),
	(15,1,5,'20150214002915795','2015-02-10 16:29:16',26000,'Diskon Member',10,2600,0,0,0,0,23400,85000,61600,'CASH',3,'Bram',0,NULL,NULL),
	(16,1,6,'20150214003003463','2015-02-13 16:30:03',20500,'Diskon Member',10,2050,0,0,0,0,18450,85000,66550,'CASH',3,'Bram',0,NULL,NULL),
	(17,1,7,'20150214124000580','2015-02-14 04:40:01',25000,'Diskon Member',10,2500,0,0,0,0,22500,25000,2500,'CASH',1,'Retno',0,NULL,NULL),
	(18,1,8,'20150214124022880','2015-02-14 04:40:23',17500,'Diskon Member',10,1750,0,0,0,0,15750,25000,9250,'CASH',1,'Retno',0,NULL,NULL),
	(19,1,9,'20150214124106280','2015-02-14 04:41:06',42500,'Diskon Member',10,4250,0,0,0,0,38250,50000,11750,'CASH',1,'Retno',0,NULL,NULL),
	(20,1,10,'20150214124423282','2015-02-14 04:44:23',7500,NULL,NULL,NULL,0,0,0,0,7500,10000,2500,'CASH',1,'Retno',0,NULL,NULL),
	(21,1,11,'20150214124458147','2015-02-14 04:44:58',67500,'Diskon Member',10,6750,0,0,0,0,60750,70000,9250,'CASH',1,'Retno',0,NULL,NULL),
	(22,1,12,'20150214190951989','2015-02-14 11:09:52',15500,NULL,NULL,NULL,0,0,0,0,15500,16000,500,'CASH',1,'Retno',0,NULL,NULL),
	(23,1,13,'20150214191509054','2015-02-14 11:15:09',16500,NULL,NULL,NULL,0,0,0,0,16500,17000,500,'CASH',1,'Retno',0,NULL,NULL),
	(24,1,14,'20150214193142522','2015-02-14 11:31:43',20000,'Diskon Member',10,2000,0,0,0,0,18000,20000,2000,'CASH',1,'Retno',0,NULL,NULL),
	(25,1,15,'20150214193242988','2015-02-14 11:32:43',14000,NULL,NULL,NULL,0,0,0,0,14000,15000,1000,'CASH',1,'Retno',0,NULL,NULL),
	(26,1,16,'20150214193422189','2015-02-14 11:34:22',16500,NULL,NULL,NULL,0,0,0,0,16500,20000,3500,'CASH',1,'Retno',0,NULL,NULL),
	(27,1,18,'20150212123840545','2015-02-12 04:38:41',30000,'Diskon Member',15,4500,0,0,0,0,25500,0,-25500,'CASH',3,'Bram',0,NULL,NULL),
	(28,1,19,'20150215135422865','2015-02-15 05:54:23',52500,'Diskon Member',10,5250,0,0,0,0,47250,100000,52750,'CASH',1,'Retno',0,NULL,NULL);

/*!40000 ALTER TABLE `transactions` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table user
# ------------------------------------------------------------

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `merchant_id` int(11) DEFAULT NULL,
  `remote_id` int(11) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  `user_id` varchar(50) DEFAULT NULL,
  `password` varchar(50) DEFAULT NULL,
  `role` varchar(50) DEFAULT NULL,
  `status` varchar(50) DEFAULT NULL,
  `create_by` varchar(50) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `update_by` varchar(50) DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;

INSERT INTO `user` (`id`, `merchant_id`, `remote_id`, `name`, `user_id`, `password`, `role`, `status`, `create_by`, `create_date`, `update_by`, `update_date`)
VALUES
	(15,1,2,'Niken','niken','123456','A','A','retno','2015-02-07 16:58:06','retno','2015-02-07 16:58:06'),
	(16,1,1,'Retno','retno','123456','C','A','retno','2015-02-07 16:58:10','retno','2015-02-07 16:58:10'),
	(17,1,3,'Bram','bram','123456','C','A','retno','2015-02-07 16:58:30','retno','2015-02-07 16:58:30');

/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;



/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
