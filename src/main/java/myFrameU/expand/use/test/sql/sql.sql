create table expand_test_testProduct(
id int primary key auto_increment,
name varchar(100) not null,
productTypeId int not null,
productTypeTreeIds varchar(100),
propertyValues_Ids varchar(500) default '',
propertyValues_Strs varchar(500) default ''
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

alter table expand_test_testProduct add propertyValues_Ids varchar(500) default '';
alter table expand_test_testProduct add propertyValues_Strs varchar(500) default '';


create table expand_test_testProductType(
id int primary key auto_increment,
name varchar(100) not null
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

