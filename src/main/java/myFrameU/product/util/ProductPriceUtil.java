package myFrameU.product.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import myFrameU.biz.AbstractBizI;
import myFrameU.product.entity.Product;
import myFrameU.product.entity.ProductPrice;

public class ProductPriceUtil {
	/**
	 * 将页面传递过来的各个price的参数数组分别组合成priceList
	 * 
	 * 	唯一性检查
	 * 		净含量、规格、适用三者组合唯一
	 */
	public static List<ProductPrice> createProductPriceList
	(String[] xilieNames,String[] baozhuangNames,String[] shiyongNames,String[] price1s,String[] price2s,String[] toTjrTcs,String[] productCounts) throws Exception{
		List<ProductPrice> ppList = new ArrayList<ProductPrice>();
		
		/**
		 * 筛选出不为空的来，那些只要有一个值为空的都不要
		 */
		int len=xilieNames.length;
		String xilieName = null;
		String baozhuangName = null;
		String price1=null;
		String price2= null;
		String toTjrTc=null;
		String productCount = null;
		String shiyongName=null;
		//String yesproductPrice=null;
		for(int i=0;i<len;i++){
			xilieName = xilieNames[i];
			if(null!=xilieName && !xilieName.equals("")){
				baozhuangName = baozhuangNames[i];
				price1=price1s[i];
				price2=price2s[i];
				toTjrTc=toTjrTcs[i];
				productCount=productCounts[i];
				shiyongName=shiyongNames[i];
				
				//yesproductPrice=yesproductPrices[i];
				if(null!=xilieName && !xilieName.equals("") && 
						null!=baozhuangName && !baozhuangName.equals("") && 
						null!=price1 && !price1.equals("") && 
						null!=price2 && !price2.equals("") && 
						null!=productCount && !productCount.equals("") && null!=shiyongName && !shiyongName.equals("")){
					int productCountI=new Integer(productCount);
					if(productCountI>0){
						ProductPrice pp = new ProductPrice();
						pp.setProductCount(productCountI);
						pp.setSaleCount(0);
						int ppSIZE = ppList.size();
						if(ppSIZE==0){
							pp.setYesproductPrice(1);
						}else{
							pp.setYesproductPrice(0);
						}
						pp.setXilieName(xilieName);
						pp.setBaozhuangName(baozhuangName);
						float price1F = new Float(price1);
						float price2F = new Float(price2);
						float cha=price1F-price2F;
						pp.setPrice1(price1F);
						pp.setPrice2(price2F);
						pp.setPrice1Price2(cha);
						pp.setToTjrTc(new Float(toTjrTc));
						pp.setShiyongName(shiyongName);
						/*pp.setProductId(p.getId());
						pp.setProductImg(p.getMainImage());
						pp.setProductName(p.getName());*/
						//pp.setYesproductPrice(new Integer(yesproductPrice));
						boolean isChongfu = isChongfu(ppList,pp);
						if(!isChongfu){
							ppList.add(pp);
						}
					}
				}
			}
			
		}
		
		return ppList;
	}
	
	//如果重复就是true，如果不重复就是false
	private static boolean isChongfu(List<ProductPrice> ppList,ProductPrice pp) throws Exception{
		if(null!=pp){
			String xilieName = pp.getXilieName();
			String baozhuangName = pp.getBaozhuangName();
			String shiyongName = pp.getShiyongName();
			if(null!=ppList){
				int size = ppList.size();
				ProductPrice pp_ = null;
				String pp_xilieName = null;
				String pp_baozhuangName = null;
				String pp_shiyongName = null;
				for(int i=0;i<size;i++){
					pp_=ppList.get(i);
					pp_xilieName = pp_.getXilieName();
					pp_baozhuangName = pp_.getBaozhuangName();
					pp_shiyongName = pp_.getShiyongName();
					if(null!=pp_xilieName && null!=pp_baozhuangName && null!=pp_shiyongName){
						if(xilieName.equals(pp_xilieName) && baozhuangName.equals(pp_baozhuangName) && shiyongName.equals(pp_shiyongName)){
							return true;
						}
					}
				}
			}else{
				return false;
			}
		}
		return false;
		
	}
	
	
	/**
	 * 遍历刚生成的ppList，因为里面没有product的属性，要完善
	 */
	public static List<ProductPrice> fillProduct_pp(List<ProductPrice> ppList,Product p) throws Exception{
		List<ProductPrice> ppListNew = new ArrayList<ProductPrice>();
		int size = ppList.size();
		ProductPrice pp = null;
		for(int i=0;i<size;i++){
			pp=ppList.get(i);
			pp.setProductId(p.getId());
			pp.setProductImg(p.getMainImage());
			pp.setProductName(p.getName());
			ppListNew.add(pp);
		}
		return ppListNew;
	}
	
	
	
	
	/**
	 * 计算product总的count
	 */
	public static int jisuanProductCount(List<ProductPrice> ppList) throws Exception{
		if(null!=ppList){
			int size = ppList.size();
			ProductPrice pp = null;
			int count=0;
			int allCount=0;
			for(int i=0;i<size;i++){
				pp = ppList.get(i);
				count=pp.getProductCount();
				allCount=allCount+count;
			}
			return allCount;
		}
		return 0;
	}
	
	public static HashMap<String,Float> getMaxMinPrice(List<ProductPrice> ppList) throws Exception{
		HashMap<String,Float> map = new HashMap<String,Float>();
		if(null!=ppList){
			float maxPrice_1=0;
			float minPrice_1=1000000;
			float maxPrice_2=0;
			float minPrice_2=1000000;
			int size = ppList.size();
			ProductPrice pp = null;
			float pp_price1=0;
			float pp_price2=0;
			for(int i=0;i<size;i++){
				pp=ppList.get(i);
				pp_price1=pp.getPrice1();//网店价格
				pp_price2=pp.getPrice2();//合伙人价格
				if(pp_price1>maxPrice_1){
					maxPrice_1=pp_price1;
				}
				if(pp_price1<minPrice_1){
					minPrice_1=pp_price1;
				}
				
				if(pp_price2>maxPrice_2){
					maxPrice_2=pp_price2;
				}
				if(pp_price2<minPrice_2){
					minPrice_2=pp_price2;
				}
				
			}
			map.put("maxPrice_1",maxPrice_1);
			map.put("minPrice_1",minPrice_1);
			map.put("maxPrice_2",maxPrice_2);
			map.put("minPrice_2",minPrice_2);
		}
		return map;
	}
	
	//选中第一个
	public static HashMap<String,Object> selectFirst(List<ProductPrice> ppList) throws Exception{
		HashMap<String,Object> map = new HashMap<String,Object>();
		List<ProductPrice> ppListNEW=new ArrayList<ProductPrice>();
		if(null!=ppList){
			ProductPrice ppFirst=null;
			int size = ppList.size();
			ProductPrice pp = null;
			for(int i=0;i<size;i++){
				pp=ppList.get(i);
				if(i==0){
					pp.setNoSave_select("YES");
					ppFirst=pp;
				}
				ppListNEW.add(pp);
			}
			map.put("ppFirst", ppFirst);
			map.put("ppList", ppListNEW);
		}
		return map;
	}
	
	
	//重新计算一个product的productCount 、saleCount
	public static void againJS_product_counts(int productId,AbstractBizI aBiz) throws Exception{
		Product p = (Product)aBiz.findObjectById("from Product as p where p.id=?", new Object[]{productId});
		if(null!=p){
			List<ProductPrice> ppList = (List<ProductPrice>)aBiz.findObjectList(ProductPrice.class, new Object[]{productId}, 
					"from ProductPrice as pp where pp.productId=?",
					null, false, 0, 0);
			if(null!=ppList){
				int allPC=0;
				int allSC=0;
				ProductPrice pp = null;
				int size = ppList.size();
				int productCount=0;
				int saleCount=0;
				for(int i=0;i<size;i++){
					pp=ppList.get(i);
					productCount=pp.getProductCount();
					saleCount=pp.getSaleCount();
					allPC=allPC+productCount;
					allSC=allSC+saleCount;
				}
				p.setProductCount(allPC);
				p.setSaleCount(allSC);
				aBiz.modifyObject(p);
			}
			
		}
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
