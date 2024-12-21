drop schema if exists UserMS_DB;
create schema UserMS_DB;
use UserMS_DB;

 

 

drop table if exists Login;
create table Login(
login_id int(4) Primary key auto_increment,
emailId varchar(60),
p_word varchar(50)
);
commit;

 

drop table if exists Sign_Up;
create table Sign_Up(
user_id int(4) primary key auto_increment,
first_name varchar(20),
last_name varchar(20),
job_designation varchar(30),
job_role varchar(20),
phone_no numeric(10) check (length(phone_no)=10),
security_Q1 varchar(150),
security_A1 varchar(50),
security_Q2 varchar(150),
security_A2 varchar(50),
login_id int(4),
FOREIGN KEY (login_id) REFERENCES Login(login_id));

 

commit;

 

 

-- INSERT INTO Sign_Up(user_id,first_name,last_name,job_designation,job_Role,phone_no,security_Q1,security_A1,security_Q2,security_A2) VALUES(1000,'MathuKutty','Johnson','SA','Estimator',8912346211,'What is your favourite colour?','green','Favourite Food?','dosa');
-- INSERT INTO Sign_Up(user_id,first_name,last_name,job_designation,job_Role,phone_no,security_Q1,security_A1,security_Q2,security_A2) VALUES(1001,'Ajai','Shaji','TA','Reviewer',8899776688,'What is your favourite colour?','red','Favourite Food?','Idly');
-- INSERT INTO Sign_Up(user_id,first_name,last_name,job_designation,job_Role,phone_no,security_Q1,security_A1,security_Q2,security_A2) VALUES(1003,'Dawn','Jo','SPM','Admin',8822556677,'What is your favourite colour?','black','Favourite Food?','puttum kadalayum');

 

 

 


-- Insert into Login (login_id,emailId,p_word) values (1000,'mathu123@gmail.com','mathu@123');
-- Insert into Login (Login_id,emailId,p_word) values (1001,'ajai123@gmail.com','ajai@123');
-- Insert into Login (Login_id,emailId,p_word) values (1002,'dawn123@gmail.com','dawn@123');

 

 

select * from Login;
select * from Sign_Up;
-- select
