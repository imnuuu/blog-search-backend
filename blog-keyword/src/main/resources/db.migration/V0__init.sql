CREATE TABLE IF NOT EXISTS `keyword`
(
    `id`            BIGINT NOT NULL AUTO_INCREMENT,
    `keyword`       VARCHAR(2500)   NOT NULL,
    `count`         BIGINT          DEFAULT 0,
    `create_at`     TIMESTAMP       DEFAULT CURRENT_TIMESTAMP,
    `update_at`     TIMESTAMP       DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`)
);

CREATE INDEX keyword_idx01 ON `keyword` (`create_at`);
CREATE INDEX keyword_idx02 ON `keyword` (`update_at`);
ALTER TABLE `keyword` ADD UNIQUE KEY `UK_keyword_1` (`keyword`);