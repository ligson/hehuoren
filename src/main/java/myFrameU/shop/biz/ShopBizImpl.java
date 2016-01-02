package myFrameU.shop.biz;

import java.util.Date;

import myFrameU.account.entity.Account;
import myFrameU.biz.AbstractBizImpl;
import myFrameU.shop.entity.Certification;
import myFrameU.shop.entity.Shop;
import myFrameU.shop.entity.ShopContent;
import myFrameU.shop.entity.ShopTemplateSimple;
import myFrameU.shop.entity.ShopUser;
import myFrameU.sincerity.entity.SincerityArchives;
import myFrameU.statistics.entity.Statistics;
import myFrameU.user.entity.User;
import myFrameU.util.commonUtil.date.DateUtils;

import org.springframework.stereotype.Service;
@Service("shopBizI")
public class ShopBizImpl extends AbstractBizImpl implements ShopBizI {
	@Override
	public void registShop(Shop shop,ShopUser su) throws Exception {
		/**
		 * 1、注册shop
		 * 2、初始化shopUser
		 * 3、初始化shopAccount
		 * 4、初始化shopContent
		 * 
		 * 5、认证实体
		 */
		shop.setCertiStatus(User.CERTISTATUS.WAIT.toString());
		aDao.insertObject(shop);
		su.setShopName(shop.getName());
		su.setAddressId(shop.getAddressId());
		su.setAddressTreeIds(shop.getAddressTreeIds());
		su.setShopId(shop.getId());
		aDao.insertObject(su);
		
		
		Account account = new Account();
		account.setAddressId(shop.getAddressId());
		account.setAddressTreeIds(shop.getAddressTreeIds());
		account.setAvailablePrice(0);
		account.setFrozenPrice(0);
		account.setIsQueren(0);
		account.setTotalPrice(0);
		account.setWhoclassName(Shop.class.getName());
		account.setWhoId(shop.getId());
		account.setWhoName(shop.getName());
		Date now = new Date();
		account.setWithdrawalsPwd(now.getTime()+"");
		account.setYinhangka(null);
		account.setYinhangkaType(null);
		account.setZhifubao("");
		aDao.insertObject(account);
		
		
		ShopContent sc = new ShopContent();
		sc.setContent(null);
		sc.setOtherContent(null);
		sc.setShopId(shop.getId());
		aDao.insertObject(sc);
		
		
		
		shop.setShopUserId(su.getId());
		aDao.updateObject(shop);
		
		
		
		Certification c = new Certification();
		c.setWhoclassName(Shop.class.getName());
		c.setWhoId(shop.getId());
		c.setWhoName(shop.getName());
		c.setZizhiStatus(Certification.STATUS.WAIT.toString());
		c.setShimingStatus(Certification.STATUS.WAIT.toString());
		aDao.insertObject(c);
		
		
		
		
		Statistics s = new Statistics();
		s.setWhoclassName(Shop.class.getName());
		s.setWhoId(shop.getId());
		s.setWhoName(shop.getName());
		Date d = DateUtils.format(DateUtils.format(now), DateUtils.formatStr_yyyyMMdd);
		s.setRelDate(d);
		aDao.insertObject(s);
		
		
		
		ShopTemplateSimple ss = new ShopTemplateSimple();
		ss.setShopId(shop.getId());
		aDao.insertObject(ss);
		
		
		SincerityArchives sa = new SincerityArchives();
		sa.setWhoclassName(Shop.class.getName());
		sa.setWhopName(shop.getName());
		sa.setWhoId(shop.getId());
		sa.setScore(100);
		sa.setSincerityLevelId(1);
		aDao.insertObject(sa);
		
		
		
		
	}
	
}
