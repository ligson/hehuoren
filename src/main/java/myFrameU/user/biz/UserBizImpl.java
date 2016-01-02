package myFrameU.user.biz;

import java.util.Date;

import myFrameU.account.entity.Account;
import myFrameU.address.entity.Address;
import myFrameU.biz.AbstractBizImpl;
import myFrameU.shop.entity.Certification;
import myFrameU.shop.entity.Shop;
import myFrameU.user.entity.MyAddress;
import myFrameU.user.entity.User;

import org.springframework.stereotype.Service;
@Service("userBiz")
public class UserBizImpl extends AbstractBizImpl implements UserBizI {
	@Override
	public void registUser(User user, Address a) throws Exception {
		user.setCertiStatus(User.CERTISTATUS.WAIT.toString());
		user.setZhuceDate(new Date());
		user.setBianhao(new Date().getTime()+"");
		aDao.insertObject(user);
		
		//.......剩下的工作在after里执行
		/*InitConfig initConfig = myFrameU.user.init.InitMavenImpl.ic;
		String className=initConfig.getAfterClass();
		String method=(initConfig.getAfterMethodMap()).get("afterRegist");
		Class c = Class.forName(className);
		Method m = c.getDeclaredMethod(method, User.class, HttpServletRequest.class,UICacheManager.class,AbstractBizImpl.class);
		m.invoke(c.newInstance(), user,null,null,this);*/
		
		
		Account account = new Account();
		account.setAddressId(user.getAddressId());
		account.setAddressTreeIds(user.getAddressTreeIds());
		account.setAvailablePrice(0);
		account.setFrozenPrice(0);
		account.setIsQueren(0);
		account.setTotalPrice(0);
		account.setWhoclassName(User.class.getName());
		account.setWhoId(user.getId());
		account.setWhoName(user.getNicheng());
		Date now = new Date();
		account.setWithdrawalsPwd(now.getTime()+"");
		account.setYinhangka(null);
		account.setYinhangkaType(null);
		account.setZhifubao("");
		aDao.insertObject(account);
		
		
		MyAddress mya = new MyAddress();
		mya.setAddressId(user.getAddressId());
		mya.setAddressTreeIds(user.getAddressTreeIds());
		mya.setIsDefault(1);
		mya.setJutiAddress("");
		mya.setName("");
		mya.setPhone("");
		mya.setTelPhone("");
		mya.setUserId(user.getId());
		mya.setZipcode("");
		aDao.insertObject(mya);
		

/*
		Certification c = new Certification();
		c.setWhoclassName(User.class.getName());
		c.setWhoId(user.getId());
		c.setWhoName(user.getName());
		c.setZizhiStatus(Certification.STATUS.APPLYOK.toString());
		c.setShimingStatus(Certification.STATUS.WAIT.toString());
		aDao.insertObject(c);
		*/
		
		
		
	}

}
