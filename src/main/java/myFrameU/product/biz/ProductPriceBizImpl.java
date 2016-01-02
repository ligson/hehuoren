package myFrameU.product.biz;

import myFrameU.biz.AbstractBizImpl;
import myFrameU.exception.exception.MyStrException;
import myFrameU.product.entity.Product;
import myFrameU.product.entity.ProductPrice;
import myFrameU.product.entity.ProductPricePropertyValue;
import myFrameU.product.util.ProductPricePropertyValueUtil;

import org.springframework.stereotype.Service;
@Service("productPriceBiz")
public class ProductPriceBizImpl extends AbstractBizImpl implements
		ProductPriceBizI {

	@Override
	public void removePP(ProductPrice pp) throws Exception {
		/**
		 * 如果是默认的话，那么就得修改product的price各项之
		 */
		int pId = pp.getProductId();
		Product p = (Product)aDao.queryObjectById("from Product as p where p.id=?", new Object[]{pId});
		if(null!=p){
			int yesPP=pp.getYesproductPrice();
			if(yesPP==1){
				ProductPrice ppNew = (ProductPrice)aDao.queryObjectById("from ProductPrice as pp where pp.productId=? and pp.yesproductPrice=0 order by pp.id desc", 
						new Object[]{pId});
				if(null!=ppNew){
					p.setPrice1(ppNew.getPrice1());
					p.setPrice2(ppNew.getPrice2());
					p.setPrice1Price2(ppNew.getPrice1Price2());
					p.setToTjrTc(ppNew.getToTjrTc());
					aDao.updateObject(p);
					
					ppNew.setYesproductPrice(1);
					aDao.updateObject(ppNew);
					
				}
			}
			
			aDao.deleteObject(pp);
			aDao.getHt().flush();
			
			try{
				System.out.println("11111111111111111");
				Object allCount = (Object)aDao.j_queryObject("select sum(pp.productCount) from product_productPrice as pp where pp.productId=?", new Object[]{p.getId()});
				System.out.println("222222222222");
				if(null==allCount){
					System.out.println("33333333333333333");
					p.setProductCount(0);
				}else{
					System.out.println("44444444444444444444");
					p.setProductCount(Integer.parseInt(allCount.toString()));
				}
				System.out.println("5555555555555555");
				Object allSaleCount = (Object)aDao.j_queryObject("select sum(pp.saleCount) from product_productPrice as pp where pp.productId=?", new Object[]{p.getId()});
				System.out.println("666666666666666666");
				if(null==allSaleCount){
					System.out.println("777777777777777");
					p.setSaleCount(0);
				}else{
					System.out.println("88888888888888888");
					p.setSaleCount(Integer.parseInt(allSaleCount.toString()));
				}
				System.out.println("999999999999999999999");
				aDao.updateObject(p);
				System.out.println("101010101010");
			}catch(Exception e){
				e.printStackTrace();
			}
			
			
			ProductPricePropertyValueUtil.againUpdatePPPVs(p.getId(), this);
		}
	}

	@Override
	public void modifyPP(ProductPrice pp) throws Exception {
		if(null!=pp){
			//唯一性检查，看看有没有重复的
			String xilieName = pp.getXilieName();
			String baozhuangName = pp.getBaozhuangName();
			String shiyongName = pp.getShiyongName();
			int pId = pp.getProductId();
			
			if(pId!=0){
				ProductPrice ppOld = 
				(ProductPrice)aDao.queryObjectById
				("from ProductPrice as pp where pp.xilieName=? and pp.baozhuangName=? and pp.shiyongName=? and pp.productId=? order by pp.id desc", 
						new Object[]{xilieName,baozhuangName,shiyongName,pId});
				if(null==ppOld){
					Product p = (Product)aDao.queryObjectById("from Product as p where p.id=?", new Object[]{pId});
					if(null!=p){
						pp.setPickupAddressIds(p.getPickupAddressIds());
						aDao.updateObject(pp);
						
						Object allCountO = (Object)aDao.j_queryObject("select sum(pp.productCount) from product_productPrice as pp where pp.productId=?", new Object[]{p.getId()});
						if(null==allCountO){
							p.setProductCount(0);
						}else{
							p.setProductCount(Integer.parseInt(allCountO.toString()));
						}
						aDao.updateObject(p);
					}
					
					ProductPricePropertyValueUtil.againUpdatePPPVs(p.getId(), this);
				}else{
					throw new MyStrException("抱歉，您要修改成的新值组合已经存在，不能重复");
				}
			}
		}
		
		
	}

	@Override
	public void modifyPPYes(ProductPrice pp, int yesproductPrice)
			throws Exception {
		int ppId = pp.getId();
		int productId = pp.getProductId();
		Product p = (Product)aDao.queryObjectById("from Product as p where p.id=?", new Object[]{productId});
		if(null!=p){
			int oldYes = pp.getYesproductPrice();
			if(yesproductPrice!=oldYes){
				if(oldYes==1){
					//原先是默认的，现在要修改成不默认
					pp.setYesproductPrice(0);
					aDao.updateObject(pp);
					
					//重新选择一个
					ProductPrice ppNew=(ProductPrice)aDao.queryObjectById
							("from ProductPrice as pp where pp.productId=? and pp.id!=? order by pp.id desc",
							new Object[]{productId,ppId});
					if(null!=ppNew){
						p.setPrice1(ppNew.getPrice1());
						p.setPrice2(ppNew.getPrice2());
						p.setPrice1Price2(ppNew.getPrice1Price2());
						p.setToTjrTc(ppNew.getToTjrTc());
						aDao.updateObject(p);
					}
				}else if(oldYes==0){
					//原先不是默认的，现在要修改成默认的，涉及到三个对象，product，productPRICE1,PRODUCTpRICE2
					ProductPrice pp1=(ProductPrice)aDao.queryObjectById("from ProductPrice as pp where pp.productId=? and pp.yesproductPrice=1",
							new Object[]{productId});
					if(null!=pp1){
						//原先有一个默认的
						pp1.setYesproductPrice(0);
						aDao.updateObject(pp1);
					}
					pp.setYesproductPrice(1);
					aDao.updateObject(pp);
					
					
					p.setPrice1(pp.getPrice1());
					p.setPrice2(pp.getPrice2());
					p.setPrice1Price2(pp.getPrice1Price2());
					p.setToTjrTc(pp.getToTjrTc());
					aDao.updateObject(p);
					
				}
			}
		}
		
	}

	@Override
	public void addPP(ProductPrice pp,Product p) throws Exception {
		String xilieName = pp.getXilieName();
		String baozhuangName = pp.getBaozhuangName();
		String shiyongName = pp.getShiyongName();
		if(null!=xilieName && !xilieName.equals("") && null!=baozhuangName && !baozhuangName.equals("") && null!=shiyongName && !shiyongName.equals("")){
			ProductPrice ppOld = (ProductPrice)aDao.queryObjectById("from ProductPrice as pp where pp.xilieName=? and pp.baozhuangName=? and pp.shiyongName=? and pp.productId=? order by pp.id desc", 
					new Object[]{xilieName,baozhuangName,shiyongName,p.getId()});
			if(null==ppOld){
				/**
				 * 第一、插入
				 * 第二、更新product的总的productCount
				 * 第三、更新product对应的ProductPricePropertyValue
				 * 
				 * 第四、插入的最后一个要计算product的price1price2
				 */
				pp.setPickupAddressIds(p.getPickupAddressIds());
				aDao.insertObject(pp);
				
				aDao.getHt().flush();
				
				Object allCount = (Object)aDao.j_queryObject("select sum(pp.productCount) from product_productPrice as pp where pp.productId=?", new Object[]{p.getId()});
				if(null==allCount){
					p.setProductCount(0);
				}else{
					p.setProductCount(Integer.parseInt(allCount.toString()));
				}
				p.setPrice1(pp.getPrice1());
				p.setPrice2(pp.getPrice2());
				p.setPrice1Price2(pp.getPrice1Price2());
				p.setToTjrTc(pp.getToTjrTc());
				aDao.updateObject(p);
				
				
				
				/**
				 * 分别对系列、包装、适用
				 */
				ProductPricePropertyValue pppv_xilie=(ProductPricePropertyValue)aDao.queryObjectById
						("from ProductPricePropertyValue as pppv where pppv.productId=? and pppv.keyPy=?", 
						new Object[]{p.getId(),"xilieName"});
				if(null==pppv_xilie){
					pppv_xilie = new ProductPricePropertyValue();
					pppv_xilie.setProductId(p.getId());
					pppv_xilie.setKeyPy("xilieName");
					pppv_xilie.setKeyName("净含量");
					pppv_xilie.setKeyValues(xilieName);
					aDao.insertObject(pppv_xilie);
				}else{
					/*String keyValues=pppv_xilie.getKeyValues();
					String keyValues_add_douhao=keyValues+",";
					if(!keyValues_add_douhao.contains(xilieName+",")){
						String keyValues_new=keyValues+","+xilieName;
						pppv_xilie.setKeyValues(keyValues_new);
						aDao.updateObject(pppv_xilie);
					}*/
				}
				
				
				ProductPricePropertyValue pppv_baozhuang=(ProductPricePropertyValue)aDao.queryObjectById
						("from ProductPricePropertyValue as pppv where pppv.productId=? and pppv.keyPy=?", 
						new Object[]{p.getId(),"baozhuangName"});
				if(null==pppv_baozhuang){
					pppv_baozhuang = new ProductPricePropertyValue();
					pppv_baozhuang.setProductId(p.getId());
					pppv_baozhuang.setKeyPy("baozhuangName");
					pppv_baozhuang.setKeyName("规格");
					pppv_baozhuang.setKeyValues(baozhuangName);
					aDao.insertObject(pppv_baozhuang);
				}else{
					/*String keyValues=pppv_baozhuang.getKeyValues();
					String keyValues_add_douhao=keyValues+",";
					if(keyValues_add_douhao.contains(baozhuangName+",")){
						String keyValues_new=keyValues+","+baozhuangName;
						pppv_baozhuang.setKeyValues(keyValues_new);
						aDao.updateObject(pppv_baozhuang);
					}*/
				}
				
				ProductPricePropertyValue pppv_shiyong=(ProductPricePropertyValue)aDao.queryObjectById
						("from ProductPricePropertyValue as pppv where pppv.productId=? and pppv.keyPy=?", 
						new Object[]{p.getId(),"shiyongName"});
				if(null==pppv_shiyong){
					pppv_shiyong = new ProductPricePropertyValue();
					pppv_shiyong.setProductId(p.getId());
					pppv_shiyong.setKeyPy("shiyongName");
					pppv_shiyong.setKeyName("适用");
					pppv_shiyong.setKeyValues(shiyongName);
					aDao.insertObject(pppv_shiyong);
				}else{
					/*String keyValues=pppv_shiyong.getKeyValues();
					String keyValues_add_douhao=keyValues+",";
					if(keyValues_add_douhao.contains(shiyongName+",")){
						String keyValues_new=keyValues+","+shiyongName;
						pppv_shiyong.setKeyValues(keyValues_new);
						aDao.updateObject(pppv_shiyong);
					}*/
				}
				
				
				
				
				ProductPricePropertyValueUtil.againUpdatePPPVs(p.getId(), this);
				
			}else{
				throw new MyStrException("抱歉，您要添加的价格组合已经存在，不能重复");
			}
		}
		
	}

}
