create table account (
     id bigint not null auto_increment,
     created_date datetime(6),
     modified_date datetime(6),
     email varchar(255) not null,
     nick_name varchar(255),
     password varchar(255),
     signup_type varchar(255) not null,
     time_value integer,
     primary key (id)
 ) engine=InnoDB;

alter table account add constraint UK_ACCOUNT_EMAIL unique (email);
