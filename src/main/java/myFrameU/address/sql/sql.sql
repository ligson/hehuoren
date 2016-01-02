create table address (
id int primary key auto_increment,
name varchar(20) not null,
fatherId int default 1,
parent_id int,
key parent_id(parent_id),
constraint address_parent_id foreign key(parent_id) references address(id),
rootTypeId tinyint not null,
isROOT tinyint not null,
isLeaf tinyint not null,
jibie tinyint not null,
url varchar(20) not null,
treeId varchar(50) not null,
firstZm varchar(2),
isOpen int default 1,
diqu int default 0,
isHot int default 0
)ENGINE=InnoDB DEFAULT CHARSET=utf8;


