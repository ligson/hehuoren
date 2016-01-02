package myFrameU.user.controller;

import java.math.BigInteger;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import myFrame.cache.CacheKey;
import myFrameU.address.entity.Address;
import myFrameU.biz.util.MyActionUtil;
import myFrameU.exception.exception.MyRefererException;
import myFrameU.exception.exception.MyStrException;
import myFrameU.pager.pager.Pager;
import myFrameU.spring.mvc.CommonField;
import myFrameU.spring.mvc.FatherController;
import myFrameU.spring.mvc.SDynaActionForm;
import myFrameU.user.entity.MyAddress;
import myFrameU.user.entity.User;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
public class MyAddressController extends FatherController {
	
	

	@RequestMapping("/user/myAddress/findDefault")
	public void findDefault(HttpServletRequest req,HttpServletResponse res) throws Exception{
		SDynaActionForm scForm = getSDynaActionForm(req);
		User user = (User)req.getSession().getAttribute("myUser");
		MyAddress mya=(MyAddress)aBiz.findObjectById("from MyAddress as a where a.userId=? and a.isDefault=1", new Object[]{user.getId()});
		req.setAttribute("myAddressDefault", mya);
	}
	
	

	@RequestMapping("/user/myAddress/findAll")
	public String findAll(HttpServletRequest req,HttpServletResponse res) throws Exception{
		SDynaActionForm scForm = getSDynaActionForm(req);
		User user = (User)req.getSession().getAttribute("myUser");
		Pager pager = new MyActionUtil(req,"").getPager(req,aBiz,"select count(a.id) from user_myAddress as a where a.userId=?",new Object[]{user.getId()});
		List<MyAddress> myaList=(List<MyAddress>)aBiz.findObjectList(MyAddress.class, new Object[]{user.getId()}, "from MyAddress as a where a.userId=?", null, true, pager.getStartRow(), pager.getPageSize());
		req.setAttribute("myAddressList", myaList);
		return "manage/user/user/myAddressList";
	}
	

	@RequestMapping("/user/myAddress/selectDefault")
	public void selectDefault(HttpServletRequest req,HttpServletResponse res) throws Exception{
		SDynaActionForm scForm = getSDynaActionForm(req);
		User user = (User)req.getSession().getAttribute("myUser");
		Integer id = scForm.getInteger("id");
		if(null!=user && null!=id){
			MyAddress mya=(MyAddress)aBiz.findObjectById("from MyAddress as a where a.userId=? and a.isDefault=1", new Object[]{user.getId()});
			if(null!=mya){
				//如果这个用户之前有一个是默认的
				int myaId=mya.getId();
				if(myaId==id.intValue()){
					//如果正好就是这个，没有必要修改了
				}else{
					//如果不是，但是还有其他的是默认的，那就两个步骤了
					mya.setIsDefault(0);
					aBiz.modifyObject(mya);
					aBiz.j_execute("update user_myAddress set isDefault=1 where id=?", new Object[]{id});
				}
			}else{
				//如果之前一个都没有，那就一步设定就可以了
				aBiz.j_execute("update user_myAddress set isDefault=1 where id=?", new Object[]{id});
			}
		}
	}
	
	
	@RequestMapping(value={"/user/myAddress/findMyAdd2Mod","/wrap/user/myAddress/findMyAdd2Mod"})
	public String findMyAdd2Mod(HttpServletRequest req,HttpServletResponse res) throws Exception{
		SDynaActionForm scForm = getSDynaActionForm(req);
		Integer id = scForm.getInteger("id");
		if(null!=id){
			MyAddress mya=(MyAddress)aBiz.findObjectById("from MyAddress as a where a.id=?", new Object[]{id});
			if(null!=mya){
				int addressId = mya.getAddressId();
				Address currentAddress  = CacheKey.CKAddress.ALLMAP.getObject(uICacheManager, addressId);
				req.setAttribute("currentAddress", currentAddress);
				req.setAttribute("myAddress", mya);
			}else{
			}
		}
		if(isWrap(req)){
			return "wrap/manage/user/user/myAddressMod";
		}else{
			return "manage/user/user/myAddressMod";
		}
		
	}
	
	
	
	@RequestMapping("/user/myAddress/removeMyAdd")
	public void removeMyAdd(HttpServletRequest req,HttpServletResponse res) throws Exception{
		SDynaActionForm scForm = getSDynaActionForm(req);
		Integer id = scForm.getInteger("id");
		if(null!=id){
			MyAddress mya=(MyAddress)aBiz.findObjectById("from MyAddress as a where a.id=?", new Object[]{id});
			if(null!=mya){
				int isDefault = mya.getIsDefault();
				if(isDefault==1){
					//不能删除默认值
					throw new MyStrException("抱歉，该地址是默认地址，不能删除");
				}else{
					aBiz.removeObject(mya);
				}
			}else{
			}
		}
	}
	
	
	

	@RequestMapping("/user/myAddress/toAdd")
	public String toAdd(HttpServletRequest req,HttpServletResponse res) throws Exception{
		SDynaActionForm scForm = getSDynaActionForm(req);
		User user = (User)req.getSession().getAttribute("myUser");
		return "manage/user/user/myAddressAdd";
	}
	
	
	@RequestMapping("/user/myAddress/addMyAddress")
	public String addMyAddress(HttpServletRequest req,HttpServletResponse res) throws Exception{
		SDynaActionForm scForm = getSDynaActionForm(req);
		String redirect = scForm.getString(CommonField.redirect);
		String name = scForm.getString("name");
		Integer addressId = scForm.getInteger("addressId");
		String jutiAddress=scForm.getString("jutiAddress");
		String telPhone = scForm.getString("telPhone");
		String zipcode = scForm.getString("zipcode");
		//String phone = scForm.getString("phone");
		//Double coorX=scForm.getDouble("coorX");
		//Double coorY=scForm.getDouble("coorY");
		User user = (User)req.getSession().getAttribute("myUser");
		
		if(null!=name && null!=addressId && null!=telPhone && null!=user){
			BigInteger count=(BigInteger)aBiz.j_queryObject(
					"select count(id) from user_myAddress where name=? and addressId=? and jutiAddress=? and telPhone=? and userId=?",
					new Object[]{name,addressId,jutiAddress,telPhone,user.getId()});
			if(null==count || count.intValue()==0){
				Address a = CacheKey.CKAddress.ALLMAP.getObject(uICacheManager, addressId);
				if(null!=a){
					
					MyAddress mya_n=new MyAddress();
					mya_n.setAddressId(a.getId());
					mya_n.setAddressTreeIds(a.getTreeId());
				//	mya_n.setCoorX(coorX);
				//	mya_n.setCoorY(coorY);
					mya_n.setJutiAddress(jutiAddress);
					mya_n.setName(name);
				//	mya_n.setPhone(phone);
					mya_n.setTelPhone(telPhone);
					mya_n.setZipcode(zipcode);
					
					mya_n.setUserId(user.getId());
					
					MyAddress mya=(MyAddress)aBiz.findObjectById("from MyAddress as a where a.userId=? and a.isDefault=1", new Object[]{user.getId()});
					if(null!=mya){
						//如果已经有了默认地址了，则这个在添加的时候，就不是默认的
						mya_n.setIsDefault(0);
					}else{
						mya_n.setIsDefault(1);
					}
					aBiz.addObject(mya_n);
				}	
			}else{
				throw new MyRefererException("抱歉，该地址已经存在，不能重复添加");
			}
		}else{
			throw new MyRefererException("请填写完整资料");
		}
		return redirect;
	}
	
	
	
	@RequestMapping(value={"/user/myAddress/modifyMyAddress","/wrap/user/myAddress/modifyMyAddress"})
	public String modifyMyAddress(HttpServletRequest req,HttpServletResponse res) throws Exception{
		SDynaActionForm scForm = getSDynaActionForm(req);
		String redirect = scForm.getString(CommonField.redirect);
		String name = scForm.getString("name");
		Integer addressId = scForm.getInteger("addressId");
		String jutiAddress=scForm.getString("jutiAddress");
		String telPhone = scForm.getString("telPhone");
		String zipcode = scForm.getString("zipcode");
		//String phone = scForm.getString("phone");
		//Double coorX=scForm.getDouble("coorX");
		//Double coorY=scForm.getDouble("coorY");
		User user = (User)req.getSession().getAttribute("myUser");
		Integer id  = scForm.getInteger("id");
		
		if(null!=name && null!=addressId && null!=telPhone && null!=user && null!=id){
			MyAddress mya_n = (MyAddress)aBiz.findObjectById("from MyAddress as a where a.id=?", new Object[]{id});
			
			if(null!=mya_n){
				Address a = CacheKey.CKAddress.ALLMAP.getObject(uICacheManager, addressId);
				if(null!=a){

					mya_n.setAddressId(a.getId());
					mya_n.setAddressTreeIds(a.getTreeId());
				//	mya_n.setCoorX(coorX);
				//	mya_n.setCoorY(coorY);
					mya_n.setJutiAddress(jutiAddress);
					mya_n.setName(name);
				//	mya_n.setPhone(phone);
					mya_n.setTelPhone(telPhone);
					mya_n.setZipcode(zipcode);
					aBiz.modifyObject(mya_n);
					req.getSession().setAttribute("myAddress", mya_n);
				}	
			}else{
			}
		}else{
			throw new MyRefererException("请填写完整资料");
		}
		if(isWrap(req)){
			return null;
		}else{
			return redirect;
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
