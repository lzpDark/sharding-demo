drop database db_sharding0;
drop database db_sharding1;

create database if not exists db_sharding0;
use db_sharding0;
create table if not exists t_order0 (order_id bigint primary key, user_id bigint not null, name varchar(20) default "");
create table if not exists t_order1 (order_id bigint primary key, user_id bigint not null, name varchar(20) default "");

create database if not exists db_sharding1;
use db_sharding1;
create table if not exists t_order0 (order_id bigint primary key, user_id bigint not null, name varchar(20) default "");
create table if not exists t_order1 (order_id bigint primary key, user_id bigint not null, name varchar(20) default "");