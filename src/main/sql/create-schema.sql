SET FOREIGN_KEY_CHECKS=0;
DROP TABLE IF EXISTS `Business`;

--
-- Table structure for table `Business`
--
CREATE TABLE `Search` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `term` varchar(255) DEFAULT NULL UNIQUE,
  PRIMARY KEY (`id`),
  KEY (`term`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;


SET FOREIGN_KEY_CHECKS=1;