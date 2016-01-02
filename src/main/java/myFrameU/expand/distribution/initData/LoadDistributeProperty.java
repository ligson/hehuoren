package myFrameU.expand.distribution.initData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import myFrameU.biz.AbstractBizI;
import myFrameU.ehcache.initData.AbstractWebInit;
import myFrameU.ehcache.util.UICacheManager;
import myFrameU.expand.distribution.entity.PropertyDistribute;
import myFrameU.expand.init.DistributeUitlEntity;
import myFrameU.expand.init.InitConfig;

public class LoadDistributeProperty extends AbstractWebInit {
	@Override
	public void initData(ServletContext sc, UICacheManager uICacheManager,
			AbstractBizI aBiz) throws Exception {
		/**
		 * 将所有的分配加载上来
		 */
		List<String> classNameList = new ArrayList<String>();
		HashMap<String,HashMap<String,List<PropertyDistribute>>> map=new HashMap<String,HashMap<String,List<PropertyDistribute>>>();
		InitConfig initConfig=myFrameU.expand.init.InitMavenImpl.ic;
		List<DistributeUitlEntity> distribute_dbuList=initConfig.getDistribute_dbuList();
		int ddsize = distribute_dbuList.size();
		DistributeUitlEntity dd = null;
		String className=null;
		HashMap<String,List<PropertyDistribute>> classExpandPropertyMap = null;
		for(int ki=0;ki<ddsize;ki++){
			dd=distribute_dbuList.get(ki);
			className=dd.getClassName();
			if(!classNameList.contains(className)){
				classNameList.add(className);
				System.out.println("加载扩展属性分配数据===11111111111111111"+className);
			}
		}
		
		int size = classNameList.size();
		String cn=null;
		for(int i=0;i<size;i++){
			cn=classNameList.get(i);
			classExpandPropertyMap = getExpandPropertys(cn,aBiz);
			System.out.println("加载扩展属性分配数据==="+cn);
			map.put(className, classExpandPropertyMap);			
		}
		
		
		
		
		
		
		
		sc.setAttribute("distributePropertyMap", map);
		uICacheManager.putObjectCached("web", "distributePropertyMap", map);		
		
		for(Map.Entry<String,HashMap<String,List<PropertyDistribute>>> entry: map.entrySet()) {
			 System.out.println(entry.getKey() + ":" + entry.getValue() + "\t"+"_____________________________________________________________________________________________________________________________________");
		}		
	}
	
	private HashMap<String,List<PropertyDistribute>> getExpandPropertys(String className,AbstractBizI aBiz) throws Exception{

		/**
		 * className+dRange查找分配对象 需要根据优先级筛选
		 */

		List<PropertyDistribute> pdList = (List<PropertyDistribute>) aBiz.findObjectList(PropertyDistribute.class, new Object[] {className},
						"from PropertyDistribute as pd where pd.className=?",
						null, false, 0, 0);

		
		
		
		//里面放准备要筛选出去不要的pd
		List<String> pdIdsList_remove=new ArrayList<String>();
		int size = pdList.size();
		int priority=0;
		PropertyDistribute pd = null;
		String pvIds=null;
		String[] pvIds_array=null;
		for(int i=0;i<size;i++){
			pd=pdList.get(i);
			priority=pd.getPriority();
			pvIds=pd.getPropertyValueIds();
			pvIds_array=pvIds.split(",");
			//String pvIds_=","+pvIds+",";
			if(priority==1){
				/**
				 * 	找到它从哪继承的对应的那个分配对象pd
					className+propertyId+(propertyValueIds)
					propertyValueIds:需要找的这个pd的pvIds必须都在currentPd的pvIds中.
					因为pd中的propertyValueIds的值是升序的【1，5，10，21】
					所以优先级1和0之间的关系是：
					【1，5，10，21】
					【1，5，10，21，24，32，45】
				 * 
				 * ================以上思维错误================================
				 * 【19,22,24】
				 * 【19,20,22,24】
				 */
				int size1=pdList.size();
				PropertyDistribute pd1=null;
				String pvIds1=null;
				for(int j=0;j<size1;j++){
					pd1=pdList.get(j);
					if(pd1.getdRange().equals("ALL") && pd.getClassName().equals(pd1.getClassName())){
						//都是product的，且范围是ALL的。
						if(pd1.getId()!=pd.getId()){
							pvIds1=pd1.getPropertyValueIds();
							String pvIds1_=","+pvIds1+",";
							//【，19，22，24，】父
							//【，19，20，22，24，】子(外面那层)
							boolean isRemove=false;
							int len=pvIds_array.length;
							for(int kk=0;kk<len;kk++){
								if(pvIds1_.contains(","+pvIds_array[kk]+",")){
									//pvIds_array[kk]//19 外层，子
									isRemove=true;
									break;
								}
							}
							if(isRemove){
								if(!pdIdsList_remove.contains("pdId_"+pd1.getId())){
									pdIdsList_remove.add("pdId_"+pd1.getId());
								}
							}
						}
					}
				}
			}
		}

		
		

		/*System.out.println("下面打印准备要筛选出去的pdIdsList_remove=============================================开始");
		int llsize = pdIdsList_remove.size();
		for(int i=0;i<llsize;i++){
			System.out.println((pdIdsList_remove.get(i)));
		}
		System.out.println("下面打印准备要筛选出去的pdIdsList_remove=============================================开始");
		*/
		
		List<PropertyDistribute> lastList = new ArrayList<PropertyDistribute>();
		int ksize=pdList.size();
		PropertyDistribute kpd=null;
		int kpdId=0;
		for(int i=0;i<ksize;i++){
			kpd=pdList.get(i);
			kpdId=kpd.getId();
			if(!pdIdsList_remove.contains("pdId_"+kpdId)){
				lastList.add(kpd);
			}
		}
		
		
		
		
		System.out.println("下面打印筛选出去之后的lastlist=============================================开始");
		int ppsize = lastList.size();
		for(int i=0;i<ppsize;i++){
			System.out.println((lastList.get(i)).getdRange());
		}
		System.out.println("下面打印筛选出去之后的lastlist=============================================结束");
		
		
		
		
		/**
		 * 将lastList封装成map
		 * <'ALL',List<PropertyDistribute>>
		 * <'{'key':'prodcutTypeId','value':'1'}',List<PropertyDistribute>>
		 */
		HashMap<String,List<PropertyDistribute>> lastMap=new HashMap<String,List<PropertyDistribute>>();
		int msize=lastList.size();
		PropertyDistribute mpd=null;
		String mdRange=null;
		List<PropertyDistribute> mlist=null;
		for(int i=0;i<msize;i++){
			mpd=lastList.get(i);
			mdRange=mpd.getdRange();
			mlist=lastMap.get(mdRange);
			if(null==mlist){
				mlist = new ArrayList<PropertyDistribute>();
				mlist.add(mpd);
			}else{
				mlist.add(mpd);
			}
			lastMap.put(mpd.getdRange(), mlist);
		}
		return lastMap;
		

	}
}
