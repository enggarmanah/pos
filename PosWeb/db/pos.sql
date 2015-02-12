# ************************************************************
# Sequel Pro SQL dump
# Version 4096
#
# http://www.sequelpro.com/
# http://code.google.com/p/sequel-pro/
#
# Host: 127.0.0.1 (MySQL 5.6.22)
# Database: pos
# Generation Time: 2015-02-12 18:08:33 +0000
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
	(28,1,'38793d11-daf4-410b-8ca1-9f234e68e304','2015-02-11 17:36:09'),
	(29,1,'4b8f6fa1-80f3-4e2c-a82a-678c6cf2f5c4','2015-02-11 17:37:05'),
	(30,1,'9efb1a21-3bdc-4a79-b3cc-caba93bec554','2015-02-12 00:07:50'),
	(31,1,'03f957db-b0c6-44e8-b33d-a72b88a2ec08','2015-02-12 00:38:38'),
	(32,1,'ea6ef24a-2fd1-40b8-b009-d8ba0e957c24','2015-02-12 18:07:47');

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
	(19,1,1,'Diskon Member',15,'A','retno','2015-02-04 15:51:04','retno','2015-02-07 01:31:33'),
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

INSERT INTO `product` (`id`, `merchant_id`, `remote_id`, `product_group_id`, `name`, `type`, `price`, `pic_required`, `commision`, `promo_price`, `promo_start`, `promo_end`, `status`, `create_by`, `create_date`, `update_by`, `update_date`)
VALUES
	(15,1,14,2,'Ayam Asam Manis','P',5000,'N',NULL,NULL,NULL,NULL,'A','retno','2015-02-12 01:35:27','retno','2015-02-12 01:34:22'),
	(16,1,4,2,'Ayam Bakar Kecap Manis','P',5000,'N',NULL,NULL,NULL,NULL,'I','retno','2015-02-12 01:35:27','retno','2015-02-12 01:34:22'),
	(17,1,13,2,'Ayam Bakar Petis Udang Spesial','P',5000,'N',NULL,NULL,NULL,NULL,'A','retno','2015-02-12 01:35:27','retno','2015-02-12 01:34:22'),
	(18,1,19,2,'Ayam Bakar Spesial','P',10000,'N',NULL,NULL,NULL,NULL,'A','retno','2015-02-12 01:35:27','retno','2015-02-12 01:34:22'),
	(19,1,10,2,'Ayam Balado Sambal Pete','P',5000,'N',NULL,4500,'2014-12-15 16:00:00','2014-12-15 16:00:00','A','retno','2015-02-12 01:35:27','retno','2015-02-12 01:34:22'),
	(20,1,3,2,'Ayam Goreng Spesial Jamur','P',6500,'N',NULL,NULL,NULL,NULL,'A','retno','2015-02-12 01:35:27','retno','2015-02-12 01:34:22'),
	(21,1,2,2,'Bakso Sapi Spesial Jamur Merang','P',5000,'N',NULL,NULL,NULL,NULL,'A','retno','2015-02-12 01:35:27','retno','2015-02-12 01:34:22'),
	(22,1,7,2,'Bakso Urat Spesial','P',5000,'N',NULL,NULL,NULL,NULL,'A','retno','2015-02-12 01:35:27','retno','2015-02-12 01:34:22'),
	(23,1,15,2,'Cah Kangkung','P',7500,'N',NULL,NULL,NULL,NULL,'A','retno','2015-02-12 01:35:27','retno','2015-02-12 01:34:22'),
	(24,1,1,2,'Daging Sapi Asap Spesial','P',10000,'N',NULL,NULL,NULL,NULL,'A','retno','2015-02-12 01:35:27','retno','2015-02-12 01:34:22'),
	(25,1,20,4,'Facial AH 80','S',50000,'Y',NULL,NULL,NULL,NULL,'A','retno','2015-02-12 01:35:27','retno','2015-02-12 01:34:22'),
	(26,1,8,2,'Gado-gado','P',5000,'N',NULL,NULL,NULL,NULL,'A','retno','2015-02-12 01:35:27','retno','2015-02-12 01:34:22'),
	(27,1,18,2,'Sambal Goreng Ati','P',5000,'N',NULL,NULL,NULL,NULL,'D','retno','2015-02-12 01:35:27','retno','2015-02-12 01:34:22'),
	(28,1,12,3,'Sate Kambing','P',5000,'N',NULL,NULL,NULL,NULL,'A','retno','2015-02-12 01:35:27','retno','2015-02-12 01:34:22'),
	(29,1,5,3,'Sate Kambing Muda','P',5000,'N',NULL,NULL,NULL,NULL,'A','retno','2015-02-12 01:35:27','retno','2015-02-12 01:34:22'),
	(30,1,11,2,'Soto Ayam','P',5000,'N',NULL,NULL,NULL,NULL,'A','retno','2015-02-12 01:35:27','retno','2015-02-12 01:34:22'),
	(31,1,16,3,'Tongseng Kambing','P',25000,'N',NULL,NULL,NULL,NULL,'A','retno','2015-02-12 01:35:27','retno','2015-02-12 01:34:22'),
	(32,1,6,1,'Udang Bakar Madu','P',15000,'N',NULL,NULL,NULL,NULL,'A','retno','2015-02-12 01:35:27','retno','2015-02-12 01:34:22');

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
	(14,1,5,'Menu Sapi','D','retno','2015-02-07 05:10:31','retno','2015-02-07 05:15:29');

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
  `quantity` int(11) DEFAULT NULL,
  `employee_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `transaction_item` WRITE;
/*!40000 ALTER TABLE `transaction_item` DISABLE KEYS */;

INSERT INTO `transaction_item` (`id`, `merchant_id`, `remote_id`, `transaction_id`, `product_id`, `product_name`, `product_type`, `price`, `quantity`, `employee_id`)
VALUES
	(1,1,1,1,19,'Ayam Bakar Spesial','P',10000,1,0),
	(2,1,2,1,15,'Cah Kangkung','P',7500,1,0),
	(3,1,3,2,6,'Udang Bakar Madu','P',15000,1,0),
	(4,1,4,2,16,'Tongseng Kambing','P',25000,1,0),
	(5,1,5,2,5,'Sate Kambing Muda','P',5000,1,0);

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
	(1,1,1,'20150213020027454','2015-02-12 18:00:27',17500,'Diskon Member',15,2625,0,0,0,0,14875,15000,125,'CASH',3,'Bram',0,NULL,NULL),
	(2,1,2,'20150213020117754','2015-02-12 18:01:18',45000,'Diskon Member',15,6750,0,0,0,0,38250,50000,11750,'CASH',3,'Bram',0,NULL,NULL);

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
