/*产品类型*/
create table product_productType(
id int primary key auto_increment,
name varchar(200) not null,
isLeaf tinyint not null,
isROOT tinyint not null,
jibie tinyint not null,
treeId varchar(100) not null,
url varchar(32) not null,
rootTypeId int not null,
parent_id int,
key parent_id(parent_id),
constraint productType_parent_id foreign key(parent_id) references product_productType(id),
proTypeInfo varchar(500),
fm varchar(50) default 'productType1.jpg',
smallImg varchar(50) default 'productType1.jpg',
allName varchar(100) default '',
webTicheng double(10,2) default 0
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

alter table product_productType add webTicheng double(10,2) default 0;


create table product_productContent(
id int primary key auto_increment,
productId int,
content text,
contentName varchar(50),
info varchar(200)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;



create table product_productTesefuwu(
id int primary key auto_increment,
name varchar(50),
addProductMust boolean default false,
tsfwkey varchar(20) default 'tswf'
)ENGINE=InnoDB DEFAULT CHARSET=utf8;



drop table product_product;
create table product_product(
id int primary key auto_increment,
name varchar(100),
lunboImage1 varchar(50),
lunboImage2 varchar(50),
lunboImage3 varchar(50),
lunboImage4 varchar(50),
lunboImage5 varchar(50),
mainImage varchar(50),
productTypeId int default 0 not null,
productTypeTreeIds varchar(100),
productContentId int default 0 not null,
addressId int,
addressTreeIds varchar(20),
productCount int default 1,
status enum('CLOSE','OK'),
relDate datetime,
propertyValues_Ids varchar(500) default '',
propertyValues_Strs varchar(500) default '',
saleCount int default 0,
price1 float(10,2) default 0,
price2 float(10,2) default 0,
toTjrTc float(10,2) default 0,
price1Price2 float(10,2) default 0,
pickupAddressIds varchar(100)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;




create table product_productPrice(
id int primary key auto_increment,
productId int,
productName varchar(100),
productImg varchar(50),
xilieName varchar(100),
baozhuangName varchar(100),
price1 float(10,2),
price2 float(10,2),
toTjrTc float(10,2),
price1Price2 float(10,2),
yesproductPrice int default 0,
productCount int default 0,
saleCount int default 0,
pickupAddressIds varchar(100),
shiyongName varchar(100),
keyName1 varchar(100),
keyName2 varchar(100),
keyName3 varchar(100),
keyName4 varchar(100),
keyName5 varchar(100)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
alter table product_productPrice add pickupAddressIds varchar(100);


alter table product_productPrice add shiyongName varchar(100);
alter table product_productPrice add keyName1 varchar(100);
alter table product_productPrice add keyName2 varchar(100);
alter table product_productPrice add keyName3 varchar(100);
alter table product_productPrice add keyName4 varchar(100);
alter table product_productPrice add keyName5 varchar(100);


alter table product_productPrice add productCount int default 0;
alter table product_productPrice add saleCount int default 0;


create table product_productPricePropertyValue(
id int primary key auto_increment,
productId int,
keyPy varchar(100),
keyName varchar(100),
keyValues varchar(1000)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;