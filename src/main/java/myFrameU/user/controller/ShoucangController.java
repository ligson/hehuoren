package myFrameU.user.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import myFrameU.exception.exception.MyStrException;
import myFrameU.shop.entity.Shop;
import myFrameU.spring.mvc.FatherController;
import myFrameU.spring.mvc.SDynaActionForm;
import myFrameU.user.biz.ShoucangBizI;
import myFrameU.user.entity.Shoucang;
import myFrameU.user.entity.User;
import myFrameU.user.init.InitConfig;
import myFrameU.util.sshUtil.hibernate.EntityTableUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ShoucangController extends FatherController {
	@Autowired
	private ShoucangBizI shoucangBiz;
	
	public ShoucangBizI getShoucangBiz() {
		return shoucangBiz;
	}
	public void setShoucangBiz(ShoucangBizI shoucangBiz) {
		this.shoucangBiz = shoucangBiz;
	}
	@RequestMapping("/user/shoucang/sc")
	public void shoucang(HttpServletRequest req,HttpServletResponse res) throws Exception{
		SDynaActionForm form = getSDynaActionForm(req);
		String scEntity=form.getString("scEntity");
		Integer scEntityId = form.getInteger("scEntityId");
		User user = (User)req.getSession().getAttribute("myUser");
		if(null!=scEntity && null!=scEntityId && scEntityId.intValue()!=0 && null!=user){
			Shoucang sc_old=(Shoucang)shoucangBiz.findObjectById("from Shoucang as sc where sc.userId=? and sc.scEntityId=? and scEntity='"+scEntity+"'", new Object[]{user.getId(),scEntityId});
			if(null==sc_old){
				
				Shoucang sc = new Shoucang();
				sc.setUserId(user.getId());
				sc.setRelDate(new Date());
				sc.setScEntityId(scEntityId);
				sc.setScEntity(scEntity);
				shoucangBiz.shoucang(sc);
				
			}else{
				throw new MyStrException("已经收藏");
			}
		}
	}
	@RequestMapping("/user/shoucang/remove")
	public void removeShoucang(HttpServletRequest req,HttpServletResponse res) throws Exception{
		SDynaActionForm form = getSDynaActionForm(req);
		String scEntity=form.getString("scEntity");
		Integer scEntityId = form.getInteger("scEntityId");
		User user = (User)req.getSession().getAttribute("myUser");
		if(null!=scEntity && null!=scEntityId && scEntityId.intValue()!=0 && null!=user){
			Shoucang sc=(Shoucang)shoucangBiz.findObjectById("from Shoucang as sc where sc.userId=? and sc.scEntityId=? and scEntity='"+scEntity+"'", new Object[]{user.getId(),scEntityId});
			if(null!=sc){
				shoucangBiz.removeSc(sc);
			}
		}
	}
	@RequestMapping("/user/shoucang/removeById")
	public void removeById(HttpServletRequest req,HttpServletResponse res) throws Exception{
		SDynaActionForm form = getSDynaActionForm(req);
		Integer id = form.getInteger("id");
		User user = (User)req.getSession().getAttribute("myUser");
		if(null!=id && id!=0){
			Shoucang sc=(Shoucang)shoucangBiz.findObjectById("from Shoucang as sc where sc.id=?", new Object[]{id});
			if(null!=sc){
				shoucangBiz.removeSc(sc);
			}
		}
	}
	@RequestMapping(value={"user/shoucang/finds","wrap/user/shoucang/finds"})
	public <T> String findscs(HttpServletRequest req,HttpServletResponse res) throws Exception{
		SDynaActionForm form = getSDynaActionForm(req);
		String orderBy=form.getString("orderBy");
		String queryArgs=form.getString("queryArgs");
		String searchStr=form.getString("searchStr");
		String scEntity=form.getString("scEntity");
		req.setAttribute("scEntity", scEntity);
		User user = (User)req.getSession().getAttribute("myUser");
		
		String userIdStr="{'key':'userId','value':'"+user.getId()+"','filedType':'int'}";
		if(null==queryArgs || queryArgs.equals("")){
			queryArgs="["+userIdStr+"]";
		}else{
			if(!queryArgs.contains(userIdStr)){
				queryArgs = queryArgs.substring(0,queryArgs.length()-1);
				queryArgs=queryArgs+","+userIdStr+"]";
			}
		}
		String scEntityStr="{'key':'scEntity','value':'"+scEntity+"'}";
		if(!queryArgs.contains(scEntityStr)){
			queryArgs=queryArgs.substring(0,queryArgs.length()-1);
			queryArgs=queryArgs+","+scEntityStr+"]";
		}
		
		
		InitConfig initConfig = myFrameU.user.init.InitMavenImpl.ic;
		 HashMap<String,String> shoucangMap=initConfig.getShoucangMap();
		if(null!=shoucangMap){
			String s = shoucangMap.get(scEntity);
			if(null!=s){
				//说明配置上了
				aBiz.findEntitysByArgs(Shoucang.class, EntityTableUtil.tableName(Shoucang.class.getName()),
						queryArgs, orderBy, searchStr, true, 0, "shoucangList", req);
				
				//scEntityId_3,shoucang
				HashMap<String,Shoucang> shoucangListMap=new HashMap<String,Shoucang>();
				List<Shoucang> shoucangList = (List<Shoucang>) req.getAttribute("shoucangList");
				if(null!=shoucangList){
					StringBuffer idsb = new StringBuffer();
					int size = shoucangList.size();
					Shoucang sc = null;
					
					for(int i=0;i<size;i++){
						sc=shoucangList.get(i);
						shoucangListMap.put("scEntityId_"+sc.getScEntityId(), sc);
						if(i==(size-1)){
							idsb.append(sc.getScEntityId());
						}else{
							idsb.append(sc.getScEntityId()).append(",");
						}
					}
					req.setAttribute("shoucangListMap", shoucangListMap);
					String ids=idsb.toString();
					if(null!=ids && !ids.equals("")){
						Class c = Class.forName(scEntity);
						if(null!=c){
							List<T> list = (List<T>) aBiz.findObjectList(c, null, "from "+scEntity+" as c where c.id in("+ids+") order by c.id desc", null, false, 0, 0);
							if(scEntity.equals(Shop.class.getName())){
								List<Shop> shopList = (List<Shop>) list;
								int slsize = shopList.size();
								Shop shop = null;
								for(int i=0;i<slsize;i++){
									shop = shopList.get(i);
									shop.setMainProductTypeList(uICacheManager);
								}
								req.setAttribute("shopList", shopList);
								
							}/*else if(scEntity.equals(AuctionPeriod.class.getName())){
								List<AuctionPeriod> auctionPeriodList = (List<AuctionPeriod>) list;
								req.setAttribute("auctionPeriodList", auctionPeriodList);
							}else if(scEntity.equals(Special.class.getName())){
								List<Special> specialList = (List<Special>) list;
								req.setAttribute("specialList", specialList);
								
							}*/
						}
					}
				}
			}
		}
/*		
		
		List<ProductType> ptList = CacheKey.CKProductType.ALLMAP.getList(uICacheManager);
		req.setAttribute("productTypeList", ptList);
		
		*/
		/*if(isWrap(req)){
			if(scEntity.equals(Shop.class.getName())){
				return "wrap/manage/user/shoucang/shoucangList_shop";
			}else if(scEntity.equals(AuctionPeriod.class.getName())){
				return "wrap/manage/user/shoucang/shoucangList_ap";
			}else if(scEntity.equals(Special.class.getName())){
				return "wrap/manage/user/shoucang/shoucangList_special";
			}
		}else{
			if(scEntity.equals(Shop.class.getName())){
				return "manage/user/shoucang/shoucangList_shop";
			}else if(scEntity.equals(AuctionPeriod.class.getName())){
				return "manage/user/shoucang/shoucangList_ap";
			}else if(scEntity.equals(Special.class.getName())){
				return "manage/user/shoucang/shoucangList_special";
			}
		}
		*/
		
		
		
		return "manage/user/shoucang/shoucangList";
	}
}
