package hhr.order.controller;

import hhr.order.entity.Cart;
import hhr.order.entity.CartItem;
import hhr.order.entity.PickUpAddress;
import hhr.order.util.CartUtil;
import hhr.order.util.PickUpAddressUtil;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import myFrameU.exception.exception.MyStrException;
import myFrameU.product.entity.Product;
import myFrameU.product.entity.ProductPrice;
import myFrameU.spring.mvc.FatherController;
import myFrameU.spring.mvc.SDynaActionForm;
import myFrameU.user.biz.UserBizI;
import myFrameU.user.entity.User;
import myFrameU.weixin.base.service.impl.AutoRegistAndLoginImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
public class CartController extends FatherController {
	//@Autowired
	private UserBizI userBiz;
	public UserBizI getUserBiz() {
		return userBiz;
	}
	public void setUserBiz(UserBizI userBiz) {
		this.userBiz = userBiz;
	}

	
	@RequestMapping(value={"/wrap/user/cart/addProduct"})
	public void addProduct(HttpServletRequest req,HttpServletResponse res) throws Exception{
		/**
		 */
		SDynaActionForm form = this.getSDynaActionForm(req);
		User user = (User)req.getSession().getAttribute("myUser");
		//Integer productId = form.getInteger("productId");
		Integer productPriceId = form.getInteger("productPriceId");
		
		Integer ocount = form.getInteger("ocount");
		Integer pickupAddressId= form.getInteger("pickupAddressId");
		
		if(null==ocount || ocount.intValue()==0){
			throw new MyStrException("抱歉，如果要删除请点击删除按钮");
		}
		
		/*String shouhuoTelphone=form.getString("shouhuoTelphone");
		String shouhuoName=form.getString("shouhuoName");
		if(null!=shouhuoName && !shouhuoName.equals("")){
			
		}else{
			throw new MyStrException("抱歉，请填写真实姓名");
		}*/
		/*if(null!=shouhuoTelphone && !shouhuoTelphone.equals("")){
			String verPhone = PhoneUtil.vailterTelPhone(shouhuoTelphone);
			if(null==verPhone){
				User userDB = (User)aBiz.findObjectById("from User as u where u.name=? order by u.id desc", new Object[]{shouhuoTelphone});
				if(null!=userDB){
					int userDBID=userDB.getId();
					int sessionUserID=user.getId();
					if(userDBID!=sessionUserID){
						throw new MyStrException("抱歉，该手机号已经被注册本系统，请更换手机号");
					}
				}
			}else{
				throw new MyStrException("抱歉，请输入正确的手机号码");
			}
		}else{
			throw new MyStrException("抱歉，请输入您的真实手机号码");
		}
		*/
		//处理user的name、telPhone、和真实姓名（selfInfo）
		/*String zsName = user.getSelfInfo();
		if(null==zsName || zsName.equals("")){
			user.setTelPhone(shouhuoTelphone);
			user.setName(shouhuoTelphone);
			user.setSelfInfo(shouhuoName);
			aBiz.modifyObject(user);
			req.getSession().setAttribute("myUser", user);
		}
		*/
		
		user=(User)aBiz.findObjectById("from User as u where u.id=?", new Object[]{user.getId()});
		req.getSession().setAttribute("myUser", user);
		
		
		if(null!=user){
			if(null!=productPriceId && productPriceId.intValue()!=0){
				ProductPrice productPrice = (ProductPrice)aBiz.findObjectById("from ProductPrice as pp where pp.id=?", new Object[]{productPriceId});
				if(null!=productPrice){
					if(null!=ocount && ocount.intValue()!=0){
						if(null!=pickupAddressId && pickupAddressId.intValue()!=0){
							int productId = productPrice.getProductId();
							Product product = (Product)aBiz.findObjectById("from Product as p where p.id=?", new Object[]{productId});
							if(null!=product){
								synchronized(ProductPrice.getLockKC(productPrice.getId())){
									/**
									 * 判断库存是否足够
									 */
									//int proCount = product.getProductCount();
									int proCount = productPrice.getProductCount();
									if(ocount.intValue()>proCount){
										throw new MyStrException("抱歉，库存不足，库存剩余量："+proCount+"件");
									}
									Cart myCart = (Cart)req.getSession().getAttribute("myCart");
									if(null==myCart){
										myCart = CartUtil.createCart(user, aBiz);
										req.getSession().setAttribute("myCart", myCart);
									}
									
									HashSet<CartItem> cartItemSet = (HashSet<CartItem>) myCart.getCartItemSet();
									CartItem ci=CartUtil.findCIByPPId_in_cart(myCart, productPriceId);
									if(null==ci){
										ci = new CartItem();
										ci.setCart(myCart);
										ci.setOcount(ocount);
										ci.setPickupAddressId(pickupAddressId);//也可以最后再去选择
										ci.setProductId(productId);
										ci.setProductPriceId(productPriceId);
										ci=CartUtil.jsCartItemPrice(user, ci, productPrice);
										cartItemSet.add(ci);
									}else{
										ci.setOcount(ocount);
										ci=CartUtil.jsCartItemPrice(user, ci, productPrice);
									}
									
									myCart=CartUtil.jsCartPrice(myCart, user);
									myCart = CartUtil.getProductIds(myCart);
									CartUtil.printCart(myCart);
								}
							}
						}else{
							throw new MyStrException("请选择自提点！");
						}
					}else{
						throw new MyStrException("请选择数量");
					}
				}
			}else{
			}
		}
	}
	
	
	@RequestMapping(value={"/wrap/user/cart/removeCartItem"})
	public void removeCartItem(HttpServletRequest req,HttpServletResponse res) throws Exception{
		SDynaActionForm form = this.getSDynaActionForm(req);
		Cart myCart = (Cart)req.getSession().getAttribute("myCart");
		User user = (User)req.getSession().getAttribute("myUser");
		//Integer productId = form.getInteger("productId");
		Integer productPriceId = form.getInteger("productPriceId");
		if(null!=user && null!=productPriceId && productPriceId.intValue()!=0){
			ProductPrice pp = (ProductPrice)aBiz.findObjectById("from ProductPrice as pp where pp.id=?", new Object[]{productPriceId});
			//Product product = (Product)aBiz.findObjectById("from Product as p where p.id=?", new Object[]{productId});
			if(null!=pp){
				/**
				 * 1、检查购物车里有没有这个pId对应的cartItem
				 * 2、
				 */
				CartItem ci = CartUtil.findCIByPPId_in_cart(myCart, productPriceId);
				if(null!=ci){
					HashSet<CartItem> cartItemSet = (HashSet<CartItem>) myCart.getCartItemSet();
					if(null!=cartItemSet){
						cartItemSet.remove(ci);
						myCart.setCartItemSet(cartItemSet);
						
						myCart=CartUtil.jsCartPrice(myCart, user);
						myCart = CartUtil.getProductIds(myCart);
						CartUtil.printCart(myCart);
						
						
						//查看cart里还有没有ci了，如果没有则清空cart
						HashSet<CartItem> ciSet = (HashSet<CartItem>) myCart.getCartItemSet();
						if(null!=ciSet){
							int size = ciSet.size();
							if(size==0){
								req.getSession().removeAttribute("myCart");
							}
						}
						
					}
				}
			}
		}
	}
	
	
	
	
	
	@RequestMapping(value={"/wrap/user/cart/modifyCICount"})
	public void modifyCICount(HttpServletRequest req,HttpServletResponse res) throws Exception{
		SDynaActionForm form = this.getSDynaActionForm(req);
		Cart myCart = (Cart)req.getSession().getAttribute("myCart");
		User user = (User)req.getSession().getAttribute("myUser");
		//Integer productId = form.getInteger("productId");
		Integer productPriceId = form.getInteger("productPriceId");
		Integer ocount = form.getInteger("ocount");
		if(null!=user && null!=productPriceId && productPriceId.intValue()!=0 && null!=ocount){
			ProductPrice pp = (ProductPrice)aBiz.findObjectById("from ProductPrice as pp where pp.id=?", new Object[]{productPriceId});
			//Product product = (Product)aBiz.findObjectById("from Product as p where p.id=?", new Object[]{productId});
			if(null!=pp){
				synchronized(ProductPrice.getLockKC(pp.getId())){
					/**
					 * 1、检查购物车里有没有这个pId对应的cartItem
					 * 2、
					 */
					CartItem ci = CartUtil.findCIByPPId_in_cart(myCart, productPriceId);
					if(null!=ci){
						int ocountI = ocount.intValue();
						if(ocountI==0){
							//说明是要删除ci
							HashSet<CartItem> cartItemSet = (HashSet<CartItem>) myCart.getCartItemSet();
							if(null!=cartItemSet){
								cartItemSet.remove(ci);
								myCart.setCartItemSet(cartItemSet);
								
								myCart=CartUtil.jsCartPrice(myCart, user);
								myCart = CartUtil.getProductIds(myCart);
								CartUtil.printCart(myCart);
							}
						}else{
							int oldCount = ci.getOcount();
							if(ocountI!=oldCount){
								int proCount = pp.getProductCount();
								//int proCount = product.getProductCount();
								if(ocount.intValue()>proCount){
									throw new MyStrException("抱歉，库存不足，库存剩余量："+proCount+"件");
								}
								
								HashSet<CartItem> cartItemSet = (HashSet<CartItem>) myCart.getCartItemSet();
								if(null!=cartItemSet){
									ci.setOcount(ocountI);
									ci=CartUtil.jsCartItemPrice(user, ci, pp);
									
									myCart=CartUtil.jsCartPrice(myCart, user);
									myCart = CartUtil.getProductIds(myCart);
									CartUtil.printCart(myCart);
									
									
								}
							}
						}
					}
				}
			}
		}
	}
	
	
	
	@RequestMapping(value={"/wrap/user/cart/clearCart"})
	public void clearCart(HttpServletRequest req,HttpServletResponse res) throws Exception{
		SDynaActionForm form = this.getSDynaActionForm(req);
		Cart myCart = (Cart)req.getSession().getAttribute("myCart");
		User user = (User)req.getSession().getAttribute("myUser");
		if(null!=user){
			req.getSession().removeAttribute("myCart");
		}
	}
	
	
	@RequestMapping(value={"/wrap/user/cart/modifyPuaId"})
	public void modifyPuaId(HttpServletRequest req,HttpServletResponse res) throws Exception{
		SDynaActionForm form = this.getSDynaActionForm(req);
		Cart myCart = (Cart)req.getSession().getAttribute("myCart");
		User user = (User)req.getSession().getAttribute("myUser");
		//Integer productId = form.getInteger("productId");
		Integer productPriceId = form.getInteger("productPriceId");
		Integer pickupAddressId= form.getInteger("pickupAddressId");
		if(null!=user && null!=productPriceId && productPriceId.intValue()!=0 && null!=pickupAddressId && pickupAddressId.intValue()!=0){
			CartItem ci = CartUtil.findCIByPPId_in_cart(myCart, productPriceId);
			if(null!=ci){
				/**
				 * 现在客户要求同一个产品，只能有一个自提点
				 */
				myCart=CartUtil.modifyCartItems_sameProduct_pickId(myCart, ci.getProductId(),pickupAddressId);
				//ci.setPickupAddressId(pickupAddressId);
				CartUtil.printCart(myCart);
				req.getSession().setAttribute("myCart", myCart);
			}
		}
	}
	
	
	

	@RequestMapping(value={"/wrap/user/cart/myCart"})
	public String myCart(HttpServletRequest req,HttpServletResponse res) throws Exception{
		SDynaActionForm form = this.getSDynaActionForm(req);
		User user = (User)req.getSession().getAttribute("myUser");
		if(null==user){
			String code = req.getParameter("code");
			if(null!=code && !code.equals("")){
				AutoRegistAndLoginImpl wx_aral = new AutoRegistAndLoginImpl();
				user = wx_aral.autoRegistLogin(user, code, userBiz, uICacheManager,null);
				req.getSession().setAttribute("myUser", user);
			}
		}
		
		user = (User)req.getSession().getAttribute("myUser");
		if(null!=user){
			Cart myCart = (Cart)req.getSession().getAttribute("myCart");
			
			HashMap<String,ProductPrice> productPriceMap= CartUtil.getProductPriceMap(myCart, aBiz);
			req.setAttribute("productPriceMap", productPriceMap);
			
			
			List<PickUpAddress> pickUpAddressList=
					PickUpAddressUtil.findPickUpAddressList(user.getAddressId(), uICacheManager);
			req.setAttribute("pickUpAddressList", pickUpAddressList);
			
		}
		return "wrap/manage/user/order/myCart";
	}
	
	
	
	
	
	
	
}
