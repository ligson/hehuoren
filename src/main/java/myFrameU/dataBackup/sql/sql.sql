create table mySqlData (
id int primary key auto_increment,
filePath varchar(200) not null,
size varchar(20),
relDate datetime,
current int
)ENGINE=InnoDB DEFAULT CHARSET=utf8;