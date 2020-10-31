drop table if exists tasks;
drop table if exists users;

create table users (
 user_id int auto_increment primary key,
 name varchar(20) not null,
 active boolean not null
);

create table tasks (
	task_id int auto_increment primary key,
	user_id int not null,
	description varchar(50) not null,
	completed boolean not null,
	
	constraint fk_user_id foreign key (user_id) references users(user_id)
);

insert into users (name, active) values
('Anne', true), 
('Bob', true), 
('Cecil', true);

insert into tasks (user_id, description, completed) values
(1, 'task #1 that belongs to Anne', true),
(1, 'task #2 that belongs to Anne', false),
(1, 'task #3 that belongs to Anne', false),

(2, 'task #1 that belongs to Bob', false),
(2, 'task #2 that belongs to Bob', true),
(2, 'task #3 that belongs to Bob', false),

(3, 'task #1 that belongs to Cecil', false),
(3, 'task #2 that belongs to Cecil', false),
(3, 'task #3 that belongs to Cecil', true);

