package hhr.order.controller;

import hhr.order.entity.Cart;
import hhr.order.entity.CartItem;
import hhr.order.entity.Order;
import hhr.order.entity.PickUpAddress;
import hhr.order.util.CartUtil;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import myFrame.cache.CacheKey;
import myFrameU.address.entity.Address;
import myFrameU.exception.exception.MyStrException;
import myFrameU.product.entity.ProductPrice;
import myFrameU.spring.mvc.CommonField;
import myFrameU.spring.mvc.FatherController;
import myFrameU.spring.mvc.SDynaActionForm;
import myFrameU.util.commonUtil.text.PhoneUtil;
import myFrameU.util.sshUtil.hibernate.EntityTableUtil;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
public class PickUpAddressController extends FatherController {
	@RequestMapping(value={"admin/pua/finds"})
	public String finds(HttpServletRequest req,HttpServletResponse res) throws Exception{
		/*List<PickUpAddress> pickUpAddressList = CacheKey.CKPickUpAddress.ALLMAP.getList(uICacheManager);
		req.setAttribute("pickUpAddressList", pickUpAddressList);
		*/
		SDynaActionForm form = getSDynaActionForm(req);
		/*String fromDB=form.getString("fromDB");
		if(null==fromDB || fromDB.equals("")){
			fromDB="yes";
		}
		if(fromDB.equals("yes")){
			String orderBy = form.getString("orderBy");
			String queryArgs = form.getString("queryArgs");
			
			
			aBiz.findEntitysByArgs(
					PickUpAddress.class, 
					EntityTableUtil.tableName(PickUpAddress.class.getName()), 
					queryArgs, orderBy, null, true, 0, "pickUpAddressList", req);
			
		}else{
			List<PickUpAddress> pickUpAddressList = CacheKey.CKPickUpAddress.ALLMAP.getList(uICacheManager);
			req.setAttribute("pickUpAddressList", pickUpAddressList);
		}
		*/
		
		String orderBy = form.getString("orderBy");
		String queryArgs = form.getString("queryArgs");
		
		
		aBiz.findEntitysByArgs(
				PickUpAddress.class, 
				EntityTableUtil.tableName(PickUpAddress.class.getName()), 
				queryArgs, orderBy, null, true, 0, "pickUpAddressList", req);
		
		
		return this.getForward("order/pickupAddressList", req);
	}
	
	
	@RequestMapping(value={"admin/pua/add"})
	public String add(HttpServletRequest req,HttpServletResponse res) throws Exception{
		SDynaActionForm form = this.getSDynaActionForm(req);
		String redirect = form.getString(CommonField.redirect);
		String name  = form.getString("name");
		//String tpName=form.getString("tpName");
		String markedNum=form.getString("markedNum");
		String addressStr = form.getString("addressStr");
		String telPhone = form.getString("telPhone");
		Integer addressId = form.getInteger("addressId");
		if(null!=name && !name.equals("") && null!=addressStr && !addressStr.equals("")){
			if(/*null!=tpName && !tpName.equals("") &&*/ null!=telPhone && !telPhone.equals("")){
				if(null!=addressId && addressId.intValue()!=0){
					Address a = CacheKey.CKAddress.ALLMAP.getObject(uICacheManager, addressId);
					if(null!=a){
						PickUpAddress pua = new PickUpAddress();
						pua.setAddressId(addressId);
						pua.setAddressTreeIds(a.getTreeId());
						pua.setAddressStr(addressStr);
						pua.setName(name);
						pua.setTelPhone(telPhone);
						//pua.setTpName(tpName);
						pua.setMarkedNum(markedNum);
						aBiz.addObject(pua);
					}
				}
			}else{
				throw new MyStrException("请输入自提点电话和类别名称");	
			}
		}else{
			throw new MyStrException("请输入自提点名称和具体地址");
		}
		return redirect;
	}
	
	
	@RequestMapping(value={"admin/pua/findId"})
	public String findId(HttpServletRequest req,HttpServletResponse res) throws Exception{
		SDynaActionForm form = this.getSDynaActionForm(req);
		Integer id = form.getInteger("id");
		if(null!=id && id.intValue()!=0){
			PickUpAddress pua = (PickUpAddress)aBiz.findObjectById("from PickUpAddress as p where p.id=?", new Object[]{id});
			req.setAttribute("pickUpAddress", pua);
		}
		return this.getForward("order/pickupAddress", req);
	}
	
	@RequestMapping(value={"admin/pua/findId2Modify"})
	public String findId2Modify(HttpServletRequest req,HttpServletResponse res) throws Exception{
		SDynaActionForm form = this.getSDynaActionForm(req);
		Integer id = form.getInteger("id");
		if(null!=id && id.intValue()!=0){
			PickUpAddress pua = (PickUpAddress)aBiz.findObjectById("from PickUpAddress as p where p.id=?", new Object[]{id});
			req.setAttribute("pickUpAddress", pua);
			if(null!=pua){
				int addressId = pua.getAddressId();
				Address currentAddress = CacheKey.CKAddress.ALLMAP.getObject(uICacheManager, addressId);
				req.setAttribute("currentAddress", currentAddress);
			}
		}
		return this.getForward("order/pickupAddressMod", req);
	}
	
	@RequestMapping(value={"admin/pua/modify"})
	public String modify(HttpServletRequest req,HttpServletResponse res) throws Exception{
		SDynaActionForm form = this.getSDynaActionForm(req);
		String redirect = form.getString(CommonField.redirect);
		Integer id = form.getInteger("id");
		if(null!=id && id.intValue()!=0){
			PickUpAddress pua = (PickUpAddress)aBiz.findObjectById("from PickUpAddress as p where p.id=?", new Object[]{id});
			if(null!=pua){
				String name  = form.getString("name");
				//String tpName=form.getString("tpName");
				String markedNum=form.getString("markedNum");
				String addressStr = form.getString("addressStr");
				String telPhone = form.getString("telPhone");
				Integer addressId = form.getInteger("addressId");
				if(null!=name && !name.equals("") && null!=addressStr && !addressStr.equals("")){
					if(/*null!=tpName && !tpName.equals("") &&*/ null!=telPhone && !telPhone.equals("")){
						if(null!=addressId && addressId.intValue()!=0){
							Address a = CacheKey.CKAddress.ALLMAP.getObject(uICacheManager, addressId);
							if(null!=a){
								pua.setAddressId(addressId);
								pua.setAddressTreeIds(a.getTreeId());
								pua.setAddressStr(addressStr);
								pua.setName(name);
								pua.setTelPhone(telPhone);
								//pua.setTpName(tpName);
								pua.setMarkedNum(markedNum);
								
								aBiz.modifyObject(pua);
							}
						}
					}else{
						throw new MyStrException("请输入自提点电话和类别名称");	
					}
				}else{
					throw new MyStrException("请输入自提点名称和具体地址");
				}
			}
		}
		return redirect;
	}
	
	
	
	@RequestMapping(value={"admin/pua/remove"})
	public void remove(HttpServletRequest req,HttpServletResponse res) throws Exception{
		SDynaActionForm form = this.getSDynaActionForm(req);
		Integer id = form.getInteger("id");
		if(null!=id && id.intValue()!=0){
			PickUpAddress pua = (PickUpAddress)aBiz.findObjectById("from PickUpAddress as p where p.id=?", new Object[]{id});
			req.setAttribute("pickUpAddress", pua);
			if(null!=pua){
				aBiz.removeObject(pua);
			}
		}
	}
	
	
	@RequestMapping(value={"pua/search"})
	public String search(HttpServletRequest req,HttpServletResponse res) throws Exception{
		
		String txt = req.getParameter("txt");
		if(null!=txt && !txt.equals("")){
			List<PickUpAddress> pickUpAddressList = null;
			String verphone = PhoneUtil.vailterTelPhone(txt);
			if(null==verphone){
				//搜索电话
				pickUpAddressList = (List<PickUpAddress>)aBiz
						.findObjectList(PickUpAddress.class, 
								new Object[]{txt}, 
								"from PickUpAddress as p where p.telPhone=?", null, false, 0, 0);
			}else{
				pickUpAddressList = (List<PickUpAddress>)aBiz
						.findObjectList(PickUpAddress.class, 
								new Object[]{"%"+txt+"%","%"+txt+"%","%"+txt+"%"}, 
								"from PickUpAddress as p where p.name like ? or p.addressStr like ? or p.markedNum like ?", null, false, 0, 0);
			}
			req.setAttribute("pickUpAddressList", pickUpAddressList);
		}else{
			throw new MyStrException("请输入检索文字");
		}
		
		return "wrap/fg/help/pickList";
	}
	
	

	@RequestMapping(value={"wrap/pua/findByAddId"})
	public String findByAddId(HttpServletRequest req,HttpServletResponse res) throws Exception{
		SDynaActionForm form = this.getSDynaActionForm(req);
		Integer addId = form.getInteger("addId");
		Integer productPriceId=form.getInteger("productPriceId");
		if(null!=addId && addId!=0){
			List<PickUpAddress> pickList = null;
			if(null!=productPriceId && productPriceId.intValue()!=0){
				ProductPrice pp = (ProductPrice)aBiz.findObjectById("from ProductPrice as pp where pp.id=?", 
						new Object[]{productPriceId});
				if(null!=pp){
					
					//判断这个productPrice有没有在myCart购物车中
					//看看是否已经在购物车里了
					CartItem ci = null;
					Cart cart = (Cart)req.getSession().getAttribute("myCart");
					if(null!=cart){
						ci = CartUtil.findCIByPPId_in_cart(cart, productPriceId);
						if(null!=ci){
							int count = ci.getOcount();
							req.setAttribute("ciCount", count);
							req.setAttribute("cartItem", ci);
						}
					}
					
					/**
					 * 查找这个购物车中的cartItem,看看有没有同属于一个product的，如果有，则拿出那个自提点来，
					 * 同一个产品product只能有一个自提点
					 */
					if(null==ci){
						//如果非常明确是购买的是productPrice一样的，那就没有必要了，这里只是考虑如果productPrice没有具体准确
						//对应的，那么久去找product不是具体准确对应的
						CartItem ci_pro=CartUtil.findCIByPId_in_cart_ONE(cart, pp.getProductId());
						if(null!=ci_pro){
							req.setAttribute("ci_pro", ci_pro);
							int ci_pro_pickUpAddressId=ci_pro.getPickupAddressId();
							req.setAttribute("ci_pro_pickUpAddressId", ci_pro_pickUpAddressId);
						}
					}
					
					
					pickList = (List<PickUpAddress>)aBiz.findObjectList(PickUpAddress.class, 
							new Object[]{addId}, "from PickUpAddress as p where p.addressId=?",
							null, false, 0, 0);
					
					
					String pickIdsKH=pp.getPickupAddressIds();
					if(null!=pickIdsKH && !pickIdsKH.equals("")){
						/*pickIdsKH=pickIdsKH.substring(1,pickIdsKH.length()-1);
						String[] pickIdsKHArray=pickIdsKH.split("]\\[");
						if(null!=pickIdsKHArray){
							int len = pickIdsKHArray.length;
							if(len>0){
								String pickId_every=null;
								StringBuffer pickIdSB = new StringBuffer();
								for(int i=0;i<len;i++){
									pickId_every=pickIdsKHArray[i];
									if(i==(len-1)){
										pickIdSB.append(pickId_every);
									}else{
										pickIdSB.append(pickId_every).append(",");
									}
								}
								String pickIds = pickIdSB.toString();
								if(null!=pickIds && !pickIds.equals("")){
									pickList = (List<PickUpAddress>)aBiz.findObjectList(PickUpAddress.class, 
											new Object[]{addId}, "from PickUpAddress as p where p.addressId=?",
											null, false, 0, 0);
								}
							}
						}*/
					}else{
						/*pickList = (List<PickUpAddress>)aBiz.findObjectList(PickUpAddress.class, 
								new Object[]{addId}, "from PickUpAddress as p where p.addressId=?",
								null, false, 0, 0);*/
					}
				}
				
			}else{
				pickList = (List<PickUpAddress>)aBiz.findObjectList(PickUpAddress.class, 
						new Object[]{addId}, "from PickUpAddress as p where p.addressId=?",
						null, false, 0, 0);
			}
			
			req.setAttribute("pickUpAddressList", pickList);
		}
		return "wrap/fg/pickAjax";
	}
	
	
	
	
}
