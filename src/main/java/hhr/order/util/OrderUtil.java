package hhr.order.util;

import hhr.order.entity.Order;
import hhr.order.entity.OrderItem;
import hhr.order.entity.PickUpAddress;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import myFrameU.biz.AbstractBizI;
import myFrameU.exception.exception.MyStrException;
import myFrameU.product.entity.Product;
import myFrameU.product.entity.ProductPrice;
import myFrameU.product.util.ProductPriceUtil;
import myFrameU.user.entity.User;
import myFrameU.util.commonUtil.text.PhoneUtil;

public class OrderUtil {
	
	//处理收货人电话和姓名
	public static Order fillShouhuoRen(HttpServletRequest req,AbstractBizI aBiz,Order o,User user,String shouhuoName,String shouhuoTelphone) throws Exception{
		if(null!=shouhuoName && !shouhuoName.equals("")){
			
		}else{
			throw new MyStrException("抱歉，请填写真实姓名");
		}
		if(null!=shouhuoTelphone && !shouhuoTelphone.equals("")){
			String verPhone = PhoneUtil.vailterTelPhone(shouhuoTelphone);
			if(null==verPhone){
				User userDB = (User)aBiz.findObjectById("from User as u where u.name=? order by u.id desc", new Object[]{shouhuoTelphone});
				if(null!=userDB){
					int userDBID=userDB.getId();
					int sessionUserID=user.getId();
					if(userDBID!=sessionUserID){
						//throw new MyStrException("抱歉，该手机号已经被注册本系统，请更换手机号");
					}
				}
			}else{
				throw new MyStrException("抱歉，请输入正确的手机号码");
			}
		}else{
			throw new MyStrException("抱歉，请输入您的真实手机号码");
		}
		
		//处理user的name、telPhone、和真实姓名（selfInfo）
		String zsName = user.getSelfInfo();
		if(null==zsName || zsName.equals("")){
			user.setTelPhone(shouhuoTelphone);
			user.setName(shouhuoTelphone);
			user.setSelfInfo(shouhuoName);
			aBiz.modifyObject(user);
			req.getSession().setAttribute("myUser", user);
		}
		o.setShouhuoName(shouhuoName);
		o.setShouhuoTelphone(shouhuoTelphone);
		return o;
	}
	
	public static String getProductIds(List<Order> oList) throws Exception{
		List<Integer> productIdList = new ArrayList<Integer>();
		if(null!=oList){
			int size = oList.size();
			Order o = null;
			Set<OrderItem> oiSet=null;
			Iterator it = null;
			OrderItem oi = null;
			int productId = 0;
			for(int i=0;i<size;i++){
				o=oList.get(i);
				
				oiSet =  o.getOiSet();
				it = oiSet.iterator();
				while(it.hasNext()){
					oi = (OrderItem)it.next();
					productId = oi.getProductId();
					if(!productIdList.contains(productId)){
						productIdList.add(productId);
					}
				}
			}
		}
		StringBuffer sb = new StringBuffer();
		Integer proId = null;
		int productId =  0;
		int listSize = productIdList.size();
		for( int i=0;i<listSize;i++){
			productId =  productIdList.get(i);
			if(i==(listSize-1)){
				sb.append(productId);
			}else{
				sb.append(productId).append(",");
			}
			
		}
		
		return sb.toString();
		
	}
	
	
	
	public static String getProductIds(Order o) throws Exception{
		StringBuffer pIdsb = new StringBuffer("");
		Set<OrderItem> oiSet = o.getOiSet();
		if(null!=oiSet){
			int size  = oiSet.size();
			if(size>0){
				int productId = 0 ;
				OrderItem oi = null;
				Iterator it = oiSet.iterator();
				while(it.hasNext()){
					oi = (OrderItem)it.next();
					productId = oi.getProductId();
					pIdsb.append(productId).append(",");
				}
			}
		}
		String pIds = pIdsb.toString();
		if(null!=pIds){
			if(pIds.endsWith(",")){
				pIds = pIds.substring(0,pIds.length()-1);
			}
		}
		
		return pIds;
	}
	
	public static HashMap<String,Product> getProductMap(Order order,AbstractBizI aBiz) throws Exception{
		HashMap<String,Product> productMap= null;
		if(null!=order){
			String productIds = OrderUtil.getProductIds(order);
			if(null!=productIds && !productIds.equals("")){
				productMap = new HashMap<String,Product>();	
				List<Product> productList = (List<Product>)aBiz.findObjectList(
						Product.class, null, 
						"from Product as p where p.id in("+productIds+")", null, false, 0, 0);
				int size = productList.size();
				Product p = null;
				for(int i=0;i<size;i++){
					p= productList.get(i);
					productMap.put("productId_"+p.getId(), p);
				}
				//req.setAttribute("productMap", productMap);
			}
		}
		return productMap;
	}
	
	
	
	
	
	
	//=========================================
	public static String getProductPriceIds(List<Order> oList) throws Exception{
		List<Integer> productPriceIdList = new ArrayList<Integer>();
		if(null!=oList){
			int size = oList.size();
			Order o = null;
			Set<OrderItem> oiSet=null;
			Iterator it = null;
			OrderItem oi = null;
			//int productId = 0;
			int productPriceId=0;
			for(int i=0;i<size;i++){
				o=oList.get(i);
				
				oiSet =  o.getOiSet();
				it = oiSet.iterator();
				while(it.hasNext()){
					oi = (OrderItem)it.next();
					//productId = oi.getProductId();
					productPriceId=oi.getProductPriceId();
					if(!productPriceIdList.contains(productPriceId)){
						productPriceIdList.add(productPriceId);
					}
				}
			}
		}
		StringBuffer sb = new StringBuffer();
		Integer proId = null;
		//int productId =  0;
		int productPriceId=0;
		int listSize = productPriceIdList.size();
		for( int i=0;i<listSize;i++){
			productPriceId =  productPriceIdList.get(i);
			if(i==(listSize-1)){
				sb.append(productPriceId);
			}else{
				sb.append(productPriceId).append(",");
			}
			
		}
		
		return sb.toString();
		
	}
	public static String getProductPriceIds(Order o) throws Exception{
		StringBuffer pIdsb = new StringBuffer("");
		Set<OrderItem> oiSet = o.getOiSet();
		if(null!=oiSet){
			int size  = oiSet.size();
			if(size>0){
				int productPriceId = 0;
				//int productId = 0 ;
				OrderItem oi = null;
				Iterator it = oiSet.iterator();
				while(it.hasNext()){
					oi = (OrderItem)it.next();
					//productId = oi.getProductId();
					productPriceId = oi.getProductPriceId();
					pIdsb.append(productPriceId).append(",");
				}
			}
		}
		String pIds = pIdsb.toString();
		if(null!=pIds){
			if(pIds.endsWith(",")){
				pIds = pIds.substring(0,pIds.length()-1);
			}
		}
		
		return pIds;
	}
	
	public static HashMap<String,ProductPrice> getProductPriceMap(Order order,AbstractBizI aBiz) throws Exception{
		HashMap<String,ProductPrice> productPriceMap= null;
		if(null!=order){
			String productPriceIds = OrderUtil.getProductPriceIds(order);
			if(null!=productPriceIds && !productPriceIds.equals("")){
				productPriceMap = new HashMap<String,ProductPrice>();	
				List<ProductPrice> productPriceList = (List<ProductPrice>)aBiz.findObjectList(
						ProductPrice.class, null, 
						"from ProductPrice as p where p.id in("+productPriceIds+")", null, false, 0, 0);
				int size = productPriceList.size();
				ProductPrice pp = null;
				for(int i=0;i<size;i++){
					pp= productPriceList.get(i);
					productPriceMap.put("productPriceId_"+pp.getId(), pp);
				}
				//req.setAttribute("productMap", productMap);
			}
		}
		return productPriceMap;
	}
	
	
	//===============================================================================================================
	public static void againJSProducts_counts(Order o,AbstractBizI aBiz) throws Exception{
		if(null!=o){
			Set<OrderItem> oiSet =  o.getOiSet();
			if(null!=oiSet){
				Iterator it = oiSet.iterator();
				OrderItem oi = null;
				int productId=0;
				while(it.hasNext()){
					oi=(OrderItem)it.next();
					productId=oi.getProductId();
					ProductPriceUtil.againJS_product_counts(productId, aBiz);
				}
			}
		}
	}
	
	
	//找到所有的不重复的自提点orderId_
	public static Map<String,List<PickUpAddress>> getPickListMap(List<Order> orderList,HashMap<String,PickUpAddress> pickMap){
		Map<String,List<PickUpAddress>> map = new HashMap<String,List<PickUpAddress>>();
		if(null!=orderList){
			Order o = null;
			int size = orderList.size();
			Set<OrderItem> oiSet = null;
			OrderItem oi = null;
			for(int i=0;i<size;i++){
				List<PickUpAddress> myList = new ArrayList<PickUpAddress>();
				o=orderList.get(i);
				oiSet=o.getOiSet();
				if(null!=oiSet){
					Iterator it = oiSet.iterator();
					int pickUpAddressId = 0;
					PickUpAddress pick = null;
					while(it.hasNext()){
						oi = (OrderItem)it.next();
						if(null!=oi){
							pickUpAddressId=oi.getPickupAddressId();
							pick=pickMap.get("pickUpAddressId_"+pickUpAddressId);
							if(null!=pick){
								if(!myList.contains(pick)){
									myList.add(pick);
								}
							}
						}
					}
				}
				map.put("orderId_"+o.getId(), myList);
			}
		}
		return map;
	}
}
