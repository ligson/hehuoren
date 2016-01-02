
create table history (
id int primary key auto_increment,
historyType enum('LOGIN','ACCOUNT'),
whoclassName varchar(50),
whoName varchar(50),
whoId int,
title varchar(100),
relDate datetime,
ip varchar(50),
extraData varchar(100),
addressStr varchar(100)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

