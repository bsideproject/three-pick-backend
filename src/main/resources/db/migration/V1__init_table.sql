CREATE TABLE IF NOT EXISTS `account`
(
     `id`                       bigint         NOT NULL auto_increment,
     `email`                    varchar(255)   NOT NULL,
     `password`                 varchar(255),
     `nick_name`                varchar(255),
     `time_value`               integer,
     `next_time_value`          integer        DEFAULT '0',
     `next_time_value_date`     date,
     `change_count`             integer DEFAULT '0',
     `signup_type`              varchar(255)   NOT NULL,
     `status`                   varchar(255)   NOT NULL DEFAULT 'ACTIVE',
     `coach_mark`               bit(1)         NOT NULL DEFAULT b'0',
     `last_login_date`          date,
     `created_date`             datetime       NOT NULL DEFAULT current_timestamp ,
     `modified_date`            datetime       NOT NULL DEFAULT current_timestamp ,
     primary key (id)
);
CREATE UNIQUE INDEX UK_ACCOUNT_EMAIL ON account(email);

CREATE TABLE IF NOT EXISTS `retrospect`
(
     `id`              bigint       NOT NULL AUTO_INCREMENT,
     `account_id`      bigint       NOT NULL,
     `content`         varchar(500) NOT NULL,
     `retrospect_date` date         NOT NULL DEFAULT (current_date),
     `created_date`    datetime     NOT NULL DEFAULT current_timestamp ,
     `modified_date`   datetime     NOT NULL DEFAULT current_timestamp ,
     PRIMARY KEY (`id`)
);
CREATE UNIQUE INDEX idx_retrospect_account_id_retrospect_date ON retrospect(account_id, retrospect_date);
