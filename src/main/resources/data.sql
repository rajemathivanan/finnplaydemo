create database finnplaydemo;


create table finnplaydemo.app_user(user_id int  not null auto_increment,
first_name varchar(100) not null,
last_name varchar(100),
password varchar(100) not null,
email varchar(100) not null, 
birth_date date,
constraint pk_user primary key(user_id));