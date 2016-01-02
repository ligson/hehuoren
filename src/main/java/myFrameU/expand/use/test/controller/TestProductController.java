package myFrameU.expand.use.test.controller;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import myFrameU.exception.exception.MyStrException;
import myFrameU.expand.distribution.entity.PropertyDistribute;
import myFrameU.expand.use.test.biz.TestProductBizI;
import myFrameU.expand.use.test.entity.TestProduct;
import myFrameU.expand.use.util.ExpandPropertyUseUtil;
import myFrameU.spring.mvc.FatherController;
import myFrameU.spring.mvc.SDynaActionForm;
import myFrameU.util.sshUtil.hibernate.EntityTableUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
@RequestMapping("/expand/test")
public class TestProductController extends FatherController {
	@Autowired
	private TestProductBizI proBiz;
	
	/**
	 * 系统启动的时候，已经将Product的扩展属性（ALL+部分）的分配情况都加载进来了，存在在cache中web中的distributePropertyMap
	 * <ALL,List<PropertyDistribute>>
	 * <dRange,List<PropertyDistribute>>
	 * @param req
	 * @param res
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="toAddTestProduct")
	public String toAddTestProduct(HttpServletRequest req,HttpServletResponse res) throws Exception{
		SDynaActionForm form = getSDynaActionForm(req);
		String className=form.getString("className");
		String dRange=form.getString("dRange");
		List<PropertyDistribute> list = ExpandPropertyUseUtil.getPropertyDistributes(className, dRange, uICacheManager);
		req.setAttribute("propertyDistributeList", list);
		return "/myFrameU/expand/useTest/addTestProduct";
	}
	@RequestMapping(value="findDistributePropertys_productTypeId")
	public String findDistributePropertys_productTypeId(HttpServletRequest req,HttpServletResponse res) throws Exception{
		SDynaActionForm form = getSDynaActionForm(req);
		String className=form.getString("className");
		String dRange=form.getString("dRange");
		List<PropertyDistribute> list = ExpandPropertyUseUtil.getPropertyDistributes(className, dRange, uICacheManager);
		req.setAttribute("propertyDistributeList", list);
		return "/myFrameU/expand/useTest/propertyAddHTML";
	}
	
	/**
	 * 第一：将传过来的propertyValues解析成两部分（propertyValues_Ids+propertyValues_Strs）
	 * @param req
	 * @param res
	 * @throws Exception
	 */
	@RequestMapping(value="addProduct")
	public void addProduct(HttpServletRequest req,HttpServletResponse res) throws Exception{
		SDynaActionForm form = getSDynaActionForm(req);
		String name = form.getString("name");
		Integer productTypeId=form.getInteger("productTypeId");
		if(null==name || name.equals("")){
			throw new MyStrException("请填写产品名称");
		}
		if(null==productTypeId || productTypeId==0){
			throw new MyStrException("请填写产品分类");
		}
		String propertyValues = form.getString("propertyValues");
		if(null!=propertyValues && propertyValues.equals("undefined")){
			propertyValues="";
		}
		
		TestProduct pro=new TestProduct();
		pro.setName(name);
		pro.setProductTypeId(productTypeId);
		
		HashMap<String,String> map = ExpandPropertyUseUtil.getPropertyValues_Ids_Strs(propertyValues);
		pro.setPropertyValues_Ids(map.get("propertyValues_Ids"));
		pro.setPropertyValues_Strs(map.get("propertyValues_Strs"));
		proBiz.addProduct(pro);
	}
	
	
	/**
	 * 第一：从缓存中拿出product的属性分配对象List<PropertyDistribute> list
	 * 		1、加载ALL的属性list
	 * 		2、根据product.productTypeId，拿出dRange={'key':'productTypeId','value':'productTypeId'}的属性list
	 * 
	 * 第二：将product的propertyValues_Ids和propertyValues_Strs两个字符串组成一个字符串，共同组装成一个list<JSONPropertyValues>
	 * 
	 * 		将第二步的list改成map，<propertyId,truePvalue>,方便下一步中通过propertyId直接获取真实的pvalue
	 * 		 
	 * 第三：循环第一步拿到的所有的list
	 * 		int propertyId=0;
	 * 		for(){
	 * 			pd=....
	 * 			propertyId=pd.getPropertyId();
	 * 		}
	 * 
	 * 		int propertyId=0;
	 * 		for(){
	 * 			pd=....
	 * 			propertyId=pd.getPropertyId();
	 * 		}
	 * @param req
	 * @param res
	 * @throws Exception
	 */
	@RequestMapping(value="findProduct")
	public String findProduct(HttpServletRequest req,HttpServletResponse res) throws Exception{
		SDynaActionForm form = getSDynaActionForm(req);
		Integer id=form.getInteger("id");
		if(null==id || id==0){
			throw new MyStrException("请填写productId");
		}
		TestProduct p = (TestProduct)aBiz.findObjectById("from TestProduct as p where p.id=?", new Object[]{id});
		if(null!=p){
			req.setAttribute("product", p);
			//第一步
			List<PropertyDistribute> listALL = ExpandPropertyUseUtil.getPropertyDistributes(
					TestProduct.class.getName(), "ALL", uICacheManager);
			req.setAttribute("propertyDistributeList_all", listALL);
			int productTypeId=p.getProductTypeId();
			if(productTypeId!=0){
				//{'key':'productTypeId','value':'1'}
				String dRange="{'key':'productTypeId','value':'"+productTypeId+"'}";
				List<PropertyDistribute> listPT = ExpandPropertyUseUtil.getPropertyDistributes(
						TestProduct.class.getName(), dRange, uICacheManager);
				req.setAttribute("propertyDistributeList_dRangePT", listPT);
			}
			
			//第二步
			HashMap<String, String> map = ExpandPropertyUseUtil.getPropertyValuesMap(uICacheManager, p);
			req.setAttribute("propertyValuesMap", map);
			
			//第三步放在页面上
			
		}
		return "/myFrameU/expand/useTest/product";
		
	}
	
	
	
	
	/**
	 * 去修改
	 * 
	 * 去添加+查询Id
	 * 		就是去添加，只不过要查处每个property你当时选择的值而已，这部分工作其实就是byId
	 * 
	 * 多了一项，就是要将propertyValue_Ids和propertyValue_Ids合并成一个数组字符串
	 * 
	 * 
	 * @param req
	 * @param res
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="toModTestProduct")
	public String toModTestProduct(HttpServletRequest req,HttpServletResponse res) throws Exception{
		SDynaActionForm form = getSDynaActionForm(req);
		Integer id=form.getInteger("id");
		if(null==id || id==0){
			throw new MyStrException("请填写productId");
		}
		TestProduct p = (TestProduct)aBiz.findObjectById("from TestProduct as p where p.id=?", new Object[]{id});
		if(null!=p){
			req.setAttribute("product", p);
			//第一步
			List<PropertyDistribute> listALL = ExpandPropertyUseUtil.getPropertyDistributes(
					TestProduct.class.getName(), "ALL", uICacheManager);
			req.setAttribute("propertyDistributeList_all", listALL);
			int productTypeId=p.getProductTypeId();
			if(productTypeId!=0){
				//{'key':'productTypeId','value':'1'}
				String dRange="{'key':'productTypeId','value':'"+productTypeId+"'}";
				List<PropertyDistribute> listPT = ExpandPropertyUseUtil.getPropertyDistributes(
						TestProduct.class.getName(), dRange, uICacheManager);
				req.setAttribute("propertyDistributeList_dRangePT", listPT);
			}
			
			//第二步
			HashMap<String, String> map = ExpandPropertyUseUtil.getPropertyValuesMap(uICacheManager, p);
			req.setAttribute("propertyValuesMap", map);
			
			
			//第三步
			
			String propertyValues_Ids=p.getPropertyValues_Ids();
			String propertyValue_Strs=p.getPropertyValues_Strs();
			String last = ExpandPropertyUseUtil.mergePvs(propertyValues_Ids, propertyValue_Strs);
			req.setAttribute("propertyValuesResultForm", last);
			
		}
		return "/myFrameU/expand/useTest/modTestProduct";
	}
	
	
	/**
	 * 修改
	 */
	@RequestMapping(value="modTestProduct")
	public void modTestProduct(HttpServletRequest req,HttpServletResponse res) throws Exception{
		SDynaActionForm form = getSDynaActionForm(req);
		String name = form.getString("name");
		if(null==name || name.equals("")){
			throw new MyStrException("请填写产品名称");
		}
		String propertyValues = form.getString("propertyValues");
		if(null!=propertyValues && propertyValues.equals("undefined")){
			propertyValues="";
		}
		Integer id=form.getInteger("id");
		if(null==id || id==0){
			throw new MyStrException("请填写productId");
		}
		TestProduct pro = (TestProduct)aBiz.findObjectById("from TestProduct as p where p.id=?", new Object[]{id});
		if(null!=pro){
			pro.setName(name);
			System.out.println("服务器接收到到propertyValues="+propertyValues);
			HashMap<String,String> map = ExpandPropertyUseUtil.getPropertyValues_Ids_Strs(propertyValues);
			pro.setPropertyValues_Ids(map.get("propertyValues_Ids"));
			pro.setPropertyValues_Strs(map.get("propertyValues_Strs"));
			aBiz.modifyObject(pro);
		}
	}
	
	
	

	/**
	 * 这个方法主要用来测试list，而不是组合查询测试
	 * 其实和查询ID是一样的
	 * 
	 * 
	 */
	@RequestMapping(value="findProductList")
	public String findProductList(HttpServletRequest req,HttpServletResponse res) throws Exception{
		SDynaActionForm form = getSDynaActionForm(req);
		String forwardPage=form.getString("forwardPage");
		List<TestProduct> pList = (List<TestProduct>)aBiz.findObjectList(TestProduct.class, null, "from TestProduct as p", null, false, 0, 0);
		req.setAttribute("productList", pList);
		
		
		//用来存放typeId为key的分配情况
		//其实也可以完全拿出整个系统所有的calss所有的数据的分配map来，到页面上去找
		HashMap<String,List<PropertyDistribute>> typeMap=new HashMap<String,List<PropertyDistribute>>();
		
		
		List<PropertyDistribute> listALL = ExpandPropertyUseUtil.getPropertyDistributes(
				TestProduct.class.getName(), "ALL", uICacheManager);
		req.setAttribute("propertyDistributeList_all", listALL);
		int size = pList.size();
		TestProduct p = null;
		HashMap<String, String> map = null;
		for(int i=0;i<size;i++){
			p=pList.get(i);
			map = ExpandPropertyUseUtil.getPropertyValuesMap(uICacheManager, p);
			p.setPropertyValuesMap(map);
			
			int productTypeId=p.getProductTypeId();
			if(productTypeId!=0){
				List<PropertyDistribute> listPT=typeMap.get("productTypeId_"+productTypeId);
				if(null==listPT){
					String dRange="{'key':'productTypeId','value':'"+productTypeId+"'}";
					listPT = ExpandPropertyUseUtil.getPropertyDistributes(
							TestProduct.class.getName(), dRange, uICacheManager);
					typeMap.put("productTypeId_"+productTypeId, listPT);
				}
			}
		}
		req.setAttribute("propertyDistributeList_dRangePT_Map", typeMap);
		return getForward(forwardPage, "myFrameU/expand/useTest/productList");
	}
	
	
	
	@RequestMapping(value="findProductListQueryArgs")
	public String findProductListQueryArgs(HttpServletRequest req,HttpServletResponse res) throws Exception{
		SDynaActionForm form = getSDynaActionForm(req);
		String forwardPage=form.getString("forwardPage");
		
		//[{'pId':'7','pvId':'36'},{'pId':'3','pvId':'18'},{'pId':'10','pvId':'43,42,41,40'},{'pId':'11','pvId':'46'}]
		String queryArgsProperty=form.getString("queryArgsProperty");
		
		List<PropertyDistribute> listALL = ExpandPropertyUseUtil.getPropertyDistributes(
				TestProduct.class.getName(), "ALL", uICacheManager);
		req.setAttribute("propertyDistributeList_all", listALL);
		
		System.out.println(queryArgsProperty);
		req.setAttribute("queryArgsProperty", queryArgsProperty);
		
		
		String queryArgs=form.getString("queryArgs");
		String orderBy=form.getString("orderBy");
		String searchStr=form.getString("searchStr");
		List<TestProduct> pList = (List<TestProduct>)aBiz.findEntitysByArgs(TestProduct.class, EntityTableUtil.tableName(TestProduct.class.getName()), queryArgs, orderBy, searchStr, true, 20, "productList", req);
		
		
		
		
		//用来存放typeId为key的分配情况
				//其实也可以完全拿出整个系统所有的calss所有的数据的分配map来，到页面上去找
		HashMap<String,List<PropertyDistribute>> typeMap=new HashMap<String,List<PropertyDistribute>>();
				
		
		int size = pList.size();
		TestProduct p = null;
		HashMap<String, String> map = null;
		for(int i=0;i<size;i++){
			p=pList.get(i);
			map = ExpandPropertyUseUtil.getPropertyValuesMap(uICacheManager, p);
			p.setPropertyValuesMap(map);
			
			int productTypeId=p.getProductTypeId();
			if(productTypeId!=0){
				List<PropertyDistribute> listPT=typeMap.get("productTypeId_"+productTypeId);
				if(null==listPT){
					String dRange="{'key':'productTypeId','value':'"+productTypeId+"'}";
					listPT = ExpandPropertyUseUtil.getPropertyDistributes(
							TestProduct.class.getName(), dRange, uICacheManager);
					typeMap.put("productTypeId_"+productTypeId, listPT);
				}
			}
		}
		req.setAttribute("propertyDistributeList_dRangePT_Map", typeMap);
		return getForward(forwardPage, "myFrameU/expand/useTest/productListQueryArgs");
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
