package myFrameU.expand.distribution.biz;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import myFrameU.biz.AbstractBizImpl;
import myFrameU.exception.exception.MyStrException;
import myFrameU.expand.distribution.entity.PropertyDistribute;
import myFrameU.expand.distribution.util.DistributePropertyUtil;
import myFrameU.expand.libraryProperty.entity.SystemLibraryProperty;
import myFrameU.expand.libraryProperty.entity.SystemLibraryPropertyValue;
import myFrameU.util.commonUtil.collection.MyCollectionUtils;
import myFrameU.util.commonUtil.enumU.EnumUtil;

import org.springframework.stereotype.Service;
@Service("distributeProBiz")
public class DistributePropertyBizImpl extends AbstractBizImpl implements
		DistributePropertyBizI {

	@Override
	public void addDistributePropertyBatch(HashMap<String,SystemLibraryProperty> libraryPropertyMap,String className,
			String dRange,String[] must,
			String[] propertyValueIds, String[] propertyIds) throws Exception {
		//先结合pids和pvids两者，确定一共选择了多少个有效property，然后创建多少个distributeProperty
		//什么是有效选择了property：非input输入类型的property，如果只是选择了propertyId，而没有选择相应的pvids，则视为无效
		//propertyValueIds这个数组设置的非常有技巧，pId${requestScoep.p.id}_pvId${pv.id}
		//
		if(null==dRange){
			dRange="ALL";
		}else{
			
		}
		/**
		 * 处理pvIds，生成以pId为key的map
		 * <pId3,--34,45>
		 */
		HashMap<String,String> pvMap=new HashMap<String,String>();
		int pvLen=propertyValueIds.length;
		String pId_pvId=null;
		String[] pId_pvId_array=null;
		String pIdStr=null;
		String pvIdStr=null;
		String pvIds_str=null;
		for(int i=0;i<pvLen;i++){
			pId_pvId=propertyValueIds[i];
			//pId3_pvId45
			pId_pvId_array=pId_pvId.split("_");
			pIdStr=pId_pvId_array[0];//pId3
			pvIdStr=pId_pvId_array[1];//pvId45
			pvIds_str=pvMap.get(pIdStr);
			if(null==pvIds_str){
				pvMap.put(pIdStr, pvIdStr.replace("pvId", ""));
			}else{
				pvIds_str=pvIds_str+","+pvIdStr.replace("pvId", "");
				pvMap.put(pIdStr, pvIds_str);
			}
		}
		
		
		/**
		 * 处理must数组，生成以pId为key的map
		 * <pId3,MUST>
		 */
		HashMap<String,String> mustMap=new HashMap<String,String>();
		int mustLen=must.length;
		String m=null;
		String pIdStr_m=null;
		for(int i=0;i<mustLen;i++){
			m=must[i];//pId${requestScope.p.id}_NO 或者 pId${requestScope.p.id}_MUST
			pIdStr_m=m.replace("_NO", "").replace("_MUST", "");
			if(m.contains("_NO")){
				mustMap.put(pIdStr_m, PropertyDistribute.MUST.NO.toString());
			}else if(m.contains("_MUST")){
				mustMap.put(pIdStr_m, PropertyDistribute.MUST.MUST.toString());
			}
		}
		
		
		
		/**
		 * 处理transfer，生成以pId为key的map
		 * <pId3,SELF>
		 */
		/*HashMap<String,String> transferMap=new HashMap<String,String>();
		int transferLen=transfer.length;
		String t=null;
		String pIdStr_t=null;
		for(int i=0;i<transferLen;i++){
			t=transfer[i];
			pIdStr_t=t.replace("_SELF", "").replace("_SONS", "").replace("_SONSSONS", "");
			if(t.contains("_SELF")){
				transferMap.put(pIdStr_t, PropertyDistribute.TRANSFER.SELF.toString());
			}else if(t.contains("_SONS")){
				transferMap.put(pIdStr_t, PropertyDistribute.TRANSFER.SONS.toString());
			}else if(t.contains("_SONSSONS")){
				transferMap.put(pIdStr_t, PropertyDistribute.TRANSFER.SONSSONS.toString());
			}
		}
		*/
		
		
		int pIdLen=propertyIds.length;
		String pId=null;
		String pvIds_fromPvmap=null;
		SystemLibraryProperty libraryProperty=null;
		PropertyDistribute pd=null;
		for(int i=0;i<pIdLen;i++){
			pId=propertyIds[i];//3
			pvIds_fromPvmap=pvMap.get("pId"+pId);
			if(null==pvIds_fromPvmap){
				//如果这个PID没有在map里，有两种情况，一种是input类型的p，一种是只选择了p而没有选择相应的pv
				libraryProperty=libraryPropertyMap.get("pId_"+pId);
				if(EnumUtil.equalsE(SystemLibraryProperty.ADDTYPE.INPUT, libraryProperty.getAddType())){
					//如果是输入类型的property
					pd=new PropertyDistribute(className,dRange,new Integer(pId),mustMap.get("pId"+pId),"");
				}else{
					//其他三种类型，必须要选择相应的pv
					
				}
			}else{
				//-----
				pvIds_fromPvmap=MyCollectionUtils.sortStringArray(pvIds_fromPvmap);
				pd=new PropertyDistribute(className,dRange,new Integer(pId),mustMap.get("pId"+pId),pvIds_fromPvmap);
			}
			Object o = DistributePropertyUtil.unique(pd, aDao);
			if(null!=o){
				if(o instanceof PropertyDistribute){
					//ok
					aDao.insertObject(pd);
				}else{
					
				}
			}
		}
		
	}

	
	/**
	 * 删除一个属性分配
	 * 		1）先根据要删除的property，组合出要删除的json，然后找到级联的被分配到的数据记录，删除其中的json部分
	 * 		2）删除这个分配
	 */
	@Override
	public void delDistributeProperty(int propertyId, String className,
			String dRange) throws Exception {
		if(null!=className){
			if(dRange.equals("ALL")){
				//说明是给某一个类的所有数据添加这个属性
				//第一）删除这个class的所有数据的json部分
				//===============================================================
				//第二）删除分配
				aDao.j_execute("delete from expand_propertyDistribute where className=? and dRange='ALL'", new Object[]{className});
			}else{
				//说明给某一个类的部分数据添加这个属性
				aDao.j_execute("delete from expand_propertyDistribute where className=? and dRange=?", new Object[]{className,dRange});
			}
		}
		
	}

	@Override
	public void addDistributeProperty(
			HashMap<String, SystemLibraryProperty> libraryPropertyMap,
			String className, String dRange,  int propertyId) throws Exception {
		if(null!=className){
			//第一）检查唯一性
			PropertyDistribute pd = new PropertyDistribute();
			pd.setClassName(className);
			pd.setdRange(dRange);
			pd.setMust("NO");
			pd.setPropertyId(propertyId);
			pd.setPropertyValueIds("");
			
			Object o = DistributePropertyUtil.unique(pd, aDao);
			if(null!=o){
				if(o instanceof PropertyDistribute){
					//ok
					pd=(PropertyDistribute)o;
					aDao.insertObject(pd);
				}else if(o instanceof String){
					throw new MyStrException(o.toString());
				}
			}
		}
	}

	/**
	 * 无论是del还是add pv，都必须要有分配pd
	 * 
	 * 根据pvId找到pId
	 * 根据propertyId+className+dRange找到唯一的一个属性分配对象
	 */
	@Override
	public void modifyDistributePropertyValue(
			HashMap<String, SystemLibraryProperty> libraryPropertyMap,
			String className, String dRange, int propertyValueId) throws Exception {
		if(propertyValueId!=0){
			SystemLibraryPropertyValue sp = (SystemLibraryPropertyValue)aDao.queryObjectById("from SystemLibraryPropertyValue as sp where sp.id=?", new Object[]{propertyValueId});
			if(null!=sp){
				int propertyId = sp.getSysLibraryProperty().getId();
				if(propertyId!=0){
					
					String hql="from PropertyDistribute as sp where sp.propertyId=? and sp.className=? and sp.dRange=?";
					PropertyDistribute pd = (PropertyDistribute)aDao.queryObjectById(hql, new Object[]{propertyId,className,dRange});
					if(null!=pd){
						boolean havedDistribute=false;
						//判断是否这个pv已经被分配过来了，别造成多次分配
						HashMap<String,String> propertyValueIdsMap=pd.getPropertyValueIdsMap();
						if(null!=propertyValueIdsMap){
							String pvIdStr=propertyValueIdsMap.get("pvId_"+propertyValueId);
							if(null!=pvIdStr && !pvIdStr.equals("")){
								havedDistribute=true;
							}
						}
						
						if(!havedDistribute){
							//还没有分配过这个pv，说明要add
							String propertyValueIds=pd.getPropertyValueIds();
							if(null==propertyValueIds || propertyValueIds.equals("")){
								propertyValueIds=propertyValueId+"";
							}else{
								propertyValueIds=propertyValueIds+","+propertyValueId;
							}
							propertyValueIds=MyCollectionUtils.sortStringArray(propertyValueIds);
							aDao.j_execute("update expand_propertyDistribute set propertyValueIds=? where id=?", new Object[]{propertyValueIds,pd.getId()});	
						}else{
							//已经分配过这个pv，说明要del
							
							//String propertyValueIds=pd.getPropertyValueIds();
							StringBuffer newPropertyValueIds=new StringBuffer("");
							for(Map.Entry<String, String> entry: propertyValueIdsMap.entrySet()) {
								String s=propertyValueId+"";
								// System.out.print(entry.getKey() + ":" + entry.getValue() + "\t");
								 if(!s.equals(entry.getValue())){
									 newPropertyValueIds.append(entry.getValue()).append(",");
								 }
							}
							
							String newPropertyValueIds_str=newPropertyValueIds.toString();
							if(newPropertyValueIds_str.endsWith(",")){
								newPropertyValueIds_str=newPropertyValueIds_str.substring(0,newPropertyValueIds_str.length()-1);
							}
							if(newPropertyValueIds_str.startsWith(",")){
								newPropertyValueIds_str=newPropertyValueIds_str.substring(1,newPropertyValueIds_str.length());
							}
							newPropertyValueIds_str=MyCollectionUtils.sortStringArray(newPropertyValueIds_str);
							aDao.j_execute("update expand_propertyDistribute set propertyValueIds=? where id=?", new Object[]{newPropertyValueIds_str,pd.getId()});
							
							/**
							 * 删除的时候要注意：如果优先级为0，则代表是【所有数据的属性】或者是【部分数据自己的属性】，直接删除即可。
							 * 					如果优先级为1，则代表是不分数据追加过特有属性值的属性。
							 * 									这情况下，要判断
							 * 										如果把自己特有追加的属性值全部都删除完了，那么就要连这个pd分配对象一起删除
							 * 										如果....
							 */
							
							int priority=pd.getPriority();
							if(priority==1){
								String lastPvIds=newPropertyValueIds_str;
								String hqlAll="from PropertyDistribute as sp where sp.propertyId=? and sp.className=? and sp.dRange='ALL'";
								PropertyDistribute pdAll = (PropertyDistribute)aDao.queryObjectById(hqlAll, new Object[]{propertyId,className});
								if(null!=pdAll){
									String pdAllOld=pdAll.getPropertyValueIds();
									if(null==pdAllOld){
										pdAllOld="";
									}
									if(null==lastPvIds){
										lastPvIds="";
									}
									if(lastPvIds.equals(pdAllOld)){
										//级联删除pd
										aDao.deleteObject(pd);
									}else{
										//如果不相同，还得看顺序。【22,21,20】【21,20,22】你说这俩应该不应该相同
										
										/**
										 * s1=【22,21,20】
										 * s2=【21,20,22】
										 */
										String[] pdAllOld_array=null;
										if(pdAllOld.contains(",")){
											pdAllOld_array=pdAllOld.split(",");
										}else{
											pdAllOld_array=new String[1];
											pdAllOld_array[0]=pdAllOld;
										}
										
										String[] lastPvIds_array=null;
										if(lastPvIds.contains(",")){
											lastPvIds_array=lastPvIds.split(",");
										}else{
											lastPvIds_array=new String[1];
											lastPvIds_array[0]=lastPvIds;
										}
										
										
										boolean b = MyCollectionUtils.numberArrayEqual(pdAllOld_array, lastPvIds_array);
										if(b){
											aDao.deleteObject(pd);
										}
										
									}
								}
							}
							
							
							
						}
					}else{
						/**add的时候
						 * 		当这个pd还不存在的时候，不存在del的情况，所以没有必要判断add还是del的了。
						 * 
						 * 这时候还没有分配property，能不能选取分配propertyValue呢？
						 * 				如果类的所有数据都有了这个属性，可以自动创建一个pd
						 * 				如果类的所有数据也没有这个属性，则不可以创建
						 */
						String hqlAll="from PropertyDistribute as sp where sp.propertyId=? and sp.className=? and sp.dRange='ALL'";
						PropertyDistribute pdAll = (PropertyDistribute)aDao.queryObjectById(hqlAll, new Object[]{propertyId,className});
						if(null!=pdAll){
							PropertyDistribute mySelfPd=new PropertyDistribute();
							mySelfPd.setClassName(pdAll.getClassName());
							mySelfPd.setdRange(dRange);
							mySelfPd.setMust(pdAll.getMust());
							mySelfPd.setPriority(1);
							mySelfPd.setPropertyId(propertyId);
							String oldPVIds=pdAll.getPropertyValueIds();
							String newPVIds=null;
							if(null==oldPVIds){
								oldPVIds="";
							}
							if(oldPVIds.equals("")){
								newPVIds=propertyValueId+"";
							}else{
								newPVIds=oldPVIds+","+propertyValueId;
							}
							newPVIds=MyCollectionUtils.sortStringArray(newPVIds);
							mySelfPd.setPropertyValueIds(newPVIds);
							aDao.insertObject(mySelfPd);
						}else{
							throw new MyStrException("抱歉，您还没有分配这个property，不能分配这个property下的value");
						}
					}
				}
			}
		}
		
	
		/*if(null==dRange || dRange.equals("ALL")){
			//说明是给某一个类的所有数据添加这个属性
			hql="from SystemLibraryProperty as sp where sp.propertyId=? and sp.className=? and sp.dRange='ALL'";
		}else{
			//说明给某一个类的部分数据添加这个属性
		}*/
	}

}
