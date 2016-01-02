package myFrameU.adv.controller;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import myFrame.cache.CacheKey;
import myFrameU.adv.entity.AdvertingPage;
import myFrameU.adv.entity.Advertisement;
import myFrameU.adv.entity.Advertising;
import myFrameU.exception.exception.MyStrException;
import myFrameU.spring.mvc.CommonField;
import myFrameU.spring.mvc.FatherController;
import myFrameU.spring.mvc.SDynaActionForm;
import myFrameU.util.commonUtil.enumU.EnumUtil;
import myFrameU.util.sshUtil.hibernate.EntityTableUtil;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
@RequestMapping("admin/adv/")
public class AdvertisementController extends FatherController {
	@RequestMapping("findAdvertisements")
	public String finds(HttpServletRequest req,HttpServletResponse res) throws Exception{
		SDynaActionForm form = getSDynaActionForm(req);
		HashMap<String,Advertisement> advertisementMap =  CacheKey.CKAdvertisement.addId_advertingMarked_map.getMap(uICacheManager);
		req.setAttribute("advertisementMap", advertisementMap);
		
		
		//aBiz.findEntitysByArgs(Advertisement.class, EntityTableUtil.tableNameC(Advertisement.class), null, null, null, false, 50, "advertisementList", req);
		HashMap<String,AdvertingPage> advertingPage = CacheKey.CKAdvertingPage.ALLMAP.getMap(uICacheManager);
		req.setAttribute("advertingPageMap", advertingPage);
		
		HashMap<String,Advertising> advertisingMap = CacheKey.CKAdvertising.markedNumMap.getMap(uICacheManager);
		req.setAttribute("advertisingMap", advertisingMap);
		
		return this.getForward("systools/advertisementList", req);
	}
	
	
	@RequestMapping("findAdvertisementId")
	public String findId(HttpServletRequest req,HttpServletResponse res) throws Exception{
		SDynaActionForm form = getSDynaActionForm(req);
		Integer id = form.getInteger("id");
		if(null!=id && id.intValue()!=0){
			Advertisement a = (Advertisement)aBiz.findObjectById("from Advertisement as a where a.id=?", new Object[]{id});
			req.setAttribute("advertisement", a);
		}
		return this.getForward("systools/advertisement", req);
	}
	@RequestMapping("findAdvertisementId2Mod")
	public String findId2Mod(HttpServletRequest req,HttpServletResponse res) throws Exception{
		SDynaActionForm form = getSDynaActionForm(req);
		Integer id = form.getInteger("id");
		if(null!=id && id.intValue()!=0){
			Advertisement a = (Advertisement)aBiz.findObjectById("from Advertisement as a where a.id=?", new Object[]{id});
			req.setAttribute("advertisement", a);
			if(null!=a){
				Advertising adving = (Advertising)aBiz.findObjectById("from Advertising as a where a.id=?", new Object[]{a.getAdvertising().getId()});
				req.setAttribute("advertising", adving);
			}
		}
		return this.getForward("systools/advertisementMod", req);
	}
	
	
	@RequestMapping("closeAdvertiment")
	public void close(HttpServletRequest req,HttpServletResponse res) throws Exception{
		SDynaActionForm form = getSDynaActionForm(req);
		Integer id = form.getInteger("id");
		if(null!=id && id.intValue()!=0){
			Advertisement a = (Advertisement)aBiz.findObjectById("from Advertisement as a where a.id=?", new Object[]{id});
			if(null!=a){
				a.setStatus(Advertisement.STATUS.CLOSE.toString());
				aBiz.modifyObject(a);
			}
		}
	}
	
	
	
	@RequestMapping("modifyAdvertisement")
	public String modifyAdvertisement(HttpServletRequest req,HttpServletResponse res) throws Exception{
		SDynaActionForm form = getSDynaActionForm(req);
		Integer id = form.getInteger("id");
		String picUrl=form.getString("picUrl");
		String picSmall=form.getString("picSmall");
		String picTitle=form.getString("picTitle");
		String picA=form.getString("picA");
		String flashUrl=form.getString("flashUrl");
		Integer indexNum = form.getInteger("indexNum");
		if(null==indexNum){
			indexNum=0;
		}
		String redirect = form.getString(CommonField.redirect);
		/*Integer advertisingId = form.getInteger("advertising");
		if(null!=advertisingId && advertisingId.intValue()!=0){
			
		}*/
		if(null!=id && id.intValue()!=0){
			Advertisement a = (Advertisement)aBiz.findObjectById("from Advertisement as a where a.id=?", new Object[]{id});
			if(null!=a){
				int advertisingId = a.getAdvertising().getId();
				if(advertisingId!=0){
					Advertising adving = (Advertising)aBiz.findObjectById("from Advertising as a where a.id=?", new Object[]{advertisingId});
					if(null!=adving){
						a.setPicA(picA);
						a.setPicSmall(picSmall);
						a.setPicTitle(picTitle);
						a.setPicUrl(picUrl);
						a.setFlashUrl(flashUrl);
						String advType = adving.getAdvType();
						if(indexNum!=a.getIndexNum()){
							if(EnumUtil.equalsE(Advertising.ADVTYPE.FOUCS, advType)){
								int picNumber=adving.getPicNumber();
								if(indexNum>0 && indexNum<=picNumber){
									Advertisement a_ = (Advertisement)aBiz.findObjectById("from Advertisement as a where a.advertising.id=? and a.indexNum=? and a.status='ING'", new Object[]{id,indexNum});
									if(null==a){
										a.setIndexNum(indexNum);
									}else{
										throw new MyStrException("抱歉，该广告位的第"+indexNum+"个位置已经有生效的广告");
									}
								}
							}
						}
						aBiz.modifyObject(a);
					}
				}
			}
		}
		return redirect;
		
		
	}
	
	
	
	
	
	
	//========================================================================
	/**
	 * 简单的生效失效
	 * 		不判断生效时候是否已经有人占位了
	 */
	@RequestMapping("modifyStatus")
	public void modifyStatus(HttpServletRequest req,HttpServletResponse res) throws Exception{
		SDynaActionForm form = getSDynaActionForm(req);
		Integer id = form.getInteger("id");
		String status = form.getString("status");
		if(null!=id && id.intValue()!=0 && null!=status && !status.equals("")){
			Advertisement a = (Advertisement)aBiz.findObjectById("from Advertisement as a where a.id=?", new Object[]{id});
			if(null!=a){
				a.setStatus(status);
				aBiz.modifyObject(a);
			}
		}
	}
	
	
	
	
	
	
	
	
}
