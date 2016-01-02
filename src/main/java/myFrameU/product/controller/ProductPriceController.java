package myFrameU.product.controller;

import hhr.order.entity.Cart;
import hhr.order.entity.CartItem;
import hhr.order.entity.PickUpAddress;
import hhr.order.util.CartUtil;
import hhr.order.util.PickUpAddressUtil;
import hhr.user.util.UserUtil;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import myFrameU.exception.exception.MyStrException;
import myFrameU.product.biz.ProductPriceBizI;
import myFrameU.product.entity.Product;
import myFrameU.product.entity.ProductPrice;
import myFrameU.product.entity.ProductPricePropertyValue;
import myFrameU.product.util.ProductPricePropertyValueUtil;
import myFrameU.product.util.ProductPriceUtil;
import myFrameU.spring.mvc.FatherController;
import myFrameU.spring.mvc.SDynaActionForm;
import myFrameU.user.entity.User;
import myFrameU.util.commonUtil.json.JSONUtils;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
public class ProductPriceController extends FatherController {
	@Autowired
	private ProductPriceBizI productPriceBiz;
	public ProductPriceBizI getProductPriceBiz() {
		return productPriceBiz;
	}
	public void setProductPriceBiz(ProductPriceBizI productPriceBiz) {
		this.productPriceBiz = productPriceBiz;
	}
	
	@RequestMapping(value={"/admin/proprice/removePP"})
	public void removePP(HttpServletRequest req,HttpServletResponse res) throws Exception{
		SDynaActionForm form = this.getSDynaActionForm(req);
		Integer productPriceId = form.getInteger("productPriceId");
		if(null!=productPriceId && productPriceId.intValue()!=0){
			ProductPrice pp = (ProductPrice)aBiz.findObjectById("from ProductPrice as pp where pp.id=?", new Object[]{productPriceId});
			if(null!=pp){
				productPriceBiz.removePP(pp);
			}
		}
	}
	
	@RequestMapping(value={"/admin/proprice/modifyPP"})
	public void modifyPP(HttpServletRequest req,HttpServletResponse res) throws Exception{
		SDynaActionForm form = this.getSDynaActionForm(req);
		Integer productPriceId = form.getInteger("productPriceId");
		String xilieName = form.getString("xilieName");
		String baozhuangName = form.getString("baozhuangName");
		float price1=form.getFloat("price1");
		float price2 = form.getFloat("price2");
		float toTjrTc = form.getFloat("toTjrTc");
		int productCount=form.getInteger("productCount");
		String shiyongName= form.getString("shiyongName");
		
		//int yesproductPrice= form.getInteger("yesproductPrice");
		
		if(null!=productPriceId && productPriceId.intValue()!=0){
			ProductPrice pp = (ProductPrice)aBiz.findObjectById("from ProductPrice as pp where pp.id=?", new Object[]{productPriceId});
			if(null!=pp){
				if(null==xilieName || xilieName.equals("")){
					throw new MyStrException("请填写净含量");
				}
				if(null==baozhuangName || baozhuangName.equals("")){
					throw new MyStrException("请填写规格");
				}
				if(null==shiyongName || shiyongName.equals("")){
					throw new MyStrException("请填写适用");
				}
				if(price1==0){
					throw new MyStrException("请填写网店价格");
				}
				if(price2==0){
					throw new MyStrException("请填写合伙人价格");
				}
				
				pp.setXilieName(xilieName);
				pp.setShiyongName(shiyongName);
				pp.setShiyongName(shiyongName);
				
				pp.setBaozhuangName(baozhuangName);
				pp.setPrice1(price1);
				pp.setPrice2(price2);
				pp.setPrice1Price2(price1-price2);
				pp.setToTjrTc(toTjrTc);
				pp.setProductCount(productCount);
				
				productPriceBiz.modifyPP(pp);
			}
		}
	}
	
	
	@RequestMapping(value={"/admin/proprice/modifyYes"})
	public void modifyYes(HttpServletRequest req,HttpServletResponse res) throws Exception{
		SDynaActionForm form = this.getSDynaActionForm(req);
		Integer productPriceId = form.getInteger("productPriceId");
		int yesproductPrice = form.getInteger("yesproductPrice");
		if(null!=productPriceId && productPriceId.intValue()!=0){
			ProductPrice pp = (ProductPrice)aBiz.findObjectById("from ProductPrice as pp where pp.id=?", new Object[]{productPriceId});
			if(null!=pp){
				productPriceBiz.modifyPPYes(pp, yesproductPrice);
			}
		}
	}
	
	@RequestMapping(value={"/admin/proprice/addPP"})
	public void addPP(HttpServletRequest req,HttpServletResponse res) throws Exception{
		SDynaActionForm form = this.getSDynaActionForm(req);
		Integer productId = form.getInteger("productId");
		String xilieName = form.getString("xilieName");
		String baozhuangName = form.getString("baozhuangName");
		float price1 = form.getFloat("price1");
		float price2 = form.getFloat("price2");
		float toTjrTc = form.getFloat("toTjrTc");
		Integer productCount = form.getInteger("productCount");
		String shiyongName= form.getString("shiyongName");
		if(null==productCount || productCount.intValue()==0){
			throw new MyStrException("请填写数量");
		}
		
		if(null==xilieName || xilieName.equals("")){
			throw new MyStrException("请填写净含量");
		}
		if(null==baozhuangName || baozhuangName.equals("")){
			throw new MyStrException("请填写规格");
		}
		if(null==shiyongName || shiyongName.equals("")){
			throw new MyStrException("请填写适用");
		}
		if(price1==0){
			throw new MyStrException("请填写网店价格");
		}
		if(price2==0){
			throw new MyStrException("请填写合伙人价格");
		}
		
		
		if(null!=productId && productId.intValue()!=0){
			Product p =(Product)aBiz.findObjectById("from Product as p where p.id=?", new Object[]{productId});
			if(null!=p){
				ProductPrice pp = new ProductPrice();
				pp.setXilieName(xilieName);
				pp.setBaozhuangName(baozhuangName);
				pp.setShiyongName(shiyongName);
				
				pp.setPrice1(price1);
				pp.setPrice2(price2);
				pp.setPrice1Price2(price1-price2);
				pp.setToTjrTc(toTjrTc);
				pp.setProductCount(productCount);
				
				
				
				pp.setProductId(productId);
				pp.setProductImg(p.getMainImage());
				pp.setProductName(p.getName());
				
				pp.setYesproductPrice(0);
				
				productPriceBiz.addPP(pp, p);
				
				
			}
		}
	}
	
	
	
	@RequestMapping(value={"/wrap/proprice/findsByPId"})
	public String findsByPId(HttpServletRequest req,HttpServletResponse res) throws Exception{
		SDynaActionForm form = this.getSDynaActionForm(req);
		Integer productId = form.getInteger("productId");
		if(null!=productId && productId.intValue()!=0){
			List<ProductPrice> ppList = (List<ProductPrice>)aBiz.findObjectList(
					ProductPrice.class, new Object[]{productId}, 
					"from ProductPrice as pp where pp.productId=?",
					null, false, 0, 0);
			req.setAttribute("productPriceList", ppList);
			
		}
		return "wrap/fg/productPriceList";
	}
	
	
	
	
	
	
	
	
	
	//============================================
	@RequestMapping(value={"/wrap/product/toBuy_step1"})
	public String toBuy_step1(HttpServletRequest req,HttpServletResponse res) throws Exception{
		SDynaActionForm form = getSDynaActionForm(req);
		Integer productId=form.getInteger("productId");//这里的id是productId，不是productPriceId
		User user = (User)req.getSession().getAttribute("myUser");
		if(null==user){
			return "redirect:/wrap/toUserLogin";
		}
		if(null!=productId && productId.intValue()!=0){
			Product p = (Product)aBiz.findObjectById("from Product as p where p.id=?", new Object[]{productId});
			if(null!=p){
				List<ProductPricePropertyValue> pppvList = ProductPricePropertyValueUtil.getPPPVList(productId, aBiz);
				req.setAttribute("pppvList", pppvList);
				req.setAttribute("product", p);
				List<ProductPrice> ppList = (List<ProductPrice>)aBiz.findObjectList(
						ProductPrice.class, 
						new Object[]{p.getId()}, 
						"from ProductPrice as pp where pp.productId=?",
						null, false, 0, 0);
				HashMap<String,Object> map = ProductPriceUtil.selectFirst(ppList);
				if(null!=map){
					ProductPrice ppFirst = (ProductPrice)map.get("ppFirst");
					req.setAttribute("ppFirst", ppFirst);
					ppList = (List<ProductPrice>)map.get("ppList");
					req.setAttribute("productPriceList", ppList);
					
					
					HashMap<String,Float> priceMap = ProductPriceUtil.getMaxMinPrice(ppList);
					req.setAttribute("priceMap", priceMap);
					boolean canUseHHRPrice=UserUtil.canUserHHRPrice(user);
					req.setAttribute("canUseHHRPrice", canUseHHRPrice);
					
				}
			}
		}
		return "wrap/fg/buyProduct_1";
	}
	
	@RequestMapping(value={"/wrap/product/toBuy_step2"})
	public String toBuy_step2(HttpServletRequest req,HttpServletResponse res) throws Exception{
		SDynaActionForm form = getSDynaActionForm(req);
		Integer id=form.getInteger("id");//这里的id是productPrticeId，不是productId
		User user = (User)req.getSession().getAttribute("myUser");
		if(null==user){
			return "redirect:/wrap/toUserLogin";
		}
		if(null!=id && id.intValue()!=0){
			//找自己的推荐人
			int tjrId = user.getTuijianRenId();
			if(tjrId!=0){
				User tjr = (User)aBiz.findObjectById("from User as u where u.id=?", new Object[]{tjrId});
				req.setAttribute("myTJR", tjr);
			}
			ProductPrice pp = (ProductPrice)aBiz.findObjectById("from ProductPrice as pp where pp.id=?", new Object[]{id});
			//Product product = (Product)aBiz.findObjectById("from Product as p where p.id=?", new Object[]{id});
			if(null!=pp){
				req.setAttribute("productPrice", pp);
				req.setAttribute("ciCount", 1);
				
				//看看是否已经在购物车里了
				CartItem ci = null;
				Cart cart = (Cart)req.getSession().getAttribute("myCart");
				if(null!=cart){
					ci = CartUtil.findCIByPPId_in_cart(cart, id);
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
				
				
				
				/**
				 * 找自提点
				 * 		地区+product
				 */
				List<PickUpAddress> pickUpAddressList=
						PickUpAddressUtil.findPickUpAddressList(user.getAddressId(), pp.getPickupAddressIds(), uICacheManager);
				req.setAttribute("pickUpAddressList", pickUpAddressList);
				
				
			}
		}
		return "wrap/fg/buyProduct_2";
	}
	
	
	
	
	@RequestMapping(value={"/wrap/proprice/search"})
	public void search(HttpServletRequest req,HttpServletResponse res) throws Exception{
		int ppId = 0;
		net.sf.json.JSONObject jo = null;
		String json=null;
		SDynaActionForm form = this.getSDynaActionForm(req);
		String xilieName = form.getString("xilieName");
		String baozhuangName = form.getString("baozhuangName");
		String shiyongName = form.getString("shiyongName");
		Integer productId = form.getInteger("productId");
		if(null!=productId && productId.intValue()!=0){
			if(null!=xilieName && !xilieName.equals("")
					&& null!=baozhuangName && !baozhuangName.equals("")
					&& null!=shiyongName && !shiyongName.equals("")){
				System.out.println("xilieName="+xilieName);
				System.out.println("baozhuangName="+baozhuangName);
				System.out.println("shiyongName="+shiyongName);
				System.out.println("productId="+productId);
				
				ProductPrice pp =(ProductPrice)aBiz.findObjectById
						("from ProductPrice as p where p.xilieName=? and p.baozhuangName=? and p.shiyongName=? and p.productId=?", 
								new Object[]{xilieName,baozhuangName,shiyongName,productId});
				if(null!=pp){
					System.out.println("pp不为空..............");
					ppId=pp.getId();
					jo=JSONUtils.toJSONObject(pp);
					//json=JSONUtils.toJSONString(pp);
				}else{
					System.out.println("PP为空。。。。。。。。。");
				}
			}
		}
		if(null!=jo){
			this.renderData(res, jo.toString());	
		}else{
			this.renderData(res, "");
		}
		
	}
	
	
	
	
	
}
