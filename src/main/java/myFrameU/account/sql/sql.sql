create table account(
id int primary key auto_increment,
whoclassName varchar(50),
whoId int,
whoName varchar(50),
zhifubao varchar(30),
yinhangka varchar(50),
yinhangkaType varchar(20),
isQueren int,
withdrawalsPwd varchar(20),
addressId int default 0,
addressTreeIds varchar(50),
availablePrice float(10,2),
frozenPrice float(10,2),
totalPrice float(10,2),
xsPrice float(10,2) default 0,
xcPrice float(10,2) default 0
)ENGINE=InnoDB DEFAULT CHARSET=utf8;



create table accountItem(
id int primary key auto_increment,
account_id int,
key account_id(account_id),
constraint accountItem_account_id foreign key(account_id) references account(id),
markedNum varchar(32),
amatch varchar(100),
itemType enum('RECHARGE','WITHDRAWALS','INCOME','SPENDING','FROZEN'),
status enum('WAIT','FINISH','CLOSE'),
price float(10,2),
info varchar(100),
extraData varchar(100),
relDate datetime,
callbackHaveddeal boolean default false,
whoclassName varchar(50),
whoId int,
priceType enum('DEFAULT','XS','XC') default 'DEFAULT',
outerType enum('ADMIN','WEIXIN','ZHIFUBAO'),
outerMarkedNum varchar(100),
outerMarkedNum2 varchar(100)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;



alter table accountItem add priceType enum('DEFAULT','XS','XC') default 'DEFAULT';
alter table accountItem add rechargeType enum('WX','CFT','ZFB') default 'ZFB';




alter table accountItem add amatch varchar(100);
alter table accountItem add whoclassName varchar(50);
alter table accountItem add whoId int;