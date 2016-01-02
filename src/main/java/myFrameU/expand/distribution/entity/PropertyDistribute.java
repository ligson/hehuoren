package myFrameU.expand.distribution.entity;

import java.io.Serializable;
import java.util.HashMap;

import myFrameU.expand.libraryProperty.entity.SystemLibraryPropertyValue;

/**
 * 
 * 属性分配
 * 		1）可以分配给一个class所有的对象，如为global类所有对象都加上颜色属性。
 * 		2）可以分配给一个class类下某一部分对象，如为type1类型的product增加颜色属性。
 * 		这两种属性分配不是交集，不是子集，而是互补
 * 		----------------------------------------------------------------------
 * 		|								|									|
 * 		|			classProperty		|			objectProperty			|
 * 		|								|									|
 * 		-----------------------------------------------------------------------
 * 
 * 
 * ________________________________________________________________________________
 * 					|			|			|			|			|
 * 		className	|	范围		|	PID		|	PvIds	|	must	|
 * _________________|___________|___________|___________|___________|___________
 * 					|			|			|			|			|
 * 		product		|	All		|	1		|	1,2		|	MUST	|
 *------------------------------------------------------------------------
 * 					|			|			|			|			|
 * 		product		|{k:tpe,v:1}|	2		|	5,8,9	|	no		|			
 * ------------------------------------------------------------------------
 * 					|			|			|			|			|
 * 		product		|{k:tpe,v:2}|  1(error) |			|			|			
 * ------------------------------------------------------------------
 * 		product		|{k:tpe,v:1}   2(error)	|			|			|			

		
		第二个错误真的错误，不可能为同一个className的同一个范围分配两个完全一样的p
		第一个错误是假的错误，因为有优先级的关系，参考《优先级》


 * 	范围：
 * 		{key:productTypeId,value:5}:给productTypeId=5的这部分product分配属性
 * 
 * 
 * 
 * 
 * 属性备选值分配
 * 		如可以分配给ProductType这个类一个颜色的属性，
 * 		同时指定颜色属性中的黄色和白色可以作为ProductType的备选值
 *
 * 唯一性确定
 * 		1）如果分配给某一个class的所有对象
 * 				A）propertyId+className+ALL确定唯一。
 * 				B）type=1的product中有了颜色了，现在想给product所有的数据都加上颜色这个属性
 * 					那么先取消type=1的product的颜色属性，然后在product统一所有的数据都加上这个属性，并且将原先有的那部分的值恢复过来
 * 		2）如果分配给product的某一部分对象
 * 				A）先判断className+propertyId+范围，查看自己是不是有了这个属性了。
 * 				B）再判断className+propertyId是否有了，也就是product统一的全部的数据是不是已经有了
 * 					B1）如果判断全部数据中没有这个属性。则完全可以分配
 * 					B2）如果全部数据中有了这个属性，则判断优先级，如果优先级大于前者，则可分配，如果优先级＜=前者，则不可以分配。
 * 		优先级的判断：
 * 			propertyValueIds.count
 * 			if(dp1.propertyValueIds.count>dp2.propertyValues.count){
 * 				则优先级：dp1>dp2;
 * 			}
 * 
 * 优先级
 * 		背景：首先为Product的所有数据分配了一个颜色属性，值选为黄色、红色、蓝色（系统property的备选之是：黄色、红色、蓝色、黑色、白色）
 * 			 然后为product.productTypeId=3的这些product记录分配黑色和白色
 * 		此时出现了：所有数据分配了颜色属性，同时部分数据也要声明分配一个颜色属性，只是值不同，后者的值要多于前者
 * 		那么后者的优先级就大于前者，在add product的时候，查询出所有的property，还要做一个工作，就是根据优先级筛选property
 * 
 * 		为一个类的所有数据分配属性的时候，优先级=0
 * 		为一个类的部分数据分配属性的时候
 * 				优先级=0
 * 				优先级=1
 * 
 * 
 * transfer属性解释：
 * 		1）只作用于object!=0，也就是分配给对象的时候。
 * 		2) 只作用于tree型的具体对象分配的时候
 * 
 * 
 */
public class PropertyDistribute   implements Serializable{
	private int id;
	private String className;//my.....ProductType
	private String dRange;//范围
	
	
	
	
	private int propertyId;
	private String must;//是否必须填写
	public enum MUST{MUST,NO}
	
	/**
	 * 存储备选值的id，必须要从小到大排列，如21,10,20错误。必须要存储成：10,20,21
	 */
	private String propertyValueIds;//备选值的选取。多值之间请用英文逗号隔开
	
	
	
	/*private String transfer;//是否传递只用于tree型结构的对象中。
	public enum TRANSFER{SELF,SONS,SONSSONS} //self代表就自己使用，不传递，sons代表传给自己儿子们，sonsons代表传递给所有子孙
	*/
	
	private int priority;//优先级。默认的是0，后者覆盖前者则为1
	
	public PropertyDistribute(){}
	public PropertyDistribute(String className,String dRange,int propertyId,String must,String propertyValueIds){
		this.className=className;
		this.dRange=dRange;
		this.propertyId=propertyId;
		this.must=must;
		this.propertyValueIds=propertyValueIds;
	}
	//===============================================================
	private HashMap<String,String> propertyValueIdsMap=new HashMap<String,String>();
	public HashMap<String,String> getPropertyValueIdsMap() {
		try{
			String[] as=propertyValueIds.split(",");
			int len=as.length;
			String s = null;
			for(int i=0;i<len;i++){
				s=as[i];
				propertyValueIdsMap.put("pvId_"+s, s+"");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return propertyValueIdsMap;
	}

	
	
	public void setPropertyValueIdsMap(HashMap<String, String> propertyValueIdsMap) {
		this.propertyValueIdsMap = propertyValueIdsMap;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	
	public int getPropertyId() {
		return propertyId;
	}
	public void setPropertyId(int propertyId) {
		this.propertyId = propertyId;
	}

	public String getPropertyValueIds() {
		return propertyValueIds;
	}
	public void setPropertyValueIds(String propertyValueIds) {
		this.propertyValueIds = propertyValueIds;
	}

	public String getMust() {
		return must;
	}
	public void setMust(String must) {
		this.must = must;
	}
	public String getdRange() {
		return dRange;
	}
	public void setdRange(String dRange) {
		this.dRange = dRange;
	}
	public int getPriority() {
		return priority;
	}
	public void setPriority(int priority) {
		this.priority = priority;
	}

	
	
	
	
	
	
}
