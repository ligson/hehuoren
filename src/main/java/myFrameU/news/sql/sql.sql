
/*新闻类型*/
drop table newsType;
create table newsType(
id int primary key auto_increment,
name varchar(20) not null,
isLeaf tinyint not null,
isROOT tinyint not null,
jibie tinyint not null,
treeId varchar(100) not null,
url varchar(32) not null,
rootTypeId int not null,
parent_id int,
key parent_id(parent_id),
constraint newstype_parent_id foreign key(parent_id) references newsType(id),
returnPage varchar(20),
fatherId int default 0,
image1 varchar(128),
image2 varchar(128),
pinyin varchar(128),
allName varchar(256)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;




/*新闻表*/
drop table news;
create table news(
id int primary key auto_increment,
title varchar(100),
title1 varchar(100),
image varchar(50),
info varchar(128),
relDate datetime not null,
status enum('WAITAPPLY','APPLYEERROR','OK','CLOSE') default 'OK',
newsTypeROOTId int,
newsTypeTreeIds varchar(128),
viewsCount int default 0,
roleType enum('WEB','SHOP','USER') default 'WEB',
roleId int default 0,
addressId int,
addressTreeIds varchar(30),
canDel enum('YES','NO') default 'YES',
newsContentId int default 0,
newsTypeId int
)ENGINE=InnoDB DEFAULT CHARSET=utf8;





/*新闻内容表*/
drop table newsContent;
create table newsContent(
id int primary key auto_increment,
contentName1 varchar(128),
contentValue1 text,
contentName2 varchar(128),
contentValue2 text,
newsId int default 0
)ENGINE=InnoDB DEFAULT CHARSET=utf8;














