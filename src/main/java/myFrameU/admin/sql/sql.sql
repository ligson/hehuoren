create table admin(
id int primary key auto_increment,
name varchar(50) not null,
password varchar(16) not null,
superOrCom int default 0,
status int default 1,
addressId int,
addressTreeIds varchar(),
cityName varchar(30),
isCanAccount int default 0,
telPhone varchar(20),
wxId varchar(100)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
 
