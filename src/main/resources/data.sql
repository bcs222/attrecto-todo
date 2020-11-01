drop table if exists users_roles;
drop table if exists roles;
drop table if exists tasks;
drop table if exists users;

create table roles (
	role_id int auto_increment primary key,
	name varchar(20) not null,
	admin_role boolean	
);

create table users (
	user_id int auto_increment primary key,
	name varchar(20) not null,
	active boolean not null
);

create table users_roles (
	user_id int not null,
	role_id int not null,
	
	constraint fk_ur_user_id foreign key (user_id) references users(user_id),
	constraint fk_ur_role_id foreign key (role_id) references roles(role_id),
	
	primary key (user_id, role_id)
);

create table tasks (
	task_id int auto_increment primary key,
	user_id int not null,
	description varchar(50) not null,
	completed boolean not null,
	deadline date,
	
	constraint fk_user_id foreign key (user_id) references users(user_id)
);

insert into roles (name, admin_role) values
('ADMIN', true),
('EMP', false);

insert into users (name, active) values
('Anne', true), 
('Bob', true), 
('Cecil', true);

insert into users_roles (user_id, role_id) values
(1, 1),
(1, 2),
(2, 2),
(3, 2);

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

