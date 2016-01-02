package myFrameU.adv.initData;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import myFrame.cache.CacheKey;
import myFrameU.adv.entity.Advertisement;
import myFrameU.adv.entity.Advertising;
import myFrameU.adv.init.InitConfig;
import myFrameU.biz.AbstractBizI;
import myFrameU.ehcache.initData.AbstractWebInit;
import myFrameU.ehcache.util.UICacheManager;
import myFrameU.util.commonUtil.enumU.EnumUtil;

public class LoadAdverment extends AbstractWebInit{
	
	@Override
	public void initData(ServletContext sc,UICacheManager uICacheManager,AbstractBizI aBiz) throws Exception {
		
		
		
		
	
		
		InitConfig initConfig = myFrameU.adv.init.InitMavenImpl.ic;
		String advertingInsert=initConfig.getAdvermentInsert();
		if(null!=advertingInsert && !advertingInsert.equals("")){
			if(advertingInsert.equals("database")){
				List<Advertisement> aList = initConfig.getAdvertisementList();
				if(null!=aList){
					int size = aList.size();
					if(size>0){
						Advertisement a = null;
						Advertisement a1 = null;
						String advertisingMarkedNum = null;
						for(int i=0;i<size;i++){
							a=aList.get(i);
							advertisingMarkedNum=a.getAdvertisingMarkedNum();
							if(null!=advertisingMarkedNum && !advertisingMarkedNum.equals("")){
								Advertising adving=(Advertising)aBiz.findObjectById("from Advertising as a where a.markedNum=?", new Object[]{advertisingMarkedNum});
								if(null!=adving){
									String advType = adving.getAdvType();
									boolean canInsert = false;
									if(EnumUtil.equalsE(Advertising.ADVTYPE.FOUCS, advType)){
										a1=(Advertisement)aBiz.findObjectById("from Advertisement as a where a.advertisingMarkedNum=? and a.status='ING' and a.indexNum=?", new Object[]{advertisingMarkedNum,a.getIndexNum()});
										if(null==a1){
											canInsert=true;
										}
									}else{
										a1=(Advertisement)aBiz.findObjectById("from Advertisement as a where a.advertisingMarkedNum=? and a.status='ING'", new Object[]{advertisingMarkedNum});
										if(null==a1){
											canInsert=true;
										}
									}
									
									if(canInsert){
										a.setRelDate(new Date());
										a.setMarkeNum(new Date().getTime()+"");
										a.setEndDate(null);
										a.setAdvertising(adving);
										a.setAdvType(adving.getAdvType());
										
										aBiz.addObject(a);
									}
								}
							}
						}
					}
				}
			}
		}
		
		
		
		
		
		
		
		
		
	/*	
		//为栏目页面的广告创建
		Advertising lm_ad = (Advertising)aBiz.findObjectById("from Advertising as a where a.markedNum='advertingMarkedNum_productLanmu_1'", null);
		if(null!=lm_ad){
			List<String> ptIdsList = new ArrayList<String>();
			List<ProductType> ptList = CacheKey.CKProductType.ALLMAP.getList(uICacheManager);
			int picNum=lm_ad.getPicNumber();
			String havedAdvsPtIds = (String)aBiz.j_queryObject
					("select group_concat('[',sonByValue,']') from adv_advertisement where advertising_id=? group by sonByValue", new Object[]{lm_ad});
			if(null!=havedAdvsPtIds){
				//说明有的大类已经创建了
				int ptSize = ptList.size();
				ProductType pt = null;
				for(int i=0;i<ptSize;i++){
					pt=ptList.get(i);
					if(pt.getIsROOT()==0){
						//是根目录大类
						if(!havedAdvsPtIds.contains("["+pt.getId()+"]")){
							ptIdsList.add(pt.getId()+"");
						}
					}
				}
			}else{
				//一个大类都没有
				int ptSize = ptList.size();
				ProductType pt = null;
				for(int i=0;i<ptSize;i++){
					pt=ptList.get(i);
					if(pt.getIsROOT()==0){
						//是根目录大类
						ptIdsList.add(pt.getId()+"");
					}
				}
			}
			
			
			if(null!=ptIdsList){
				int ptIdSize = ptIdsList.size();
				String ptId = null;
				for(int i=0;i<ptIdSize;i++){
					ptId=ptIdsList.get(i);
					for(int j=1;j<=picNum;j++){
						Advertisement a=new Advertisement();
						a.setAdvertising(lm_ad);
						a.setMarkeNum("lanmu_1-"+ptId+"-"+j);
						a.setAddressId(165);
						a.setAddressTreeIds("[7][165]");
						a.setAdvertisingMarkedNum(lm_ad.getMarkedNum());
						a.setAdvType(lm_ad.getAdvType());
						a.setIndexNum(j);
						a.setIsJiaoqian(1);
						a.setIsWeb(1);
						a.setPicA("http://www.yicangjia.com/");
						a.setPicSmall("");
						a.setPicTitle("");
						a.setPicUrl("img/adv/lanm1.jpg");
						a.setRelDate(new Date());
						a.setRemainTime(0);
						a.setShopId(0);
						a.setSonByValue(ptId);
						a.setStatus(Advertisement.STATUS.ING.toString());
						a.setWidthAndHeight("");
						aBiz.addObject(a);
					}
				}
			}
			
			
			
			
			
			
			
		}
		
		*/
		
		//处理广告，map，key是（addressId+"_"+advingMarkeNum），value是这个广告位下面的有效广告
		Map<String,List<Advertisement>> adverMap_advering = new HashMap<String,List<Advertisement>>();
		List<Advertisement> amList1 = (List<Advertisement>)aBiz.
		findObjectList(Advertisement.class,null,
				"from Advertisement as a left join fetch a.advertising order by a.markeNum",
				null, false, 0,0);
		
		int size = amList1.size();
		Advertisement a = null;
		String markeNum=null;
		String advingMarkeNum=null;
		List<Advertisement> avList = null;
		//String key=null;
		for(int i=0;i<size;i++){
			a=amList1.get(i);
			markeNum=a.getMarkeNum();
			advingMarkeNum=a.getAdvertising().getMarkedNum();
			avList=adverMap_advering.get(advingMarkeNum);
			if(null==avList){
				avList=new ArrayList<Advertisement>();
				avList.add(a);
				adverMap_advering.put(advingMarkeNum, avList);
			}else{
				avList.add(a);
			}
		}
		
		sc.setAttribute("advertisementMap", adverMap_advering);
		uICacheManager.putObjectCached("web", "advertisementMap", (Serializable) adverMap_advering);
	}
	

	
	
	
	
	
}
