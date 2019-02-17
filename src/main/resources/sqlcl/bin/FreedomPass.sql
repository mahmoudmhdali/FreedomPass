CREATE DATABASE  IF NOT EXISTS `db_freedom_pass_app` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `db_freedom_pass_app`;
-- MySQL dump 10.13  Distrib 8.0.13, for Win64 (x86_64)
--
-- Host: localhost    Database: db_freedom_pass_app
-- ------------------------------------------------------
-- Server version	5.6.14

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `tbl_admin_passes`
--

DROP TABLE IF EXISTS `tbl_admin_passes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `tbl_admin_passes` (
  `id` bigint(20) NOT NULL,
  `VALIDITY` int(4) DEFAULT NULL,
  `NAME` varchar(45) DEFAULT NULL,
  `DESCRIPTION` varchar(200) DEFAULT NULL,
  `IMAGE_PATH` varchar(100) DEFAULT NULL,
  `CREATED_DATE` timestamp NULL DEFAULT NULL,
  `DELETED_DATE` timestamp NULL DEFAULT NULL,
  `UPDATED_DATE` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_admin_passes`
--

LOCK TABLES `tbl_admin_passes` WRITE;
/*!40000 ALTER TABLE `tbl_admin_passes` DISABLE KEYS */;
INSERT INTO `tbl_admin_passes` VALUES (1,60,'Pass 1','Pess 1 Description',NULL,'2018-01-22 10:53:01',NULL,NULL),(2,30,'Pass 2','Pass 2 Description',NULL,'2018-01-22 10:53:01',NULL,NULL);
/*!40000 ALTER TABLE `tbl_admin_passes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_admin_passes_outlet_offers`
--

DROP TABLE IF EXISTS `tbl_admin_passes_outlet_offers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `tbl_admin_passes_outlet_offers` (
  `PASS_ID` bigint(20) DEFAULT NULL,
  `OUTLET_OFFER_ID` bigint(20) DEFAULT NULL,
  KEY `FK_23523657235672_idx` (`PASS_ID`),
  KEY `FK_98683475872_idx` (`OUTLET_OFFER_ID`),
  CONSTRAINT `FK_23523657235672` FOREIGN KEY (`PASS_ID`) REFERENCES `tbl_admin_passes` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_98683475872` FOREIGN KEY (`OUTLET_OFFER_ID`) REFERENCES `tbl_user_outlet_offer` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_admin_passes_outlet_offers`
--

LOCK TABLES `tbl_admin_passes_outlet_offers` WRITE;
/*!40000 ALTER TABLE `tbl_admin_passes_outlet_offers` DISABLE KEYS */;
INSERT INTO `tbl_admin_passes_outlet_offers` VALUES (1,1);
/*!40000 ALTER TABLE `tbl_admin_passes_outlet_offers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_blacklist`
--

DROP TABLE IF EXISTS `tbl_blacklist`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `tbl_blacklist` (
  `black_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `msisdn` varchar(15) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `dateblacklisted` datetime NOT NULL,
  `bwflag` tinyint(1) NOT NULL DEFAULT '0' COMMENT '0 for blacklist\n1 for whitelist',
  PRIMARY KEY (`black_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_blacklist`
--

LOCK TABLES `tbl_blacklist` WRITE;
/*!40000 ALTER TABLE `tbl_blacklist` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbl_blacklist` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_general_dashboard`
--

DROP TABLE IF EXISTS `tbl_general_dashboard`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `tbl_general_dashboard` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `item_key` varchar(45) NOT NULL,
  `counter_value` longtext,
  `item_type` tinyint(1) NOT NULL,
  `query` longtext NOT NULL,
  `filters` varchar(45) DEFAULT NULL,
  `enabled` tinyint(1) NOT NULL DEFAULT '1',
  `colors` varchar(200) DEFAULT NULL,
  `xaxiscolumn` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_general_dashboard`
--

LOCK TABLES `tbl_general_dashboard` WRITE;
/*!40000 ALTER TABLE `tbl_general_dashboard` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbl_general_dashboard` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_groups`
--

DROP TABLE IF EXISTS `tbl_groups`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `tbl_groups` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(256) NOT NULL,
  `CREATED_DATE` timestamp NULL DEFAULT NULL,
  `UPDATED_DATE` timestamp NULL DEFAULT NULL,
  `DESCRIPTION` varchar(100) DEFAULT NULL,
  `DELETED_DATE` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `NAME` (`NAME`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_groups`
--

LOCK TABLES `tbl_groups` WRITE;
/*!40000 ALTER TABLE `tbl_groups` DISABLE KEYS */;
INSERT INTO `tbl_groups` VALUES (1,'Installer Group','2018-01-22 08:26:16',NULL,'installer group',NULL),(2,'Support Group','2018-01-22 08:25:16',NULL,NULL,NULL),(3,'All Administrator Groups','2018-01-22 08:30:16','2018-09-25 12:43:07','admin group',NULL);
/*!40000 ALTER TABLE `tbl_groups` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_groups_reports`
--

DROP TABLE IF EXISTS `tbl_groups_reports`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `tbl_groups_reports` (
  `GROUP_ID` bigint(20) NOT NULL,
  `REPORT_ID` bigint(20) NOT NULL,
  PRIMARY KEY (`GROUP_ID`,`REPORT_ID`),
  KEY `GROUPS_ROLES_FK2_idx` (`REPORT_ID`),
  CONSTRAINT `GROUPS_REPORTS_FK1` FOREIGN KEY (`GROUP_ID`) REFERENCES `tbl_groups` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `GROUPS_REPORTS_FK2` FOREIGN KEY (`REPORT_ID`) REFERENCES `tbl_reports` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_groups_reports`
--

LOCK TABLES `tbl_groups_reports` WRITE;
/*!40000 ALTER TABLE `tbl_groups_reports` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbl_groups_reports` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_groups_roles`
--

DROP TABLE IF EXISTS `tbl_groups_roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `tbl_groups_roles` (
  `GROUP_ID` bigint(20) NOT NULL,
  `ROLE_ID` bigint(20) NOT NULL,
  PRIMARY KEY (`GROUP_ID`,`ROLE_ID`),
  KEY `GROUPS_ROLES_FK2` (`ROLE_ID`),
  CONSTRAINT `GROUPS_ROLES_FK1` FOREIGN KEY (`GROUP_ID`) REFERENCES `tbl_groups` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `GROUPS_ROLES_FK2` FOREIGN KEY (`ROLE_ID`) REFERENCES `tbl_roles` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_groups_roles`
--

LOCK TABLES `tbl_groups_roles` WRITE;
/*!40000 ALTER TABLE `tbl_groups_roles` DISABLE KEYS */;
INSERT INTO `tbl_groups_roles` VALUES (1,1),(1,2),(2,2),(1,3),(3,3),(1,4),(3,4),(1,5),(3,5),(1,6),(3,6),(1,7),(3,7),(1,8),(3,8),(1,9),(3,9),(1,10),(3,10),(1,11),(3,11),(1,12),(3,12),(1,13),(3,13),(1,14),(3,14),(1,15),(3,15),(1,16),(3,16),(1,17),(3,17),(1,18),(3,18),(1,19),(3,19);
/*!40000 ALTER TABLE `tbl_groups_roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_languages`
--

DROP TABLE IF EXISTS `tbl_languages`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `tbl_languages` (
  `ID` int(11) NOT NULL,
  `NAME` varchar(45) NOT NULL,
  `prefix` varchar(5) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_languages`
--

LOCK TABLES `tbl_languages` WRITE;
/*!40000 ALTER TABLE `tbl_languages` DISABLE KEYS */;
INSERT INTO `tbl_languages` VALUES (1,'English','en'),(2,'Francais','fr'),(3,'عربي','ar');
/*!40000 ALTER TABLE `tbl_languages` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_notification_events`
--

DROP TABLE IF EXISTS `tbl_notification_events`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `tbl_notification_events` (
  `event_id` int(11) NOT NULL AUTO_INCREMENT,
  `event_name` varchar(50) NOT NULL DEFAULT '0',
  `web_notification_flag` tinyint(4) NOT NULL DEFAULT '0',
  `sms_notification_flag` tinyint(4) NOT NULL DEFAULT '0',
  `email_notification_flag` tinyint(4) NOT NULL DEFAULT '0',
  PRIMARY KEY (`event_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_notification_events`
--

LOCK TABLES `tbl_notification_events` WRITE;
/*!40000 ALTER TABLE `tbl_notification_events` DISABLE KEYS */;
INSERT INTO `tbl_notification_events` VALUES (1,'ADD_USER',1,0,0),(2,'UPDATE_USER',1,0,0),(3,'DELETE_USER',1,0,0);
/*!40000 ALTER TABLE `tbl_notification_events` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_notification_texts`
--

DROP TABLE IF EXISTS `tbl_notification_texts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `tbl_notification_texts` (
  `text_id` int(11) NOT NULL AUTO_INCREMENT,
  `event_id` int(11) NOT NULL DEFAULT '0',
  `language_id` int(11) NOT NULL DEFAULT '0',
  `text` varchar(200) NOT NULL DEFAULT '0',
  PRIMARY KEY (`text_id`),
  KEY `FKajfdd0f3dx7rgrt928uxc4glb` (`event_id`),
  KEY `FKaqajhefbhu4etl0q4dky99k6y` (`language_id`),
  CONSTRAINT `FKajfdd0f3dx7rgrt928uxc4glb` FOREIGN KEY (`event_id`) REFERENCES `tbl_notification_events` (`event_id`),
  CONSTRAINT `FKhrhsefhebr24wehdbnsc` FOREIGN KEY (`language_id`) REFERENCES `tbl_languages` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_notification_texts`
--

LOCK TABLES `tbl_notification_texts` WRITE;
/*!40000 ALTER TABLE `tbl_notification_texts` DISABLE KEYS */;
INSERT INTO `tbl_notification_texts` VALUES (1,1,1,'EEE User \"$USER_NAME$\" has been added by \"$USERNAME$\".'),(2,2,1,'EEE User \"$USER_NAME$\" has been updated by \"$USERNAME$\".'),(3,3,1,'EEE User \"$USER_NAME$\" has been removed by \"$USERNAME$\".'),(4,1,2,'FFF User \"$USER_NAME$\" has been added by \"$USERNAME$\".'),(5,2,2,'FFF User \"$USER_NAME$\" has been updated by \"$USERNAME$\".'),(6,3,2,'FFF User \"$USER_NAME$\" has been removed by \"$USERNAME$\".'),(7,1,3,'AAA User \"$USER_NAME$\" has been added by \"$USERNAME$\".'),(8,2,3,'AAA User \"$USER_NAME$\" has been updated by \"$USERNAME$\".'),(9,3,3,'AAA User \"$USER_NAME$\" has been removed by \"$USERNAME$\".');
/*!40000 ALTER TABLE `tbl_notification_texts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_outlet_category`
--

DROP TABLE IF EXISTS `tbl_outlet_category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `tbl_outlet_category` (
  `id` bigint(20) NOT NULL,
  `name` varchar(45) DEFAULT NULL,
  `CREATED_DATE` timestamp NULL DEFAULT NULL,
  `DELETED_DATE` timestamp NULL DEFAULT NULL,
  `UPDATED_DATE` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_outlet_category`
--

LOCK TABLES `tbl_outlet_category` WRITE;
/*!40000 ALTER TABLE `tbl_outlet_category` DISABLE KEYS */;
INSERT INTO `tbl_outlet_category` VALUES (1,'Category 1','2018-01-22 10:53:01',NULL,NULL),(2,'Category 2','2018-01-22 10:53:01',NULL,NULL);
/*!40000 ALTER TABLE `tbl_outlet_category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_outlet_offer_type`
--

DROP TABLE IF EXISTS `tbl_outlet_offer_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `tbl_outlet_offer_type` (
  `id` bigint(20) NOT NULL,
  `name` varchar(100) DEFAULT NULL,
  `CREATED_DATE` timestamp NULL DEFAULT NULL,
  `DELETED_DATE` timestamp NULL DEFAULT NULL,
  `UPDATED_DATE` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_outlet_offer_type`
--

LOCK TABLES `tbl_outlet_offer_type` WRITE;
/*!40000 ALTER TABLE `tbl_outlet_offer_type` DISABLE KEYS */;
INSERT INTO `tbl_outlet_offer_type` VALUES (1,'Type 1','2018-01-22 10:53:01',NULL,NULL),(2,'Type 2','2018-01-22 10:53:01',NULL,NULL);
/*!40000 ALTER TABLE `tbl_outlet_offer_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_pages`
--

DROP TABLE IF EXISTS `tbl_pages`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `tbl_pages` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_pages`
--

LOCK TABLES `tbl_pages` WRITE;
/*!40000 ALTER TABLE `tbl_pages` DISABLE KEYS */;
INSERT INTO `tbl_pages` VALUES (1,'DashBoard'),(2,'Settings');
/*!40000 ALTER TABLE `tbl_pages` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_pages_labels`
--

DROP TABLE IF EXISTS `tbl_pages_labels`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `tbl_pages_labels` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `page_id` int(11) NOT NULL,
  `label_id` int(11) NOT NULL,
  `label_name` varchar(45) DEFAULT NULL,
  `lang_id` int(11) NOT NULL,
  `label` varchar(45) NOT NULL,
  `label_level` int(11) NOT NULL DEFAULT '1',
  `index_legend` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=181 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_pages_labels`
--

LOCK TABLES `tbl_pages_labels` WRITE;
/*!40000 ALTER TABLE `tbl_pages_labels` DISABLE KEYS */;
INSERT INTO `tbl_pages_labels` VALUES (1,2,1,NULL,1,'SETTINGS ID',2,NULL),(2,2,2,NULL,1,'MSISDN LENGTH',2,NULL),(3,2,1,NULL,1,'General',1,NULL),(4,2,2,NULL,1,'LOGIN AUTHENTICATION',1,NULL),(5,2,1,NULL,2,'General FFF',1,NULL),(6,2,2,NULL,2,'LOGIN AUTHENTICATION FFF',1,NULL),(7,2,2,NULL,2,'MSISDN LENGTH FFF',2,NULL),(8,2,2,NULL,3,'MSISDN LENGTH AAA',2,NULL),(9,2,2,NULL,3,'LOGIN AUTHENTICATION AAA',1,NULL),(10,2,3,NULL,1,'LOCK ACCOUNT DURATION IN MINUTES EEE',2,NULL),(11,2,3,NULL,2,'LOCK ACCOUNT DURATION IN MINUTES FFF',2,NULL),(12,2,3,NULL,3,'LOCK ACCOUNT DURATION IN MINUTES AAA',2,NULL),(174,2,4,NULL,1,'NUMBER OF ATTEMPTS BETWEEN LOGIN IN SECONDS E',2,NULL),(175,2,4,NULL,2,'NUMBER OF ATTEMPTS BETWEEN LOGIN IN SECONDS F',2,NULL),(176,2,4,NULL,3,'NUMBER OF ATTEMPTS BETWEEN LOGIN IN SECONDS A',2,NULL),(177,2,5,NULL,1,'NUMBER OF ATTEMPTS PER LOGIN FAIL EEE',2,NULL),(178,2,5,NULL,2,'NUMBER OF ATTEMPTS PER LOGIN FAIL FFF',2,NULL),(179,2,5,NULL,3,'NUMBER OF ATTEMPTS PER LOGIN FAIL AAA',2,NULL),(180,2,1,NULL,3,'General AAA',1,NULL);
/*!40000 ALTER TABLE `tbl_pages_labels` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_persistent_login`
--

DROP TABLE IF EXISTS `tbl_persistent_login`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `tbl_persistent_login` (
  `SERIES` varchar(256) DEFAULT NULL,
  `USERNAME` varchar(256) DEFAULT NULL,
  `TOKEN` varchar(256) DEFAULT NULL,
  `LAST_USED` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_persistent_login`
--

LOCK TABLES `tbl_persistent_login` WRITE;
/*!40000 ALTER TABLE `tbl_persistent_login` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbl_persistent_login` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_reports`
--

DROP TABLE IF EXISTS `tbl_reports`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `tbl_reports` (
  `id` bigint(20) NOT NULL,
  `name` varchar(45) DEFAULT NULL,
  `proc_name` longtext,
  `last_query_contains_where` tinyint(4) NOT NULL DEFAULT '0',
  `last_query_contains_order` tinyint(4) NOT NULL DEFAULT '0',
  `last_query_contains_group` tinyint(4) NOT NULL DEFAULT '0',
  `chart_title` varchar(300) DEFAULT NULL,
  `chart_subtitle` varchar(300) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_reports`
--

LOCK TABLES `tbl_reports` WRITE;
/*!40000 ALTER TABLE `tbl_reports` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbl_reports` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_reports_filter`
--

DROP TABLE IF EXISTS `tbl_reports_filter`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `tbl_reports_filter` (
  `id` bigint(20) NOT NULL,
  `report_field` varchar(45) DEFAULT NULL,
  `field_type` varchar(45) DEFAULT NULL,
  `report_id` bigint(20) DEFAULT NULL,
  `display_name` varchar(45) DEFAULT NULL,
  `select_query` longtext,
  `field_index` varchar(45) DEFAULT NULL,
  `required` tinyint(4) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `gdfgsdfs_idx` (`report_id`),
  CONSTRAINT `fk_report_id` FOREIGN KEY (`report_id`) REFERENCES `tbl_reports` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_reports_filter`
--

LOCK TABLES `tbl_reports_filter` WRITE;
/*!40000 ALTER TABLE `tbl_reports_filter` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbl_reports_filter` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_reports_style`
--

DROP TABLE IF EXISTS `tbl_reports_style`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `tbl_reports_style` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_reports_style`
--

LOCK TABLES `tbl_reports_style` WRITE;
/*!40000 ALTER TABLE `tbl_reports_style` DISABLE KEYS */;
INSERT INTO `tbl_reports_style` VALUES (1,'Table'),(2,'Pie Chart'),(3,'Bar Chart'),(4,'Line Chart');
/*!40000 ALTER TABLE `tbl_reports_style` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_reports_style_join`
--

DROP TABLE IF EXISTS `tbl_reports_style_join`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `tbl_reports_style_join` (
  `report_id` bigint(20) NOT NULL,
  `report_style_id` int(11) NOT NULL,
  KEY `fk_report_style_id_idx` (`report_style_id`),
  KEY `fk_report_style_idasdwefs_idx` (`report_id`),
  CONSTRAINT `fk_report_style_id` FOREIGN KEY (`report_style_id`) REFERENCES `tbl_reports_style` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_report_style_idxx` FOREIGN KEY (`report_id`) REFERENCES `tbl_reports` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_reports_style_join`
--

LOCK TABLES `tbl_reports_style_join` WRITE;
/*!40000 ALTER TABLE `tbl_reports_style_join` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbl_reports_style_join` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_roles`
--

DROP TABLE IF EXISTS `tbl_roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `tbl_roles` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `ROLE` varchar(256) DEFAULT NULL,
  `IS_SYSTEM_ROLE` tinyint(4) DEFAULT '1',
  `ROLE_LABEL` varchar(256) DEFAULT 'Default',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_roles`
--

LOCK TABLES `tbl_roles` WRITE;
/*!40000 ALTER TABLE `tbl_roles` DISABLE KEYS */;
INSERT INTO `tbl_roles` VALUES (1,'INSTALLER',0,'Installer role. This role is hidden to the user.'),(2,'SUPPORT',0,'Support role. This role is hidden to the user.'),(3,'VIEW_REPORTS',1,'View Reports'),(4,'VIEW_USERS',1,'View System Users'),(5,'ADD_USERS',1,'Add System Users'),(6,'EDIT_USERS',1,'Edit System Users'),(7,'DELETE_USERS',1,'Delete System Users'),(8,'VIEW_DASHBOARD',1,'View Dashboard'),(9,'VIEW_GROUPS',1,'View Groups'),(10,'ADD_GROUPS',1,'Add Groups'),(11,'EDIT_GROUPS',1,'Edit Groups'),(12,'DELETE_GROUPS',1,'Delete Groups'),(13,'VIEW_SETTINGS',1,'View Settings'),(14,'ADD_SETTINGS',1,'Add Settings'),(15,'EDIT_SETTINGS',1,'Edit Settings'),(16,'DELETE_SETTINGS',1,'Delete Settings'),(17,'VIEW_BLACKLISTS',1,'View Blacklist'),(18,'ADD_BLACKLISTS',1,'Add Blacklist'),(19,'DELETE_BLACKLISTS',1,'Delete Blacklist');
/*!40000 ALTER TABLE `tbl_roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_settings`
--

DROP TABLE IF EXISTS `tbl_settings`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `tbl_settings` (
  `setting_id` int(11) NOT NULL AUTO_INCREMENT,
  `MSISDN_LENGTH` int(11) NOT NULL DEFAULT '0',
  `LOCK_ACCOUNT_DURATION` int(11) NOT NULL DEFAULT '0',
  `NUMBER_OF_SECONDS_BETWEEN_ATTEMPTS` int(11) NOT NULL DEFAULT '0',
  `NUMBER_OF_ATTEMPTS_PER_LOGIN_FAIL` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`setting_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_settings`
--

LOCK TABLES `tbl_settings` WRITE;
/*!40000 ALTER TABLE `tbl_settings` DISABLE KEYS */;
INSERT INTO `tbl_settings` VALUES (1,8,1,10,5);
/*!40000 ALTER TABLE `tbl_settings` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_settings_categories`
--

DROP TABLE IF EXISTS `tbl_settings_categories`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `tbl_settings_categories` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `display` int(2) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_settings_categories`
--

LOCK TABLES `tbl_settings_categories` WRITE;
/*!40000 ALTER TABLE `tbl_settings_categories` DISABLE KEYS */;
INSERT INTO `tbl_settings_categories` VALUES (1,'GENERAL',1),(2,'LOGIN AUTHENTICATION',1),(3,'CATEGORY_AUTOINC',0);
/*!40000 ALTER TABLE `tbl_settings_categories` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_settings_mapping`
--

DROP TABLE IF EXISTS `tbl_settings_mapping`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `tbl_settings_mapping` (
  `COLUMNID` int(11) NOT NULL AUTO_INCREMENT,
  `COLUMNNAME` varchar(255) NOT NULL,
  `COLUMNDESCRIPTION` varchar(255) NOT NULL,
  `LABELDISPLAY` varchar(255) NOT NULL,
  `FIELDTYPE` varchar(255) NOT NULL,
  `RELATEDCOLUMNS` int(11) NOT NULL DEFAULT '0',
  `COLUMNVALUE` varchar(255) NOT NULL DEFAULT '',
  `QUERYTEXT` varchar(500) NOT NULL DEFAULT '',
  `ENABLED` int(11) NOT NULL DEFAULT '0',
  `EDITABLE` int(11) NOT NULL DEFAULT '0',
  `SUBTABLENAME` varchar(255) NOT NULL DEFAULT '',
  `AUTOINC` int(11) NOT NULL DEFAULT '0',
  `UNIQUEVALUE` int(11) NOT NULL DEFAULT '0',
  `MANDATORY` int(11) NOT NULL DEFAULT '0',
  `RELATEDCOLNAME` varchar(50) NOT NULL DEFAULT '',
  `RELATEDAUTOINCCOLNAME` varchar(50) NOT NULL DEFAULT '',
  `COLUMNCATEGORY` varchar(50) NOT NULL DEFAULT 'GENERAL',
  `ISADMIN` int(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`COLUMNID`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_settings_mapping`
--

LOCK TABLES `tbl_settings_mapping` WRITE;
/*!40000 ALTER TABLE `tbl_settings_mapping` DISABLE KEYS */;
INSERT INTO `tbl_settings_mapping` VALUES (1,'SETTING_ID','AUTO INC COLUMN','SETTINGS ID','NUMBER',0,'','',1,0,'',1,0,0,'','','CATEGORY_AUTOINC',0),(2,'MSISDN_LENGTH','MSISDN_LENGTH','MSISDN LENGTH','NUMBER',0,'','',1,2,'',0,0,1,'','','GENERAL',0),(3,'LOCK_ACCOUNT_DURATION','LOCK_ACCOUNT_DURATION','LOCK ACCOUNT DURATION IN MINUTES','NUMBER',0,'','',1,2,'',0,0,1,'','','LOGIN AUTHENTICATION',0),(4,'NUMBER_OF_SECONDS_BETWEEN_ATTEMPTS','NUMBER_OF_SECONDS_BETWEEN_ATTEMPTS','NUMBER OF ATTEMPTS BETWEEN LOGIN IN SECONDS','NUMBER',0,'','',1,2,'',0,0,1,'','','LOGIN AUTHENTICATION',0),(5,'NUMBER_OF_ATTEMPTS_PER_LOGIN_FAIL','NUMBER_OF_ATTEMPTS_PER_LOGIN_FAIL','NUMBER OF ATTEMPTS PER LOGIN FAIL','NUMBER',0,'','',1,2,'',0,0,1,'','','LOGIN AUTHENTICATION',0);
/*!40000 ALTER TABLE `tbl_settings_mapping` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_user_attempts`
--

DROP TABLE IF EXISTS `tbl_user_attempts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `tbl_user_attempts` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `USER_PROFILE_ID` bigint(20) NOT NULL,
  `ATTEMPTS` smallint(1) NOT NULL,
  `LAST_MODIFIED` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`ID`),
  KEY `USER_ATTEMPTS_FK1` (`USER_PROFILE_ID`),
  CONSTRAINT `USER_ATTEMPTS_FK1` FOREIGN KEY (`USER_PROFILE_ID`) REFERENCES `tbl_user_profiles` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=1549300098550117 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_user_attempts`
--

LOCK TABLES `tbl_user_attempts` WRITE;
/*!40000 ALTER TABLE `tbl_user_attempts` DISABLE KEYS */;
INSERT INTO `tbl_user_attempts` VALUES (1,1,0,'2018-09-27 09:43:24');
/*!40000 ALTER TABLE `tbl_user_attempts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_user_company_info`
--

DROP TABLE IF EXISTS `tbl_user_company_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `tbl_user_company_info` (
  `id` bigint(20) NOT NULL,
  `COUNTRY` int(4) DEFAULT NULL,
  `INFO` varchar(100) DEFAULT NULL,
  `USER_ID` bigint(20) DEFAULT NULL,
  `CREATED_DATE` timestamp NULL DEFAULT NULL,
  `DELETED_DATE` timestamp NULL DEFAULT NULL,
  `UPDATED_DATE` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `USER_ID_COMPANY_INFO_idx` (`USER_ID`),
  CONSTRAINT `USER_ID_COMPANY_INFO` FOREIGN KEY (`USER_ID`) REFERENCES `tbl_user_profiles` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_user_company_info`
--

LOCK TABLES `tbl_user_company_info` WRITE;
/*!40000 ALTER TABLE `tbl_user_company_info` DISABLE KEYS */;
INSERT INTO `tbl_user_company_info` VALUES (1,0,'Info 1',3,'2018-01-22 10:53:01',NULL,NULL);
/*!40000 ALTER TABLE `tbl_user_company_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_user_company_info_images`
--

DROP TABLE IF EXISTS `tbl_user_company_info_images`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `tbl_user_company_info_images` (
  `id` bigint(20) NOT NULL,
  `PATH` varchar(500) DEFAULT NULL,
  `IMAGE_INDEX` int(4) DEFAULT NULL,
  `USER_COMPANY_INFO_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `USER_COMPANY_INFO_ID_KEY_idx` (`USER_COMPANY_INFO_ID`),
  CONSTRAINT `USER_COMPANY_INFO_ID_KEY` FOREIGN KEY (`USER_COMPANY_INFO_ID`) REFERENCES `tbl_user_company_info` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_user_company_info_images`
--

LOCK TABLES `tbl_user_company_info_images` WRITE;
/*!40000 ALTER TABLE `tbl_user_company_info_images` DISABLE KEYS */;
INSERT INTO `tbl_user_company_info_images` VALUES (1,'Image 1',1,1),(2,'Image 2',2,1),(3,'Image 3',3,1),(4,'Image 4',4,1);
/*!40000 ALTER TABLE `tbl_user_company_info_images` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_user_company_info_locations`
--

DROP TABLE IF EXISTS `tbl_user_company_info_locations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `tbl_user_company_info_locations` (
  `id` bigint(20) NOT NULL,
  `longitude` varchar(100) DEFAULT NULL,
  `latitude` varchar(100) DEFAULT NULL,
  `USER_COMPANY_INFO_ID` bigint(20) DEFAULT NULL,
  `LOCATION_INDEX` int(4) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `USER_COMPANY_INFO_ID_KEY2_idx` (`USER_COMPANY_INFO_ID`),
  CONSTRAINT `USER_COMPANY_INFO_ID_KEY2` FOREIGN KEY (`USER_COMPANY_INFO_ID`) REFERENCES `tbl_user_company_info` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_user_company_info_locations`
--

LOCK TABLES `tbl_user_company_info_locations` WRITE;
/*!40000 ALTER TABLE `tbl_user_company_info_locations` DISABLE KEYS */;
INSERT INTO `tbl_user_company_info_locations` VALUES (1,'111','111',1,1),(2,'222','222',1,2),(3,'333','333',1,3),(4,'444','444',1,4);
/*!40000 ALTER TABLE `tbl_user_company_info_locations` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_user_company_passes`
--

DROP TABLE IF EXISTS `tbl_user_company_passes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `tbl_user_company_passes` (
  `id` bigint(20) NOT NULL,
  `PASS_ID` bigint(20) DEFAULT NULL,
  `USER_COMPANY_INFO_ID` bigint(20) DEFAULT NULL,
  `NUMBER_OF_USERS` int(5) DEFAULT NULL,
  `CREATED_DATE` timestamp NULL DEFAULT NULL,
  `DELETED_DATE` timestamp NULL DEFAULT NULL,
  `UPDATED_DATE` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `PASS_ID_KEY1_idx` (`PASS_ID`),
  KEY `USER_COMPANY_ID_KEY3_idx` (`USER_COMPANY_INFO_ID`),
  CONSTRAINT `PASS_ID_KEY1` FOREIGN KEY (`PASS_ID`) REFERENCES `tbl_admin_passes` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `USER_COMPANY_ID_KEY3` FOREIGN KEY (`USER_COMPANY_INFO_ID`) REFERENCES `tbl_user_company_info` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_user_company_passes`
--

LOCK TABLES `tbl_user_company_passes` WRITE;
/*!40000 ALTER TABLE `tbl_user_company_passes` DISABLE KEYS */;
INSERT INTO `tbl_user_company_passes` VALUES (1,1,1,1000,'2018-01-22 10:53:01',NULL,NULL),(2,2,1,100,'2018-01-22 10:53:01',NULL,NULL);
/*!40000 ALTER TABLE `tbl_user_company_passes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_user_outlet_info`
--

DROP TABLE IF EXISTS `tbl_user_outlet_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `tbl_user_outlet_info` (
  `id` bigint(20) NOT NULL,
  `COUNTRY` varchar(45) DEFAULT NULL,
  `INFO` varchar(45) DEFAULT NULL,
  `USER_ID` bigint(20) DEFAULT NULL,
  `CREATED_DATE` timestamp NULL DEFAULT NULL,
  `DELETED_DATE` timestamp NULL DEFAULT NULL,
  `UPDATED_DATE` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `USER_ID_OUTLET_INFO_idx` (`USER_ID`),
  CONSTRAINT `USER_ID_OUTLET_INFO` FOREIGN KEY (`USER_ID`) REFERENCES `tbl_user_profiles` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_user_outlet_info`
--

LOCK TABLES `tbl_user_outlet_info` WRITE;
/*!40000 ALTER TABLE `tbl_user_outlet_info` DISABLE KEYS */;
INSERT INTO `tbl_user_outlet_info` VALUES (1,'Country 1','Outlet 1',4,'2018-01-22 10:53:01',NULL,NULL);
/*!40000 ALTER TABLE `tbl_user_outlet_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_user_outlet_info_category`
--

DROP TABLE IF EXISTS `tbl_user_outlet_info_category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `tbl_user_outlet_info_category` (
  `USER_OUTLET_ID` bigint(20) DEFAULT NULL,
  `OUTLET_CATEGORY_ID` bigint(20) DEFAULT NULL,
  KEY `FK32525423421342_idx` (`USER_OUTLET_ID`),
  KEY `FK8758734452347862_idx` (`OUTLET_CATEGORY_ID`),
  CONSTRAINT `FK32525423421342` FOREIGN KEY (`USER_OUTLET_ID`) REFERENCES `tbl_user_outlet_info` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK8758734452347862` FOREIGN KEY (`OUTLET_CATEGORY_ID`) REFERENCES `tbl_outlet_category` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_user_outlet_info_category`
--

LOCK TABLES `tbl_user_outlet_info_category` WRITE;
/*!40000 ALTER TABLE `tbl_user_outlet_info_category` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbl_user_outlet_info_category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_user_outlet_info_images`
--

DROP TABLE IF EXISTS `tbl_user_outlet_info_images`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `tbl_user_outlet_info_images` (
  `id` bigint(20) NOT NULL,
  `PATH` varchar(500) DEFAULT NULL,
  `IMAGE_INDEX` int(4) DEFAULT NULL,
  `USER_OUTLET_INFO_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `USER_OUTLET_INFO_ID_KEY_idx` (`USER_OUTLET_INFO_ID`),
  CONSTRAINT `USER_OUTLET_INFO_ID_KEY` FOREIGN KEY (`USER_OUTLET_INFO_ID`) REFERENCES `tbl_user_outlet_info` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_user_outlet_info_images`
--

LOCK TABLES `tbl_user_outlet_info_images` WRITE;
/*!40000 ALTER TABLE `tbl_user_outlet_info_images` DISABLE KEYS */;
INSERT INTO `tbl_user_outlet_info_images` VALUES (1,'Image 1',1,1),(2,'Image 2',2,1),(3,'Image 3',3,1),(4,'Image 4',4,1);
/*!40000 ALTER TABLE `tbl_user_outlet_info_images` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_user_outlet_info_locations`
--

DROP TABLE IF EXISTS `tbl_user_outlet_info_locations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `tbl_user_outlet_info_locations` (
  `id` bigint(20) NOT NULL,
  `longitude` varchar(100) DEFAULT NULL,
  `latitude` varchar(100) DEFAULT NULL,
  `USER_OUTLET_INFO_ID` bigint(20) DEFAULT NULL,
  `LOCATION_INDEX` int(4) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `USER_OUTLET_INFO_ID_KEY2_idx` (`USER_OUTLET_INFO_ID`),
  CONSTRAINT `USER_OUTLET_INFO_ID_KEY2` FOREIGN KEY (`USER_OUTLET_INFO_ID`) REFERENCES `tbl_user_outlet_info` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_user_outlet_info_locations`
--

LOCK TABLES `tbl_user_outlet_info_locations` WRITE;
/*!40000 ALTER TABLE `tbl_user_outlet_info_locations` DISABLE KEYS */;
INSERT INTO `tbl_user_outlet_info_locations` VALUES (1,'111','111',1,1),(2,'222','222',1,2),(3,'333','333',1,3),(4,'444','444',1,4);
/*!40000 ALTER TABLE `tbl_user_outlet_info_locations` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_user_outlet_offer`
--

DROP TABLE IF EXISTS `tbl_user_outlet_offer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `tbl_user_outlet_offer` (
  `id` bigint(20) NOT NULL,
  `VALIDITY` int(5) DEFAULT NULL,
  `NUMBER_OF_USAGE` int(5) DEFAULT NULL,
  `TYPE_OF_USAGE` int(4) DEFAULT NULL,
  `TYPE` bigint(20) DEFAULT NULL,
  `USER_OUTLET_INFO_ID` bigint(20) DEFAULT NULL,
  `CREATED_DATE` timestamp NULL DEFAULT NULL,
  `DELETED_DATE` timestamp NULL DEFAULT NULL,
  `UPDATED_DATE` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK458438782952987_idx` (`TYPE`),
  KEY `FK9999328579224_idx` (`USER_OUTLET_INFO_ID`),
  CONSTRAINT `FK458438782952987` FOREIGN KEY (`TYPE`) REFERENCES `tbl_outlet_offer_type` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK9999328579224` FOREIGN KEY (`USER_OUTLET_INFO_ID`) REFERENCES `tbl_user_outlet_info` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_user_outlet_offer`
--

LOCK TABLES `tbl_user_outlet_offer` WRITE;
/*!40000 ALTER TABLE `tbl_user_outlet_offer` DISABLE KEYS */;
INSERT INTO `tbl_user_outlet_offer` VALUES (1,30,5,1,1,1,'2018-01-22 10:53:01',NULL,NULL);
/*!40000 ALTER TABLE `tbl_user_outlet_offer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_user_outlet_offer_used`
--

DROP TABLE IF EXISTS `tbl_user_outlet_offer_used`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `tbl_user_outlet_offer_used` (
  `id` bigint(20) NOT NULL,
  `USED_DATE` timestamp NULL DEFAULT NULL,
  `COUNTER` int(5) DEFAULT NULL,
  `NEXT_RESET_DATE` timestamp NULL DEFAULT NULL,
  `USER_ID` bigint(20) DEFAULT NULL,
  `OUTLET_OFFER_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK63475685235346_idx` (`USER_ID`),
  KEY `FK8754385787283577963_idx` (`OUTLET_OFFER_ID`),
  CONSTRAINT `FK63475685235346` FOREIGN KEY (`USER_ID`) REFERENCES `tbl_user_profiles` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK8754385787283577963` FOREIGN KEY (`OUTLET_OFFER_ID`) REFERENCES `tbl_user_outlet_offer` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_user_outlet_offer_used`
--

LOCK TABLES `tbl_user_outlet_offer_used` WRITE;
/*!40000 ALTER TABLE `tbl_user_outlet_offer_used` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbl_user_outlet_offer_used` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_user_pass_purchased`
--

DROP TABLE IF EXISTS `tbl_user_pass_purchased`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `tbl_user_pass_purchased` (
  `id` bigint(20) NOT NULL,
  `USER_ID` bigint(20) DEFAULT NULL,
  `USER_COMPANY_PASS_ID` bigint(20) DEFAULT NULL,
  `IS_PAID` tinyint(4) DEFAULT NULL,
  `VALID_TILL` timestamp NULL DEFAULT NULL,
  `STATUS` int(4) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `USER_ID_USER_PASS_PURCH_idx` (`USER_ID`),
  KEY `USER_PASS_PUR_USER_COMP_idx` (`USER_COMPANY_PASS_ID`),
  CONSTRAINT `USER_ID_USER_PASS_PURCH` FOREIGN KEY (`USER_ID`) REFERENCES `tbl_user_profiles` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `USER_PASS_PUR_USER_COMP` FOREIGN KEY (`USER_COMPANY_PASS_ID`) REFERENCES `tbl_user_company_passes` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_user_pass_purchased`
--

LOCK TABLES `tbl_user_pass_purchased` WRITE;
/*!40000 ALTER TABLE `tbl_user_pass_purchased` DISABLE KEYS */;
INSERT INTO `tbl_user_pass_purchased` VALUES (1,5,1,1,'2018-01-22 10:53:01',1);
/*!40000 ALTER TABLE `tbl_user_pass_purchased` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_user_profile_groups`
--

DROP TABLE IF EXISTS `tbl_user_profile_groups`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `tbl_user_profile_groups` (
  `USER_PROFILE_ID` bigint(20) NOT NULL,
  `GROUP_ID` bigint(20) NOT NULL,
  KEY `fk_user_profile` (`USER_PROFILE_ID`),
  KEY `fk_group` (`GROUP_ID`),
  CONSTRAINT `fk_group` FOREIGN KEY (`GROUP_ID`) REFERENCES `tbl_groups` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_user_profile` FOREIGN KEY (`USER_PROFILE_ID`) REFERENCES `tbl_user_profiles` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_user_profile_groups`
--

LOCK TABLES `tbl_user_profile_groups` WRITE;
/*!40000 ALTER TABLE `tbl_user_profile_groups` DISABLE KEYS */;
INSERT INTO `tbl_user_profile_groups` VALUES (2,1),(1,3);
/*!40000 ALTER TABLE `tbl_user_profile_groups` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_user_profiles`
--

DROP TABLE IF EXISTS `tbl_user_profiles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `tbl_user_profiles` (
  `ID` bigint(20) NOT NULL,
  `NAME` varchar(64) NOT NULL,
  `EMAIL` varchar(256) NOT NULL,
  `PASSWORD` varchar(256) NOT NULL,
  `ENABLED` tinyint(4) NOT NULL DEFAULT '1',
  `ACCOUNT_EXPIRED` tinyint(4) NOT NULL DEFAULT '0',
  `ACCOUNT_LOCKED` tinyint(4) NOT NULL DEFAULT '0',
  `CREDENTIAL_EXPIRED` tinyint(4) NOT NULL DEFAULT '0',
  `CREATED_DATE` timestamp NULL DEFAULT NULL,
  `RESET_PASSWORD_TOKEN` varchar(200) DEFAULT NULL,
  `RESET_PASSWORD_TOKEN_VALIDITY` timestamp NULL DEFAULT NULL,
  `UPDATED_DATE` timestamp NULL DEFAULT NULL,
  `DELETED_DATE` timestamp NULL DEFAULT NULL,
  `JOB_TITLE` varchar(64) DEFAULT NULL,
  `MOBILE_NUMBER` varchar(20) DEFAULT NULL,
  `LANGUAGE_ID` int(11) NOT NULL DEFAULT '1',
  `LAST_NAME` varchar(64) NOT NULL,
  `TYPE` int(4) NOT NULL,
  `QR_CODE_PATH` varchar(500) NOT NULL,
  `PARENT_ID` bigint(20) DEFAULT NULL,
  `COUNTRY` int(4) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_LANGUAGE_USER_idx` (`LANGUAGE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_user_profiles`
--

LOCK TABLES `tbl_user_profiles` WRITE;
/*!40000 ALTER TABLE `tbl_user_profiles` DISABLE KEYS */;
INSERT INTO `tbl_user_profiles` VALUES (1,'System User','sysuser@apliman.com','$2a$10$Wet4W6yJnVM6bU8QFX75kuNoxGXnbx/kk5oJI05FAWTIYEpS.Ufc2',1,1,1,1,'2018-01-22 10:53:01',NULL,NULL,'2018-09-27 09:44:25',NULL,'System','12345676',1,'',0,'',NULL,NULL),(2,'Installer','installer@apliman.com','$2a$10$Wet4W6yJnVM6bU8QFX75kuNoxGXnbx/kk5oJI05FAWTIYEpS.Ufc2',1,1,1,1,'2018-01-22 10:53:01',NULL,NULL,NULL,NULL,NULL,NULL,1,'',0,'',NULL,NULL),(3,'Company 1','company1@apliman.com','$2a$10$Wet4W6yJnVM6bU8QFX75kuNoxGXnbx/kk5oJI05FAWTIYEpS.Ufc2',1,1,1,1,'2018-01-22 10:53:01',NULL,NULL,NULL,NULL,'System','12345676',1,'',1,'',NULL,NULL),(4,'Outlet 1','outlet1@apliman.com','$2a$10$Wet4W6yJnVM6bU8QFX75kuNoxGXnbx/kk5oJI05FAWTIYEpS.Ufc2',1,1,1,1,'2018-01-22 10:53:01',NULL,NULL,NULL,NULL,'System','12345676',1,'',2,'',NULL,NULL),(5,'User Under Company 1','companyUser1@apliman.com','$2a$10$Wet4W6yJnVM6bU8QFX75kuNoxGXnbx/kk5oJI05FAWTIYEpS.Ufc2',1,1,1,1,'2018-01-22 10:53:01',NULL,NULL,NULL,NULL,'System','12345676',1,'',3,'',3,NULL);
/*!40000 ALTER TABLE `tbl_user_profiles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_userprofile_notification_event`
--

DROP TABLE IF EXISTS `tbl_userprofile_notification_event`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `tbl_userprofile_notification_event` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `NOTIFICATION_EVENT_ID` int(11) NOT NULL,
  `USER_ID` bigint(20) NOT NULL,
  `enabled` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FKhbfuhas87y23rhjfhfew734rfs_idx` (`NOTIFICATION_EVENT_ID`),
  KEY `FKhbfuybajsnbf8278rwjnsf87wfs_idx` (`USER_ID`),
  CONSTRAINT `FKhbfuhas87y23rhjfhfew734rfs` FOREIGN KEY (`NOTIFICATION_EVENT_ID`) REFERENCES `tbl_notification_events` (`event_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FKhbfuybajsnbf8278rwjnsf87wfs` FOREIGN KEY (`USER_ID`) REFERENCES `tbl_user_profiles` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_userprofile_notification_event`
--

LOCK TABLES `tbl_userprofile_notification_event` WRITE;
/*!40000 ALTER TABLE `tbl_userprofile_notification_event` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbl_userprofile_notification_event` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_web_notifications`
--

DROP TABLE IF EXISTS `tbl_web_notifications`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `tbl_web_notifications` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `date_added` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `user_id` bigint(20) NOT NULL,
  `seen_flag` tinyint(4) NOT NULL DEFAULT '0',
  `text` varchar(255) DEFAULT NULL,
  `href` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK9k06vkj2qt5sekqw657j059tp` (`user_id`),
  CONSTRAINT `fk_userId1234124` FOREIGN KEY (`user_id`) REFERENCES `tbl_user_profiles` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_web_notifications`
--

LOCK TABLES `tbl_web_notifications` WRITE;
/*!40000 ALTER TABLE `tbl_web_notifications` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbl_web_notifications` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-02-17 20:50:34
