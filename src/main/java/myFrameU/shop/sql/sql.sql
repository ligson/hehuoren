create table shop_shopUser(
id int primary key auto_increment,
name varchar(50),
password varchar(50),
lianxiren varchar(20),
lianxirenPhone varchar(20),
shopId int,
addressId int,
addressTreeIds varchar(100),
shopName varchar(100),
wxId varchar(100)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;


create table shop(
id int primary key auto_increment,
shopLevelId int,
name varchar(100),
smallName varchar(50),
logo varchar(100),
phone varchar(20),
info varchar(200),
specificAddress varchar(50),
ruzhuDate datetime,
specialCount int,
shoucangCount int ,
clinchCount int,
clinchPrice float(10,2),
productCount int,
mainProductTypeIds varchar(100),
totalScore float(10,2),
grade int,
shopUserId int,
addressId int,
addressTreeIds varchar(100),
certiStatus enum('WAIT','OK'),
status enum('WAIT','OK','FROZEN'),
ptVip enum('YES','NO') default 'NO',
auctionPeriodCount int default 0
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

alter table shop add auctionPeriodCount int default 0;
alter table shop add ptVip enum('YES','NO') default 'NO';
alter table shop add specialCount int default 0;
alter table shop add clinchPrice float(10,2) default 0;
alter table shop add status enum('WAIT','OK','FROZEN');


create table certification(
id int primary key auto_increment,
whoclassName varchar(50),
whoId int,
whoName varchar(50),
yingyezhizhaoUrl varchar(100),
yingyezhizhaoHaoma varchar(50),
zuzhijigoudaimazheng varchar(100),
shuiwudengjizheng varchar(100),
paimaixukezheng varchar(100),
wenwupmxkzheng varchar(100),
shenfenzhengzUrl varchar(100),
shenfenzhengfUrl varchar(100),
shenfenzhenghaoma varchar(50),
trueName varchar(10),
zizhiStatus enum('WAIT','WAITAPPLY','APPLYOK','APPLYERROR'),
shimingStatus enum('WAIT','WAITAPPLY','APPLYOK','APPLYERROR'),
yuanyinzizhi varchar(300),
yuanyinshiming varchar(300)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;











create table shop_shopTemplateSimple(
id int primary key auto_increment,
shopId int,
bannerBackground varchar(30) default 'img/shop/bannerBg.jpg',
banner varchar(30) default 'img/shop/banner.jpg',
smallFocus1 varchar(30) default 'img/shop/smallFocus1.jpg',
smallFocus2 varchar(30) default 'img/shop/smallFocus2.jpg',
smallFocus3 varchar(30) default 'img/shop/smallFocus3.jpg',
smallFocus4 varchar(30) default 'img/shop/smallFocus4.jpg',
smallFocus5 varchar(30) default 'img/shop/smallFocus5.jpg',
bigBackground varchar(30) default 'img/shop/defalutBigBk.jpg',
footerBackgroundColor varchar(30) default '#222',
menuBg varchar(30) default '#88c248',
menuHoverBg varchar(30) default '#4bae36',
titleBg varchar(30) default '#everyDiv_title_outer_4.png',
titleHeight varchar(10) default '30px',
titleFontColor varchar(10) default '#fff',
css text,
divBorderColor varchar(10) default '#4bae36',
divBorderWidth varchar(10) default '1px'
)ENGINE=InnoDB DEFAULT CHARSET=utf8;




