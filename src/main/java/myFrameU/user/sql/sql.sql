
/**
 * 用户表
 */
drop table user_user;
create table user_user (
id int primary key auto_increment,
name varchar(20) not null,
bianhao varchar(20) not null,
password varchar(32),
nicheng varchar(20),
telPhone varchar(20),
phone varchar(20),
email varchar(50),
zhifubao varchar(30),
touxiang varchar(50),
qq varchar(20),
msn varchar(50),
sex int default 1,
age int,
status enum('OK','FORZEN'),
zhuceDate datetime,
zhuceIp varchar(50),
selfInfo varchar(300),
addressId int,
addressTreeIds varchar(18) default '',
totalScore float(10,2),
grade int,
userWw varchar(50) default '',
chushengDate datetime,
userLevelId int default 3,
certiStatus enum('WAIT','OK'),
tuijianRenId int default 0,
fensiCount int default 0,
beProbationDate datetime,
beDate datetime,
my2wm varchar(50),
wxId varchar(100),
tuijianRenNicheng varchar(50),
ifNosubscribe_subscribeEvent varchar(1000)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

alter table user_user add ifNosubscribe_subscribeEvent varchar(1000);




create table shoucang (
id int primary key auto_increment,
userId int,
scEntity varchar(50),
scEntityId int,
relDate datetime
)ENGINE=InnoDB DEFAULT CHARSET=utf8;







create table user_myAddress (
id int primary key auto_increment,
userId int,
addressId int,
addressTreeIds varchar(100) default '',
name varchar(20),
jutiAddress varchar(300),
zipcode varchar(20),
telPhone varchar(50),
phone varchar(50),
isDefault int,
qq varchar(20),
email varchar(50),
msn varchar(50),
ww varchar(50),
coorX double(15,10),
coorY double(15,10)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;




