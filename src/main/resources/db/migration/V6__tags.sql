CREATE TABLE IF NOT EXISTS `tags` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS `tutorial_tags` (
  `tutorial_id` bigint NOT NULL,
  `tag_id` bigint NOT NULL,
  PRIMARY KEY (`tutorial_id`, `tag_id`)
);

ALTER TABLE `tutorial_tags`
ADD FOREIGN KEY (tutorial_id) REFERENCES tutorials(id);
ALTER TABLE `tutorial_tags`
ADD FOREIGN KEY (tag_id) REFERENCES tags(id);