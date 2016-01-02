package myFrameU.adv.util;


public final class GetConstantAdv {


	//广告是否交钱的
	public final static int Advertisement_isJiaoqian_yes=1;
	public final static int Advertisement_isJiaoqian_no=2;
	
	//是否是024pmP的图片
	public final static int Advertisement_isWeb_ok=1;
	public final static int Advertisement_isWeb_no=2;
	

	
	
	/*public static String ifShopHavezigeBuy(Shop shop,Advertising adving,HashMap<String,Address> addressAll_map,AbstractBizI aBiz,HttpServletRequest req) throws Exception{
		String error=null;
		int addressId=shop.getAddressId();
		Address a = addressAll_map.get("addId_"+addressId);
		if(null!=a){
			int shengId=a.getRootTypeId();
			int chengshiId=a.getFatherId();
			String advType = adving.getAdvType();
			boolean haveOld=false;
			if(advType!=2){
				Advertisement am = (Advertisement)aBiz.findObjectById("from Advertisement as a where a.advertising.id=? and a.status=1 and addressId=?", new Object[]{adving.getId(),chengshiId});
				if(null!=am){
					haveOld=true;
				}
			}else{
				String haveOldIndexNums=(String)aBiz.j_queryObject("select group_concat(CONVERT(indexNum,char)) from advertisement where advertising_id=? and status=1 and addressId=?", new Object[]{adving.getId(),chengshiId});
				List<String> advType2IndexNumList = new ArrayList<String>();
				if(null!=haveOldIndexNums && !haveOldIndexNums.equals("")){
					String[] hoinArray=haveOldIndexNums.split(",");
					if(null!=hoinArray){
						int hoinalength=hoinArray.length;
						if(hoinalength==adving.getPicNumber()){
							haveOld=true;
						}
						String haveOldIndexNums_last=haveOldIndexNums+",";
						for(int i=1;i<=adving.getPicNumber();i++){
							if(!haveOldIndexNums_last.contains(i+",")){
								advType2IndexNumList.add(i+"");
							}
						}
					}
				}else{
					for(int i=1;i<=adving.getPicNumber();i++){
						advType2IndexNumList.add(i+"");
					}
				}
				req.setAttribute("advType2IndexNumList", advType2IndexNumList);
				
			}
			
			if(!haveOld){
				int shopId=shop.getId();
				shop=(Shop)aBiz.findObjectById("from Shop as s where s.id=?", new Object[]{shopId});
				if(null!=shop){
					req.getSession().setAttribute("myShop", shop);
					//再次查看有没有资格
					int jifen=adving.getJifen();
					int needJifen=30*jifen;
					float shopTotalScore=shop.getTotalScore();
					if(shopTotalScore>=needJifen){
						//积分够了
						req.setAttribute("needJifen", needJifen);
					}else{
						error="您的积分不足";
					}
				}
			}else{
				error="该广告位已经被抢";
			}
		}
		return error;
	}*/
}
