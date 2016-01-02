create table expand_propertyDistribute(
id int primary key auto_increment,
className varchar(100) not null,
dRange varchar(50) default 'ALL',
propertyId int not null,
must enum('MUST','NO') not null,
propertyValueIds varchar(100),
priority int default 0
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

alter table expand_propertyDistribute add priority  int default 0;


