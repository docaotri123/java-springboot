CREATE TABLE IF NOT EXISTS `comments` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `content` varchar(255) DEFAULT NULL,
  `tutorial_id` bigint,
  PRIMARY KEY (`id`)
);

ALTER TABLE `comments`
ADD FOREIGN KEY (tutorial_id) REFERENCES tutorials(id);