create table adv_advertingPage(
id int primary key auto_increment,
name varchar(100) not null,
nameKey varchar(50) not null
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

alter table adv_advertingPage add haveSon enum('YES','NO') default 'NO';
alter table adv_advertingPage add sonBy enum('PRODUCTTYPE');


create table adv_advertising (
id int primary key auto_increment,
markedNum varchar(100) not null,
info varchar(100) not null,
priceInfo varchar(100) not null,
widthAndheight varchar(100) not null,
width int default 0,
height int default 0,
advType enum('IMAGE','FOUCS','FLASH','BIG','LEFTRIGHT'),
status enum('WAIT','ING','CLOSE'),
picNumber int not null,
advertingPageNameKey varchar(50),
addressId int default 0,
addressTreeIds varchar(30),
image varchar(50),
jifen int default 0,
saleNum int default 0,
liulanNum int default 0,
price varchar(50) not null,
width100 int default 0
)ENGINE=InnoDB DEFAULT CHARSET=utf8;



alter table adv_advertising add haveSon enum('YES','NO') default 'NO';
alter table adv_advertising add sonBy enum('PRODUCTTYPE');
alter table adv_advertising add sonByValue varchar(50);

/**
 * 广告表
 */

create table adv_advertisement(
id int primary key auto_increment,
markeNum varchar(100) not null,
advertising_id int,
key advertising_id(advertising_id),
constraint advertisement_advertising_id foreign key(advertising_id) references adv_advertising(id),
addressId int default 0,
addressTreeIds varchar(18) default '',
relDate datetime,
beginDate datetime,
endDate datetime,
advType enum('IMAGE','FOUCS','FLASH','BIG','LEFTRIGHT'),
indexNum int not null default 1,
remainTime int not null default 5,
picUrl varchar(50) not null,
picSmall varchar(60),
picTitle varchar(100) not null,
picA varchar(100),
flashUrl varchar(50),
oldPrice float(10,3) not null,
price float(10,3) not null,
priceMeiyue float (10,3) not null,
widthAndHeight varchar(100) not null,
isWeb int not null default 0,
status enum('WAIT','ING','CLOSE'),
isJiaoqian int not null,
shopId int,
shopName varchar(100) default '',
advertisingMarkedNum varchar(100),
sonByValue varchar(50) default ''
)ENGINE=InnoDB DEFAULT CHARSET=utf8;




























