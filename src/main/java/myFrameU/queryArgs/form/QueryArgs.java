package myFrameU.queryArgs.form;

public class QueryArgs {
	//这俩必须填写
	private String key;//如果遇到实体类中的属性名和表中的字段名不相符的时候,如外键则ccs|ccs_id
	private String value;//正常的就是=，如status=1，如果此value为!1，那么传到后台便是status
	
	
	
	//这俩可以不用填写
	//private String pt;//属性类型，[common]代表是在car表中的属性，[Expand]代表的是扩展属性。默认的是common.
	private String nextRel;//nextRelationship与下一个条件之间的关系。现在只能简单的实现and和or.默认是and.
	
	
	private String filedType;//int，float，double，varchar,datetime,tree.默认的是varchar
	private String operators;//运算符，= != > < >= <= ,默认的是=
	
	
	//private String operatorsIsTree_Table;//如果operators为tree,则需要根据传递过去的productId,从table中查询这条记录的treeId(注释:treeId为:[][][][])
	
	//private int isWithAs;//0是默认的,带有as.1是不带as
	
	
	
	
	public QueryArgs(){}
	public QueryArgs(String key,String value){
		this.key=key;
		this.value=value;
	}
	
	
	
	
	
	
	
	
	public String getFiledType() {
		return filedType;
	}
	public void setFiledType(String filedType) {
		this.filedType = filedType;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}

	
	public String getNextRel() {
		return nextRel;
	}
	public void setNextRel(String nextRel) {
		/*if(null==nextRel || nextRel.equals("")){
			this.nextRel="and";
		}else{
			this.nextRel = nextRel;
		}*/
		this.nextRel = nextRel;
	}

	public String getOperators() {
		if(null==operators){
			return "=";
		}else{
			return operators;
		}
	}
	public void setOperators(String operators) {
		this.operators = operators;
	}
	
	
}
