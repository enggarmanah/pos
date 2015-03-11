# ************************************************************
# Sequel Pro SQL dump
# Version 4096
#
# http://www.sequelpro.com/
# http://code.google.com/p/sequel-pro/
#
# Host: 173.194.234.195 (MySQL 5.5.38)
# Database: tokoku
# Generation Time: 2015-03-07 02:49:42 +0000
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
	(81,4,'a829381c-be86-4b6d-9970-f497296fe18c','2015-02-24 18:45:19'),
	(82,4,'92e68a79-d752-42cd-8e6c-c8caaf4eda6f','2015-02-25 06:04:14'),
	(83,4,'52c56a04-56e8-4879-9f0d-c60afd5e8444','2015-02-25 06:05:23'),
	(84,4,'741245c7-1aa5-4d9e-a920-656b02ddcc04','2015-03-01 15:52:40'),
	(85,NULL,'741245c7-1aa5-4d9e-a920-656b02ddcc04','2015-01-01 00:00:00'),
	(86,4,'d937fb2a-a467-43e4-9caa-b12f72218cb9','2015-03-01 09:31:52'),
	(87,1,'efd40f66-12ea-4556-a0f0-1f5898f01848','2015-03-01 10:11:54'),
	(88,4,'b9286cd0-84cc-4c09-8560-b88d00856fb9','2015-03-01 10:29:59'),
	(89,4,'ea1a947a-5144-42cb-84aa-e1908a1b7320','2015-01-01 00:00:00'),
	(90,4,'4de44bad-47ad-40e7-b628-0538944b0afa','2015-01-01 00:00:00'),
	(91,4,'2a899ecd-549a-40e4-a2e1-d3412f1c3e77','2015-03-01 15:48:49'),
	(92,4,'6f398f89-fad9-4111-9921-8475536ab2f7','2015-03-01 15:33:19'),
	(93,4,'925c4bb8-eeff-4d99-a568-008465d34381','2015-03-01 15:38:33'),
	(94,1,'2f2c45ce-65fa-4dba-b9e4-c9144f7e463c','2015-03-02 09:48:02'),
	(95,1,'925c4bb8-eeff-4d99-a568-008465d34381','2015-03-02 06:06:01'),
	(96,4,'a8c0d3a5-736b-4fec-9cee-76a58ef29967','2015-03-03 13:47:52'),
	(97,1,'d602277b-4090-4e57-99a6-89aa9ffdc9e0','2015-03-05 00:58:40'),
	(98,1,'52dfa8ce-1ee0-4bfe-8284-249a9cac8a6a','2015-03-05 01:03:44'),
	(99,1,'2bdf42d5-f8ad-4d00-a8ec-1541a280ff02','2015-03-05 01:05:37'),
	(100,1,'b7b50913-fb8d-4cef-bd52-0e56cc8b2d6a','2015-03-05 01:06:42'),
	(101,1,'1fa5fe6d-5dd3-4cd5-a795-4740b1da4cd6','2015-03-05 01:07:40'),
	(102,1,'7c3ed84e-f389-47e5-9877-dff589eb11bb','2015-03-05 16:09:55'),
	(103,1,'bbd9832c-c9fc-44a0-9a6e-334201ceb30c','2015-03-05 15:11:06'),
	(104,1,'a8c0d3a5-736b-4fec-9cee-76a58ef29967','2015-03-05 15:52:16'),
	(105,1,'4d582b3f-2bf4-46fd-a7a9-c24ca2b44a6d','2015-03-06 05:52:30'),
	(106,4,'094f400b-5caa-4c62-8dc3-6992810fa155','2015-03-06 06:47:19'),
	(107,4,'de4ab2fd-f0b3-411e-aa0f-090c2dbd0e76','2015-03-06 15:04:30'),
	(108,4,'152ddd68-9a0b-4004-a803-cc056ccca8bd','2015-03-06 15:55:33');

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
	(20,1,4,'Diskon Nominal',5,'D','radix','2015-02-04 15:51:04','radix','2015-02-07 01:50:48'),
	(21,4,1,'Diskon Member',20,'A','admin','2015-03-06 06:22:49','admin','2015-03-06 06:22:49'),
	(22,4,46,'diskon umur 12',12,'A','admin','2015-03-06 13:46:34','admin','2015-03-06 13:46:34'),
	(23,4,47,'diskon umur 13',13,'A','admin','2015-03-06 13:46:43','admin','2015-03-06 13:46:43'),
	(24,4,48,'diskon umur 14',14,'A','admin','2015-03-06 13:46:53','admin','2015-03-06 13:46:53'),
	(25,4,49,'diskon umur 15',15,'A','admin','2015-03-06 13:47:01','admin','2015-03-06 13:47:01'),
	(26,4,50,'diskon umur 16',16,'A','admin','2015-03-06 13:47:10','admin','2015-03-06 13:47:10'),
	(27,4,3,'diskon umur 17',17,'A','admin','2015-03-06 13:33:40','admin','2015-03-06 13:33:40'),
	(28,4,4,'diskon umur 18',18,'A','admin','2015-03-06 13:34:01','admin','2015-03-06 13:34:01'),
	(29,4,5,'diskon umur 19',19,'A','admin','2015-03-06 13:34:48','admin','2015-03-06 13:34:48'),
	(30,4,6,'diskon umur 20',20,'A','admin','2015-03-06 13:37:03','admin','2015-03-06 13:37:03'),
	(31,4,7,'diskon umur 21',21,'A','admin','2015-03-06 13:37:22','admin','2015-03-06 13:37:22'),
	(32,4,8,'diskon umur 22',22,'A','admin','2015-03-06 13:37:58','admin','2015-03-06 13:37:58'),
	(33,4,9,'diskon umur 23',23,'A','admin','2015-03-06 13:38:11','admin','2015-03-06 13:38:11'),
	(34,4,10,'diskon umur 24',24,'A','admin','2015-03-06 13:38:22','admin','2015-03-06 13:38:22'),
	(35,4,11,'diskon umur 25',25,'A','admin','2015-03-06 13:38:33','admin','2015-03-06 13:38:33'),
	(36,4,12,'diskon umur 26',26,'A','admin','2015-03-06 13:38:44','admin','2015-03-06 13:38:44'),
	(37,4,13,'diskon umur 27',27,'A','admin','2015-03-06 13:38:54','admin','2015-03-06 13:38:54'),
	(38,4,14,'diskon umur 28',28,'A','admin','2015-03-06 13:39:04','admin','2015-03-06 13:39:04'),
	(39,4,2,'diskon umur 29',29,'A','admin','2015-03-06 13:21:24','admin','2015-03-06 13:21:24'),
	(40,4,15,'diskon umur 30',30,'A','admin','2015-03-06 13:39:21','admin','2015-03-06 13:39:21'),
	(41,4,16,'diskon umur 31',31,'A','admin','2015-03-06 13:39:31','admin','2015-03-06 13:39:31'),
	(42,4,17,'diskon umur 32',32,'A','admin','2015-03-06 13:39:44','admin','2015-03-06 13:39:44'),
	(43,4,18,'diskon umur 33',33,'A','admin','2015-03-06 13:39:52','admin','2015-03-06 13:39:52'),
	(44,4,19,'diskon umur 34',34,'A','admin','2015-03-06 13:40:00','admin','2015-03-06 13:40:00'),
	(45,4,20,'diskon umur 35',35,'A','admin','2015-03-06 13:40:11','admin','2015-03-06 13:40:11'),
	(46,4,21,'diskon umur 36',36,'A','admin','2015-03-06 13:40:21','admin','2015-03-06 13:40:21'),
	(47,4,22,'diskon umur 37',37,'A','admin','2015-03-06 13:40:33','admin','2015-03-06 13:40:33'),
	(48,4,23,'diskon umur 38',38,'A','admin','2015-03-06 13:40:43','admin','2015-03-06 13:40:43'),
	(49,4,24,'diskon umur 39',39,'A','admin','2015-03-06 13:40:57','admin','2015-03-06 13:40:57'),
	(50,4,25,'diskon umur 40',40,'A','admin','2015-03-06 13:41:08','admin','2015-03-06 13:41:08'),
	(51,4,26,'diskon umur 41',41,'A','admin','2015-03-06 13:41:17','admin','2015-03-06 13:41:17'),
	(52,4,27,'diskon umur 42',42,'A','admin','2015-03-06 13:41:29','admin','2015-03-06 13:41:29'),
	(53,4,28,'diskon umur 43',43,'A','admin','2015-03-06 13:41:39','admin','2015-03-06 13:41:39'),
	(54,4,29,'diskon umur 44',44,'A','admin','2015-03-06 13:41:48','admin','2015-03-06 13:41:48'),
	(55,4,30,'diskon umur 45',45,'A','admin','2015-03-06 13:41:57','admin','2015-03-06 13:41:57'),
	(56,4,31,'diskon umur 46',46,'A','admin','2015-03-06 13:42:06','admin','2015-03-06 13:42:06'),
	(57,4,32,'diskon umur 47',47,'A','admin','2015-03-06 13:42:15','admin','2015-03-06 13:42:15'),
	(58,4,33,'diskon umur 48',48,'A','admin','2015-03-06 13:42:25','admin','2015-03-06 13:42:25'),
	(59,4,34,'diskon umur 49',49,'A','admin','2015-03-06 13:42:34','admin','2015-03-06 13:42:34'),
	(60,4,35,'diskon umur 50',50,'A','admin','2015-03-06 13:42:46','admin','2015-03-06 13:42:46'),
	(61,4,36,'diskon umur 51',51,'A','admin','2015-03-06 13:43:14','admin','2015-03-06 13:43:14'),
	(62,4,37,'diskon umur 52',52,'A','admin','2015-03-06 13:43:23','admin','2015-03-06 13:43:23'),
	(63,4,38,'diskon umur 53',53,'A','admin','2015-03-06 13:43:31','admin','2015-03-06 13:43:31'),
	(64,4,39,'diskon umur 54',54,'A','admin','2015-03-06 13:43:40','admin','2015-03-06 13:43:40'),
	(65,4,40,'diskon umur 55',55,'A','admin','2015-03-06 13:43:49','admin','2015-03-06 13:43:49'),
	(66,4,41,'diskon umur 56',56,'A','admin','2015-03-06 13:44:00','admin','2015-03-06 13:44:00'),
	(67,4,42,'diskon umur 57',57,'A','admin','2015-03-06 13:44:09','admin','2015-03-06 13:44:09'),
	(68,4,43,'diskon umur 58',58,'A','admin','2015-03-06 13:44:17','admin','2015-03-06 13:44:17'),
	(69,4,44,'diskon umur 59',59,'A','admin','2015-03-06 13:44:38','admin','2015-03-06 13:44:38'),
	(70,4,45,'diskon umur 60',60,'A','admin','2015-03-06 13:44:46','admin','2015-03-06 13:44:46');

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
  `capacity` int(11) DEFAULT NULL,
  `status` varchar(50) DEFAULT NULL,
  `create_by` varchar(50) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `update_by` varchar(50) DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `merchant` WRITE;
/*!40000 ALTER TABLE `merchant` DISABLE KEYS */;

INSERT INTO `merchant` (`id`, `remote_id`, `name`, `type`, `address`, `telephone`, `contact_name`, `contact_telephone`, `login_id`, `password`, `period_start`, `period_end`, `tax_percentage`, `service_charge_percentage`, `capacity`, `status`, `create_by`, `create_date`, `update_by`, `update_date`)
VALUES
	(14,1,'Warung Pojok','R','Jl. Panjaitan 50 Jakarta Pusat','(021) 76541234','Agus Mustopo','021 76541234','warjok','warjok','2014-12-22 08:00:00','2015-12-22 08:00:00',0,0,NULL,'A','retno','2015-02-07 10:41:59','bram','2015-02-17 17:13:56'),
	(15,2,'Warung Babe Gimin','R','Jl Hasanuddin 31 Jakarta Pusat','','Suparman','021 756123','babegimin','babegimin','2015-02-06 16:00:00','2016-02-06 16:00:00',0,0,NULL,'A','retno','2015-02-07 10:41:55','retno','2015-02-17 18:49:01'),
	(16,3,'Warung Sate Pak Barno','R','Jl Pucang Sari 1 Laweyan Solo','(0271) 79812345','Subarno','081915431918','satebarno','satebarno','2015-02-23 16:00:00','2016-02-23 16:00:00',NULL,NULL,NULL,'A',NULL,'2015-02-24 14:07:23',NULL,'2015-02-24 14:07:23'),
	(17,4,'Mbok Rawit Semarang','R','Jl. Durian Raya Ruko Segitiga Emas Kav.8 Banyumanik Semarang','082225905581','Riena Anggraeni','081904328288','mbokrawit','1','2015-02-24 16:00:00','2030-02-24 16:00:00',0,0,NULL,'A','admin','2015-02-24 18:14:51','admin','2015-02-24 18:38:31');

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
  `stock` int(11) DEFAULT NULL,
  `min_stock` int(11) DEFAULT NULL,
  `status` varchar(50) DEFAULT NULL,
  `create_by` varchar(50) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `update_by` varchar(50) DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `product` WRITE;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;

INSERT INTO `product` (`id`, `merchant_id`, `remote_id`, `product_group_id`, `name`, `type`, `price`, `cost_price`, `pic_required`, `commision`, `promo_price`, `promo_start`, `promo_end`, `stock`, `min_stock`, `status`, `create_by`, `create_date`, `update_by`, `update_date`)
VALUES
	(15,1,14,2,'Ayam Asam Manis','P',7500,6000,'N',NULL,NULL,NULL,NULL,NULL,NULL,'A','retno','2015-02-12 01:35:27','retno','2015-02-14 10:41:40'),
	(16,1,4,2,'Ayam Bakar Kecap Manis','P',10000,6000,'N',NULL,NULL,NULL,NULL,NULL,NULL,'I','retno','2015-02-12 01:35:27','retno','2015-02-14 10:42:28'),
	(17,1,13,2,'Ayam Bakar Petis Udang Spesial','P',7500,5000,'N',NULL,NULL,NULL,NULL,NULL,NULL,'A','retno','2015-02-12 01:35:27','retno','2015-02-14 10:46:10'),
	(18,1,19,2,'Ayam Bakar Spesial','P',10000,NULL,'N',NULL,NULL,NULL,NULL,NULL,NULL,'A','retno','2015-02-12 01:35:27','retno','2015-02-12 01:34:22'),
	(19,1,10,2,'Ayam Balado Sambal Pete','P',6500,NULL,'N',NULL,4500,'2014-12-15 16:00:00','2014-12-15 16:00:00',NULL,NULL,'A','retno','2015-02-12 01:35:27','retno','2015-02-12 01:34:22'),
	(20,1,3,2,'Ayam Goreng Spesial Jamur','P',6500,NULL,'N',NULL,NULL,NULL,NULL,NULL,NULL,'A','retno','2015-02-12 01:35:27','retno','2015-02-12 01:34:22'),
	(21,1,2,5,'Bakso Sapi Spesial Jamur Merang','P',8000,NULL,'N',NULL,NULL,NULL,NULL,NULL,NULL,'A','retno','2015-02-12 01:35:27','bram','2015-02-13 15:15:29'),
	(22,1,7,5,'Bakso Urat Spesial','P',8000,NULL,'N',NULL,NULL,NULL,NULL,NULL,NULL,'A','retno','2015-02-12 01:35:27','bram','2015-02-13 15:15:34'),
	(23,1,15,2,'Cah Kangkung','P',7500,NULL,'N',NULL,NULL,NULL,NULL,NULL,NULL,'A','retno','2015-02-12 01:35:27','retno','2015-02-12 01:34:22'),
	(24,1,1,5,'Daging Sapi Asap Spesial','P',10000,NULL,'N',NULL,NULL,NULL,NULL,NULL,NULL,'A','retno','2015-02-12 01:35:27','bram','2015-02-13 15:15:43'),
	(25,1,20,4,'Facial AH 80','S',50000,NULL,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'D','retno','2015-02-12 01:35:27','retno','2015-02-12 01:34:22'),
	(26,1,8,2,'Gado-gado','P',5000,NULL,'N',NULL,NULL,NULL,NULL,NULL,NULL,'A','retno','2015-02-12 01:35:27','retno','2015-02-12 01:34:22'),
	(27,1,18,2,'Sambal Goreng Ati','P',7500,NULL,'N',NULL,NULL,NULL,NULL,NULL,NULL,'D','retno','2015-02-12 01:35:27','retno','2015-02-12 01:34:22'),
	(28,1,12,3,'Sate Kambing','P',17500,NULL,'N',NULL,NULL,NULL,NULL,NULL,NULL,'A','retno','2015-02-12 01:35:27','retno','2015-02-12 01:34:22'),
	(29,1,5,3,'Sate Kambing Muda','P',17500,NULL,'N',NULL,NULL,NULL,NULL,NULL,NULL,'A','retno','2015-02-12 01:35:27','retno','2015-02-12 01:34:22'),
	(30,1,11,2,'Soto Ayam','P',10000,NULL,'N',NULL,NULL,NULL,NULL,NULL,NULL,'A','retno','2015-02-12 01:35:27','retno','2015-02-12 01:34:22'),
	(31,1,16,3,'Tongseng Kambing','P',25000,NULL,'N',NULL,NULL,NULL,NULL,NULL,NULL,'A','retno','2015-02-12 01:35:27','retno','2015-02-12 01:34:22'),
	(32,1,6,1,'Udang Bakar Madu','P',15000,NULL,'N',NULL,NULL,NULL,NULL,NULL,NULL,'A','retno','2015-02-12 01:35:27','retno','2015-02-12 01:34:22'),
	(33,4,1,1,'Paket Ayam Kungpou','P',17500,10000,'Y',100,NULL,NULL,NULL,NULL,NULL,'D','admin','2015-02-24 17:55:56','admin','2015-02-24 17:55:56'),
	(34,4,2,1,'Paket Udang Bakar','P',17500,10000,'Y',100,NULL,NULL,NULL,NULL,NULL,'D','admin','2015-02-24 17:58:04','admin','2015-02-24 17:58:04'),
	(35,4,9,2,'Mie Pedesh Ayam Dewa','P',13500,8000,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-02-24 18:24:04','admin','2015-03-01 11:43:52'),
	(36,4,8,2,'Mie Pedesh Ayam Galau','P',12500,7500,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-02-24 18:23:13','admin','2015-03-01 11:43:39'),
	(37,4,7,2,'Mie Pedesh Ayam Suwung','P',12500,7500,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-02-24 18:22:26','admin','2015-03-01 11:43:30'),
	(38,4,10,2,'Mie Pedesh Seafood Dewa','P',16000,9000,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-02-24 18:26:10','admin','2015-03-01 11:44:18'),
	(39,4,4,1,'Paket Ayam Balado','P',17500,10000,'Y',100,NULL,NULL,NULL,NULL,NULL,'D','admin','2015-02-24 18:19:27','admin','2015-02-24 18:19:27'),
	(40,4,3,1,'Paket Cumi Bakar','P',17500,10000,'Y',100,NULL,NULL,NULL,NULL,NULL,'D','admin','2015-02-24 18:18:35','admin','2015-02-24 18:18:35'),
	(41,4,5,14,'Paket Kiddie A','P',19500,8500,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-02-24 18:19:58','admin','2015-03-06 07:13:51'),
	(42,4,6,14,'Paket Kiddie B','P',18000,8500,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-02-24 18:21:00','admin','2015-03-06 07:13:59'),
	(43,4,12,2,'Mie Pedesh Seafood Galau','P',15000,8000,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-02-25 18:45:25','admin','2015-03-01 11:44:12'),
	(44,4,11,2,'Mie Pedesh Seafood Suwung','P',15000,8000,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-02-25 18:44:24','admin','2015-03-01 11:44:03'),
	(45,4,15,2,'Mie Pedesh Special Dewa','P',18500,10000,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-02-25 18:53:16','admin','2015-02-25 18:53:16'),
	(46,4,14,2,'Mie Pedesh Special Galau','P',17500,10000,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-02-25 18:51:43','admin','2015-03-01 11:44:36'),
	(47,4,13,9,'Mie Pedesh Special Suwung','P',17500,10000,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-02-25 18:50:17','admin','2015-03-01 11:44:30'),
	(48,4,25,3,'Cumi Asam Manis','P',25000,12500,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-02-26 13:51:56','admin','2015-03-01 11:48:17'),
	(49,4,27,3,'Cumi Cabe Kering ','P',25000,12500,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-02-26 13:53:41','admin','2015-03-01 11:48:24'),
	(50,4,26,3,'Cumi Lada Hitam','P',25000,12500,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-02-26 13:52:59','admin','2015-03-01 11:48:31'),
	(51,4,28,3,'Cumi Mbok Rawit','P',25000,12500,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-02-26 13:54:18','admin','2015-03-01 11:48:53'),
	(52,4,29,3,'Cumi Mentega ','P',25000,12500,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-02-26 13:54:44','admin','2015-03-01 11:48:58'),
	(53,4,64,5,'Es Teh Manis','P',2500,1000,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-02-26 14:30:01','admin','2015-03-01 12:02:37'),
	(54,4,56,4,'French Fries','P',10000,6000,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-02-26 14:18:11','admin','2015-03-01 12:00:31'),
	(55,4,30,3,'Gurami Asam Manis ','P',45000,25000,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-02-26 13:55:39','admin','2015-03-01 11:49:24'),
	(56,4,31,3,'Gurami Cabe Kering','P',45000,25000,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-02-26 13:56:08','admin','2015-03-01 11:49:39'),
	(57,4,32,3,'Gurami Mbok Rawit','P',45000,25000,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-02-26 13:56:54','admin','2015-03-01 11:49:44'),
	(58,4,33,3,'Kepiting Asam Manis ','P',80000,45000,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-02-26 13:57:56','admin','2015-03-01 11:50:04'),
	(59,4,35,3,'Kepiting Cabe Kering','P',80000,45000,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-02-26 13:59:14','admin','2015-03-01 11:50:11'),
	(60,4,34,3,'Kepiting Lada Hitam ','P',80000,45000,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-02-26 13:58:42','admin','2015-03-01 11:50:17'),
	(61,4,36,3,'Kepiting Mbok Rawit ','P',80000,45000,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-02-26 13:59:59','admin','2015-03-01 11:50:22'),
	(62,4,37,3,'Kepiting Mentega ','P',80000,45000,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-02-26 14:00:37','admin','2015-03-01 11:57:17'),
	(63,4,38,3,'Kerang Ijo Asam Manis','P',15000,8000,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-02-26 14:02:38','admin','2015-03-01 11:57:49'),
	(64,4,41,3,'Kerang Ijo Cabe Kering ','P',15000,8000,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-02-26 14:04:42','admin','2015-03-01 11:57:55'),
	(65,4,39,3,'Kerang Ijo Lada Hitam ','P',15000,8000,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-02-26 14:03:20','admin','2015-03-01 11:57:59'),
	(66,4,40,3,'Kerang Ijo Mbok Rawit','P',15000,8000,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-02-26 14:03:49','admin','2015-03-01 11:58:03'),
	(67,4,42,3,'Kerang Ijo Mentega ','P',15000,8000,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-02-26 14:05:13','admin','2015-03-01 11:58:10'),
	(68,4,18,2,'Mie Tomyum Ayam Dewa','P',17000,10000,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-02-26 13:45:37','admin','2015-03-01 11:45:10'),
	(69,4,17,2,'Mie Tomyum Ayam Galau','P',16000,10000,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-02-26 13:44:46','admin','2015-03-01 11:45:03'),
	(70,4,16,2,'Mie Tomyum Ayam Suwung','P',16000,10000,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-02-26 13:43:27','admin','2015-03-01 11:44:57'),
	(71,4,21,2,'Mie Tomyum Seafood Dewa','P',19000,11000,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-02-26 13:47:51','admin','2015-03-01 11:45:42'),
	(72,4,20,2,'Mie Tomyum Seafood Galau','P',18000,10000,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-02-26 13:47:13','admin','2015-03-01 11:45:26'),
	(73,4,19,2,'Mie Tomyum Seafood Suwung','P',18000,10000,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-02-26 13:46:32','admin','2015-03-01 11:45:18'),
	(74,4,24,2,'Mie Tomyum Special Dewa','P',21000,12000,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-02-26 13:50:28','admin','2015-03-01 11:47:14'),
	(75,4,23,2,'Mie Tomyum Special Galau ','P',20000,11000,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-02-26 13:49:39','admin','2015-03-01 11:47:00'),
	(76,4,22,2,'Mie Tomyum Special Suwung','P',20000,11000,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-02-26 13:49:02','admin','2015-03-01 11:46:41'),
	(77,4,59,4,'Nasi Putih','P',3500,2000,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-02-26 14:20:04','admin','2015-03-06 05:59:56'),
	(78,4,60,4,'Onion Ring ','P',7500,4000,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-02-26 14:21:07','admin','2015-03-01 12:01:26'),
	(79,4,55,4,'Pisang Bakar ','P',12500,7000,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-02-26 14:17:37','admin','2015-03-01 12:00:10'),
	(80,4,54,4,'Pisang Kremes','P',12500,7000,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-02-26 14:17:03','admin','2015-03-01 12:00:16'),
	(81,4,57,4,'Singkong Keju','P',12500,7000,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-02-26 14:19:02','admin','2015-03-01 12:00:43'),
	(82,4,58,4,'Singkong Mbok Rawit','P',12500,7000,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-02-26 14:19:36','admin','2015-03-01 12:00:47'),
	(83,4,43,3,'Srimping Asam Manis','P',17500,10000,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-02-26 14:09:20','admin','2015-03-01 11:58:29'),
	(84,4,45,3,'Srimping Cabe Kering ','P',17500,10000,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-02-26 14:10:51','admin','2015-03-01 11:58:34'),
	(85,4,44,3,'Srimping Lada Hitam ','P',17500,10000,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-02-26 14:09:50','admin','2015-03-01 11:58:38'),
	(86,4,46,3,'Srimping Mbok Rawit','P',17500,10000,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-02-26 14:11:16','admin','2015-03-01 11:58:42'),
	(87,4,47,3,'Srimping Mentega','P',17500,10000,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-02-26 14:12:30','admin','2015-03-01 11:58:46'),
	(88,4,53,4,'Tahu Mbok Rawit','P',12500,7000,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-02-26 14:16:27','admin','2015-03-01 11:59:54'),
	(89,4,63,5,'Teh Manis','P',2000,1000,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-02-26 14:27:14','admin','2015-03-01 12:02:46'),
	(90,4,48,3,'Udang Asam Manis','P',25000,12500,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-02-26 14:13:48','admin','2015-03-01 11:59:08'),
	(91,4,50,3,'Udang Cabe Kering','P',25000,12500,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-02-26 14:14:52','admin','2015-03-01 11:59:12'),
	(92,4,49,3,'Udang Lada Hitam ','P',25000,12500,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-02-26 14:14:19','admin','2015-03-01 11:59:17'),
	(93,4,51,3,'Udang Mbok Rawit','P',25000,12500,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-02-26 14:15:16','admin','2015-03-01 11:59:21'),
	(94,4,52,3,'Udang Mentega','P',25000,12500,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-02-26 14:15:45','admin','2015-03-01 11:59:26'),
	(95,4,62,5,'Wedang Roti ','P',8000,5000,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-02-26 14:22:39','admin','2015-03-01 12:01:45'),
	(96,4,61,5,'Wedang Seger','P',8000,5000,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-02-26 14:22:10','admin','2015-03-01 12:01:58'),
	(97,4,125,8,'Angel Kiss Hot','P',10000,5000,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-03-01 10:49:41','admin','2015-03-01 10:49:41'),
	(98,4,124,8,'Angel Kiss King Size','P',11000,5500,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-03-01 10:48:34','admin','2015-03-01 10:48:34'),
	(99,4,123,8,'Angel Kiss Regular','P',7500,4500,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-03-01 10:47:44','admin','2015-03-01 10:47:44'),
	(100,4,113,8,'BBM (Banana Berry Milk) Hot','P',11000,5500,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-03-01 10:36:22','admin','2015-03-01 10:36:22'),
	(101,4,112,8,'BBM (Banana Berry Milk) King Size','P',12000,6500,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-03-01 10:34:56','admin','2015-03-01 10:34:56'),
	(102,4,111,8,'BBM (Banana Berry Milk) Regular','P',8000,4500,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-03-01 06:17:18','admin','2015-03-01 06:17:18'),
	(103,4,101,6,'Beef Grill Mbok Rawit','P',55000,32000,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-03-01 06:07:21','admin','2015-03-01 06:07:21'),
	(104,4,102,6,'Beef Steak Mbok Rawit','P',55000,32000,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-03-01 06:07:56','admin','2015-03-01 06:07:56'),
	(105,4,140,8,'Berry Blast Hot ','P',10000,5000,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-03-01 11:04:38','admin','2015-03-01 11:04:38'),
	(106,4,139,8,'Berry Blast King Size','P',11000,5500,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-03-01 11:04:05','admin','2015-03-01 11:04:05'),
	(107,4,138,8,'Berry Blast Regular','P',7500,4500,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-03-01 11:03:36','admin','2015-03-01 11:03:36'),
	(108,4,109,7,'Big Burger Mbok Rawit','P',14000,7500,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-03-01 06:14:22','admin','2015-03-01 06:14:22'),
	(109,4,128,8,'Blue Dragon Hot','P',10000,5000,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-03-01 10:56:45','admin','2015-03-01 10:56:45'),
	(110,4,127,8,'Blue Dragon King Size','P',11000,5500,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-03-01 10:51:04','admin','2015-03-01 10:51:04'),
	(111,4,126,8,'Blue Dragon Regular','P',7500,4500,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-03-01 10:50:33','admin','2015-03-01 10:50:33'),
	(112,4,156,8,'Cappuccino Hot','P',11000,5500,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-03-01 11:40:19','admin','2015-03-01 11:40:19'),
	(113,4,155,8,'Cappuccino King Size','P',12000,6500,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-03-01 11:39:59','admin','2015-03-01 11:39:59'),
	(114,4,154,8,'Cappuccino Regular','P',8000,4500,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-03-01 11:39:03','admin','2015-03-01 11:39:24'),
	(115,4,108,7,'Chicken Cordon Bleu','P',16000,8500,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-03-01 06:13:36','admin','2015-03-01 06:13:36'),
	(116,4,103,6,'Chicken Steak Mbok Rawit','P',35000,17500,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-03-01 06:08:54','admin','2015-03-01 06:08:54'),
	(117,4,146,9,'Chocoberry Hot','P',10000,5000,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-03-01 11:30:43','admin','2015-03-01 11:30:43'),
	(118,4,145,8,'Chocoberry King Size','P',11000,5500,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-03-01 11:30:12','admin','2015-03-01 11:30:12'),
	(119,4,149,8,'Chocoberry Nut Hot','P',11000,5500,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-03-01 11:34:11','admin','2015-03-01 11:34:11'),
	(120,4,148,8,'Chocoberry Nut King Size','P',12000,6500,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-03-01 11:33:35','admin','2015-03-01 11:33:35'),
	(121,4,147,8,'Chocoberry Nut Regular','P',8000,4500,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-03-01 11:32:51','admin','2015-03-01 11:32:51'),
	(122,4,144,8,'Chocoberry Regular','P',7500,4500,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-03-01 11:29:19','admin','2015-03-01 11:29:19'),
	(123,4,116,8,'Cocoa Milk Hot','P',11000,5500,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-03-01 10:40:02','admin','2015-03-01 10:40:02'),
	(124,4,115,8,'Cocoa Milk King Size','P',12000,6500,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-03-01 10:38:20','admin','2015-03-01 10:38:20'),
	(125,4,114,8,'Cocoa Milk Regular','P',8000,4500,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-03-01 10:37:17','admin','2015-03-01 10:37:17'),
	(126,4,105,7,'Crispy Calamary Ring','P',14500,8000,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-03-01 06:11:15','admin','2015-03-01 06:11:15'),
	(127,4,73,5,'Es Jeruk','P',4500,2000,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-03-01 05:43:11','admin','2015-03-01 05:43:11'),
	(128,4,74,5,'Es Jeruk Jumbo','P',5500,2000,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-03-01 05:43:45','admin','2015-03-01 05:43:45'),
	(129,4,68,5,'Es Lemon Tea ','P',4500,2000,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-03-01 05:39:16','admin','2015-03-01 05:39:16'),
	(130,4,70,5,'Es Lemon Tea Jumbo','P',5500,2000,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-03-01 05:40:45','admin','2015-03-01 05:40:45'),
	(131,4,66,5,'Es Teh Manis Jumbo','P',3500,1000,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-03-01 05:37:09','admin','2015-03-01 05:37:09'),
	(132,4,107,7,'Fish & Chip ','P',16000,8500,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-03-01 06:13:02','admin','2015-03-01 06:13:02'),
	(133,4,104,7,'Fruit Salad','P',8500,4000,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-03-01 06:09:46','admin','2015-03-01 06:09:46'),
	(134,4,86,9,'Hot Cappuccino','P',6000,2000,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-03-01 05:56:17','admin','2015-03-01 05:56:17'),
	(135,4,87,9,'Hot Chocolate','P',6000,2000,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-03-01 05:56:42','admin','2015-03-01 05:56:42'),
	(136,4,84,9,'Hot Mocca','P',5000,2000,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-03-01 05:55:02','admin','2015-03-01 05:55:02'),
	(137,4,83,9,'Hot Vanilla Latte','P',5000,2000,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-03-01 05:54:31','admin','2015-03-01 05:54:31'),
	(138,4,85,9,'Hot White Coffee','P',5000,2000,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-03-01 05:55:45','admin','2015-03-01 05:55:45'),
	(139,4,91,9,'Iced Cappuccino','P',7000,2000,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-03-01 05:59:40','admin','2015-03-01 05:59:40'),
	(140,4,93,9,'Iced Chocolate','P',7000,2000,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-03-01 06:00:47','admin','2015-03-01 06:00:47'),
	(141,4,89,9,'Iced Mocca','P',6000,2000,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-03-01 05:58:18','admin','2015-03-01 05:58:18'),
	(142,4,92,9,'Iced Vanilla','P',7000,2000,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-03-01 06:00:23','admin','2015-03-01 06:00:23'),
	(143,4,88,9,'Iced Vanilla Latte','P',6000,2000,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-03-01 05:57:40','admin','2015-03-01 05:57:40'),
	(144,4,90,9,'Iced White Coffee','P',6000,2000,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-03-01 05:59:00','admin','2015-03-01 05:59:00'),
	(145,4,98,6,'Iga Bakar Mbok Rawit','P',55000,32000,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-03-01 06:05:04','admin','2015-03-01 06:05:04'),
	(146,4,99,6,'Iga Barbeque Mbok Rawit','P',55000,32000,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-03-01 06:05:54','admin','2015-03-01 06:05:54'),
	(147,4,71,5,'Jeruk Anget','P',4000,2000,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-03-01 05:41:57','admin','2015-03-01 05:41:57'),
	(148,4,72,5,'Jeruk Anget Jumbo','P',5000,2000,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-03-01 05:42:41','admin','2015-03-01 05:42:41'),
	(149,4,76,5,'Juice Avocado','P',8000,4000,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-03-01 05:47:54','admin','2015-03-01 05:47:54'),
	(150,4,78,5,'Juice Lime RtD','P',10000,5000,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-03-01 05:50:18','admin','2015-03-01 05:50:18'),
	(151,4,81,5,'Juice Mango RtD','P',10000,5000,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-03-01 05:52:28','admin','2015-03-01 05:52:28'),
	(152,4,82,5,'Juice Nanas RtD','P',10000,5000,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-03-01 05:53:03','admin','2015-03-01 05:53:03'),
	(153,4,80,5,'Juice Orange RtD','P',10000,5000,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-03-01 05:51:59','admin','2015-03-01 05:51:59'),
	(154,4,79,5,'Juice Sirsat RtD','P',10000,5000,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-03-01 05:51:15','admin','2015-03-01 05:51:15'),
	(155,4,75,5,'Juice Strawberry','P',8000,4000,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-03-01 05:47:23','admin','2015-03-01 05:47:23'),
	(156,4,77,5,'Juice Tomato','P',8000,4000,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-03-01 05:48:23','admin','2015-03-01 05:48:23'),
	(157,4,67,5,'Lemon Tea Anget','P',4000,2000,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-03-01 05:38:28','admin','2015-03-01 05:38:28'),
	(158,4,69,5,'Lemon Tea Jumbo','P',5000,2000,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-03-01 05:40:11','admin','2015-03-01 05:40:11'),
	(159,4,132,8,'Lychee Cooler','P',7500,4500,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-03-01 10:59:56','admin','2015-03-01 10:59:56'),
	(160,4,134,8,'Lychee Cooler Hot','P',10000,5000,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-03-01 11:01:03','admin','2015-03-01 11:01:03'),
	(161,4,133,8,'Lychee Cooler King Size','P',11000,5500,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-03-01 11:00:40','admin','2015-03-01 11:00:40'),
	(162,4,153,8,'Milko (Milky Oreo) Hot','P',11000,5500,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-03-01 11:38:36','admin','2015-03-01 11:38:36'),
	(163,4,152,8,'Milko (Milky Oreo) King Size','P',12000,6500,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-03-01 11:37:56','admin','2015-03-01 11:37:56'),
	(164,4,150,8,'Milko (Milky Oreo) Regular','P',8000,4500,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-03-01 11:35:02','admin','2015-03-01 11:35:58'),
	(165,4,96,5,'Milkshake Blueberry','P',12000,5000,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-03-01 06:02:33','admin','2015-03-01 06:02:33'),
	(166,4,95,5,'Milkshake Chocolate','P',12000,5000,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-03-01 06:01:47','admin','2015-03-01 06:01:47'),
	(167,4,94,5,'Milkshake Strawberry','P',12000,5000,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-03-01 06:01:15','admin','2015-03-01 06:01:15'),
	(168,4,97,5,'Milkshake Vanilla','P',12000,5000,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-03-01 06:02:51','admin','2015-03-01 06:02:51'),
	(169,4,151,8,'Milky (Milky Oreo) King Size','P',12000,6500,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-03-01 11:35:34','admin','2015-03-01 11:35:34'),
	(170,4,143,8,'MLT (Milk Lychee Tea) Hot','P',11000,5500,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-03-01 11:28:00','admin','2015-03-01 11:28:00'),
	(171,4,142,8,'MLT (Milk Lychee Tea) King Size','P',12000,6500,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-03-01 11:08:42','admin','2015-03-01 11:09:02'),
	(172,4,141,8,'MLT (Milk Lychee Tea) Regular','P',8000,4500,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-03-01 11:05:37','admin','2015-03-01 11:07:25'),
	(173,4,100,6,'Pindang Iga Mbok Rawit','P',55000,32000,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-03-01 06:06:37','admin','2015-03-01 06:06:37'),
	(174,4,137,8,'S-Moc Hot','P',10000,5000,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-03-01 11:03:07','admin','2015-03-01 11:03:07'),
	(175,4,136,8,'S-Moc King Size','P',11000,5500,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-03-01 11:02:36','admin','2015-03-01 11:02:36'),
	(176,4,135,8,'S-Moc Regular','P',7500,4500,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-03-01 11:01:55','admin','2015-03-01 11:01:55'),
	(177,4,106,7,'Seafood Basket','P',14500,8000,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-03-01 06:11:47','admin','2015-03-01 06:11:47'),
	(178,4,110,7,'Spaghetti Carbonara','P',14000,7500,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-03-01 06:14:58','admin','2015-03-01 06:14:58'),
	(179,4,131,8,'Strada Hot','P',10000,5000,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-03-01 10:59:03','admin','2015-03-01 10:59:03'),
	(180,4,130,8,'Strada King Size','P',11000,5500,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-03-01 10:58:28','admin','2015-03-01 10:58:28'),
	(181,4,129,8,'Strada Regular','P',7500,4500,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-03-01 10:57:32','admin','2015-03-01 10:57:32'),
	(182,4,119,8,'Strawberry Wakwaw Hot','P',10000,5000,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-03-01 10:43:51','admin','2015-03-01 10:43:51'),
	(183,4,118,8,'Strawberry Wakwaw King Size','P',11000,5500,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-03-01 10:42:48','admin','2015-03-01 10:42:48'),
	(184,4,117,8,'Strawberry Wakwaw Regular','P',7500,4500,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-03-01 10:42:08','admin','2015-03-01 10:42:08'),
	(185,4,65,5,'Teh Manis Jumbo','P',3000,1000,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-03-01 05:35:32','admin','2015-03-01 05:35:32'),
	(186,4,122,8,'Vanilla Grape Sensation Hot','P',10000,5000,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-03-01 10:46:49','admin','2015-03-01 10:46:49'),
	(187,4,121,8,'Vanilla Grape Sensation King Size','P',11000,5500,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-03-01 10:46:11','admin','2015-03-01 10:46:11'),
	(188,4,120,8,'Vanilla Grape Sensation Regular','P',7500,4500,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-03-01 10:44:50','admin','2015-03-01 10:44:50'),
	(189,4,179,12,'Baby Buncis Garlic','P',15000,NULL,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-03-05 13:58:39','admin','2015-03-05 13:58:39'),
	(190,4,181,12,'Baby Buncis Mbok Rawit','P',15000,NULL,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-03-05 13:59:26','admin','2015-03-05 13:59:26'),
	(191,4,180,12,'Baby Buncis Oyster','P',15000,NULL,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-03-05 13:59:02','admin','2015-03-05 13:59:02'),
	(192,4,172,12,'Baby Buncis Tauco','P',15000,NULL,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-03-05 13:51:18','admin','2015-03-05 13:59:44'),
	(193,4,171,12,'Baby Kaylan Garlic','P',15000,NULL,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-03-05 13:49:55','admin','2015-03-05 13:55:25'),
	(194,4,177,12,'Baby Kaylan Mbok Rawit','P',15000,NULL,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-03-05 13:57:16','admin','2015-03-05 13:57:16'),
	(195,4,176,12,'Baby Kaylan Oyster','P',15000,NULL,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-03-05 13:56:23','admin','2015-03-05 13:56:48'),
	(196,4,178,12,'Baby Kaylan Tauco','P',15000,NULL,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-03-05 13:57:48','admin','2015-03-05 13:58:02'),
	(197,4,184,9,'Banana Coffee','P',12500,4000,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-03-05 14:03:20','admin','2015-03-05 14:04:09'),
	(198,4,164,10,'Beef Blackpepper','P',38000,NULL,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-03-05 13:42:58','admin','2015-03-05 13:42:58'),
	(199,4,165,10,'Beef Mbok Rawit','P',38000,NULL,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-03-05 13:43:21','admin','2015-03-05 13:43:21'),
	(200,4,163,10,'Beef X.O.','P',38000,NULL,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-03-05 13:42:08','admin','2015-03-05 13:42:08'),
	(201,4,170,12,'Brokoli Garlic','P',15000,6500,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-03-05 13:47:14','admin','2015-03-05 13:53:00'),
	(202,4,174,12,'Brokoli Mbok Rawit','P',15000,6500,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-03-05 13:54:19','admin','2015-03-05 13:54:19'),
	(203,4,173,12,'Brokoli Oyster','P',15000,6500,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-03-05 13:53:29','admin','2015-03-05 13:53:40'),
	(204,4,175,12,'Brokoli Tauco','P',15000,6500,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-03-05 13:54:42','admin','2015-03-05 13:54:42'),
	(205,4,162,10,'Chicken Asam Manis','P',22500,NULL,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-03-05 13:41:39','admin','2015-03-05 13:41:39'),
	(206,4,161,10,'Chicken Balado','P',22500,NULL,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-03-05 13:41:12','admin','2015-03-05 13:41:12'),
	(207,4,159,10,'Chicken Blackpepper','P',20000,NULL,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-03-05 13:40:03','admin','2015-03-05 13:40:03'),
	(208,4,157,10,'Chicken Kungpou','P',20000,NULL,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-03-05 13:38:59','admin','2015-03-05 13:38:59'),
	(209,4,160,10,'Chicken Mbok Rawit','P',22500,NULL,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-03-05 13:40:38','admin','2015-03-05 13:40:38'),
	(210,4,158,10,'Chicken X.O.','P',20000,NULL,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-03-05 13:39:29','admin','2015-03-05 13:39:29'),
	(211,4,202,13,'Choco Banana Milk King Size','P',15000,7000,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-03-05 14:29:22','admin','2015-03-05 14:29:22'),
	(212,4,201,13,'Choco Banana Milk Regular','P',10000,6000,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-03-05 14:28:12','admin','2015-03-05 14:28:12'),
	(213,4,185,9,'Choco Coffee','P',12500,4000,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-03-05 14:04:31','admin','2015-03-05 14:04:31'),
	(214,4,194,13,'Deep Cherry Blue','P',12500,5000,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-03-05 14:18:18','admin','2015-03-05 14:18:18'),
	(215,4,188,9,'Hot Berry Coffee','P',8500,3000,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-03-05 14:07:58','admin','2015-03-05 14:07:58'),
	(216,4,189,9,'Hot BlackBerry Coffee','P',8500,3000,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-03-05 14:08:20','admin','2015-03-05 14:09:19'),
	(217,4,190,9,'Hot Coffee With Tea','P',8500,3000,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-03-05 14:08:56','admin','2015-03-05 14:08:56'),
	(218,4,191,9,'Hot Creamy Coffee','P',8500,3000,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-03-05 14:10:12','admin','2015-03-05 14:10:12'),
	(219,4,196,13,'Joyfull Of Love','P',12500,5000,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-03-05 14:19:20','admin','2015-03-05 14:19:20'),
	(220,4,183,12,'Kacang Panjang Tauco','P',10000,4000,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-03-05 14:01:21','admin','2015-03-05 14:01:21'),
	(221,4,182,12,'Karedok','P',10000,4000,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-03-05 14:00:28','admin','2015-03-05 14:00:28'),
	(222,4,195,13,'Lime Squash','P',12500,5000,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-03-05 14:18:41','admin','2015-03-05 14:18:41'),
	(223,4,187,9,'Moccacino Coffee','P',12500,4000,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-03-05 14:05:17','admin','2015-03-05 14:05:17'),
	(224,4,200,13,'Orange Bawah Sadar King Size','P',15000,7000,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-03-05 14:27:29','admin','2015-03-05 14:27:29'),
	(225,4,199,13,'Orange Bawah Sadar Regular','P',10000,6000,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-03-05 14:26:45','admin','2015-03-05 14:26:45'),
	(226,4,186,9,'Orange Coffee','P',12500,4000,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-03-05 14:04:50','admin','2015-03-05 14:04:50'),
	(227,4,169,12,'Sawi Putih Goreng Telur','P',15000,5000,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-03-05 13:45:54','admin','2015-03-05 13:48:03'),
	(228,4,193,13,'Sorrow Of Love','P',12500,5000,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-03-05 14:17:42','admin','2015-03-05 14:17:42'),
	(229,4,192,13,'Sweet-sweet ','P',12500,5000,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-03-05 14:12:10','admin','2015-03-05 14:17:17'),
	(230,4,168,10,'Udang Bakar','P',25000,NULL,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-03-05 13:45:03','admin','2015-03-05 13:45:03'),
	(231,4,167,10,'Udang Pedas Telur Asin','P',30000,NULL,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-03-05 13:44:44','admin','2015-03-05 13:44:44'),
	(232,4,166,10,'Udang X.O.','P',30000,NULL,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-03-05 13:43:54','admin','2015-03-05 13:43:54'),
	(233,4,198,13,'Vanilla Pinky King Size','P',15000,7000,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-03-05 14:26:03','admin','2015-03-05 14:26:03'),
	(234,4,197,13,'Vanilla Pinky Regular','P',10000,6000,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-03-05 14:22:26','admin','2015-03-05 14:24:42'),
	(235,4,203,14,'Nasi Goreng Hokian','P',20000,8000,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-03-06 06:02:51','admin','2015-03-06 06:02:51'),
	(236,4,204,14,'Nasi Goreng Mbok Rawit','P',25000,11500,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-03-06 06:03:26','admin','2015-03-06 06:04:25'),
	(237,4,205,14,'Nasi Goreng Seafood','P',18000,5000,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-03-06 07:05:46','admin','2015-03-06 07:05:46'),
	(238,4,206,5,'Air Mineral','P',1000,500,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-03-06 07:12:41','admin','2015-03-06 07:12:41'),
	(239,4,212,5,'Air Mineral Prima','P',2500,1300,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-03-06 07:20:32','admin','2015-03-06 07:20:59'),
	(240,4,210,15,'Camilan','P',2000,1500,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-03-06 07:18:04','admin','2015-03-06 07:18:04'),
	(241,4,209,15,'Coklat Lolypop','P',3000,2500,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-03-06 07:17:31','admin','2015-03-06 07:17:31'),
	(242,4,219,16,'GG Mild ','P',11600,10850,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-03-06 07:44:29','admin','2015-03-06 07:44:29'),
	(243,4,220,16,'GG Shiver','P',11600,10850,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-03-06 07:45:22','admin','2015-03-06 07:45:22'),
	(244,4,214,16,'Gudang Garam International','P',12400,11400,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-03-06 07:22:43','admin','2015-03-06 07:37:44'),
	(245,4,215,16,'Gudang Garam Signature','P',11500,10550,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-03-06 07:39:21','admin','2015-03-06 07:39:21'),
	(246,4,211,15,'Kacang Telor','P',1500,1000,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-03-06 07:18:47','admin','2015-03-06 07:18:47'),
	(247,4,207,15,'Kerupuk Panjang','P',2000,1500,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-03-06 07:16:47','admin','2015-03-06 07:16:47'),
	(248,4,226,9,'Kopi Hitam','P',5000,2000,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-03-06 08:03:30','admin','2015-03-06 08:03:30'),
	(249,4,229,4,'Mendoan','P',7500,2500,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-03-06 08:07:08','admin','2015-03-06 08:07:08'),
	(250,4,213,15,'Mete ','P',5000,4000,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-03-06 07:21:23','admin','2015-03-06 07:21:23'),
	(251,4,222,14,'Nasi Telur','P',6000,3000,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-03-06 07:54:04','admin','2015-03-06 07:54:04'),
	(252,4,208,15,'Pangsit','P',2000,1500,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-03-06 07:17:04','admin','2015-03-06 07:17:04'),
	(253,4,227,5,'Soda Gembira','P',8000,3000,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-03-06 08:05:52','admin','2015-03-06 08:05:52'),
	(254,4,216,16,'Surya Exclusive 12','P',12400,11350,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-03-06 07:40:47','admin','2015-03-06 07:40:47'),
	(255,4,217,16,'Surya Exclusive 16','P',16400,15400,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-03-06 07:41:42','admin','2015-03-06 07:41:42'),
	(256,4,221,16,'Surya Profesional (Merah)','P',14200,13150,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-03-06 07:46:43','admin','2015-03-06 07:46:43'),
	(257,4,218,16,'Surya Profesional Mild','P',11000,9950,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-03-06 07:43:18','admin','2015-03-06 07:43:18'),
	(258,4,228,5,'Tea Tarik','P',7500,4000,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-03-06 08:06:25','admin','2015-03-06 08:06:25'),
	(259,4,223,15,'Telur Dadar','P',2500,1500,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-03-06 07:55:58','admin','2015-03-06 07:55:58'),
	(260,4,224,15,'Telur Mata Sapi','P',2500,1500,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-03-06 07:56:19','admin','2015-03-06 07:56:19'),
	(261,4,225,10,'Telur Orak Arik','P',2500,1500,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-03-06 07:57:34','admin','2015-03-06 08:01:54'),
	(262,4,234,17,'Bubble Jelly King Size','P',2000,500,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-03-06 12:07:34','admin','2015-03-06 12:07:34'),
	(263,4,233,17,'Bubble Jelly Reg','P',1000,500,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-03-06 12:07:03','admin','2015-03-06 12:07:03'),
	(264,4,230,17,'Choco Granule','P',1000,500,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-03-06 12:03:28','admin','2015-03-06 12:05:35'),
	(265,4,231,17,'Oreo','P',1000,500,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-03-06 12:05:08','admin','2015-03-06 12:05:08'),
	(266,4,232,17,'Roll Wafer','P',1000,500,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'A','admin','2015-03-06 12:06:07','admin','2015-03-06 12:06:07');

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
	(5,1,4,'Perawatan Muka','D','retno','2015-02-03 14:57:38','retno','2015-02-07 05:10:51'),
	(14,1,5,'Menu Sapi','A','retno','2015-02-07 05:10:31','retno','2015-02-07 05:15:29'),
	(15,4,1,'Paket Individual','D','admin','2015-02-24 17:44:02','admin','2015-02-24 17:44:02'),
	(16,4,9,'Coffee','A','admin','2015-02-24 17:52:23','admin','2015-02-24 17:52:23'),
	(17,4,6,'Iga dan Steak','A','admin','2015-02-24 17:51:48','admin','2015-02-24 17:51:48'),
	(18,4,2,'Mie','A','admin','2015-02-24 17:50:06','admin','2015-02-24 18:51:08'),
	(19,4,5,'Minuman','A','admin','2015-02-24 17:50:58','admin','2015-02-24 17:51:18'),
	(20,4,3,'Seafood','A','admin','2015-02-24 17:50:29','admin','2015-02-24 17:50:29'),
	(21,4,4,'Snack','A','admin','2015-02-24 17:50:36','admin','2015-02-24 17:50:36'),
	(22,4,8,'Susu','A','admin','2015-02-24 17:52:11','admin','2015-02-24 17:52:11'),
	(23,4,7,'Western Food','A','admin','2015-02-24 17:52:03','admin','2015-02-24 17:52:03'),
	(24,4,10,'Mbok Rawit Special','A','admin','2015-03-05 13:33:47','admin','2015-03-05 13:33:47'),
	(25,4,12,'Mbok Rawit Special Vegetable','A','admin','2015-03-05 13:34:51','admin','2015-03-05 13:34:51'),
	(26,4,11,'Mbok Rawit Special Vegetables','D','admin','2015-03-05 13:34:14','admin','2015-03-05 13:34:14'),
	(27,4,13,'Special Beverages','A','admin','2015-03-05 13:35:30','admin','2015-03-05 13:35:30'),
	(28,4,14,'Rice and Kiddies','A','admin','2015-03-06 06:00:32','admin','2015-03-06 06:00:32'),
	(29,4,15,'Lain Lain','A','admin','2015-03-06 07:14:37','admin','2015-03-06 07:14:37'),
	(30,4,16,'Rokok','A','admin','2015-03-06 07:14:45','admin','2015-03-06 07:14:45'),
	(31,4,17,'topping susu','A','admin','2015-03-06 12:02:33','admin','2015-03-06 12:02:33');

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
  `commision` int(11) DEFAULT NULL,
  `employee_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `transaction_item` WRITE;
/*!40000 ALTER TABLE `transaction_item` DISABLE KEYS */;

INSERT INTO `transaction_item` (`id`, `merchant_id`, `remote_id`, `transaction_id`, `product_id`, `product_name`, `product_type`, `price`, `cost_price`, `discount`, `quantity`, `commision`, `employee_id`)
VALUES
	(23,1,1,1,14,'Ayam Asam Manis','P',7500,3000,0,1,NULL,0),
	(24,1,2,1,4,'Ayam Bakar Kecap Manis','P',10000,5000,0,1,NULL,0),
	(25,1,3,1,13,'Ayam Bakar Petis Udang Spesial','P',7500,3000,0,1,NULL,0),
	(26,1,4,2,5,'Sate Kambing Muda','P',17500,10000,0,1,NULL,0),
	(27,1,5,2,12,'Sate Kambing','P',17500,10000,0,1,NULL,0),
	(28,1,6,2,16,'Tongseng Kambing','P',25000,12000,0,1,NULL,0),
	(29,1,7,3,2,'Bakso Sapi Spesial Jamur Merang','P',8000,4000,0,1,NULL,0),
	(30,1,8,3,7,'Bakso Urat Spesial','P',8000,4000,0,3,NULL,0),
	(31,1,9,4,16,'Tongseng Kambing','P',25000,12000,0,1,NULL,0),
	(32,1,10,4,5,'Sate Kambing Muda','P',17500,10000,0,2,NULL,0),
	(33,1,11,4,6,'Udang Bakar Madu','P',15000,8000,0,2,NULL,0),
	(34,1,12,5,1,'Daging Sapi Asap Spesial','P',10000,5000,0,1,NULL,0),
	(35,1,13,5,2,'Bakso Sapi Spesial Jamur Merang','P',8000,4000,0,1,NULL,0),
	(36,1,14,5,7,'Bakso Urat Spesial','P',8000,4000,0,1,NULL,0),
	(37,1,15,6,7,'Bakso Urat Spesial','P',8000,4000,0,1,NULL,0),
	(38,1,16,6,13,'Ayam Bakar Petis Udang Spesial','P',7500,3000,0,1,NULL,0),
	(39,1,17,6,8,'Gado-gado','P',5000,5000,0,1,NULL,0),
	(40,1,18,7,14,'Ayam Asam Manis','P',7500,3000,0,1,NULL,0),
	(41,1,19,7,4,'Ayam Bakar Kecap Manis','P',10000,5000,0,1,NULL,0),
	(42,1,20,7,13,'Ayam Bakar Petis Udang Spesial','P',7500,3000,0,1,NULL,0),
	(43,1,21,8,13,'Ayam Bakar Petis Udang Spesial','P',7500,3000,0,1,NULL,0),
	(44,1,22,8,19,'Ayam Bakar Spesial','P',10000,5000,0,1,NULL,0),
	(45,1,23,9,5,'Sate Kambing Muda','P',17500,10000,0,1,NULL,0),
	(46,1,24,9,16,'Tongseng Kambing','P',25000,12000,0,1,NULL,0),
	(47,1,25,10,14,'Ayam Asam Manis','P',7500,3000,0,1,NULL,0),
	(48,1,26,11,16,'Tongseng Kambing','P',25000,12000,0,2,NULL,0),
	(49,1,27,11,5,'Sate Kambing Muda','P',17500,10000,0,1,NULL,0),
	(50,1,28,12,13,'Ayam Bakar Petis Udang Spesial','P',7500,3000,0,1,NULL,0),
	(51,1,29,12,2,'Bakso Sapi Spesial Jamur Merang','P',8000,4000,0,1,NULL,0),
	(52,1,30,13,19,'Ayam Bakar Spesial','P',10000,5000,0,1,NULL,0),
	(53,1,31,13,10,'Ayam Balado Sambal Pete','P',6500,3000,0,1,NULL,0),
	(54,1,32,14,4,'Ayam Bakar Kecap Manis','P',10000,5000,1000,1,NULL,0),
	(55,1,33,14,19,'Ayam Bakar Spesial','P',10000,5000,1000,1,NULL,0),
	(56,1,34,15,14,'Ayam Asam Manis','P',7500,3000,0,1,NULL,0),
	(57,1,35,15,3,'Ayam Goreng Spesial Jamur','P',6500,3000,0,1,NULL,0),
	(58,1,36,16,4,'Ayam Bakar Kecap Manis','P',10000,5000,0,1,NULL,0),
	(59,1,37,16,3,'Ayam Goreng Spesial Jamur','P',6500,3000,0,1,NULL,0),
	(60,1,43,18,19,'Ayam Bakar Spesial','P',10000,5000,NULL,1,NULL,0),
	(61,1,44,18,1,'Daging Sapi Asap Spesial','P',10000,5000,NULL,2,NULL,0),
	(62,1,45,19,5,'Sate Kambing Muda','P',17500,10000,1750,1,NULL,0),
	(63,1,46,19,16,'Tongseng Kambing','P',25000,12000,2500,1,NULL,0),
	(64,1,47,19,19,'Ayam Bakar Spesial','P',10000,5000,1000,1,NULL,0),
	(65,1,48,20,14,'Ayam Asam Manis','P',7500,3000,750,1,NULL,0),
	(66,1,49,20,10,'Ayam Balado Sambal Pete','P',6500,3000,650,1,NULL,0),
	(67,1,50,20,15,'Cah Kangkung','P',7500,3000,750,2,NULL,0),
	(68,1,51,20,8,'Gado-gado','P',5000,5000,500,1,NULL,0),
	(69,1,52,20,13,'Ayam Bakar Petis Udang Spesial','P',7500,3000,750,1,NULL,0),
	(70,1,53,21,13,'Ayam Bakar Petis Udang Spesial','P',7500,5000,0,1,NULL,0),
	(71,1,54,21,10,'Ayam Balado Sambal Pete','P',6500,6500,0,1,NULL,0),
	(72,1,55,21,6,'Udang Bakar Madu','P',15000,15000,0,1,NULL,0),
	(73,1,56,22,14,'Ayam Asam Manis','P',7500,6000,750,1,NULL,0),
	(74,1,57,22,8,'Gado-gado','P',5000,5000,500,1,NULL,0),
	(75,1,58,23,14,'Ayam Asam Manis','P',7500,6000,750,1,NULL,0),
	(76,1,59,23,19,'Ayam Bakar Spesial','P',10000,10000,1000,1,NULL,0),
	(77,1,60,23,8,'Gado-gado','P',5000,5000,500,2,NULL,0),
	(78,1,61,24,10,'Ayam Balado Sambal Pete','P',6500,6500,650,1,NULL,0),
	(79,1,62,24,15,'Cah Kangkung','P',7500,7500,750,2,NULL,0),
	(80,1,63,24,7,'Bakso Urat Spesial','P',8000,8000,800,1,NULL,0),
	(81,1,64,25,4,'Ayam Bakar Kecap Manis','P',10000,6000,1000,1,NULL,0),
	(82,1,65,25,10,'Ayam Balado Sambal Pete','P',6500,6500,650,1,NULL,0),
	(83,1,66,26,4,'Ayam Bakar Kecap Manis','P',10000,6000,1000,1,NULL,0),
	(84,1,67,26,3,'Ayam Goreng Spesial Jamur','P',6500,6500,650,1,NULL,0),
	(85,1,68,27,19,'Ayam Bakar Spesial','P',10000,10000,1000,1,NULL,0),
	(86,1,69,28,19,'Ayam Bakar Spesial','P',10000,10000,1000,1,NULL,0),
	(87,1,70,29,14,'Ayam Asam Manis','P',7500,6000,750,1,NULL,0),
	(88,1,71,29,10,'Ayam Balado Sambal Pete','P',6500,6500,650,1,NULL,0),
	(89,1,72,29,15,'Cah Kangkung','P',7500,7500,750,2,NULL,0),
	(90,1,73,30,12,'Sate Kambing','P',17500,17500,0,1,NULL,0),
	(91,1,74,31,14,'Ayam Asam Manis','P',7500,6000,750,1,NULL,0),
	(92,1,75,32,4,'Ayam Bakar Kecap Manis','P',10000,6000,1000,1,NULL,0),
	(93,1,76,33,4,'Ayam Bakar Kecap Manis','P',10000,6000,0,1,NULL,0),
	(94,1,77,34,16,'Tongseng Kambing','P',25000,25000,2500,1,NULL,0),
	(95,1,78,35,14,'Ayam Asam Manis','P',7500,6000,750,1,NULL,0),
	(96,1,79,36,5,'Sate Kambing Muda','P',17500,17500,0,1,NULL,0),
	(97,1,80,37,5,'Sate Kambing Muda','P',17500,17500,0,1,NULL,0),
	(98,1,81,38,10,'Ayam Balado Sambal Pete','P',6500,6500,650,1,NULL,0),
	(99,4,1,1,1,'Paket Ayam Kungpou','P',17500,10000,0,1,NULL,0),
	(100,4,2,2,9,'Mie Pedesh Ayam Dewa','P',13500,8000,0,1,NULL,0),
	(101,4,3,3,4,'Paket Ayam Balado','P',17500,10000,0,2,NULL,0),
	(102,4,4,3,7,'Mie Pedesh Ayam Suwung','P',12500,7500,0,2,NULL,0),
	(103,4,5,4,10,'Mie Pedesh Seafood Dewa','P',15000,9000,0,2,NULL,0),
	(104,4,6,4,1,'Paket Ayam Kungpou','P',17500,10000,0,3,NULL,0),
	(105,4,7,4,2,'Paket Udang Bakar','P',17500,10000,0,6,NULL,0),
	(106,4,8,5,10,'Mie Pedesh Seafood Dewa','P',15000,9000,0,2,NULL,0),
	(107,4,9,5,9,'Mie Pedesh Ayam Dewa','P',13500,8000,0,2,NULL,0),
	(108,4,10,5,1,'Paket Ayam Kungpou','P',17500,10000,0,2,NULL,0),
	(109,1,99,49,13,'Ayam Bakar Petis Udang Spesial','P',7500,5000,0,1,NULL,0),
	(110,1,100,49,15,'Cah Kangkung','P',7500,7500,0,1,NULL,0),
	(111,1,101,50,13,'Ayam Bakar Petis Udang Spesial','P',7500,5000,0,1,NULL,0),
	(112,1,102,50,15,'Cah Kangkung','P',7500,7500,0,1,NULL,0),
	(113,1,103,51,12,'Sate Kambing','P',17500,17500,0,1,NULL,0),
	(114,1,104,51,16,'Tongseng Kambing','P',25000,25000,0,1,NULL,0),
	(115,1,105,51,14,'Ayam Asam Manis','P',7500,6000,0,1,NULL,0),
	(116,1,106,52,2,'Bakso Sapi Spesial Jamur Merang','P',8000,8000,0,1,NULL,0),
	(117,1,107,52,7,'Bakso Urat Spesial','P',8000,8000,0,1,NULL,0),
	(118,1,108,52,4,'Ayam Bakar Kecap Manis','P',10000,6000,0,2,NULL,0),
	(119,1,109,52,13,'Ayam Bakar Petis Udang Spesial','P',7500,5000,0,1,NULL,0),
	(120,1,110,52,8,'Gado-gado','P',5000,5000,0,1,NULL,0),
	(121,1,111,52,11,'Soto Ayam','P',10000,10000,0,1,NULL,0),
	(122,1,112,52,19,'Ayam Bakar Spesial','P',10000,10000,0,1,NULL,0),
	(123,1,113,52,6,'Udang Bakar Madu','P',15000,15000,0,0,NULL,0),
	(124,1,114,52,14,'Ayam Asam Manis','P',7500,6000,0,1,NULL,0),
	(125,4,11,6,9,'Mie Pedesh Ayam Dewa','P',13500,8000,0,2,NULL,0),
	(126,4,12,6,25,'Cumi Asam Manis','P',25000,12500,0,1,NULL,0),
	(127,4,13,6,33,'Kepiting Asam Manis ','P',80000,45000,0,1,NULL,0),
	(128,4,14,6,64,'Es Teh Manis','P',2500,1000,0,2,NULL,0),
	(129,4,15,6,62,'Wedang Roti ','P',8000,5000,0,2,NULL,0),
	(130,1,115,53,19,'Ayam Bakar Spesial','P',10000,10000,0,1,NULL,0),
	(131,1,116,53,6,'Udang Bakar Madu','P',15000,15000,0,1,NULL,0),
	(132,1,117,53,4,'Ayam Bakar Kecap Manis','P',10000,6000,0,1,NULL,0),
	(133,1,118,53,7,'Bakso Urat Spesial','P',8000,8000,0,2,NULL,0),
	(134,1,119,54,13,'Ayam Bakar Petis Udang Spesial','P',7500,5000,0,2,NULL,0),
	(135,1,120,54,6,'Udang Bakar Madu','P',15000,15000,0,1,NULL,0),
	(136,1,121,54,14,'Ayam Asam Manis','P',7500,6000,0,1,NULL,0),
	(137,1,122,55,6,'Udang Bakar Madu','P',15000,15000,0,1,NULL,0),
	(138,1,123,55,19,'Ayam Bakar Spesial','P',10000,10000,0,1,NULL,0),
	(139,1,124,55,14,'Ayam Asam Manis','P',7500,6000,0,2,NULL,0),
	(140,1,125,55,13,'Ayam Bakar Petis Udang Spesial','P',7500,5000,0,2,NULL,0),
	(141,1,126,56,13,'Ayam Bakar Petis Udang Spesial','P',7500,5000,0,1,NULL,0),
	(142,1,127,56,1,'Daging Sapi Asap Spesial','P',10000,10000,0,22,NULL,0),
	(143,1,128,56,15,'Cah Kangkung','P',7500,7500,0,1,NULL,0),
	(144,1,129,57,13,'Ayam Bakar Petis Udang Spesial','P',7500,5000,0,2,NULL,0),
	(145,1,130,57,1,'Daging Sapi Asap Spesial','P',10000,10000,0,2,NULL,0),
	(146,4,16,8,8,'Mie Pedesh Ayam Galau','P',12500,7500,0,1,NULL,0),
	(147,4,17,8,57,'Singkong Keju','P',12500,7000,0,1,NULL,0),
	(148,4,18,9,157,'Chicken Kungpou','P',20000,20000,0,1,NULL,0),
	(149,4,19,10,20,'Mie Tomyum Seafood Galau','P',18000,10000,0,1,NULL,0),
	(150,4,20,10,76,'Juice Avocado','P',8000,4000,0,1,NULL,0),
	(151,4,21,11,7,'Mie Pedesh Ayam Suwung','P',12500,7500,0,1,NULL,0),
	(152,4,22,11,117,'Strawberry Wakwaw Regular','P',7500,4500,0,1,NULL,0),
	(153,4,23,12,9,'Mie Pedesh Ayam Dewa','P',13500,8000,0,1,NULL,0),
	(154,4,24,12,103,'Chicken Steak Mbok Rawit','P',35000,17500,0,1,NULL,0),
	(155,4,25,12,76,'Juice Avocado','P',8000,4000,0,1,NULL,0),
	(156,4,26,12,197,'Vanilla Pinky Regular','P',10000,6000,0,1,NULL,0),
	(157,4,27,12,208,'Pangsit','P',2000,1500,0,1,NULL,0),
	(158,4,28,13,8,'Mie Pedesh Ayam Galau','P',12500,7500,0,1,NULL,0),
	(159,4,29,13,16,'Mie Tomyum Ayam Suwung','P',16000,10000,0,1,NULL,0),
	(160,4,30,14,66,'Es Teh Manis Jumbo','P',3500,1000,0,1,NULL,0),
	(161,4,31,14,65,'Teh Manis Jumbo','P',3000,1000,0,1,NULL,0),
	(162,4,32,15,40,'Kerang Ijo Mbok Rawit','P',15000,8000,0,1,NULL,0),
	(163,4,33,15,46,'Srimping Mbok Rawit','P',17500,10000,0,1,NULL,0),
	(164,4,34,15,23,'Mie Tomyum Special Galau ','P',20000,11000,0,1,NULL,0),
	(165,4,35,15,159,'Chicken Blackpepper','P',20000,20000,0,1,NULL,0),
	(166,4,36,15,56,'French Fries','P',10000,6000,0,1,NULL,0),
	(167,4,37,15,53,'Tahu Mbok Rawit','P',12500,7000,0,1,NULL,0),
	(168,4,38,15,59,'Nasi Putih','P',3500,2000,0,3,NULL,0),
	(169,4,39,15,27,'Cumi Cabe Kering ','P',25000,12500,0,1,NULL,0),
	(170,4,40,15,227,'Soda Gembira','P',8000,3000,0,1,NULL,0),
	(171,4,41,15,91,'Iced Cappuccino','P',7000,2000,0,1,NULL,0),
	(172,4,42,15,212,'Air Mineral Prima','P',2500,1300,0,1,NULL,0),
	(173,4,43,15,93,'Iced Chocolate','P',7000,2000,0,1,NULL,0),
	(174,4,44,15,72,'Jeruk Anget Jumbo','P',5000,2000,0,1,NULL,0),
	(175,4,45,15,74,'Es Jeruk Jumbo','P',5500,2000,0,1,NULL,0),
	(176,4,46,16,117,'Strawberry Wakwaw Regular','P',7500,4500,0,1,NULL,0),
	(177,4,47,16,65,'Teh Manis Jumbo','P',3000,1000,0,1,NULL,0),
	(178,4,48,16,66,'Es Teh Manis Jumbo','P',3500,1000,0,1,NULL,0),
	(179,4,49,16,23,'Mie Tomyum Special Galau ','P',20000,11000,0,1,NULL,0),
	(180,4,50,16,30,'Gurami Asam Manis ','P',45000,25000,0,1,NULL,0),
	(181,4,51,16,6,'Paket Kiddie B','P',18000,8500,0,1,NULL,0),
	(182,4,52,16,208,'Pangsit','P',2000,1500,0,1,NULL,0),
	(183,4,53,17,113,'BBM (Banana Berry Milk) Hot','P',11000,5500,0,1,NULL,0),
	(184,4,54,17,87,'Hot Chocolate','P',6000,2000,0,1,NULL,0),
	(185,4,55,18,17,'Mie Tomyum Ayam Galau','P',16000,10000,0,1,NULL,0),
	(186,4,56,18,199,'Orange Bawah Sadar Regular','P',10000,6000,0,1,NULL,0),
	(187,4,57,18,66,'Es Teh Manis Jumbo','P',3500,1000,0,1,NULL,0),
	(188,4,58,18,185,'Choco Coffee','P',12500,4000,0,1,NULL,0),
	(189,4,59,18,207,'Kerupuk Panjang','P',2000,1500,0,1,NULL,0),
	(190,4,60,19,103,'Chicken Steak Mbok Rawit','P',35000,17500,0,1,NULL,0),
	(191,4,61,19,196,'Joyfull Of Love','P',12500,5000,0,1,NULL,0),
	(192,4,62,20,212,'Air Mineral Prima','P',2500,1300,0,1,NULL,0),
	(193,4,63,20,159,'Chicken Blackpepper','P',20000,20000,0,1,NULL,0),
	(194,4,64,20,27,'Cumi Cabe Kering ','P',25000,12500,0,1,NULL,0),
	(195,4,65,20,74,'Es Jeruk Jumbo','P',5500,2000,0,1,NULL,0),
	(196,4,66,20,72,'Jeruk Anget Jumbo','P',5000,2000,0,1,NULL,0),
	(197,4,67,20,56,'French Fries','P',10000,6000,0,1,NULL,0),
	(198,4,68,20,91,'Iced Cappuccino','P',7000,2000,0,1,NULL,0),
	(199,4,69,20,93,'Iced Chocolate','P',7000,2000,0,1,NULL,0),
	(200,4,70,20,40,'Kerang Ijo Mbok Rawit','P',15000,8000,0,1,NULL,0),
	(201,4,71,20,23,'Mie Tomyum Special Galau ','P',20000,11000,0,1,NULL,0),
	(202,4,72,20,59,'Nasi Putih','P',3500,2000,0,3,NULL,0),
	(203,4,73,20,227,'Soda Gembira','P',8000,3000,0,1,NULL,0),
	(204,4,74,20,46,'Srimping Mbok Rawit','P',17500,10000,0,1,NULL,0),
	(205,4,75,20,53,'Tahu Mbok Rawit','P',12500,7000,0,1,NULL,0),
	(206,4,76,21,218,'Surya Profesional Mild','P',11000,9950,0,1,NULL,0),
	(207,4,77,22,218,'Surya Profesional Mild','P',11000,9950,0,1,NULL,0),
	(208,4,78,23,218,'Surya Profesional Mild','P',11000,9950,0,1,NULL,0),
	(209,4,79,24,218,'Surya Profesional Mild','P',11000,9950,0,1,NULL,0),
	(210,4,80,25,226,'Kopi Hitam','P',5000,2000,0,1,NULL,0),
	(211,4,81,26,103,'Chicken Steak Mbok Rawit','P',35000,17500,0,1,NULL,0),
	(212,4,82,26,201,'Choco Banana Milk Regular','P',10000,6000,0,1,NULL,0),
	(213,4,83,26,11,'Mie Pedesh Seafood Suwung','P',15000,8000,0,1,NULL,0),
	(214,4,84,27,120,'Vanilla Grape Sensation Regular','P',7500,4500,0,1,NULL,0),
	(215,4,85,27,150,'Milko (Milky Oreo) Regular','P',8000,4500,0,1,NULL,0),
	(216,4,86,27,46,'Srimping Mbok Rawit','P',17500,10000,0,1,NULL,0),
	(217,4,87,27,53,'Tahu Mbok Rawit','P',12500,7000,0,1,NULL,0),
	(218,4,88,27,159,'Chicken Blackpepper','P',20000,20000,0,1,NULL,0),
	(219,4,89,27,59,'Nasi Putih','P',3500,2000,0,2,NULL,0),
	(220,4,90,28,51,'Udang Mbok Rawit','P',25000,12500,0,1,NULL,0),
	(221,4,91,28,28,'Cumi Mbok Rawit','P',25000,12500,0,1,NULL,0),
	(222,4,92,28,59,'Nasi Putih','P',3500,2000,0,1,NULL,0),
	(223,4,93,28,95,'Milkshake Chocolate','P',12000,5000,0,1,NULL,0),
	(224,4,94,29,207,'Kerupuk Panjang','P',2000,1500,0,1,NULL,0),
	(225,4,95,29,205,'Nasi Goreng Seafood','P',18000,5000,0,1,NULL,0),
	(226,4,96,29,51,'Udang Mbok Rawit','P',25000,12500,0,1,NULL,0),
	(227,4,97,29,59,'Nasi Putih','P',3500,2000,0,1,NULL,0),
	(228,4,98,29,53,'Tahu Mbok Rawit','P',12500,7000,0,1,NULL,0),
	(229,4,99,29,114,'Cocoa Milk Regular','P',8000,4500,0,1,NULL,0),
	(230,4,100,29,75,'Juice Strawberry','P',8000,4000,0,1,NULL,0),
	(231,4,101,30,207,'Kerupuk Panjang','P',2000,1500,0,1,NULL,0),
	(232,4,102,31,12,'Mie Pedesh Seafood Galau','P',15000,8000,0,1,NULL,0),
	(233,4,103,31,147,'Chocoberry Nut Regular','P',8000,4500,0,1,NULL,0),
	(234,4,104,32,39,'Kerang Ijo Lada Hitam ','P',15000,8000,0,1,NULL,0),
	(235,4,105,32,157,'Chicken Kungpou','P',20000,20000,0,1,NULL,0),
	(236,4,106,32,59,'Nasi Putih','P',3500,2000,0,2,NULL,0),
	(237,4,107,32,53,'Tahu Mbok Rawit','P',12500,7000,0,1,NULL,0),
	(238,4,108,32,73,'Es Jeruk','P',4500,2000,0,1,NULL,0),
	(239,4,109,32,64,'Es Teh Manis','P',2500,1000,0,1,NULL,0),
	(240,4,110,33,218,'Surya Profesional Mild','P',11000,9950,0,1,NULL,0),
	(241,4,111,34,53,'Tahu Mbok Rawit','P',12500,7000,0,1,NULL,0),
	(242,4,112,34,9,'Mie Pedesh Ayam Dewa','P',13500,8000,0,1,NULL,0),
	(243,4,113,34,158,'Chicken X.O.','P',20000,20000,0,1,NULL,0),
	(244,4,114,34,5,'Paket Kiddie A','P',19500,8500,0,1,NULL,0),
	(245,4,115,34,152,'Milko (Milky Oreo) King Size','P',12000,6500,0,1,NULL,0),
	(246,4,116,34,234,'Bubble Jelly King Size','P',2000,500,0,1,NULL,0),
	(247,4,117,35,28,'Cumi Mbok Rawit','P',25000,12500,0,1,NULL,0),
	(248,4,118,35,53,'Tahu Mbok Rawit','P',12500,7000,0,1,NULL,0),
	(249,4,119,35,159,'Chicken Blackpepper','P',20000,20000,0,1,NULL,0),
	(250,4,120,35,59,'Nasi Putih','P',3500,2000,0,3,NULL,0),
	(251,4,121,35,179,'Baby Buncis Garlic','P',15000,15000,0,1,NULL,0),
	(252,4,122,35,66,'Es Teh Manis Jumbo','P',3500,1000,0,3,NULL,0),
	(253,4,123,36,207,'Kerupuk Panjang','P',2000,1500,0,1,NULL,0),
	(254,4,124,36,13,'Mie Pedesh Special Suwung','P',17500,10000,0,1,NULL,0),
	(255,4,125,36,63,'Teh Manis','P',2000,1000,0,1,NULL,0),
	(256,4,126,36,57,'Singkong Keju','P',12500,7000,0,1,NULL,0),
	(257,4,127,36,108,'Chicken Cordon Bleu','P',16000,8500,0,1,NULL,0),
	(258,4,128,36,107,'Fish & Chip ','P',16000,8500,0,1,NULL,0),
	(259,4,129,36,162,'Chicken Asam Manis','P',22500,22500,0,1,NULL,0),
	(260,4,130,36,106,'Seafood Basket','P',14500,8000,0,1,NULL,0),
	(261,4,131,36,30,'Gurami Asam Manis ','P',45000,25000,0,1,NULL,0),
	(262,4,132,36,59,'Nasi Putih','P',3500,2000,0,2,NULL,0),
	(263,4,133,36,66,'Es Teh Manis Jumbo','P',3500,1000,0,3,NULL,0),
	(264,4,134,36,212,'Air Mineral Prima','P',2500,1300,0,1,NULL,0),
	(265,4,135,36,73,'Es Jeruk','P',4500,2000,0,1,NULL,0),
	(266,4,136,36,150,'Milko (Milky Oreo) Regular','P',8000,4500,0,1,NULL,0),
	(267,4,137,37,157,'Chicken Kungpou','P',20000,20000,0,1,NULL,0),
	(268,4,138,37,38,'Kerang Ijo Asam Manis','P',15000,8000,0,1,NULL,0),
	(269,4,139,37,7,'Mie Pedesh Ayam Suwung','P',12500,7500,0,1,NULL,0),
	(270,4,140,37,115,'Cocoa Milk King Size','P',12000,6500,0,1,NULL,0),
	(271,4,141,37,152,'Milko (Milky Oreo) King Size','P',12000,6500,0,1,NULL,0),
	(272,4,142,37,120,'Vanilla Grape Sensation Regular','P',7500,4500,0,0,NULL,0),
	(273,4,143,37,59,'Nasi Putih','P',3500,2000,0,1,NULL,0),
	(274,4,144,38,19,'Mie Tomyum Seafood Suwung','P',18000,10000,0,2,NULL,0),
	(275,4,145,38,207,'Kerupuk Panjang','P',2000,1500,0,1,NULL,0),
	(276,4,146,38,56,'French Fries','P',10000,6000,0,1,NULL,0),
	(277,4,147,38,76,'Juice Avocado','P',8000,4000,0,1,NULL,0),
	(278,4,148,38,68,'Es Lemon Tea ','P',4500,2000,0,1,NULL,0);

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
  `order_type` varchar(50) DEFAULT NULL,
  `order_reference` varchar(50) DEFAULT NULL,
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

INSERT INTO `transactions` (`id`, `merchant_id`, `remote_id`, `transaction_no`, `order_type`, `order_reference`, `transaction_date`, `bill_amount`, `discount_name`, `discount_percentage`, `discount_amount`, `tax_percentage`, `tax_amount`, `service_charge_percentage`, `service_charge_amount`, `total_amount`, `payment_amount`, `return_amount`, `payment_type`, `cashier_id`, `cashier_name`, `customer_id`, `customer_name`, `status`)
VALUES
	(11,1,1,'20150214002759248',NULL,NULL,'2015-01-01 16:27:59',25000,'Diskon Member',10,2500,0,0,0,0,22500,30000,7500,'CASH',3,'Bram',0,NULL,NULL),
	(12,1,2,'20150214002819591',NULL,NULL,'2015-01-03 16:28:20',60000,'Diskon Member',10,6000,0,0,0,0,54000,60000,6000,'CASH',3,'Bram',0,NULL,NULL),
	(13,1,3,'20150214002833559',NULL,NULL,'2015-02-01 16:28:34',32000,'Diskon Member',10,3200,0,0,0,0,28800,60000,31200,'CASH',3,'Bram',0,NULL,NULL),
	(14,1,4,'20150214002901724',NULL,NULL,'2015-02-03 16:29:02',90000,'Diskon Member',10,9000,0,0,0,0,81000,85000,4000,'CASH',3,'Bram',0,NULL,NULL),
	(15,1,5,'20150214002915795',NULL,NULL,'2015-02-10 16:29:16',26000,'Diskon Member',10,2600,0,0,0,0,23400,85000,61600,'CASH',3,'Bram',0,NULL,NULL),
	(16,1,6,'20150214003003463',NULL,NULL,'2015-02-13 16:30:03',20500,'Diskon Member',10,2050,0,0,0,0,18450,85000,66550,'CASH',3,'Bram',0,NULL,NULL),
	(17,1,7,'20150214124000580',NULL,NULL,'2015-02-14 04:40:01',25000,'Diskon Member',10,2500,0,0,0,0,22500,25000,2500,'CASH',1,'Retno',0,NULL,NULL),
	(18,1,8,'20150214124022880',NULL,NULL,'2015-02-14 04:40:23',17500,'Diskon Member',10,1750,0,0,0,0,15750,25000,9250,'CASH',1,'Retno',0,NULL,NULL),
	(19,1,9,'20150214124106280',NULL,NULL,'2015-02-14 04:41:06',42500,'Diskon Member',10,4250,0,0,0,0,38250,50000,11750,'CASH',1,'Retno',0,NULL,NULL),
	(20,1,10,'20150214124423282',NULL,NULL,'2015-02-14 04:44:23',7500,NULL,NULL,NULL,0,0,0,0,7500,10000,2500,'CASH',1,'Retno',0,NULL,NULL),
	(21,1,11,'20150214124458147',NULL,NULL,'2015-02-14 04:44:58',67500,'Diskon Member',10,6750,0,0,0,0,60750,70000,9250,'CASH',1,'Retno',0,NULL,NULL),
	(22,1,12,'20150214190951989',NULL,NULL,'2015-02-14 11:09:52',15500,NULL,NULL,NULL,0,0,0,0,15500,16000,500,'CASH',1,'Retno',0,NULL,NULL),
	(23,1,13,'20150214191509054',NULL,NULL,'2015-02-14 11:15:09',16500,NULL,NULL,NULL,0,0,0,0,16500,17000,500,'CASH',1,'Retno',0,NULL,NULL),
	(24,1,14,'20150214193142522',NULL,NULL,'2015-02-14 11:31:43',20000,'Diskon Member',10,2000,0,0,0,0,18000,20000,2000,'CASH',1,'Retno',0,NULL,NULL),
	(25,1,15,'20150214193242988',NULL,NULL,'2015-02-14 11:32:43',14000,NULL,NULL,NULL,0,0,0,0,14000,15000,1000,'CASH',1,'Retno',0,NULL,NULL),
	(26,1,16,'20150214193422189',NULL,NULL,'2015-02-14 11:34:22',16500,NULL,NULL,NULL,0,0,0,0,16500,20000,3500,'CASH',1,'Retno',0,NULL,NULL),
	(27,1,18,'20150212123840545',NULL,NULL,'2015-02-12 04:38:41',30000,'Diskon Member',15,4500,0,0,0,0,25500,0,-25500,'CASH',3,'Bram',0,NULL,NULL),
	(28,1,19,'20150215135422865',NULL,NULL,'2015-02-15 05:54:23',52500,'Diskon Member',10,5250,0,0,0,0,47250,100000,52750,'CASH',1,'Retno',0,NULL,NULL),
	(29,1,20,'20150218024739039',NULL,NULL,'2015-02-17 18:47:39',41500,'Diskon Member',10,4150,0,0,0,0,37350,50000,12650,'CASH',1,'Retno',0,NULL,NULL),
	(30,1,21,'20150221114447927',NULL,NULL,'2015-02-21 03:44:48',29000,NULL,NULL,NULL,0,0,0,0,29000,30000,1000,'CASH',3,'Bram',4,'Jasmine Anita',NULL),
	(31,1,22,'20150221160451819',NULL,NULL,'2015-02-21 08:04:52',12500,'Diskon Member',10,1250,0,0,0,0,11250,20000,8750,'CASH',3,'Bram',2,'Amelia Kurniawan',NULL),
	(32,1,23,'20150221160728133',NULL,NULL,'2015-02-21 08:07:28',27500,'Diskon Member',10,2750,0,0,0,0,24750,30000,5250,'CASH',3,'Bram',2,'Amelia Kurniawan',NULL),
	(33,1,24,'20150221164958896',NULL,NULL,'2015-02-21 08:49:59',29500,'Diskon Member',10,2950,0,0,0,0,26550,30000,3450,'CASH',3,'Bram',2,'Amelia Kurniawan',NULL),
	(34,1,25,'20150222170517863',NULL,NULL,'2015-02-22 09:05:18',16500,'Diskon Member',10,1650,0,0,0,0,14850,15000,150,'CASH',1,'Retno',0,NULL,NULL),
	(35,1,26,'20150222170604290',NULL,NULL,'2015-02-22 09:06:04',16500,'Diskon Member',10,1650,0,0,0,0,14850,15000,150,'CASH',1,'Retno',0,NULL,NULL),
	(36,1,27,'20150222183848987',NULL,NULL,'2015-02-22 10:38:49',10000,'Diskon Member',10,1000,0,0,0,0,9000,10000,1000,'CASH',1,'Retno',0,NULL,NULL),
	(37,1,28,'20150222185511889',NULL,NULL,'2015-02-22 10:55:12',10000,'Diskon Member',10,1000,0,0,0,0,9000,10000,1000,'CASH',1,'Retno',0,NULL,NULL),
	(38,1,29,'20150222192722819',NULL,NULL,'2015-02-22 11:27:23',29000,'Diskon Member',10,2900,0,0,0,0,26100,30000,3900,'CASH',3,'Bram',2,'Amelia Kurniawan',NULL),
	(39,1,30,'20150223001046017',NULL,NULL,'2015-02-22 16:10:46',17500,NULL,NULL,NULL,0,0,0,0,17500,20000,2500,'CASH',1,'Retno',0,NULL,NULL),
	(40,1,31,'20150223003223630',NULL,NULL,'2015-02-22 16:32:24',7500,'Diskon Member',10,750,0,0,0,0,6750,7000,250,'CASH',1,'Retno',0,NULL,NULL),
	(41,1,32,'20150223003328005',NULL,NULL,'2015-02-22 16:33:28',10000,'Diskon Member',10,1000,0,0,0,0,9000,10000,1000,'CASH',1,'Retno',0,NULL,NULL),
	(42,1,33,'20150223102416180',NULL,NULL,'2015-02-23 02:24:16',10000,NULL,NULL,NULL,0,0,0,0,10000,10000,0,'CASH',1,'Retno',0,NULL,NULL),
	(43,1,34,'20150223112727187',NULL,NULL,'2015-02-23 03:27:27',25000,'Diskon Member',10,2500,0,0,0,0,22500,30000,7500,'CASH',1,'Retno',0,NULL,NULL),
	(44,1,35,'20150223122622452',NULL,NULL,'2015-02-23 04:26:22',7500,'Diskon Member',10,750,0,0,0,0,6750,10000,3250,'CASH',1,'Retno',0,NULL,NULL),
	(45,1,36,'20150223123127355',NULL,NULL,'2015-02-23 04:31:27',17500,NULL,NULL,NULL,0,0,0,0,17500,18000,500,'CASH',1,'Retno',0,NULL,NULL),
	(46,1,37,'20150223130456391',NULL,NULL,'2015-02-23 05:04:56',17500,NULL,NULL,NULL,0,0,0,0,17500,20000,2500,'CASH',1,'Retno',0,NULL,NULL),
	(47,1,38,'20150223173001849',NULL,NULL,'2015-02-23 09:30:02',6500,'Diskon Member',10,650,0,0,0,0,5850,6000,150,'CASH',1,'Retno',0,NULL,NULL),
	(48,4,1,'20150225021539132',NULL,NULL,'2015-02-24 18:15:39',17500,NULL,NULL,NULL,0,0,0,0,17500,20000,2500,'CASH',1,'kasir1',0,NULL,NULL),
	(49,4,2,'20150225024652589',NULL,NULL,'2015-02-24 18:46:53',13500,NULL,NULL,NULL,0,0,0,0,13500,15000,1500,'CASH',1,'kasir1',0,NULL,NULL),
	(50,4,3,'20150224141120288',NULL,NULL,'2015-02-24 19:11:20',60000,NULL,NULL,NULL,0,0,0,0,60000,60000,0,'CASH',1,'kasir1',0,NULL,NULL),
	(51,4,4,'20150226013310038',NULL,NULL,'2015-02-25 18:33:10',187500,NULL,NULL,NULL,0,0,0,0,187500,105000,-82500,'CASH',1,'kasir1',0,NULL,NULL),
	(52,4,5,'20150226013555650',NULL,NULL,'2015-02-25 18:35:55',92000,NULL,NULL,NULL,0,0,0,0,92000,100000,8000,'CASH',1,'kasir1',0,NULL,NULL),
	(53,4,6,'20150226221338694',NULL,NULL,'2015-02-26 15:13:38',153000,NULL,NULL,NULL,0,0,0,0,153000,160000,7000,'CASH',3,'Vega Oktavialita',0,NULL,NULL),
	(54,1,49,'20150301134223902',NULL,NULL,'2015-03-01 06:42:23',15000,NULL,NULL,NULL,0,0,0,0,15000,20000,5000,'CASH',3,'Bram',0,NULL,'A'),
	(55,1,50,'20150301134703571','DINE_IN','5','2015-03-01 06:47:03',15000,NULL,NULL,NULL,0,0,0,0,15000,20000,5000,'CASH',3,'Bram',0,NULL,'A'),
	(56,1,51,'20150301140756736',NULL,NULL,'2015-03-01 07:07:56',50000,NULL,NULL,NULL,0,0,0,0,50000,50000,0,'CASH',3,'Bram',0,NULL,'A'),
	(57,1,52,'20150301142620558','DINE_IN','6','2015-03-01 07:26:20',76000,NULL,NULL,NULL,0,0,0,0,76000,100000,24000,'CASH',3,'Bram',0,NULL,'A'),
	(58,1,53,'20150301224645785',NULL,NULL,'2015-03-01 15:46:45',51000,NULL,NULL,NULL,0,0,0,0,51000,0,-51000,'CASH',1,'Retno',4,'Jasmine Anita','A'),
	(59,1,54,'20150301224742863',NULL,NULL,'2015-03-01 15:47:42',37500,NULL,NULL,NULL,0,0,0,0,37500,50000,12500,'CASH',1,'Retno',4,'Jasmine Anita','A'),
	(60,1,55,'20150301224959271',NULL,NULL,'2015-03-01 15:49:59',55000,'Diskon Member',10,5500,0,0,0,0,49500,49500,0,'DEBIT',3,'Bram',3,'Jihan Mutia','A'),
	(61,1,56,'20150302161859708',NULL,NULL,'2015-03-02 09:18:59',235000,NULL,NULL,NULL,0,0,0,0,235000,250000,15000,'DEBIT',3,'Bram',4,'Jasmine Anita','A'),
	(62,1,57,'20150302164726787',NULL,NULL,'2015-03-02 09:47:26',35000,NULL,NULL,NULL,0,0,0,0,35000,35,-34965,'DEBIT',3,'Bram',3,'Jihan Mutia','A'),
	(63,1,58,'20150303120844237',NULL,NULL,'2015-03-03 05:08:44',85500,'Diskon Member',10,8550,0,0,0,0,76950,76950,0,'DEBIT',3,'Bram',4,'Jasmine Anita','A'),
	(64,1,59,'20150303152937931',NULL,NULL,'2015-03-03 08:29:37',23500,'Diskon Member',10,2350,0,0,0,0,21150,50000,28850,'CASH',3,'Bram',0,NULL,'A'),
	(65,1,60,'20150304175050661',NULL,NULL,'2015-03-04 10:50:50',75000,'Diskon Kerabat',20,15000,0,0,0,0,60000,60000,0,'DEBIT',3,'Bram',2,'Amelia Kurniawan','A'),
	(66,4,7,'20150301232646778','DINE_IN','5','2015-03-01 16:26:46',78000,NULL,NULL,NULL,0,0,0,0,78000,100000,22000,'CASH',1,'kasir1',0,NULL,'A'),
	(67,4,8,'20150306134731793','DINE_IN','1','2015-03-06 06:47:31',25000,NULL,NULL,NULL,0,0,0,0,25000,25000,0,'CASH',4,'friska',0,NULL,'A'),
	(68,4,9,'20150306135713611','DINE_IN','1','2015-03-06 06:57:13',20000,NULL,NULL,NULL,0,0,0,0,20000,50000,30000,'CASH',4,'friska',0,NULL,'A'),
	(69,4,10,'20150306151536844','DINE_IN','1','2015-03-06 08:15:36',26000,NULL,NULL,NULL,0,0,0,0,26000,40000,14000,'CASH',4,'friska',0,NULL,'A'),
	(70,4,11,'20150306154947896','DINE_IN','2','2015-03-06 08:49:47',20000,NULL,NULL,NULL,0,0,0,0,20000,50000,30000,'CASH',4,'friska',0,NULL,'A'),
	(71,4,12,'20150306160024712','DINE_IN','3','2015-03-06 09:00:24',68500,NULL,NULL,NULL,0,0,0,0,68500,70000,1500,'CASH',4,'friska',0,NULL,'A'),
	(72,4,13,'20150306160129907','DINE_IN','15','2015-03-06 09:01:29',28500,NULL,NULL,NULL,0,0,0,0,28500,50000,21500,'CASH',4,'friska',0,NULL,'A'),
	(73,4,14,'20150306160959864','DINE_IN','12','2015-03-06 09:09:59',6500,NULL,NULL,NULL,0,0,0,0,6500,6500,0,'CASH',4,'friska',0,NULL,'A'),
	(74,4,15,'20150306162724678','DINE_IN','12','2015-03-06 09:27:24',165500,NULL,NULL,NULL,0,0,0,0,165500,200000,34500,'CASH',4,'friska',0,NULL,'A'),
	(75,4,16,'20150306163748920','DINE_IN','2','2015-03-06 09:37:48',99000,NULL,NULL,NULL,0,0,0,0,99000,99000,0,'CASH',4,'friska',0,NULL,'A'),
	(76,4,17,'20150306164023092','DINE_IN','4','2015-03-06 09:40:23',17000,NULL,NULL,NULL,0,0,0,0,17000,20000,3000,'CASH',4,'friska',0,NULL,'A'),
	(77,4,18,'20150306164421148','DINE_IN','3','2015-03-06 09:44:21',44000,NULL,NULL,NULL,0,0,0,0,44000,45000,1000,'CASH',4,'friska',0,NULL,'A'),
	(78,4,19,'20150306164645145',NULL,NULL,'2015-03-06 09:46:45',47500,NULL,NULL,NULL,0,0,0,0,47500,50000,2500,'CASH',4,'friska',0,NULL,'A'),
	(79,4,20,'20150306170222359','DINE_IN','12','2015-03-06 10:02:22',165500,NULL,NULL,NULL,0,0,0,0,165500,200000,34500,'CASH',4,'friska',0,NULL,'A'),
	(80,4,21,'20150306170343438',NULL,NULL,'2015-03-06 10:03:43',11000,NULL,NULL,NULL,0,0,0,0,11000,11000,0,'CASH',4,'friska',0,NULL,'A'),
	(81,4,22,'20150306170500612','DINE_IN','5','2015-03-06 10:05:00',11000,NULL,NULL,NULL,0,0,0,0,11000,11000,0,'CASH',4,'friska',0,NULL,'A'),
	(82,4,23,'20150306171209558',NULL,NULL,'2015-03-06 10:12:09',11000,NULL,NULL,NULL,0,0,0,0,11000,12000,1000,'CASH',4,'friska',0,NULL,'A'),
	(83,4,24,'20150306171326330','DINE_IN','1','2015-03-06 10:13:26',11000,NULL,NULL,NULL,0,0,0,0,11000,12000,1000,'CASH',4,'friska',0,NULL,'A'),
	(84,4,25,'20150306171453433','DINE_IN','10','2015-03-06 10:14:53',5000,NULL,NULL,NULL,0,0,0,0,5000,5000,0,'CASH',4,'friska',0,NULL,'A'),
	(85,4,26,'20150306172642768','DINE_IN','7','2015-03-06 10:26:42',60000,NULL,NULL,NULL,0,0,0,0,60000,60000,0,'CASH',4,'friska',0,NULL,'A'),
	(86,4,27,'20150306175135122','DINE_IN','14','2015-03-06 10:51:35',72500,NULL,NULL,NULL,0,0,0,0,72500,75000,2500,'CASH',4,'friska',0,NULL,'A'),
	(87,4,28,'20150306175625815','DINE_IN','3','2015-03-06 10:56:25',65500,NULL,NULL,NULL,0,0,0,0,65500,100000,34500,'CASH',4,'friska',0,NULL,'A'),
	(88,4,29,'20150306181217587','DINE_IN','5','2015-03-06 11:12:17',77000,NULL,NULL,NULL,0,0,0,0,77000,100000,23000,'CASH',4,'friska',0,NULL,'A'),
	(89,4,30,'20150306181438646','DINE_IN','5','2015-03-06 11:14:38',2000,NULL,NULL,NULL,0,0,0,0,2000,2000,0,'CASH',4,'friska',0,NULL,'A'),
	(90,4,31,'20150306182222841','DINE_IN','8','2015-03-06 11:22:22',23000,NULL,NULL,NULL,0,0,0,0,23000,25000,2000,'CASH',4,'friska',0,NULL,'A'),
	(91,4,32,'20150306183819197','DINE_IN','1','2015-03-06 11:38:19',61500,NULL,NULL,NULL,0,0,0,0,61500,62000,500,'CASH',4,'friska',0,NULL,'A'),
	(92,4,33,'20150306184033628','DINE_IN','5','2015-03-06 11:40:33',11000,NULL,NULL,NULL,0,0,0,0,11000,12000,1000,'CASH',4,'friska',0,NULL,'A'),
	(93,4,34,'20150306193206112','DINE_IN','12','2015-03-06 12:32:06',79500,NULL,NULL,NULL,0,0,0,0,79500,100000,20500,'CASH',4,'friska',0,NULL,'A'),
	(94,4,35,'20150306211147563','DINE_IN','6','2015-03-06 14:11:47',93500,NULL,NULL,NULL,0,0,0,0,93500,93500,0,'CASH',4,'friska',0,NULL,'A'),
	(95,4,36,'20150306211646219','DINE_IN','7','2015-03-06 14:16:46',180500,NULL,NULL,NULL,0,0,0,0,180500,180500,0,'CASH',4,'friska',0,NULL,'A'),
	(96,4,37,'20150306212227992','DINE_IN','6','2015-03-06 14:22:27',75000,NULL,NULL,NULL,0,0,0,0,75000,75000,0,'CASH',4,'friska',0,NULL,'A'),
	(97,4,38,'20150306212341273','DINE_IN','2','2015-03-06 14:23:41',60500,NULL,NULL,NULL,0,0,0,0,60500,61000,500,'CASH',4,'friska',0,NULL,'A');

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
	(19,4,1,'kasir1','kasir1','1','C','A','admin','2015-02-24 17:31:43','admin','2015-02-24 18:05:32'),
	(20,4,3,'Vega Oktavialita','vegha','123','C','A','admin','2015-02-26 15:09:45','admin','2015-02-26 15:11:27'),
	(21,4,4,'friska','friska','123','C','A','admin','2015-03-06 05:59:12','admin','2015-03-06 05:59:12');

/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;



/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
