create table expand_systemLibraryProperty(
id int primary key auto_increment,
propertyName varchar(50) not null,
propertyKey varchar(20) not null,
dataType enum('INT','LONG','FLOAT','DOUBLE','STRING','DATE','BOOLEAN'),
dataFrom enum('MANUAL','WEB'),
dataFromWebUrl varchar(100),
addType enum('INPUT','SELECT','RADIO','CHECKBOX'),
showType enum('ENUM','SELECT','RADIO','CHECKBOX'),
valueDefault varchar(100),
valueAlternative varchar(100)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
<!------
search boolean default false,
must boolean default false,
queryArg boolean default false,
list boolean default false
--->
<!--MYSQL保存BOOLEAN值时用1代表TRUE,0代表FALSE-->

create table expand_systemLibraryPropertyValue(
id int primary key auto_increment,
sysLibraryPropertyId int,
key sysLibraryPropertyId(sysLibraryPropertyId),
constraint systemLibraryPropertyValue_sysLibraryPropertyId foreign key(sysLibraryPropertyId) references expand_systemLibraryProperty(id),
pvalue varchar(100),
defaultValue boolean default false
)ENGINE=InnoDB DEFAULT CHARSET=utf8;






