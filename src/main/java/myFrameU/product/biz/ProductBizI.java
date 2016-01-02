package myFrameU.product.biz;

import java.util.List;

import myFrameU.biz.AbstractBizI;
import myFrameU.product.entity.Product;
import myFrameU.product.entity.ProductPrice;

public interface ProductBizI extends AbstractBizI {
	public void addProduct(Product p,String content,List<ProductPrice> ppList) throws Exception;
	
	public void modifyProduct(Product p,String oldPickIds ,String content,boolean expandModify) throws Exception;
	
	public void delProduct(Product p) throws Exception;
	
	
	public Product findProductById(int pId) throws Exception;
	
	
	public List<ProductPrice> getProductPriceListByProductId(int productId) throws Exception;
	
	
	
}
