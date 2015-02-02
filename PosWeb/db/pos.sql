-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               5.6.16 - MySQL Community Server (GPL)
-- Server OS:                    Win64
-- HeidiSQL Version:             8.3.0.4749
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

-- Dumping database structure for pos
DROP DATABASE IF EXISTS `pos`;
CREATE DATABASE IF NOT EXISTS `pos` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `pos`;


-- Dumping structure for table pos.device
DROP TABLE IF EXISTS `device`;
CREATE TABLE IF NOT EXISTS `device` (
  `id` int(11) NOT NULL,
  `merchant_id` int(11) DEFAULT NULL,
  `uuid` varchar(50) DEFAULT NULL,
  `last_sync_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `merchant_id_uuid` (`merchant_id`,`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table pos.device: ~0 rows (approximately)
DELETE FROM `device`;
/*!40000 ALTER TABLE `device` DISABLE KEYS */;
/*!40000 ALTER TABLE `device` ENABLE KEYS */;


-- Dumping structure for table pos.product_group
DROP TABLE IF EXISTS `product_group`;
CREATE TABLE IF NOT EXISTS `product_group` (
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
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- Dumping data for table pos.product_group: ~4 rows (approximately)
DELETE FROM `product_group`;
/*!40000 ALTER TABLE `product_group` DISABLE KEYS */;
INSERT INTO `product_group` (`id`, `merchant_id`, `remote_id`, `name`, `upload_status`, `create_by`, `create_date`, `update_by`, `update_date`) VALUES
	(2, NULL, 2, 'Menu Ayam', NULL, NULL, NULL, NULL, NULL),
	(3, NULL, 3, 'Menu Kambing', NULL, NULL, NULL, NULL, NULL),
	(4, NULL, 1, 'Menu Udang', NULL, NULL, NULL, NULL, NULL),
	(5, NULL, 4, 'Perawatan Muka', NULL, NULL, NULL, NULL, NULL);
/*!40000 ALTER TABLE `product_group` ENABLE KEYS */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
