create table account (
     id bigint not null auto_increment,
     email varchar(255) not null,
     password varchar(255),
     nick_name varchar(255),
     time_value integer,
     next_time_value integer default '0',
     next_time_value_date datetime(6),
     change_count integer default '0',
     signup_type varchar(255) not null,
     status varchar(255) not null default 'ACTIVE',
     coach_mark bit(1) not null default b'0',
     last_login_date datetime(6),
     created_date datetime not null default current_timestamp ,
     modified_date datetime not null default current_timestamp ,
     primary key (id)
 ) engine=InnoDB;

alter table account add constraint UK_ACCOUNT_EMAIL unique (email);
