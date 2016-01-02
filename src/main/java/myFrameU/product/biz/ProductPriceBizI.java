package myFrameU.product.biz;

import myFrameU.product.entity.Product;
import myFrameU.product.entity.ProductPrice;

public interface ProductPriceBizI {
	public void removePP(ProductPrice pp ) throws Exception;
	public void modifyPP(ProductPrice pp) throws Exception;
	
	public void modifyPPYes(ProductPrice pp,int yesproductPrice) throws Exception;
	
	
	public void addPP(ProductPrice pp,Product p ) throws Exception;
	
}
