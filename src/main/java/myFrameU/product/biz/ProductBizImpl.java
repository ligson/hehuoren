package myFrameU.product.biz;

import java.util.List;

import myFrameU.biz.AbstractBizImpl;
import myFrameU.product.entity.Product;
import myFrameU.product.entity.ProductContent;
import myFrameU.product.entity.ProductPrice;
import myFrameU.product.util.ProductPriceUtil;
import myFrameU.util.commonUtil.text.WebFormatter;

import org.springframework.stereotype.Service;
@Service("productBizI")
public class ProductBizImpl extends AbstractBizImpl implements ProductBizI {
	@Override
	public void addProduct(Product p,String content,List<ProductPrice> ppList) throws Exception {
		//第一）处理p和pc
		aDao.insertObject(p);
		ProductContent pc = new ProductContent();
		pc.setContent(content);
		String info = WebFormatter.html2text(content);
		if(null!=info){
			int len=info.length();
			if(len<50){
				pc.setInfo(info);
			}else{
				info=info.substring(0,50);
				pc.setInfo(info);
			}
		}
		pc.setProductId(p.getId());
		aDao.insertObject(pc);
		p.setProductContentId(pc.getId());
		aDao.updateObject(p);
		
		
		ppList = ProductPriceUtil.fillProduct_pp(ppList, p);
		/**
		 * 循环遍历，一直insert
		 */
		ProductPrice ppYES=null;
		int size = ppList.size();
		ProductPrice pp = null;
		int yes=0;
		for(int i=0;i<size;i++){
			pp = ppList.get(i);
			yes=pp.getYesproductPrice();
			if(yes==1){
				ppYES=pp;
			}
			pp.setPickupAddressIds(p.getPickupAddressIds());
			aDao.insertObject(pp);
		}
		
		
		int allCount = ProductPriceUtil.jisuanProductCount(ppList);
		p.setProductCount(allCount);
		if(null!=ppYES){
			p.setPrice1(ppYES.getPrice1());
			p.setPrice2(ppYES.getPrice2());
			p.setPrice1Price2(ppYES.getPrice1Price2());
			p.setToTjrTc(ppYES.getToTjrTc());
		}
		aDao.updateObject(p);
		
		
		
	}

	@Override
	public void modifyProduct(Product p,String oldPickIds, String content,boolean expandModify) throws Exception {
		int productContentId=p.getProductContentId();
		ProductContent pc = (ProductContent)aDao.queryObjectById("from ProductContent as pc where pc.id=?", new Object[]{productContentId});
		if(null!=pc){
			String contentOld = pc.getContent();
			if(null!=contentOld && null!=content){
				if(!contentOld.equals(content)){
					pc.setContent(content);
					aDao.updateObject(pc);
				}
			}
		}
		float price1=p.getPrice1();
		float price2=p.getPrice2();
		float price1price2=price1-price2;
		p.setPrice1Price2(price1price2);
		aDao.updateObject(p);
		boolean modifyPICK=false;
		String pickIdNew = p.getPickupAddressIds();
		if(null!=pickIdNew && null!=oldPickIds){
			if(!oldPickIds.equals(pickIdNew)){
				modifyPICK=true;
			}
		}else if(null==pickIdNew && null==oldPickIds){
			
		}else if(null!=pickIdNew && null==oldPickIds){
			modifyPICK=true;
		}else if(null==pickIdNew && null!=oldPickIds){
			modifyPICK=true;
		}
		if(modifyPICK){
			aDao.j_execute("update product_productPrice set pickupAddressIds=? where productId=?", 
					new Object[]{pickIdNew,p.getId()});
		}
		
	}

	@Override
	public void delProduct(Product p) throws Exception {
		aDao.deleteObject(p);
	}

	@Override
	public Product findProductById(int pId) throws Exception {
		return (Product)aDao.queryObjectById("from Product as p where p.id=?", new Object[]{pId});
	}

	@Override
	public List<ProductPrice> getProductPriceListByProductId(int productId)
			throws Exception {
		List<ProductPrice> ppList = null;
		if(productId!=0){
			ppList = (List<ProductPrice>)aDao.queryObjectList(ProductPrice.class, 
					new Object[]{productId}, 
					"from ProductPrice as pp where pp.productId=?",
					null, false, 0, 0);
		}
		return ppList;
	}

}
