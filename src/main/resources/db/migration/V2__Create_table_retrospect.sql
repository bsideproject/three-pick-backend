CREATE TABLE `RETROSPECT`
(
    `id`              bigint       NOT NULL AUTO_INCREMENT,
    `account_id`      bigint       NOT NULL,
    `content`         VARCHAR(500) NOT NULL,
    `retrospect_date` date         NOT NULL DEFAULT current_timestamp(),
    `created_date`    datetime     NOT NULL DEFAULT current_timestamp(),
    `modified_date`   datetime     NOT NULL DEFAULT current_timestamp(),
    PRIMARY KEY (`id`)
);

CREATE UNIQUE INDEX IF NOT EXISTS idx_retrospect_account_id_retrospect_date ON RETROSPECT(account_id, retrospect_date)
