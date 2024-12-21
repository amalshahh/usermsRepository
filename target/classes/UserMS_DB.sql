drop schema if exists Estimation_DB;
create schema Estimation_DB;
use Estimation_DB;


create table Login(
login_id int(4) Primary key auto_increment,
email_id varchar(40),
password varchar(17),
login_status boolean
);


create table Users(
user_id int(4) primary key auto_increment,
first_name varchar(50),
last_name varchar(50),
job_role varchar(20),
phone_no numeric(10),
-- check (length(phone_no)=10),
security_q1 varchar(100) not null,
security_a1 varchar(25),
login_id int(4), FOREIGN KEY (login_id) REFERENCES Login(login_id),
user_status boolean 
);




create table components(
comp_id int(4) primary key auto_increment,
comp_name varchar(50) unique key,
comp_remarks varchar(100),
published int(2),
created_on datetime,
updated_on datetime
);

create table complexity(
complex_id int(4) primary key auto_increment,
complex_name varchar (50)unique key,
complex_remarks varchar(100),
published int(1),
created_on datetime,
updated_on datetime
);


create table tech(
tech_id int(4) primary key auto_increment,
tech_name varchar(50)unique key,
remarks varchar(100),
published int(1),
created_on datetime,
updated_on datetime
);


create table estimationbasis(
esti_id int(4)primary key auto_increment,
tech_name varchar(50),
FOREIGN KEY(tech_name) REFERENCES tech(tech_name),
comp_name varchar(50) ,
FOREIGN KEY(comp_name) REFERENCES components(comp_name),
complex_name varchar(50),
FOREIGN KEY(complex_name) REFERENCES complexity(complex_name),
hours decimal(4,2),
esti_remarks varchar(100),
published int(1)
);

create table estimation(
estim_id int(4) primary key auto_increment,
estimation_name varchar(50),
tech_name varchar(50),
total_estimate decimal(4,2),
effort_total decimal(4,2),
published boolean
);


create table features(
feature_id int(4) primary key auto_increment,
feature_name varchar(50),
feature_description varchar(100),
estim_id int(4)
);



create table sub_tasks(
sub_id int(4)primary key auto_increment,
subtask_name varchar(50),
subtask_description varchar(100),
comp_name varchar(50),
c int(2),
vc int(2),
m int(2),
s int(2),
vs int(2),
subtask_effort decimal(4,2),
feature_id int(4)
);

create table estimationsummarytable(
id int(4) primary key auto_increment,
estimation_id int(4),
estimation_name varchar(50),
summary_details json ,
total_effort decimal(4,2),
total_effortPDWithC decimal(4,2),
total_effortPMWithC decimal(4,2),
total_effortPM decimal(4,2)
);

select * from Login;
select * from Users;
select * from components;
select * from complexity;
select * from tech;
select * from estimationbasis;
select * from estimation;
select * from features;
select * from sub_tasks;
select * from estimationsummarytable;