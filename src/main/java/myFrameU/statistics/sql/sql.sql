create table shop_statistics(
id int primary key auto_increment,
whoclassName varchar(100),
whoId int,
whoName varchar(100),
flow_pv int default 0,
flow_uv int default 0,
flow_pvCuv float(10,2) default 0,
flow_tradeUCallU float(10,2) default 0,
sale_tradeAllPrice float(10,2) default 0,
sale_tradeCount int default 0,
sale_tradeUserCount int default 0,
sale_tradePriceCtradeUserCount float(10,2) default 0,
relDate datetime
)ENGINE=InnoDB DEFAULT CHARSET=utf8;