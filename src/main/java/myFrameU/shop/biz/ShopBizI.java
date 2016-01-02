package myFrameU.shop.biz;

import myFrameU.biz.AbstractBizI;
import myFrameU.shop.entity.Shop;
import myFrameU.shop.entity.ShopUser;

public interface ShopBizI extends AbstractBizI {
	public void registShop(Shop shop,ShopUser su) throws Exception;
}
