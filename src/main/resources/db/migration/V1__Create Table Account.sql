create table account (
     id bigint not null auto_increment,
     created_date datetime(6),
     modified_date datetime(6),
     email varchar(255) not null,
     nick_name varchar(255),
     password varchar(255),
     signup_type varchar(255) not null,
     status varchar(255) default 'ACTIVE' not null,
     last_login_date datetime(6),
     time_value integer,
     total_value integer,
     next_time_value integer default '0',
     next_time_value_date datetime(6),
     change_count integer default '0',
     primary key (id)
 ) engine=InnoDB;

alter table account add constraint UK_ACCOUNT_EMAIL unique (email);
