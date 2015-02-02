# ************************************************************
# Sequel Pro SQL dump
# Version 4096
#
# http://www.sequelpro.com/
# http://code.google.com/p/sequel-pro/
#
# Host: 127.0.0.1 (MySQL 5.6.22)
# Database: pos
# Generation Time: 2015-02-02 17:21:33 +0000
# ************************************************************


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


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
	(3,1,'ec8b83e7-7431-43c9-b9b0-7d03d433bcba','2015-01-01 00:00:00');

/*!40000 ALTER TABLE `device` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table product_group
# ------------------------------------------------------------

DROP TABLE IF EXISTS `product_group`;

CREATE TABLE `product_group` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `merchant_id` int(11) DEFAULT NULL,
  `remote_id` int(11) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  `upload_status` varchar(1) DEFAULT NULL,
  `create_by` varchar(50) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `update_by` varchar(50) DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `product_group` WRITE;
/*!40000 ALTER TABLE `product_group` DISABLE KEYS */;

INSERT INTO `product_group` (`id`, `merchant_id`, `remote_id`, `name`, `upload_status`, `create_by`, `create_date`, `update_by`, `update_date`)
VALUES
	(2,1,2,'Menu Ayam',NULL,'radix','2015-02-03 00:14:29','radix','2015-02-03 00:14:29'),
	(3,1,3,'Menu Kambing',NULL,'radix','2015-02-03 00:14:29','radix','2015-02-03 00:14:29'),
	(4,1,1,'Menu Udang',NULL,'radix','2015-02-03 00:14:29','radix','2015-02-03 00:14:29'),
	(5,1,4,'Perawatan Muka',NULL,'radix','2015-02-03 00:14:29','radix','2015-02-03 00:14:29');

/*!40000 ALTER TABLE `product_group` ENABLE KEYS */;
UNLOCK TABLES;



/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
