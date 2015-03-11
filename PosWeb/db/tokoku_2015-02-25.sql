# ************************************************************
# Sequel Pro SQL dump
# Version 4096
#
# http://www.sequelpro.com/
# http://code.google.com/p/sequel-pro/
#
# Host: 127.0.0.1 (MySQL 5.6.22)
# Database: tokoku
# Generation Time: 2015-02-25 05:41:03 +0000
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
	(57,1,'dbdbbd17-8763-4dc5-99d7-a4d57be0a233','2015-02-23 15:35:31'),
	(58,1,'5d9bcc18-8c25-4a91-b61a-ab86655200c0','2015-02-24 03:16:09'),
	(59,1,'f0a4fc7b-8fdb-405e-abb2-73b4ce871778','2015-02-24 10:40:27'),
	(60,1,'a5cd8919-04b9-4674-a02e-0f4fbb902714','2015-02-24 10:42:39'),
	(61,1,'07f4162f-cd95-48a3-9363-3f6748301b81','2015-02-24 10:47:49'),
	(62,1,'bc57b12f-136f-4475-881b-fb96ad8f9906','2015-02-24 10:52:37'),
	(63,1,'f37cac70-090d-4f8c-8ca8-69ba7c747229','2015-02-24 12:22:12'),
	(64,NULL,'0e3d9fb6-e3a7-4291-a604-18b897f01c43','2015-01-01 00:00:00'),
	(65,NULL,'f08ba314-481d-4107-9fe3-a3f3c8f550ed','2015-01-01 00:00:00'),
	(66,NULL,'7959ab36-19e0-4b4b-8e65-6d49e079c886','2015-01-01 00:00:00'),
	(67,1,'7959ab36-19e0-4b4b-8e65-6d49e079c886','2015-01-01 00:00:00'),
	(68,1,'a6f19022-41d6-4fce-98c2-94758d8c95d8','2015-02-24 13:36:26'),
	(69,NULL,'0480b502-2645-4462-99c2-8e95d1192e5a','2015-01-01 00:00:00'),
	(70,1,'0480b502-2645-4462-99c2-8e95d1192e5a','2015-01-01 00:00:00'),
	(71,3,'a829381c-be86-4b6d-9970-f497296fe18c','2015-02-24 14:20:17'),
	(72,1,'8762960d-e2e4-4ad3-8213-3e3b74b81b69','2015-02-24 16:51:34'),
	(73,1,'926f6c8b-24c0-4571-a7a3-a85d91b39b54','2015-02-24 16:52:29'),
	(74,1,'9de54ae6-e83f-42f0-9ac2-708c3a8a6290','2015-02-24 16:56:07'),
	(75,1,'d795d296-31b1-42bf-b00c-1fe9b49a3811','2015-02-24 16:56:52'),
	(76,NULL,'5069fb77-722a-4116-9e4b-7acc0a7c3107','2015-01-01 00:00:00'),
	(77,NULL,'d60d83a4-00d9-46dd-a5c1-50abbf883028','2015-01-01 00:00:00'),
	(78,1,'d60d83a4-00d9-46dd-a5c1-50abbf883028','2015-01-01 00:00:00'),
	(79,4,'d60d83a4-00d9-46dd-a5c1-50abbf883028','2015-02-24 19:00:28'),
	(80,4,'5499a18d-210f-4447-a92e-917fe043acd4','2015-02-24 19:27:15'),
	(81,4,'a829381c-be86-4b6d-9970-f497296fe18c','2015-02-24 18:45:19');

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
  `address` varchar(500) DEFAULT NULL,
  `telephone` varchar(50) DEFAULT NULL,
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

INSERT INTO `merchant` (`id`, `remote_id`, `name`, `type`, `address`, `telephone`, `contact_name`, `contact_telephone`, `login_id`, `password`, `period_start`, `period_end`, `tax_percentage`, `service_charge_percentage`, `status`, `create_by`, `create_date`, `update_by`, `update_date`)
VALUES
	(14,1,'Warung Pojok','R','Jl. Panjaitan 50 Jakarta Pusat','(021) 76541234','Agus Mustopo','021 76541234','warjok','warjok','2014-12-22 08:00:00','2015-12-22 08:00:00',0,0,'A','retno','2015-02-07 10:41:59','bram','2015-02-17 17:13:56'),
	(15,2,'Warung Babe Gimin','R','Jl Hasanuddin 31 Jakarta Pusat','','Suparman','021 756123','babegimin','babegimin','2015-02-06 16:00:00','2016-02-06 16:00:00',0,0,'A','retno','2015-02-07 10:41:55','retno','2015-02-17 18:49:01'),
	(16,3,'Warung Sate Pak Barno','R','Jl Pucang Sari 1 Laweyan Solo','(0271) 79812345','Subarno','081915431918','satebarno','satebarno','2015-02-23 16:00:00','2016-02-23 16:00:00',NULL,NULL,'A',NULL,'2015-02-24 14:07:23',NULL,'2015-02-24 14:07:23'),
	(17,4,'Mbok Rawit Semarang','R','Jl. Durian Raya Ruko Segitiga Emas Kav.8 Banyumanik Semarang','082225905581','Riena Anggraeni','081904328288','mbokrawit','1','2015-02-24 16:00:00','2030-02-24 16:00:00',0,0,'A','admin','2015-02-24 18:14:51','admin','2015-02-24 18:38:31');

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
	(32,1,6,1,'Udang Bakar Madu','P',15000,NULL,'N',NULL,NULL,NULL,NULL,'A','retno','2015-02-12 01:35:27','retno','2015-02-12 01:34:22'),
	(33,4,1,1,'Paket Ayam Kungpou','P',17500,10000,'Y',100,NULL,NULL,NULL,'A','admin','2015-02-24 17:55:56','admin','2015-02-24 17:55:56'),
	(34,4,2,1,'Paket Udang Bakar','P',17500,10000,'Y',100,NULL,NULL,NULL,'A','admin','2015-02-24 17:58:04','admin','2015-02-24 17:58:04'),
	(35,4,9,2,'Mie Pedesh Ayam Dewa','P',13500,8000,'Y',100,NULL,NULL,NULL,'A','admin','2015-02-24 18:24:04','admin','2015-02-24 18:24:04'),
	(36,4,8,2,'Mie Pedesh Ayam Galau','P',12500,7500,'Y',100,NULL,NULL,NULL,'A','admin','2015-02-24 18:23:13','admin','2015-02-24 18:23:13'),
	(37,4,7,2,'Mie Pedesh Ayam Suwung','P',12500,7500,'Y',100,NULL,NULL,NULL,'A','admin','2015-02-24 18:22:26','admin','2015-02-24 18:22:26'),
	(38,4,10,2,'Mie Pedesh Seafood Dewa','P',15000,9000,'Y',100,NULL,NULL,NULL,'A','admin','2015-02-24 18:26:10','admin','2015-02-24 18:26:10'),
	(39,4,4,1,'Paket Ayam Balado','P',17500,10000,'Y',100,NULL,NULL,NULL,'A','admin','2015-02-24 18:19:27','admin','2015-02-24 18:19:27'),
	(40,4,3,1,'Paket Cumi Bakar','P',17500,10000,'Y',100,NULL,NULL,NULL,'A','admin','2015-02-24 18:18:35','admin','2015-02-24 18:18:35'),
	(41,4,5,1,'Paket Kiddie A','P',17500,10000,'Y',100,NULL,NULL,NULL,'A','admin','2015-02-24 18:19:58','admin','2015-02-24 18:19:58'),
	(42,4,6,1,'Paket Kiddie B','P',17500,10000,'Y',100,NULL,NULL,NULL,'A','admin','2015-02-24 18:21:00','admin','2015-02-24 18:21:00');

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
	(14,1,5,'Menu Sapi','A','retno','2015-02-07 05:10:31','retno','2015-02-07 05:15:29'),
	(15,4,1,'Paket Individual','A','admin','2015-02-24 17:44:02','admin','2015-02-24 17:44:02'),
	(16,4,9,'Coffee','A','admin','2015-02-24 17:52:23','admin','2015-02-24 17:52:23'),
	(17,4,6,'Iga dan Steak','A','admin','2015-02-24 17:51:48','admin','2015-02-24 17:51:48'),
	(18,4,2,'Mie','A','admin','2015-02-24 17:50:06','admin','2015-02-24 18:51:08'),
	(19,4,5,'Minuman','A','admin','2015-02-24 17:50:58','admin','2015-02-24 17:51:18'),
	(20,4,3,'Seafood','A','admin','2015-02-24 17:50:29','admin','2015-02-24 17:50:29'),
	(21,4,4,'Snack','A','admin','2015-02-24 17:50:36','admin','2015-02-24 17:50:36'),
	(22,4,8,'Susu','A','admin','2015-02-24 17:52:11','admin','2015-02-24 17:52:11'),
	(23,4,7,'Western Food','A','admin','2015-02-24 17:52:03','admin','2015-02-24 17:52:03');

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
	(23,1,1,1,14,'Ayam Asam Manis','P',7500,3000,0,1,0),
	(24,1,2,1,4,'Ayam Bakar Kecap Manis','P',10000,5000,0,1,0),
	(25,1,3,1,13,'Ayam Bakar Petis Udang Spesial','P',7500,3000,0,1,0),
	(26,1,4,2,5,'Sate Kambing Muda','P',17500,10000,0,1,0),
	(27,1,5,2,12,'Sate Kambing','P',17500,10000,0,1,0),
	(28,1,6,2,16,'Tongseng Kambing','P',25000,12000,0,1,0),
	(29,1,7,3,2,'Bakso Sapi Spesial Jamur Merang','P',8000,4000,0,1,0),
	(30,1,8,3,7,'Bakso Urat Spesial','P',8000,4000,0,3,0),
	(31,1,9,4,16,'Tongseng Kambing','P',25000,12000,0,1,0),
	(32,1,10,4,5,'Sate Kambing Muda','P',17500,10000,0,2,0),
	(33,1,11,4,6,'Udang Bakar Madu','P',15000,8000,0,2,0),
	(34,1,12,5,1,'Daging Sapi Asap Spesial','P',10000,5000,0,1,0),
	(35,1,13,5,2,'Bakso Sapi Spesial Jamur Merang','P',8000,4000,0,1,0),
	(36,1,14,5,7,'Bakso Urat Spesial','P',8000,4000,0,1,0),
	(37,1,15,6,7,'Bakso Urat Spesial','P',8000,4000,0,1,0),
	(38,1,16,6,13,'Ayam Bakar Petis Udang Spesial','P',7500,3000,0,1,0),
	(39,1,17,6,8,'Gado-gado','P',5000,5000,0,1,0),
	(40,1,18,7,14,'Ayam Asam Manis','P',7500,3000,0,1,0),
	(41,1,19,7,4,'Ayam Bakar Kecap Manis','P',10000,5000,0,1,0),
	(42,1,20,7,13,'Ayam Bakar Petis Udang Spesial','P',7500,3000,0,1,0),
	(43,1,21,8,13,'Ayam Bakar Petis Udang Spesial','P',7500,3000,0,1,0),
	(44,1,22,8,19,'Ayam Bakar Spesial','P',10000,5000,0,1,0),
	(45,1,23,9,5,'Sate Kambing Muda','P',17500,10000,0,1,0),
	(46,1,24,9,16,'Tongseng Kambing','P',25000,12000,0,1,0),
	(47,1,25,10,14,'Ayam Asam Manis','P',7500,3000,0,1,0),
	(48,1,26,11,16,'Tongseng Kambing','P',25000,12000,0,2,0),
	(49,1,27,11,5,'Sate Kambing Muda','P',17500,10000,0,1,0),
	(50,1,28,12,13,'Ayam Bakar Petis Udang Spesial','P',7500,3000,0,1,0),
	(51,1,29,12,2,'Bakso Sapi Spesial Jamur Merang','P',8000,4000,0,1,0),
	(52,1,30,13,19,'Ayam Bakar Spesial','P',10000,5000,0,1,0),
	(53,1,31,13,10,'Ayam Balado Sambal Pete','P',6500,3000,0,1,0),
	(54,1,32,14,4,'Ayam Bakar Kecap Manis','P',10000,5000,1000,1,0),
	(55,1,33,14,19,'Ayam Bakar Spesial','P',10000,5000,1000,1,0),
	(56,1,34,15,14,'Ayam Asam Manis','P',7500,3000,0,1,0),
	(57,1,35,15,3,'Ayam Goreng Spesial Jamur','P',6500,3000,0,1,0),
	(58,1,36,16,4,'Ayam Bakar Kecap Manis','P',10000,5000,0,1,0),
	(59,1,37,16,3,'Ayam Goreng Spesial Jamur','P',6500,3000,0,1,0),
	(60,1,43,18,19,'Ayam Bakar Spesial','P',10000,5000,NULL,1,0),
	(61,1,44,18,1,'Daging Sapi Asap Spesial','P',10000,5000,NULL,2,0),
	(62,1,45,19,5,'Sate Kambing Muda','P',17500,10000,1750,1,0),
	(63,1,46,19,16,'Tongseng Kambing','P',25000,12000,2500,1,0),
	(64,1,47,19,19,'Ayam Bakar Spesial','P',10000,5000,1000,1,0),
	(65,1,48,20,14,'Ayam Asam Manis','P',7500,3000,750,1,0),
	(66,1,49,20,10,'Ayam Balado Sambal Pete','P',6500,3000,650,1,0),
	(67,1,50,20,15,'Cah Kangkung','P',7500,3000,750,2,0),
	(68,1,51,20,8,'Gado-gado','P',5000,5000,500,1,0),
	(69,1,52,20,13,'Ayam Bakar Petis Udang Spesial','P',7500,3000,750,1,0),
	(70,1,53,21,13,'Ayam Bakar Petis Udang Spesial','P',7500,5000,0,1,0),
	(71,1,54,21,10,'Ayam Balado Sambal Pete','P',6500,6500,0,1,0),
	(72,1,55,21,6,'Udang Bakar Madu','P',15000,15000,0,1,0),
	(73,1,56,22,14,'Ayam Asam Manis','P',7500,6000,750,1,0),
	(74,1,57,22,8,'Gado-gado','P',5000,5000,500,1,0),
	(75,1,58,23,14,'Ayam Asam Manis','P',7500,6000,750,1,0),
	(76,1,59,23,19,'Ayam Bakar Spesial','P',10000,10000,1000,1,0),
	(77,1,60,23,8,'Gado-gado','P',5000,5000,500,2,0),
	(78,1,61,24,10,'Ayam Balado Sambal Pete','P',6500,6500,650,1,0),
	(79,1,62,24,15,'Cah Kangkung','P',7500,7500,750,2,0),
	(80,1,63,24,7,'Bakso Urat Spesial','P',8000,8000,800,1,0),
	(81,1,64,25,4,'Ayam Bakar Kecap Manis','P',10000,6000,1000,1,0),
	(82,1,65,25,10,'Ayam Balado Sambal Pete','P',6500,6500,650,1,0),
	(83,1,66,26,4,'Ayam Bakar Kecap Manis','P',10000,6000,1000,1,0),
	(84,1,67,26,3,'Ayam Goreng Spesial Jamur','P',6500,6500,650,1,0),
	(85,1,68,27,19,'Ayam Bakar Spesial','P',10000,10000,1000,1,0),
	(86,1,69,28,19,'Ayam Bakar Spesial','P',10000,10000,1000,1,0),
	(87,1,70,29,14,'Ayam Asam Manis','P',7500,6000,750,1,0),
	(88,1,71,29,10,'Ayam Balado Sambal Pete','P',6500,6500,650,1,0),
	(89,1,72,29,15,'Cah Kangkung','P',7500,7500,750,2,0),
	(90,1,73,30,12,'Sate Kambing','P',17500,17500,0,1,0),
	(91,1,74,31,14,'Ayam Asam Manis','P',7500,6000,750,1,0),
	(92,1,75,32,4,'Ayam Bakar Kecap Manis','P',10000,6000,1000,1,0),
	(93,1,76,33,4,'Ayam Bakar Kecap Manis','P',10000,6000,0,1,0),
	(94,1,77,34,16,'Tongseng Kambing','P',25000,25000,2500,1,0),
	(95,1,78,35,14,'Ayam Asam Manis','P',7500,6000,750,1,0),
	(96,1,79,36,5,'Sate Kambing Muda','P',17500,17500,0,1,0),
	(97,1,80,37,5,'Sate Kambing Muda','P',17500,17500,0,1,0),
	(98,1,81,38,10,'Ayam Balado Sambal Pete','P',6500,6500,650,1,0),
	(99,4,1,1,1,'Paket Ayam Kungpou','P',17500,10000,0,1,0),
	(100,4,2,2,9,'Mie Pedesh Ayam Dewa','P',13500,8000,0,1,0),
	(101,4,3,3,4,'Paket Ayam Balado','P',17500,10000,0,2,0),
	(102,4,4,3,7,'Mie Pedesh Ayam Suwung','P',12500,7500,0,2,0);

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
	(28,1,19,'20150215135422865','2015-02-15 05:54:23',52500,'Diskon Member',10,5250,0,0,0,0,47250,100000,52750,'CASH',1,'Retno',0,NULL,NULL),
	(29,1,20,'20150218024739039','2015-02-17 18:47:39',41500,'Diskon Member',10,4150,0,0,0,0,37350,50000,12650,'CASH',1,'Retno',0,NULL,NULL),
	(30,1,21,'20150221114447927','2015-02-21 03:44:48',29000,NULL,NULL,NULL,0,0,0,0,29000,30000,1000,'CASH',3,'Bram',4,'Jasmine Anita',NULL),
	(31,1,22,'20150221160451819','2015-02-21 08:04:52',12500,'Diskon Member',10,1250,0,0,0,0,11250,20000,8750,'CASH',3,'Bram',2,'Amelia Kurniawan',NULL),
	(32,1,23,'20150221160728133','2015-02-21 08:07:28',27500,'Diskon Member',10,2750,0,0,0,0,24750,30000,5250,'CASH',3,'Bram',2,'Amelia Kurniawan',NULL),
	(33,1,24,'20150221164958896','2015-02-21 08:49:59',29500,'Diskon Member',10,2950,0,0,0,0,26550,30000,3450,'CASH',3,'Bram',2,'Amelia Kurniawan',NULL),
	(34,1,25,'20150222170517863','2015-02-22 09:05:18',16500,'Diskon Member',10,1650,0,0,0,0,14850,15000,150,'CASH',1,'Retno',0,NULL,NULL),
	(35,1,26,'20150222170604290','2015-02-22 09:06:04',16500,'Diskon Member',10,1650,0,0,0,0,14850,15000,150,'CASH',1,'Retno',0,NULL,NULL),
	(36,1,27,'20150222183848987','2015-02-22 10:38:49',10000,'Diskon Member',10,1000,0,0,0,0,9000,10000,1000,'CASH',1,'Retno',0,NULL,NULL),
	(37,1,28,'20150222185511889','2015-02-22 10:55:12',10000,'Diskon Member',10,1000,0,0,0,0,9000,10000,1000,'CASH',1,'Retno',0,NULL,NULL),
	(38,1,29,'20150222192722819','2015-02-22 11:27:23',29000,'Diskon Member',10,2900,0,0,0,0,26100,30000,3900,'CASH',3,'Bram',2,'Amelia Kurniawan',NULL),
	(39,1,30,'20150223001046017','2015-02-22 16:10:46',17500,NULL,NULL,NULL,0,0,0,0,17500,20000,2500,'CASH',1,'Retno',0,NULL,NULL),
	(40,1,31,'20150223003223630','2015-02-22 16:32:24',7500,'Diskon Member',10,750,0,0,0,0,6750,7000,250,'CASH',1,'Retno',0,NULL,NULL),
	(41,1,32,'20150223003328005','2015-02-22 16:33:28',10000,'Diskon Member',10,1000,0,0,0,0,9000,10000,1000,'CASH',1,'Retno',0,NULL,NULL),
	(42,1,33,'20150223102416180','2015-02-23 02:24:16',10000,NULL,NULL,NULL,0,0,0,0,10000,10000,0,'CASH',1,'Retno',0,NULL,NULL),
	(43,1,34,'20150223112727187','2015-02-23 03:27:27',25000,'Diskon Member',10,2500,0,0,0,0,22500,30000,7500,'CASH',1,'Retno',0,NULL,NULL),
	(44,1,35,'20150223122622452','2015-02-23 04:26:22',7500,'Diskon Member',10,750,0,0,0,0,6750,10000,3250,'CASH',1,'Retno',0,NULL,NULL),
	(45,1,36,'20150223123127355','2015-02-23 04:31:27',17500,NULL,NULL,NULL,0,0,0,0,17500,18000,500,'CASH',1,'Retno',0,NULL,NULL),
	(46,1,37,'20150223130456391','2015-02-23 05:04:56',17500,NULL,NULL,NULL,0,0,0,0,17500,20000,2500,'CASH',1,'Retno',0,NULL,NULL),
	(47,1,38,'20150223173001849','2015-02-23 09:30:02',6500,'Diskon Member',10,650,0,0,0,0,5850,6000,150,'CASH',1,'Retno',0,NULL,NULL),
	(48,4,1,'20150225021539132','2015-02-24 18:15:39',17500,NULL,NULL,NULL,0,0,0,0,17500,20000,2500,'CASH',1,'kasir1',0,NULL,NULL),
	(49,4,2,'20150225024652589','2015-02-24 18:46:53',13500,NULL,NULL,NULL,0,0,0,0,13500,15000,1500,'CASH',1,'kasir1',0,NULL,NULL),
	(50,4,3,'20150224141120288','2015-02-24 19:11:20',60000,NULL,NULL,NULL,0,0,0,0,60000,60000,0,'CASH',1,'kasir1',0,NULL,NULL);

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
	(17,1,3,'Bram','bram','123456','C','A','retno','2015-02-07 16:58:30','retno','2015-02-07 16:58:30'),
	(18,4,2,'admin','admin','1','A','A','admin','2015-02-24 17:31:39','admin','2015-02-24 17:37:24'),
	(19,4,1,'kasir1','kasir1','1','C','A','admin','2015-02-24 17:31:43','admin','2015-02-24 18:05:32');

/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;



/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
