package myFrame.quartz;

import java.util.Date;

import myFrameU.util.commonUtil.date.DateUtils;

/**
 * 
 * @author xuxianlin
 * 优先级
 */
public class QuartzInitPriority {
	
	/**
	 * 顺序：
	 * 		SPECIAL_STATUS_BUILD2PRE_   0
	 * 				AUCTION_STATUS_WAIT2ING_    20
	 * 				SPECIAL_STATUS_PRE2ING_     19
	 * 						AUCTION_STATUS_ING2END_       40
	 * 						SPECIAL_STATUS_ING2END_       39
	 * 																													SPECIAL_STATUS_END2TONGJI_          180
	 * 										AUCTION_STATUS_END_BID_WAITPAY_        80
	 * 												AUCTION_STATUS_END_BID_NOPAY_PAY2SHOP_FINISH_       100
	 * 																																					SPECIAL_STATUS_TUIHUANBAOZHENGJIN_     300	            
	 */
	//也就是3分钟之内结束
	public static String nowDateE(String group,String name){
		Date n = null;
		Date now = new Date();
		if(group.equals("AUCTION_STATUS")){
			if(name.startsWith("WAIT2ING_")){
				n=DateUtils.getNextDate_SS(now, 20);
        	}else if(name.startsWith("ING2END_")){
        		n=DateUtils.getNextDate_SS(now, 40);
        	}else if(name.startsWith("END_BID_WAITPAY_")){
        		n=DateUtils.getNextDate_SS(now, 80);
        	}else if(name.startsWith("END_BID_NOPAY_PAY2SHOP_FINISH_")){
        		n=DateUtils.getNextDate_SS(now, 100);
        	}
		}else if(group.equals("SPECIAL_STATUS")){
			if(name.equals("BUILD2PRE_")){
				n=now;
			}else if(name.equals("PRE2ING_")){
				n=DateUtils.getNextDate_SS(now, 19);
			}else if(name.equals("ING2END_")){
				n=DateUtils.getNextDate_SS(now, 39);
			}else if(name.equals("TUIHUANBAOZHENGJIN_")){
				n=DateUtils.getNextDate_SS(now, 360);
			}else if(name.equals("END2TONGJI_")){
				n=DateUtils.getNextDate_SS(now, 180);
			}
		}
		String e=DateUtils.format(n, DateUtils.SS_MM_HH_DD_MM_YYYY);
		System.out.println(group+"_"+name+"...................e="+e);
		return e;
	}
	
	
	
	/*static AccountBizImpl accountBiz = StartQuartz.accountBiz;
	static UICacheManager uICacheManager = StartQuartz.uICacheManager;
	static OrderBizImpl orderBiz = StartQuartz.orderBiz;
	

	public static void dealEveryJob(ScheduleJob job) throws Exception{
		String group=job.getJobGroup();
		String name = job.getJobName();
		if(group.equals("AUCTION_STATUS")){
			HashMap<String,AuctionPeriod> auctionMap=job.getExtraDataMap();
			if(null!=auctionMap){
				 AuctionPeriod ap=auctionMap.get("auctionPeriod");
				 if(null!=ap){
					 int apId = ap.getId();
					 if(apId!=0){
						 ap=(AuctionPeriod)accountBiz.findObjectById("from AuctionPeriod as ap where ap.id=?", new Object[]{apId});
						 if(null!=ap){
							 	if(name.equals("WAIT2ING_"+ap.getId())){
			        				MyQuartzJobExcuteUtilAuctionPeriod.AUCTION_STATUS_WAIT2ING(accountBiz, ap);
					        	}else if(name.equals("ING2END_"+ap.getId())){
					        		MyQuartzJobExcuteUtilAuctionPeriod.AUCTION_STATUS_ING2END(accountBiz, ap);
					        	}else if(name.equals("END_BID_WAITPAY_"+ap.getId())){
					        		MyQuartzJobExcuteUtilAuctionPeriod.AUCTION_STATUS_END_BID_WAITPAY(accountBiz, ap);
					        	}else if(name.equals("END_BID_NOPAY_PAY2SHOP_FINISH_"+ap.getId())){
					        		MyQuartzJobExcuteUtilAuctionPeriod.AUCTION_STATUS_END_BID_NOPAY_PAY2SHOP_FINISH(orderBiz,accountBiz, ap);
					        	}
						 }
					 }
				 }
			}
			
		}else if(group.equals("SPECIAL_STATUS")){
			HashMap<String,Special> specialMap=job.getExtraDataMap();
			if(null!=specialMap){
				Special s = specialMap.get("special");
				if(null!=s){
					int  specialId = s.getId();
					if(specialId!=0){
						s = (Special)accountBiz.findObjectById("from Special as s where s.id=?", new Object[]{specialId});
						if(name.equals("BUILD2PRE_"+specialId)){
	        				MyQuartzJobExcuteUtilSpecial.SPECIAL_STATUS_BUILD2PRE(accountBiz, s,uICacheManager);
	        			}else if(name.equals("PRE2ING_"+specialId)){
	        				MyQuartzJobExcuteUtilSpecial.SPECIAL_STATUS_PRE2ING(accountBiz, s);
	        			}else if(name.equals("ING2END_"+specialId)){
	        				MyQuartzJobExcuteUtilSpecial.SPECIAL_STATUS_ING2END(accountBiz, s);
	        			}else if(name.equals("TUIHUANBAOZHENGJIN_"+specialId)){
	        				MyQuartzJobExcuteUtilSpecial.SPECIAL_STATUS_TUIHUANBAOZHENGJIN(accountBiz, s,uICacheManager);
	        			}else if(name.equals("END2TONGJI_"+specialId)){
	        				MyQuartzJobExcuteUtilSpecial.SPECIAL_STATUS_ING2END_TONGJI(accountBiz, s,uICacheManager);
	        			}
					}
				}
			}
		}
		jobMap.remove(job.getJobGroup()+"_"+job.getJobName());
	}
	
	
	private static void dealEveryList(List<ScheduleJob>  list,String listName) throws Exception{
		System.out.println("开始处理过期的job----------------------------------listName="+listName+"==========size="+list.size());
		int size=list.size();
		ScheduleJob job = null;
		HashMap<String,AuctionPeriod> auctionMap=null;
		AuctionPeriod ap = null;
		for(int i=0;i<size;i++){
			job=list.get(i);
			dealEveryJob(job);
		}
	}
	
	
	static Map<String, ScheduleJob> jobMap = MyJobManager.getJobMapExpired();
	//处理过期的job
	public static void dealExpiredJobs(){

		
		*//**
		 * 找出过期的来,按照优先级分组
		 *//*
		List<ScheduleJob> auction_wait2ing_list =new ArrayList<ScheduleJob>();
		List<ScheduleJob> auction_ing2end_list =new ArrayList<ScheduleJob>();
		List<ScheduleJob> auction_endbid2waitpay_list =new ArrayList<ScheduleJob>();
		List<ScheduleJob> auction_endbidnopay2paybzj_list =new ArrayList<ScheduleJob>();
		List<ScheduleJob> special_b2p_list =new ArrayList<ScheduleJob>();
		List<ScheduleJob> special_p2ing_list =new ArrayList<ScheduleJob>();
		List<ScheduleJob> special_ing2end_list =new ArrayList<ScheduleJob>();
		List<ScheduleJob> special_end2tongji_list =new ArrayList<ScheduleJob>();
		List<ScheduleJob> special_tongji2bzj_list =new ArrayList<ScheduleJob>();
	
		
		
		//Map<String, ScheduleJob> jobMap = MyJobManager.getJobMap();
		String group_name=null;
		ScheduleJob job1 = null;
		String group = null;
		String name=null;
		for(Map.Entry<String, ScheduleJob> entry : jobMap.entrySet()){   
			group_name=entry.getKey();
			job1=entry.getValue();
			group = job1.getJobGroup();
			name=job1.getJobName();
			if(group.equals("AUCTION_STATUS")){
				if(name.startsWith("WAIT2ING_")){
					auction_wait2ing_list.add(job1);
				}else if(name.startsWith("ING2END_")){
					auction_ing2end_list.add(job1);
				}else if(name.startsWith("END_BID_WAITPAY_")){
					auction_endbid2waitpay_list.add(job1);
				}else if(name.startsWith("END_BID_NOPAY_PAY2SHOP_FINISH_")){
					auction_endbidnopay2paybzj_list.add(job1);
				}
			}else if(group.equals("SPECIAL_STATUS")){
				if(name.startsWith("BUILD2PRE_")){
					special_b2p_list.add(job1);
				}else if(name.startsWith("PRE2ING_")){
					special_p2ing_list.add(job1);
				}else if(name.startsWith("ING2END_")){
					special_ing2end_list.add(job1);
				}else if(name.startsWith("END2TONGJI_")){
					special_end2tongji_list.add(job1);
				}else if(name.startsWith("TUIHUANBAOZHENGJIN_")){
					special_tongji2bzj_list.add(job1);
				}
			}
		} 
		
		
		
		try {
			dealEveryList(auction_wait2ing_list,"auction_wait2ing_list");
			dealEveryList(auction_ing2end_list,"auction_ing2end_list");
			dealEveryList(auction_endbid2waitpay_list,"auction_endbid2waitpay_list");
			dealEveryList(auction_endbidnopay2paybzj_list,"auction_endbidnopay2paybzj_list");
			dealEveryList(special_b2p_list,"special_b2p_list");
			dealEveryList(special_p2ing_list,"special_p2ing_list");
			dealEveryList(special_ing2end_list,"special_ing2end_list");
			dealEveryList(special_end2tongji_list,"special_end2tongji_list");
			dealEveryList(special_tongji2bzj_list,"special_tongji2bzj_list");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
		
		
		
		
		
		
		
	}
	

	*/
	
	
	
}
