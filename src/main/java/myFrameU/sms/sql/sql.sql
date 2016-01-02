/*短信记录*/
create table sMSRecord(
id int primary key auto_increment,
relDate datetime,
telPhone varchar(16) not null,
sdkMtType varchar(20),
sendOrRec varchar(4),
content varchar(128),
ext varchar(4),
stime varchar(32),
rrid varchar(32),
sdkResult  varchar(32),
smsRecordOtherJSON varchar(120)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

