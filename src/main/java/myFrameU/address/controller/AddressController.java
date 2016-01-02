package myFrameU.address.controller;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import myFrameU.address.entity.Address;
import myFrameU.spring.mvc.FatherController;
import myFrameU.spring.mvc.SDynaActionForm;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 现在只做各种查询，不做增删该
 */
@Controller
@RequestMapping(value={"/address"})
public class AddressController extends FatherController {
	
	@RequestMapping(value="/findAllRoots")
	public String findAllRoots(HttpServletRequest req){
		SDynaActionForm form=getSDynaActionForm(req);
		HashMap<String,Address> addressROOT_map=(HashMap<String, Address>) uICacheManager.getObjectCached("web", "addressROOT_map");
		req.setAttribute("addressROOT_map", addressROOT_map);
		return getForward(form.getString("forwardPage"), "addressList");
	}
	
	
	@RequestMapping(value="/findAll")
	public String findAll(HttpServletRequest req){
		SDynaActionForm form=getSDynaActionForm(req);
		HashMap<String,Address> addressAll_map=(HashMap<String, Address>) uICacheManager.getObjectCached("web", "addressAll_map");
		req.setAttribute("addressAll_map", addressAll_map);
		return getForward(form.getString("forwardPage"), "addressList");
	}
	
	
	@RequestMapping(value="/findOpen")
	public String findOpen(HttpServletRequest req){
		SDynaActionForm form=getSDynaActionForm(req);
		HashMap<String,Address> addressIsOpen_map=(HashMap<String, Address>) uICacheManager.getObjectCached("web", "addressIsOpen_map");
		req.setAttribute("addressIsOpen_map", addressIsOpen_map);
		return getForward(form.getString("forwardPage"), "addressList");
	}
	
	
	
	@RequestMapping(value="/findSons", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String findSons(HttpServletRequest req,HttpServletResponse res) throws Exception{
		System.out.println("调用了findSons。。。。。。。。。。。。。。。。");
		StringBuffer sb = new StringBuffer();
		SDynaActionForm form=getSDynaActionForm(req);
		String end = form.getString("end");
		if(null==end){
			end="city";
		}
		//处理查询出来的select的onchange方法
		String endChange=form.getString("endChange");
		
		Integer id=form.getInteger("id");
		if(null!=id){
			/*HashMap<String,Address> addressROOT_map=(HashMap<String, Address>) uICacheManager.getObjectCached("web", "addressROOT_map");
			List<Address> addressROOTList = (List<Address>)MyCollectionUtils.map2list(addressROOT_map);
			req.setAttribute("addressROOTList", addressROOTList);
			*/
			HashMap<String,Address> addressAll_map=(HashMap<String, Address>) uICacheManager.getObjectCached("web", "addressAll_map");
			Address a=addressAll_map.get("addId_"+id);
			if(null!=a){
				//req.setAttribute("currentAddress", a);
				List<Address> addressList = (List<Address>)aBiz.findObjectList(Address.class, new Object[]{id}, "from Address as a where a.fatherId=?", null, false, 0, 0);
				req.setAttribute("addressList", addressList);
				if(end.equals("city")){
					//要在选择城市这阶段就结束
					if(a.getIsROOT()==0){
						//当前是根目录，要选择市
						if(null!=endChange && !endChange.equals("")){
							sb.append("<select name='addressId'").append(" onChange='").append(endChange).append("(this)' >");
						}else{
							sb.append("<select name='addressId'>");
						}
						
						sb.append("<option value='0'>请选择市</option>");
						int size = addressList.size();
						Address add = null;
						for(int i=0;i<size; i++){
							add=addressList.get(i);
							sb.append("<option value='").append(add.getId()).append("'>").append(add.getName()).append("</option>");
						}
						sb.append("</select>");
					}
				}
			}
		}
		return sb.toString();
	}
	
	
	
	
}
