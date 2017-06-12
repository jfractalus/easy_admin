CREATE TABLE IF NOT EXISTS `player` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `age` int(11) NOT NULL,
  `birthday` date DEFAULT NULL,
  `last_seen` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

INSERT INTO `player` VALUES (1, 'Ronaldo', 35, '1982-01-15', '2017-05-18 14:28:19');
INSERT INTO `access_table` VALUES (6,'ROLE_TECH','/player',1,1,1,1,1);