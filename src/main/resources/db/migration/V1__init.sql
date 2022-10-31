CREATE TABLE IF NOT EXISTS `products` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `price` double DEFAULT NULL,
  `productName` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
);