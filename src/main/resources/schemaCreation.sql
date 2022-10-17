-- Script to create the test environment

-- run each of these statements one by one
create schema graphql_hibernate_reactive_test;

use graphql_hibernate_reactive_test;

create table parent (
	id int not null auto_increment primary key,
	name varchar(64) default null
);

create table child (
	id int not null auto_increment primary key,
 	name varchar(64) default null,
 	parent_id int not null,
 	foreign key (parent_id) references parent(id)
);
