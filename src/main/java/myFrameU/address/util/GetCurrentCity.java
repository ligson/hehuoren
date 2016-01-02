package myFrameU.address.util;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import myFrameU.address.entity.Address;
import myFrameU.biz.AbstractBizI;


public class GetCurrentCity {
	
	//接受addressId----城市ID
	//接受addressStr---城市url
	public static void getCity(HttpServletRequest req,AbstractBizI aBiz,Integer addressId,String addressStr) throws Exception{
		HttpSession session = req.getSession();
		//List<Address> addressList_all = (List<Address>)req.getSession().getServletContext().getAttribute("addressList_all");
		HashMap<String,Address> addressAll_map = (HashMap<String,Address>)req.getSession().getServletContext().getAttribute("addressAll_map");
		Integer addressId_old=(Integer)session.getAttribute("addressId");
		//addressStr的优先级要大于addressId,有了addressStr说明是用户强制转换
		if(null!=addressStr && !addressStr.equals("") && !addressStr.equals("/")){
			boolean needAgain=false;
			if(addressStr.startsWith("/")){
				addressStr=addressStr.replace("/", "");
			}
			String oldAddressStr=(String)req.getSession().getAttribute("addressStr");
			if(null==oldAddressStr){
				//以前没有,现在要从主站换成到城市站
				needAgain=true;
			}else if(!oldAddressStr.equals(addressStr)){
				needAgain=true;
			}
			if(needAgain){
				Address a = (Address)aBiz.findObjectById("from Address as a where a.url=?", new Object[]{addressStr});
				if(null!=a){
					req.getSession().setAttribute("address", a);
					req.getSession().setAttribute("addressStr", a.getUrl());
					req.getSession().setAttribute("addressId", a.getId());
					if(a.getJibie()==2){
						//ifIsCity(addressAll_map, addressList_all, addressSon_map, a, a.getId(), req);
					}
					addressId=a.getId();
				}
			}
			
		}else{
			//回总站
			//申请预约 招标    效果图  百科 问答,这些都没有城市区分.只有shopList\首页有城市站
			String path=req.getServletPath();
			path=path.replace(".do", "");
			String method=req.getParameter("method");
			if(null!=path && null!=method){
				if(path.equals("/f_question") || 
						path.equals("/f_news") || 
						path.equals("/f_caseAlbum") ||
						path.equals("/f_task") || 
						path.equals("/f_yuyue")){
					
				}else{
					String url=req.getRequestURI();
					req.getSession().removeAttribute("addressId");
					req.getSession().removeAttribute("addressStr");
					req.getSession().removeAttribute("address");
				}
			}
		}
		
		/*
		if(null==addressId){
			//如果id为空，则就再去检查下有没有addressStr，如果还为空，则设定addressId=0
			if(null!=addressStr){
				if(addressStr.startsWith("/")){
					addressStr=addressStr.replace("/", "");
				}
				Address a = (Address)aBiz.findObjectById("from Address as a where a.url=?", new Object[]{addressStr});
				if(null!=a){
					req.getSession().setAttribute("address", a);
					req.getSession().setAttribute("addressStr", a.getUrl());
					req.getSession().setAttribute("addressId", a.getId());
					if(a.getJibie()==2){
						//ifIsCity(addressAll_map, addressList_all, addressSon_map, a, a.getId(), req);
					}
					addressId=a.getId();
				}
			}else{
				addressId=0;
			}
		}else{
			Address a=(Address)addressAll_map.get("addId_"+addressId);
			//Address a = (Address)aBiz.findObjectById("from Address as a where a.id=?", new Object[]{addressId});
			if(null!=a){
				addressId=a.getId();
				req.getSession().setAttribute("address", a);
				req.getSession().setAttribute("addressStr", a.getUrl());
				req.getSession().setAttribute("addressId", a.getId());
				if(a.getJibie()==2){
					//ifIsCity(addressAll_map, addressList_all, addressSon_map, a, a.getId(), req);
				}
			}
			if(null==addressId_old){
				addressId_old=0;
			}
		}*/
	}
	
	
	private static void ifIsCity(HashMap<String,Address> addressAll_map,List<Address> addressList_all,HashMap<String,List<Address>> addressSon_map,
			Address a,int addressId,HttpServletRequest req){
		int shengId=a.getRootTypeId();
		String provinceIdStr="addId_"+shengId;
		Address province=addressAll_map.get(provinceIdStr);
		req.getSession().setAttribute("province", province);
		
		List<Address> addSonList = null;
		addSonList=addressSon_map.get("parentId_"+addressId);
		if(null==addSonList){
			String parentIdStr=null;
			int size = addressList_all.size();
			Address everyA=null;
			for(int i=0;i<size;i++){
				everyA=addressList_all.get(i);
				if(everyA.getFatherId()==addressId){
					parentIdStr="parentId_"+addressId;
					if(null==addressSon_map.get(parentIdStr)){
						addSonList=new ArrayList<Address>();
						addSonList.add(everyA);
						addressSon_map.put(parentIdStr, addSonList);
					}else{
						addSonList=addressSon_map.get(parentIdStr);
						addSonList.add(everyA);
					}
				}
			}
		}
	}
}
