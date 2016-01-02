create table sincerity_sincerityLevel(
id int primary key auto_increment,
levelName varchar(20) not null,
lowScore float(10,2) default 0,
heighScore float(10,2) default 0
)ENGINE=InnoDB DEFAULT CHARSET=utf8;


create table sincerity_sincerityType(
id int primary key auto_increment,
typeName varchar(100) not null,
addOrMis enum('ADD','MIS') default 'MIS',
score float(10,2) default 0,
typeKey varchar(100) not null
)ENGINE=InnoDB DEFAULT CHARSET=utf8;



create table sincerity_sincerityArchives(
id int primary key auto_increment,
whoclassName varchar(100) not null,
whopName varchar(100),
whoId int,
score float(10,2) default 0,
sincerityLevelId int default 1
)ENGINE=InnoDB DEFAULT CHARSET=utf8;





create table sincerity_sincerityArchivesItem(
id int primary key auto_increment,
whoclassName varchar(100) not null,
whopName varchar(100),
whoId int,
score float(10,2) default 0,
sincertityArchiversId int,
sincerityTypeId int,
addOrMis enum('ADD','MIS') default 'MIS',
info varchar(500),
title varchar(80),
extraData varchar(100),
relDate datetime
)ENGINE=InnoDB DEFAULT CHARSET=utf8;



































