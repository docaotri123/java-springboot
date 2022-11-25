CREATE TABLE IF NOT EXISTS `tutorials` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `title` double DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `tutorial_detail_id` bigint,
  PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS `tutorial_details` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_by` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
);

ALTER TABLE `tutorials`
ADD FOREIGN KEY (tutorial_detail_id) REFERENCES tutorial_details(id);