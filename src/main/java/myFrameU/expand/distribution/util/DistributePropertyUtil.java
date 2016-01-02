package myFrameU.expand.distribution.util;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;

import myFrameU.dao.AbstractDaoI;
import myFrameU.exception.exception.MyStrException;
import myFrameU.expand.distribution.entity.PropertyDistribute;

public class DistributePropertyUtil {
	
	//在分配的时候，都要通过唯一性检查
	//唯一性检查通过了，则返回，否则null
	public static Object unique(PropertyDistribute pd,AbstractDaoI aDao) throws Exception{
		/**
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
		 */
		
		if(null!=pd){
			String dRange=pd.getdRange();
			int propertyId=pd.getPropertyId();
			String className=pd.getClassName();
			if(dRange.equals("ALL")){
				//说明是给类的所有数据记录都加
				BigInteger count=(BigInteger)aDao.j_queryObject("select count(id) from expand_propertyDistribute where propertyId=? and className=? and dRange='ALL'", new Object[]{propertyId,className});
				if(null!=count && count.intValue()>0){
					//已经存在了
					return null;
				}else{
					
					
					
					
					/**
					 * 检查，这个属性是不是被分配给了这个类的某些数据了
					 * 				如果已经被分配给了某些数据了，这里先这么做：返回异常提醒说已经被...,请你先取消那些分配
					 */
					List<PropertyDistribute> pdOldList=(List<PropertyDistribute>)aDao.queryObjectList(PropertyDistribute.class, new Object[]{className,propertyId}, 
							"from PropertyDistribute as p where p.className=? and p.dRange!='ALL' and p.propertyId=?", null, false, 0, 0);
					if(null==pdOldList || pdOldList.size()==0){
						//说明也没有被分配过到这个类的某些数据
						//这时候可以追加这个property了
						return pd;
					}else{
						StringBuffer dRangesb=new StringBuffer("");
						int size =pdOldList.size();
						PropertyDistribute pd1 = null;
						for(int i=0;i<size;i++){
							pd1=pdOldList.get(i);
							dRangesb.append(pd1.getdRange());
						}
						return "该属性已经分配给了"+className+"某些数据上了，请先取消那些数据对该属性的分配:"+dRangesb.toString();
						
					}
					
					
					
					
					
					
					
					//return pd;
				}
			}else{
				//说明是类的某一部分数据加
				PropertyDistribute pdMyself = (PropertyDistribute)aDao.queryObjectById("from PropertyDistribute where propertyId=? and className=? and dRange=?", new Object[]{propertyId,className,pd.getdRange()});
				if(null!=pdMyself){
					//如果最准确的情况下已经存在了，则不可能在分配了
					return null;
				}else{
					//再判断class级别
					//现在优先级只考虑=1的情况，也就是优先级只出现在分配全部数据和分配部分数据的时候
					//对于tree类型的儿子和孙子的优先级等不做考虑
					PropertyDistribute pdAll = (PropertyDistribute)aDao.queryObjectById("from PropertyDistribute where propertyId=? and className=? and dRange='ALL'", new Object[]{propertyId,className});
					if(null!=pdAll){
						//如果类的所有数据分配了这个property了。
						String pdAll_pvIds=pdAll.getPropertyValueIds();
						String pdSel_pvIds=pd.getPropertyValueIds();
						if(null==pdAll_pvIds || pdAll_pvIds.equals("")){
							//如果类的所有数据虽然分配了这个property，但是没有propertyValue
							if(null!=pdSel_pvIds && !pdSel_pvIds.equals("")){
								//这时候肯定比原先的那个pv多。
								//优先级=1
								pd.setPriority(1);
								return pd;
							}else{
								//级别相同
								return null;
							}
						}else{
							if(null!=pdSel_pvIds && !pdSel_pvIds.equals("")){
								if(pdSel_pvIds.length()>pdAll_pvIds.length()){
									//优先级=1
									pd.setPriority(1);
									return pd;
								}else{
									return null;
								}
							}
						}
					}else{
						//如果类没有分配，他自己也没有分配，则可以ok了
						return pd;
					}
				}
				
				
				
			}
		}
		return null;
	}
}
