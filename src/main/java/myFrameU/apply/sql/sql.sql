
create table apply_apply (
id int primary key auto_increment,
sponsorClassName varchar(100) not null,
sponsorId int,
dealWithClassName varchar(100),
dealWithId int,
applyTypeKey varchar(50),
applyTitle varchar(100),
applyContent varchar(300),
speed enum('WAIT','ING','FINISH') not null,
result enum('NO','CLOSE','APPLYOK','APPLYERROR'),
remarks varchar(300),
dealDate datetime,
relDate datetime,
extraData varchar(100),
sponsorName varchar(100) default ''
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

