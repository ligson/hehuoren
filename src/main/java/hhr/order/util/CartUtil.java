package hhr.order.util;

import hhr.order.entity.Cart;
import hhr.order.entity.CartItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import myFrameU.biz.AbstractBizI;
import myFrameU.product.entity.Product;
import myFrameU.product.entity.ProductPrice;
import myFrameU.user.entity.User;

public class CartUtil {
	
	public static Cart createCart(User user,AbstractBizI aBiz) throws Exception{
		Cart cart = new Cart();
		cart.setMyAddressId(0);
		cart.setRemarks("");
		cart.setToHehuorenPrice(0);
		cart.setTotalCount(0);
		cart.setToWebPrice(0);
		int tjrId = user.getTuijianRenId();
		cart.setTuijianRenId(tjrId);
		cart.setTujianRenName(user.getTuijianRenNicheng());
		cart.setUserId(user.getId());
		cart.setUserName(user.getNicheng());
		
		Set<CartItem> cartItemSet = new HashSet<CartItem>();
		cart.setCartItemSet(cartItemSet);
		
		
		return cart;
	}
	
	
	
	//根据user的级别来计算一个cartItem的单价和总价
	//还有给上级推荐人多少钱，给web多少钱
	public static CartItem jsCartItemPrice(User user,CartItem ci,ProductPrice pp) throws Exception{
		if(null==ci || null==pp){
			return null;
		}
		/**
		 * 一、levelId=1，即我是普通的客户
		 * 		1、如果我的tuijianrenId不为0，则可以享受到我推荐人的合伙人的价格
		 * 		2、如果我没有推荐人，则必须是使用网店价格
		 * 二、levelId=2|3，即是试用期|正式的合伙人，则不用判断是否有推荐人，直接是合伙人的价格去购买
		 */
		int tjrId = user.getTuijianRenId();
		float price=0;
		int levelId = user.getUserLevelId();
		if(levelId==1){
			if(tjrId==0){
				//price=p.getPrice1();
				price = pp.getPrice1();
			}else{
				//price=p.getPrice2();
				price = pp.getPrice2();
			}
		}else{
			//price=p.getPrice2();
			price = pp.getPrice2();
		}
		ci.setPrice(price);
		int count  = ci.getOcount();
		if(count>0){
			float tPrice = price*count;
			ci.setTprice(tPrice);
		}
		
		float toHehuorenPrice=0f;
		float toWebPrice=0f;
		if(tjrId==0){
			//没有推荐人，不管他自己是不是合伙人
			//全部给平台
			toHehuorenPrice=0f;
			toWebPrice=ci.getTprice();
		}else{
			//有推荐人
			//float tcBL = p.getToTjrTc();
			float tcBL = pp.getToTjrTc();
			toHehuorenPrice=(tcBL*ci.getTprice())/100;
			toWebPrice=ci.getTprice()-toHehuorenPrice;
		}
		
		ci.setToHehuorenPrice(toHehuorenPrice);
		ci.setToWebPrice(toWebPrice);
		
		return ci;
	}
	
	
	/**
	 * 根据productPriceId,查看一个cart里有没有这个cartItem
	 * 如果有就返回这个ci
	 */
	public static CartItem findCIByPPId_in_cart(Cart cart,int productPriceId) throws Exception{
		if(null==cart){
			return null;
		}
		HashSet<CartItem> ciSet = (HashSet<CartItem>) cart.getCartItemSet();
		if(null!=ciSet){
			int size = ciSet.size();
			if(size==0){
				return null;
			}else{
				CartItem ci = null;
				//int pId = 0 ;
				int ppId=0;
				Iterator it = ciSet.iterator();
				while(it.hasNext()){
					ci = (CartItem)it.next();
					//pId = ci.getProductId();
					ppId=ci.getProductPriceId();
					if(ppId==productPriceId){
						return ci;
					}
				}
			}
		}
		return null;
	}
	
	
	/**
	 * 根据productId,查看一个cart里有没有这个cartItems,注意是s，但是返回之返回carItem一个就可以了，通过这个ci拿pick
	 * 如果有就返回这个ci
	 */
	public static CartItem findCIByPId_in_cart_ONE(Cart cart,int productId) throws Exception{
		if(null==cart){
			return null;
		}
		HashSet<CartItem> ciSet = (HashSet<CartItem>) cart.getCartItemSet();
		if(null!=ciSet){
			int size = ciSet.size();
			if(size==0){
				return null;
			}else{
				CartItem ci = null;
				int pId = 0 ;
				//int ppId=0;
				Iterator it = ciSet.iterator();
				while(it.hasNext()){
					ci = (CartItem)it.next();
					pId = ci.getProductId();
					//ppId=ci.getProductPriceId();
					if(pId==productId){
						return ci;
					}
				}
			}
		}
		return null;
	}
	
	
	/**
	 * 计算cart的各个price
	 * 	遍历里面的ci，计算得出cart的
	 * 			totalCount
	 * 			totalPrice
	 * 			toHehuorenPrice
	 * 			toWebPrice
	 */
	public static Cart jsCartPrice(Cart cart,User user){
		if(null!=user && null!=cart){
			HashSet<CartItem> cartItemSet = (HashSet<CartItem>) cart.getCartItemSet();
			if(null!=cartItemSet){
				CartItem ci = null;
				float ci_price = 0f;
				float ci_tprice=0f;
				int ci_ocount=0;
				float ci_toWebPrice=0f;
				float ci_toTuijianPrice=0f;
				
				
				int cart_ocount=0;
				float cart_tprice=0f;
				float cart_toWebPrice=0f;
				float cart_toTuijian=0f;
				
				
				
				Iterator it = cartItemSet.iterator();
				while(it.hasNext()){
					ci = (CartItem) it.next();
					if(null!=ci){
						ci_price=ci.getPrice();
						ci_tprice=ci.getTprice();
						ci_ocount=ci.getOcount();
						ci_toWebPrice=ci.getToWebPrice();
						ci_toTuijianPrice=ci.getToHehuorenPrice();
						
						
						
						cart_ocount=cart_ocount+ci_ocount;
						cart_tprice=cart_tprice+ci_tprice;
						
						cart_toWebPrice=cart_toWebPrice+ci_toWebPrice;
						cart_toTuijian=cart_toTuijian+ci_toTuijianPrice;
						
						
					}
				}
				
				cart.setTotalCount(cart_ocount);
				cart.setTotalPrice(cart_tprice);
				cart.setToHehuorenPrice(cart_toTuijian);
				cart.setToWebPrice(cart_toWebPrice);
			}
		}
		return cart;
	}
	
	
	
	
	public static Cart getProductIds(Cart c) throws Exception{
		if(null!=c){
			StringBuffer sbpp=new StringBuffer("");
			StringBuffer sb = new StringBuffer("");
			HashSet<CartItem> ciSet = (HashSet<CartItem>) c.getCartItemSet();
			if(null!=ciSet){
				int size = ciSet.size();
				if(size>0){
					CartItem ci = null;
					Iterator it= ciSet.iterator();
					int productId = 0 ;
					int productPriceId=0;
					while(it.hasNext()){
						ci = (CartItem)it.next();
						productId = ci.getProductId();
						productPriceId=ci.getProductPriceId();
						sb.append("[").append(productId).append("]");
						sbpp.append("[").append(productPriceId).append("]");
					}
					
					String sbs= sb.toString();
					c.setProductIds(sbs);
					c.setProductPriceIds(sbpp.toString());
					
				}
			}
		}
		return c;
	}
	
	
	
	
	
	
	
	
	/**
	 * 打印
	 */
	public static void printCart(Cart cart){
		System.out.println("_____________________________________________________________________");
		System.out.println("userId:"+cart.getUserId()+";userNicheng:"+cart.getUserName());
		System.out.println("个数:"+cart.getTotalCount()+";总价:"+cart.getTotalPrice());
		System.out.println("给推荐人钱:"+cart.getToHehuorenPrice()+";给平台的钱:"+cart.getToWebPrice());
		HashSet<CartItem> ciSet = (HashSet<CartItem>) cart.getCartItemSet();
		if(null!=ciSet){
			CartItem ci = null;
			Iterator it = ciSet.iterator();
			while(it.hasNext()){
				ci = (CartItem)it.next();
				System.out.println("明细：产品ID["+ci.getProductId()+"],数量["+ci.getOcount()+"],单价["+ci.getPrice()+"],总价["+ci.getTprice()+"],给合伙人钱["+ci.getToHehuorenPrice()+"],给平台钱["+ci.getToWebPrice()+"]");
			}
		}
	}
	
	
	//======================================================================================================
	public static HashMap<String,Product> getProductMap(Cart cart,AbstractBizI aBiz) throws Exception{
		HashMap<String,Product> productMap= null;
		if(null!=cart){
			String productIds = cart.getProductIds();//[][][]
			/**
			 * 将以上的[][][]--->,,,,
			 */
			if(null!=productIds && !productIds.equals("")){
				String productIds_str=null;
				if(productIds.contains("][")){
					productIds=productIds.substring(1,productIds.length()-1);
					String[] proIdArray=productIds.split("]\\[");
					if(null!=proIdArray){
						int len = proIdArray.length;
						if(len>0){
							StringBuffer sb = new StringBuffer();
							String pId=null;
							for(int i=0;i<len;i++){
								pId=proIdArray[i];
								if(i==(len-1)){
									sb.append(pId);
								}else{
									sb.append(pId).append(",");
								}
							}
							productIds_str=sb.toString();
						}
					}
				}else{
					productIds_str=productIds.substring(1,productIds.length()-1);
				}
				if(null!=productIds_str && !productIds_str.equals("")){
					productMap = new HashMap<String,Product>();	
					List<Product> productList = (List<Product>)aBiz.findObjectList(
							Product.class, null, 
							"from Product as p where p.id in("+productIds_str+")", null, false, 0, 0);
					int size = productList.size();
					Product p = null;
					for(int i=0;i<size;i++){
						p= productList.get(i);
						productMap.put("productId_"+p.getId(), p);
					}
				}
				
				//req.setAttribute("productMap", productMap);
			}
		}
		return productMap;
	}
	
	
	
	
	
	
	public static HashMap<String,ProductPrice> getProductPriceMap(Cart cart,AbstractBizI aBiz) throws Exception{
		HashMap<String,ProductPrice> productMap= null;
		if(null!=cart){
			//String productIds = cart.getProductIds();//[][][]
			String productPriceIds = cart.getProductPriceIds();
			/**
			 * 将以上的[][][]--->,,,,
			 */
			if(null!=productPriceIds && !productPriceIds.equals("")){
				String productPriceIds_str=null;
				if(productPriceIds.contains("][")){
					productPriceIds=productPriceIds.substring(1,productPriceIds.length()-1);
					String[] proIdArray=productPriceIds.split("]\\[");
					if(null!=proIdArray){
						int len = proIdArray.length;
						if(len>0){
							StringBuffer sb = new StringBuffer();
							String pId=null;
							for(int i=0;i<len;i++){
								pId=proIdArray[i];
								if(i==(len-1)){
									sb.append(pId);
								}else{
									sb.append(pId).append(",");
								}
							}
							productPriceIds_str=sb.toString();
						}
					}
				}else{
					productPriceIds_str=productPriceIds.substring(1,productPriceIds.length()-1);
				}
				if(null!=productPriceIds_str && !productPriceIds_str.equals("")){
					productMap = new HashMap<String,ProductPrice>();	
					List<ProductPrice> productList = (List<ProductPrice>)aBiz.findObjectList(
							ProductPrice.class, null, 
							"from ProductPrice as p where p.id in("+productPriceIds_str+")", null, false, 0, 0);
					int size = productList.size();
					ProductPrice p = null;
					for(int i=0;i<size;i++){
						p= productList.get(i);
						productMap.put("productPriceId_"+p.getId(), p);
					}
				}
				
				//req.setAttribute("productMap", productMap);
			}
		}
		return productMap;
	}
	
	
	/**
	 * 找到cart中，同一个productId的所有的cartItem
	 */
	public static List<CartItem> getCartItemList_sameProductId(Cart cart,int productId) throws Exception{
		List<CartItem> ciList = new ArrayList<CartItem>();
		if(null!=cart){
			HashSet<CartItem> ciSet = (HashSet<CartItem>) cart.getCartItemSet();
			if(null!=ciSet){
				Iterator it = ciSet.iterator();
				CartItem ci = null;
				int productId_=0;
				while(it.hasNext()){
					ci = (CartItem)it.next();
					productId_=ci.getProductId();
					if(productId_==productId){
						ciList.add(ci);
					}
				}
			}
		}
		int size = ciList.size();
		if(size==0){
			return null;
		}else{
			return ciList;	
		}
		
	}
	
	
	
	/**
	 * 统一修改同一个product的cis，都为pickId
	 */
	public static Cart modifyCartItems_sameProduct_pickId(Cart cart,int productId,int pickId) throws Exception{
		//List<CartItem> ciList = new ArrayList<CartItem>();
		HashSet<CartItem> ciSet_new=new HashSet<CartItem>();
		if(null!=cart){
			HashSet<CartItem> ciSet = (HashSet<CartItem>) cart.getCartItemSet();
			if(null!=ciSet){
				Iterator it = ciSet.iterator();
				CartItem ci = null;
				int productId_=0;
				while(it.hasNext()){
					ci = (CartItem)it.next();
					productId_=ci.getProductId();
					if(productId_==productId){
						ci.setPickupAddressId(pickId);
						ciSet_new.add(ci);
					}
				}
			}
			cart.setCartItemSet(ciSet_new);
		}
		return cart;
		
	}
	
	
	
	
	
	
	
	
	
	
}
