package myFrameU.expand.libraryProperty.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import myFrameU.biz.util.MyActionUtil;
import myFrameU.exception.exception.MyStrException;
import myFrameU.expand.libraryProperty.biz.SystemLibraryPropertyBizI;
import myFrameU.expand.libraryProperty.entity.SystemLibraryProperty;
import myFrameU.expand.libraryProperty.util.GetConstant;
import myFrameU.spring.mvc.FatherController;
import myFrameU.spring.mvc.SDynaActionForm;
import myFrameU.util.commonUtil.enumU.EnumUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
@RequestMapping(value="/sysProperty")
public class SystemLibraryPropertyController extends FatherController {
	@Autowired
	private SystemLibraryPropertyBizI slpBizI;
	
	
	public SystemLibraryPropertyBizI getSlpBizI() {
		return slpBizI;
	}

	public void setSlpBizI(SystemLibraryPropertyBizI slpBizI) {
		this.slpBizI = slpBizI;
	}

	@RequestMapping(value="/index")
	public String index(HttpServletRequest req,HttpServletResponse res) throws Exception{
		SDynaActionForm form=getSDynaActionForm(req);
		return "/myFrameU/expand/index";
	}
	
	@RequestMapping(value="/findAllPropertys")
	public String findAllSystemLibraryPropertys(HttpServletRequest req,HttpServletResponse res) throws Exception{
		SDynaActionForm form=getSDynaActionForm(req);
		List<SystemLibraryProperty> list = (List<SystemLibraryProperty>) aBiz.findObjectList(SystemLibraryProperty.class, null, "from SystemLibraryProperty as s", null, false, 0, 0);
		MyActionUtil.printlnList(list, null);
		req.setAttribute("propertyList", list);
		return "/myFrameU/expand/systemLibraryPropertyList";
	}


	@RequestMapping(value="/addProperty")
	public void addSystemLibraryProperty(HttpServletRequest req,HttpServletResponse res) throws Exception{
		SDynaActionForm form=getSDynaActionForm(req);
		String propertyName=form.getString("propertyName");
		String propertyKey=form.getString("propertyKey");
		String dataType=form.getString("dataType");
		String dataFrom=form.getString("dataFrom");
		String dataFromWebUrl=form.getString("dataFromWebUrl");
		String addType=form.getString("addType");
		String showType=form.getString("showType");
		String valueDefault=form.getString("valueDefault");
		String valueAlternative=form.getString("valueAlternative");
		
		/*Boolean search=form.getBoolean("search",false);
		Boolean must=form.getBoolean("must",false);
		Boolean queryArg=form.getBoolean("queryArg",false);
		Boolean list=form.getBoolean("list",false);*/
		
		//处理一些必须填写的数据
		String[] array=new String[]{propertyName,propertyKey,dataType,dataFrom,addType,showType};
		if(isAllNotNull(array)){
			//处理特殊数据
			//dataFrom和dataFromWebUrl,如果url为空，且你还想设置为web，是不行的。
			if(null==dataFromWebUrl || dataFromWebUrl.equals("") || !dataFromWebUrl.startsWith("http://")){
				if(EnumUtil.equalsE(SystemLibraryProperty.DATAFROM.WEB, dataFrom)){
					throw new MyStrException("您选择的是fromWeb，但是fromWebUrl为空");
				}
			}
			//单选和下拉 的话，默认值不能包含,
			if(EnumUtil.equalsE(SystemLibraryProperty.ADDTYPE.SELECT, addType)){
				if(null!=valueDefault && !valueDefault.equals("")){
					if(valueDefault.contains(GetConstant.SystemLibraryProperty_ValueDefault_Connect)){
						throw new MyStrException("您选择的是下拉列表，默认值不能有"+GetConstant.SystemLibraryProperty_ValueDefault_Connect);
					}
				}
			}
			if(EnumUtil.equalsE(SystemLibraryProperty.ADDTYPE.RADIO, addType)){
				if(null!=valueDefault && !valueDefault.equals("")){
					if(valueDefault.contains(GetConstant.SystemLibraryProperty_ValueAlternative_Connect)){
						throw new MyStrException("您选择的是单选，默认值不能有"+GetConstant.SystemLibraryProperty_ValueAlternative_Connect);
					}
				}
			}
			
			
			//下拉、多选、单选，必须要有至少一个备选值
			if(!EnumUtil.equalsE(SystemLibraryProperty.ADDTYPE.INPUT, addType)){
				if(null!=valueAlternative && !valueAlternative.equals("")){
				}else{
					throw new MyStrException("您选择的是非输入框，需要填写备选值");
				}
			}
			
			
			
			//第一）检查propertyKey是否在数据库中已经存在
			SystemLibraryProperty slp1=(SystemLibraryProperty) aBiz.findObjectById("from SystemLibraryProperty as c where c.propertyKey=?", new Object[]{propertyKey});
			if(null==slp1){
				SystemLibraryProperty p=new SystemLibraryProperty();
				p.setPropertyKey(propertyKey);
				p.setPropertyName(propertyName);
				p.setDataType(dataType);
				p.setDataFrom(dataFrom);
				p.setDataFromWebUrl(dataFromWebUrl);
				p.setAddType(addType);
				p.setShowType(showType);
				p.setValueDefault(valueDefault);
				p.setValueAlternative(valueAlternative);
				/*p.setSearch(search);
				p.setQueryArg(queryArg);
				p.setMust(must);
				p.setList(list);*/
				slpBizI.addSystemLibraryProperty(p);
			}else{
				throw new MyStrException("请更换propertyKey");
			}
		}else{
			throw new MyStrException("请填写完整的值");
		}
		
	}
	//--------------------------------------------------------------------------------------------------
	@RequestMapping(value="/addPVs")
	public String addPvs(HttpServletRequest req,HttpServletResponse res) throws Exception{
		SDynaActionForm form=getSDynaActionForm(req);
		String pvalue=form.getString("pvalue");
		Integer pId=form.getInteger("id");
		if(null!=pId && null!=pvalue && !pvalue.equals("")){
			SystemLibraryProperty p = (SystemLibraryProperty) aBiz.findObjectById("from SystemLibraryProperty as p where p.id=?", new Object[]{pId});
			if(null!=p){
				slpBizI.addSLPropertyValues(p, pvalue);
			}
		}else{
			throw new MyStrException("请填写propertyId,或者value值不为空");
		}
		return "/myFrameU/expand/systemLibraryPropertyList";
	}
	
	
	@RequestMapping(value="/modifyPv")
	public void modifyPropertyValue(HttpServletRequest req,HttpServletResponse res) throws Exception{
		SDynaActionForm form=getSDynaActionForm(req);
		String pvalue=form.getString("pvalue");
		Boolean defaultValue=form.getBoolean("defaultValue", false);
		Integer pvId=form.getInteger("pvId");
		String modifyValueOrDefault=form.getString("modifyValueOrDefault");
		slpBizI.modifySLPropertyValues(pvId, pvalue, defaultValue, modifyValueOrDefault);
	}
	
	@RequestMapping(value="/modifyP")
	public void modifyProperty(HttpServletRequest req,HttpServletResponse res) throws Exception{
		SDynaActionForm form=getSDynaActionForm(req);
		String propertyName=form.getString("propertyName");
		String propertyKey=form.getString("propertyKey");
		String dataType=form.getString("dataType");
		String dataFrom=form.getString("dataFrom");
		String dataFromWebUrl=form.getString("dataFromWebUrl");
		String addType=form.getString("addType");
		String showType=form.getString("showType");
		Integer id=form.getInteger("id");
		if(null!=id){
			SystemLibraryProperty oldP=(SystemLibraryProperty)aBiz.findObjectById("from SystemLibraryProperty as c where c.id=?", new Object[]{id});
			if(null!=oldP){
				oldP.setPropertyKey(propertyKey);
				oldP.setPropertyName(propertyName);
				oldP.setDataType(dataType);
				oldP.setDataFrom(dataFrom);
				oldP.setDataFromWebUrl(dataFromWebUrl);
				oldP.setAddType(addType);
				oldP.setShowType(showType);
				slpBizI.modifySLProperty(oldP);
			}
		}
	}
	
	
	
	
	@RequestMapping(value="/findPropertyById")
	public String findPropertyById(HttpServletRequest req,HttpServletResponse res) throws Exception{
		SDynaActionForm form=getSDynaActionForm(req);
		Integer id=form.getInteger("id");
		SystemLibraryProperty oldP=(SystemLibraryProperty)aBiz.findObjectById("from SystemLibraryProperty as c where c.id=?", new Object[]{id});
		req.setAttribute("systemLibraryProperty", oldP);
		return "/myFrameU/expand/systemLibraryProperty";
	}
	
	
	@RequestMapping(value="/uniquePropertyKey")
	public void uniquePropertyKey(HttpServletRequest req,HttpServletResponse res) throws Exception{
		SDynaActionForm form=getSDynaActionForm(req);
		String propertyKey = form.getString("propertyKey");
		if(null!=propertyKey && !propertyKey.equals("")){
			SystemLibraryProperty oldP=(SystemLibraryProperty)aBiz.findObjectById("from SystemLibraryProperty as c where c.propertyKey=?", new Object[]{propertyKey});
			if(null!=oldP){
				throw new MyStrException("请输入唯一的key值,该key已经存在");
			}else{
				
			}
		}else{
			throw new MyStrException("请输入唯一的key值");
		}
	}
	
	/*@RequestMapping(value="/test")
	public void test(HttpServletRequest req,HttpServletResponse res) throws Exception{
		SDynaActionForm form=getSDynaActionForm(req);
		String type=form.getString("type");
		List<SystemLibraryProperty> list = null;
		String hql = null;
		if(type.equals("1")){
			hql="from SystemLibraryProperty as c where regexp(c.propertyKey,'c|x|r')=1";
		}else if(type.equals("2")){
			hql="from SystemLibraryProperty as c where regexp(c.propertyKey,'colo')=1";
		}
		list= (List<SystemLibraryProperty>)aBiz.findObjectList(SystemLibraryProperty.class, null, hql, null, false, 0, 0);
		int size = list.size();
		SystemLibraryProperty p = null;
		for(int i=0;i<size;i++){
			p=list.get(i);
			System.out.println(p.getPropertyName()+"=="+p.getPropertyKey());
		}
	}
	
	*/
	
	
	
	
	
	
	
	
	
	
	
	
}
