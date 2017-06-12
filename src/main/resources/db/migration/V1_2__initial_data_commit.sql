set foreign_key_checks=0;

INSERT INTO `user_roles` VALUES (1,'ROLE_ADMIN'),(2,'ROLE_USER'),(3,'ROLE_QA'), (4,'ROLE_TECH');

INSERT INTO `access_table` VALUES (1,'ROLE_ADMIN','/user',1,1,1,1,1), (2,'ROLE_ADMIN','/access',1,1,1,1,1), (3,'ROLE_ADMIN','/setting',1,1,1,1,1), (4,'ROLE_USER','/client',1,1,1,1,1), (5,'ROLE_USER','/message',1,1,1,1,1);

INSERT INTO `client` VALUES (1,30,'Petr','ADMIN'),(2,20,'Alex','USER');

INSERT INTO `message` VALUES (1,'code1','2017-05-13 00:00:00',0,'2017-05-03','<p>f</p>',1),(2,'code2','2017-05-18 14:28:19',12,'2017-05-18','test',2),(3,'code3','2017-05-18 14:28:55',1,'2017-05-18','12',2);

INSERT INTO `system_user` VALUES (1,1,'admin','wxTl+RhHmyzKHLOccb6Jgw=='),(2,1,'user','wxTl+RhHmyzKHLOccb6Jgw=='),(3,1,'qa','wxTl+RhHmyzKHLOccb6Jgw=='), (4,1,'tech','wxTl+RhHmyzKHLOccb6Jgw==');

set foreign_key_checks=1;