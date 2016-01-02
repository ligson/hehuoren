package myFrameU.expand.distribution.controller;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import myFrameU.exception.exception.MyStrException;
import myFrameU.expand.distribution.biz.DistributePropertyBizI;
import myFrameU.expand.distribution.entity.DistributionRange;
import myFrameU.expand.distribution.entity.PropertyDistribute;
import myFrameU.expand.init.DistributeUitlEntity;
import myFrameU.expand.libraryProperty.entity.SystemLibraryProperty;
import myFrameU.spring.mvc.FatherController;
import myFrameU.spring.mvc.SDynaActionForm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
@RequestMapping("/disProperty")
public class DistributePropertyController extends FatherController {
	@Autowired
	private DistributePropertyBizI distributeProBiz;
	

	
	public DistributePropertyBizI getDistributeProBiz() {
		return distributeProBiz;
	}



	public void setDistributeProBiz(DistributePropertyBizI distributeProBiz) {
		this.distributeProBiz = distributeProBiz;
	}



	@RequestMapping(value="/index")
	public String findIndex(HttpServletRequest req,HttpServletResponse res) throws Exception{
		SDynaActionForm form = getSDynaActionForm(req);
		List<DistributeUitlEntity> distribute_dbuList=myFrameU.expand.init.InitMavenImpl.ic.getDistribute_dbuList();
		req.setAttribute("distribute_dbuList", distribute_dbuList);
		return "/myFrameU/expand/distributePropertyIndex";
	}
	
	
	
/*	
	@RequestMapping(value="/findClassFields")
	public void findClassFields(HttpServletRequest req,HttpServletResponse res) throws Exception{
		SDynaActionForm form = getSDynaActionForm(req);
		String className=form.getString("className");
		if(null!=className){
			Class c = Class.forName(className);
			if(null!=c){
				StringBuffer sb = new StringBuffer();
				sb.append("<select name='dRange_key'>");
				Field[] fields = c.getDeclaredFields();
				int len=fields.length;
				Field f=null;
				for(int i=0;i<len;i++){
					f=fields[i];
					sb.append("<option value='").append(f.getName()).append("'").append(">").append(f.getName()).append("</option>");
				}
				sb.append("</select>");
				res.setContentType("text/plain;charset=UTF-8"); 
				res.getWriter().print(sb.toString());
			}else{
				throw new MyStrException("className错误");
			}
		}else{
			throw new MyStrException("className为空");
		}
	}
	
	
	
	*/
	
	
	@RequestMapping(value="/addDistributePropertyBatch")
	public void addDistributePropertyBatch(HttpServletRequest req,HttpServletResponse res) throws Exception{
		SDynaActionForm form = getSDynaActionForm(req);
		//String distributeType=form.getString("distributeType");//分配类型，object0代表分配给class,object1代表分配给object
		String className=form.getString("className");
		
		
		String dRange_key=form.getString("dRange_key");
		String dRange_value=form.getString("dRange_value");
		String dRange_keyName=form.getString("dRange_keyName");
		
		DistributionRange dr=new DistributionRange();
		dr.setKey(dRange_key);
		dr.setKeyName(dRange_keyName);
		dr.setValue(dRange_value);
		String dRange=dr.toString();
		System.out.println(dRange+"$$$$$$$$$$");
		//String[] transfer=req.getParameterValues("transfer");
		String[] must=req.getParameterValues("must");
		String[] propertyValueIds=req.getParameterValues("propertyValueIds");
		String[] propertyIds=req.getParameterValues("propertyId");
		
		
		if(null!=className && !className.equals("") && className.contains(".")){
			if(null!=propertyIds && propertyIds.length>0){
				HashMap<String,SystemLibraryProperty> libraryPropertyMap=(HashMap<String, SystemLibraryProperty>) uICacheManager.getObjectCached("web", "libraryPropertyMap");
				distributeProBiz.addDistributePropertyBatch(libraryPropertyMap, className, dRange, must, propertyValueIds, propertyIds);
			}else{
				throw new MyStrException("请选择propertys");
			}
		}else{
			throw new MyStrException("请填写正确的className");
		}
		
		
	}
	
	
	@RequestMapping(value="/findDistribute")
	public String findDistribute(HttpServletRequest req,HttpServletResponse res) throws Exception{
		SDynaActionForm form = getSDynaActionForm(req);
		String className=form.getString("className");
		String dRange=form.getString("dRange");
		
		
		req.setAttribute("className", className);
		req.setAttribute("dRange", dRange);
		if(null!=className && !className.equals("") && className.contains(".")){
			List<PropertyDistribute> pdList = null;
			/*if(null==dRange || dRange.equals("ALL")){
				//说明是查询类都所有对象的分配
				pdList=(List<PropertyDistribute>)aBiz.findObjectList(PropertyDistribute.class, new Object[]{className}, 
						"from PropertyDistribute as a where a.className=? and a.dRange='ALL'", null, false, 0, 0);
			}else{
				//说明查询类都部分对象的分配
				pdList=(List<PropertyDistribute>)aBiz.findObjectList(PropertyDistribute.class, new Object[]{className,dRange}, 
						"from PropertyDistribute as a where a.className=? and a.dRange=?", null, false, 0, 0);
			}*/
			pdList=(List<PropertyDistribute>)aBiz.findObjectList(PropertyDistribute.class, new Object[]{className,dRange}, 
					"from PropertyDistribute as a where a.className=? and a.dRange=?", null, false, 0, 0);
			
			HashMap<String,PropertyDistribute> pdMap=new HashMap<String,PropertyDistribute>();
			int size = pdList.size();
			PropertyDistribute pd = null;
			for(int i=0;i<size;i++){
				pd=pdList.get(i);
				pdMap.put("pId_"+pd.getPropertyId(), pd);
			}
			req.setAttribute("mySelfPdMap", pdMap);
			
			
			
			
			if(null==dRange || dRange.equals("ALL")){
			}else{
				//说明查询类都部分对象的分配
				//如果是分配部分数据property，在查询的时候，要查询出所在类的所有数据的property
				List<PropertyDistribute> pdList1=(List<PropertyDistribute>)aBiz.findObjectList(PropertyDistribute.class, new Object[]{className}, 
						"from PropertyDistribute as a where a.className=? and a.dRange='ALL'", null, false, 0, 0);
				HashMap<String,PropertyDistribute> pdMap1=new HashMap<String,PropertyDistribute>();
				int size1 = pdList1.size();
				PropertyDistribute pd1 = null;
				for(int i=0;i<size1;i++){
					pd1=pdList1.get(i);
					pdMap1.put("pId_"+pd1.getPropertyId(), pd1);
				}
				req.setAttribute("myAllPdMap", pdMap1);
			}
			
			
			/**
			 *  自己选中了的property
			 *  		自己的类也选择了它（优先级）
			 *  		自己的类没有选择它
			 *  
			 *  自己没有选中的property
			 *  		看看是不是自己的类的所有数据选择了它
			 */
			
			
			
			
			
			
			
		}else{
			throw new MyStrException("请填写正确的className");
		}
		return "/myFrameU/expand/distributePropertyModify";
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	//修改property分配
	//检查有没有这个分配，如果没有意思这次要add，如果有了意思就是要del
	@RequestMapping(value="/modifyDistributeProperty")
	public void modifyDistributeProperty(HttpServletRequest req,HttpServletResponse res) throws Exception{
		SDynaActionForm form = getSDynaActionForm(req);
		String className=form.getString("className");
		String dRange=form.getString("dRange");
		Integer propertyId=form.getInteger("propertyId");
		String addOrDel=null;
		if(null==propertyId){
			propertyId=0;
		}
		req.setAttribute("className", className);
		req.setAttribute("dRange", dRange);
		HashMap<String,SystemLibraryProperty> libraryPropertyMap=(HashMap<String, SystemLibraryProperty>) uICacheManager.getObjectCached("web", "libraryPropertyMap");
		if(propertyId!=0){
			PropertyDistribute pd = (PropertyDistribute)aBiz.findObjectById("from PropertyDistribute as pd where pd.propertyId=? and pd.className=? and pd.dRange=?", new Object[]{propertyId,className,dRange});
			if(null!=pd){
				//有了，说明这次要删除
				distributeProBiz.delDistributeProperty(propertyId, className, dRange);
				addOrDel="del";
			}else{
				//没有分配，说要add
				distributeProBiz.addDistributeProperty(libraryPropertyMap, className, dRange, propertyId);
				addOrDel="add";
			}
		}
		res.getWriter().print(addOrDel);
	}
	
	
	
	
	
	//修改分配值
	@RequestMapping(value="/modifyDistributePropertyValue")
	public void modifyDistributePropertyValue(HttpServletRequest req,HttpServletResponse res) throws Exception{
		SDynaActionForm form = getSDynaActionForm(req);
		String className=form.getString("className");
		String dRange=form.getString("dRange");
		//Integer propertyId=form.getInteger("propertyId");
		Integer propertyValueId=form.getInteger("propertyValueId");
		if(null==propertyValueId){
			propertyValueId=0;
		}
		HashMap<String,SystemLibraryProperty> libraryPropertyMap=(HashMap<String, SystemLibraryProperty>) uICacheManager.getObjectCached("web", "libraryPropertyMap");
		req.setAttribute("className", className);
		req.setAttribute("dRange", dRange);
		distributeProBiz.modifyDistributePropertyValue(libraryPropertyMap, className, dRange,  propertyValueId);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
