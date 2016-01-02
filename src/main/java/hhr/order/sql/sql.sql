drop table order_order;
create table order_order(
id int primary key auto_increment,
markedNum varchar(50),
tuijianRenId int default 0,
tujianRenName varchar(50),
userId int default 0,
userName varchar(50),
myAddressId int default 0,
totalCount int default 0,
totalPrice float(10,2) default 0,
toHehuorenPrice float(10,2) default 0,
toWebPrice float(10,2) default 0,
remarks varchar(300),
status enum('CLOSE','WAITPAY','PAYED','PAYING','PICKUPED'),
createDate datetime,
payDate datetime,
pickDate datetime,
shouhuoName varchar(50),
shouhuoTelphone varchar(50),
outerMarkedNum varchar(100),
outerMarkedNum1 varchar(100),
outerType varchar(20),
orderPayType enum('Unknown','outerType','Huodaofukuan') default 'Unknown'
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

alter table order_order add orderPayType enum('Unknown','outerType','Huodaofukuan') default 'Unknown';




drop table order_orderItem;
create table order_orderItem(
id int primary key auto_increment,
order_id int,
key order_id(order_id),
constraint orderItem_order_id foreign key(order_id) references order_order(id),
productId int default 0,
ocount int default 0,
price float(10,2) default 0,
tprice float(10,2) default 0,
pickupAddressId int default 0,
toHehuorenPrice float(10,2) default 0,
toWebPrice float(10,2) default 0,
productPriceId int
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

update order_orderItem set productPriceId=0 where productPriceId is null;

alter table order_orderItem add productPriceId int default 0;

create table order_pickUpAddress(
id int primary key auto_increment,
name varchar(100),
telPhone varchar(20),
addressStr varchar(200),
addressId int,
addressTreeIds varchar(100),
tpName varchar(20),
markedNum varchar(100)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
alter table order_pickUpAddress add markedNum varchar(100);
