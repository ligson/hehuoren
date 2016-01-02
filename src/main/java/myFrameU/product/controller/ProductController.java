package myFrameU.product.controller;


import hhr.order.entity.Cart;
import hhr.order.entity.CartItem;
import hhr.order.entity.PickUpAddress;
import hhr.order.util.CartUtil;
import hhr.order.util.PickUpAddressUtil;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import myFrame.cache.CacheKey;
import myFrameU.app.util.AppJsonResultUtil;
import myFrameU.exception.exception.MyRefererException;
import myFrameU.exception.exception.MyStrException;
import myFrameU.expand.distribution.entity.PropertyDistribute;
import myFrameU.expand.use.util.ExpandPropertyUseUtil;
import myFrameU.product.biz.ProductBizI;
import myFrameU.product.entity.Product;
import myFrameU.product.entity.ProductContent;
import myFrameU.product.entity.ProductPrice;
import myFrameU.product.entity.ProductType;
import myFrameU.product.util.ProductPriceUtil;
import myFrameU.product.util.ProductTypeUtil;
import myFrameU.queryArgs.util.QueryArgsUtil;
import myFrameU.spring.mvc.CommonField;
import myFrameU.spring.mvc.FatherController;
import myFrameU.spring.mvc.SDynaActionForm;
import myFrameU.user.entity.User;
import myFrameU.util.sshUtil.hibernate.EntityTableUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller("productController")
public class ProductController extends FatherController {
	@Autowired
	private ProductBizI productBizI;

	public ProductBizI getProductBizI() {
		return productBizI;
	}

	public void setProductBizI(ProductBizI productBizI) {
		this.productBizI = productBizI;
	}

	@RequestMapping(value={"/product/finds","/admin/product/finds","/user/product/finds"})
	public String finds(HttpServletRequest req,HttpServletResponse res) throws Exception{
		SDynaActionForm form = getSDynaActionForm(req);
		String orderBy = form.getString("orderBy");
		String queryArgs = form.getString("queryArgs");
		
		queryArgs=QueryArgsUtil.getRoleQueryArgs("shopId", "", "", req);
		
		
		List<ProductType> productTypeList=CacheKey.CKProductType.ALLMAP.getList(uICacheManager);
		req.setAttribute("productTypeList", productTypeList);
		
		
		aBiz.findEntitysByArgs(
				Product.class, 
				EntityTableUtil.tableName(Product.class.getName()), 
				queryArgs, orderBy, null, true, 0, "productList", req);
		
		
		
		String queryExpand=form.getString("queryExpand");
		if(null!=queryExpand && !queryExpand.equals("")){
			if(queryExpand.equals("yes")){
				List<Product> pList=(List<Product>) req.getAttribute("productList");
				if(null!=pList){
					
					//用来存放typeId为key的分配情况
							//其实也可以完全拿出整个系统所有的calss所有的数据的分配map来，到页面上去找
							HashMap<String,List<PropertyDistribute>> typeMap=new HashMap<String,List<PropertyDistribute>>();
							
							List<PropertyDistribute> listALL = ExpandPropertyUseUtil.getPropertyDistributes(
									Product.class.getName(), "ALL", uICacheManager);
							req.setAttribute("propertyDistributeList_all", listALL);
							int size = pList.size();
							Product p = null;
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
												Product.class.getName(), dRange, uICacheManager);
										typeMap.put("productTypeId_"+productTypeId, listPT);
									}
								}
							}
							req.setAttribute("propertyDistributeList_dRangePT_Map", typeMap);
				}
			}
		}
		
		return getForward("product/productList", req);
	}
	
	@RequestMapping(value={"/admin/product/toAdd_step1"})
	public String toAdd_sept1(HttpServletRequest req,HttpServletResponse res) throws Exception{
		SDynaActionForm form = getSDynaActionForm(req);
		//[5][7]
		List<ProductType> list = CacheKey.CKProductType.ALLMAP.getList(uICacheManager);
		req.setAttribute(AppJsonResultUtil.app_+"productTypeList", list);
		return getForward("product/productAdd1", req);
	}
	
	
	@RequestMapping(value={"/admin/product/toAdd_step2"})
	public String toAdd_step2(HttpServletRequest req,HttpServletResponse res) throws Exception{
		SDynaActionForm form = getSDynaActionForm(req);
		Integer productTypeId=form.getInteger("productTypeId");
		if(null!=productTypeId && productTypeId.intValue()!=0){
			ProductType pt = CacheKey.CKProductType.ALLMAP.getObject(uICacheManager, productTypeId);
			if(null!=pt){
				req.setAttribute("productType", pt);
				//加载分配给product的所有数据的属性
				List<PropertyDistribute> list = ExpandPropertyUseUtil.getPropertyDistributes(Product.class.getName(), "ALL", uICacheManager);
				req.setAttribute("propertyDistributeList_ALL", list);
				
				//分析productTypeId，看是不是叶子节点，如果不是需要找出他父亲的dRange来
				HashMap<String,List<PropertyDistribute>> prodisMap = ProductTypeUtil.getPropertyDistributesList(uICacheManager, pt);
				List<PropertyDistribute> list_root=prodisMap.get("propertyDistributeList_root");
				List<PropertyDistribute> list_self=prodisMap.get("propertyDistributeList_self");
				req.setAttribute("propertyDistributeList_root", list_root);
				req.setAttribute("propertyDistributeList_self", list_self);
				
			}
		}
		
		return getForward("product/productAdd2", req);
	}
	
	
	
	
	@RequestMapping(value="/admin/product/add")
	public String add(HttpServletRequest req,HttpServletResponse res) throws Exception{
		SDynaActionForm form = getSDynaActionForm(req);
		String name = form.getString("name");
		String redirect=form.getString(CommonField.redirect);
		Integer productTypeId=form.getInteger("productTypeId");
		String lunboImage1 = form.getString("lunboImage1");
		String lunboImage2 = form.getString("lunboImage2");
		String lunboImage3 = form.getString("lunboImage3");
		String lunboImage4 = form.getString("lunboImage4");
		String lunboImage5 = form.getString("lunboImage5");
		//Integer productCount = form.getInteger("productCount");
		/*Float price1=form.getFloat("price1");
		Float price2=form.getFloat("price2");
		Float toTjrTc=form.getFloat("toTjrTc");*/
		
		String content=form.getString("content");
		
		
		/**
		 * 现在所有产品都有所有的自提点
		 */
		String[] pickupAddressIds=req.getParameterValues("pickupAddressIds");
		
		
		if(null==name || name.equals("")){
			throw new MyRefererException("请输入产品名称");
		}
		if(null==productTypeId || productTypeId.intValue()==0){
			throw new MyRefererException("请选择产品分类");
		}
		if(null==lunboImage1 || lunboImage1.equals("")){
			throw new MyRefererException("请上传第一幅图片");
		}
		
		
		//第一）处理普通属性
		Product p = new Product();
		//p.setProductCount(productCount);
		
		//product的这三为了显示就选择的是第一个的
		/**
		p.setPrice1(price1);
		p.setPrice2(price2);
		p.setPrice1Price2(price1-price2);
		p.setToTjrTc(toTjrTc);
		**/
		
		p.setName(name);
		p.setLunboImage1(lunboImage1);
		p.setLunboImage2(lunboImage2);
		p.setLunboImage3(lunboImage3);
		p.setLunboImage4(lunboImage4);
		p.setLunboImage5(lunboImage5);
		p.setMainImage(lunboImage1);
		
		
		
		if(null!=pickupAddressIds){
			int paLen = pickupAddressIds.length;
			if(paLen>0){
				StringBuffer picksb = new StringBuffer();
				for(int i=0;i<paLen;i++){
					picksb.append("[").append(pickupAddressIds[i]).append("]");
				}
				String pickIds = picksb.toString();
				if(!pickIds.equals("")){
					p.setPickupAddressIds(pickIds);
				}
			}
		}
		
		
		
		
		
		
		ProductType pt=CacheKey.CKProductType.ALLMAP.getObject(uICacheManager, productTypeId);
		if(null!=pt){
			p.setProductTypeId(productTypeId);
			p.setProductTypeTreeIds(pt.getTreeId());
		}
		p.setRelDate(new Date());
		p.setStatus(Product.STATUS.OK.toString());
		//第二）处理扩展属性
		String porpertyValues=ExpandPropertyUseUtil.getPropertyValuesFromArray(req);
		HashMap<String,String> map = ExpandPropertyUseUtil.getPropertyValues_Ids_Strs(porpertyValues);
		if(null!=map){
			String s1=map.get(CommonField.propertyValues_Ids);
			String s2=map.get(CommonField.propertyValues_Strs);
			if(null!=s1 && !s1.equals("")){
				p.setPropertyValues_Ids(s1);
			}
			if(null!=s2 && !s2.equals("")){
				p.setPropertyValues_Strs(s2);
			}
			
		}
		
		
		String[] xilieNames = req.getParameterValues("xilieName");
		String[] baozhuangNames = req.getParameterValues("baozhuangName");
		String[] price1s=req.getParameterValues("price1");
		String[] price2s=req.getParameterValues("price2");
		String[] toTjrTcs=req.getParameterValues("toTjrTc");
		String[] productCounts=req.getParameterValues("productCount");
		String[] shiyongNames=req.getParameterValues("shiyongName");
		//String[] yesproductPrices=req.getParameterValues("yesproductPrice");
		
		System.out.println("xilieNames:"+xilieNames.length);
		System.out.println("baozhuangNames:"+baozhuangNames.length);
		System.out.println("price1s:"+price1s.length);
		System.out.println("price2s:"+price2s.length);
		System.out.println("toTjrTcs:"+toTjrTcs.length);
		//System.out.println("yesproductPrices:"+yesproductPrices.length);
		
		
		
		List<ProductPrice> ppList = ProductPriceUtil.createProductPriceList(xilieNames, baozhuangNames,shiyongNames, price1s, price2s, toTjrTcs,productCounts);
		
		productBizI.addProduct(p, content,ppList);
		
		return redirect;
	}
	
	
	
	
	
	/**
	 * 去修改
	 * @param req
	 * @param res
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/admin/product/toMod")
	public String toMod(HttpServletRequest req,HttpServletResponse res) throws Exception{
		SDynaActionForm form = getSDynaActionForm(req);
		Integer id=form.getInteger("id");
		if(null==id || id==0){
			throw new MyStrException("请填写productId");
		}
		Product p = (Product)aBiz.findObjectById("from Product as p where p.id=?", new Object[]{id});
		if(null!=p){
			
			/*//找到这个p的自提点
			String pickupAddressIds = p.getPickupAddressIds();
			if(null!=pickupAddressIds && pickupAddressIds.equals("")){
				List<String> pickIdStrList = new ArrayList<String>();
				if(pickupAddressIds.contains("][")){
					String[] pickArray=pickupAddressIds.split("]\\[");
					if(null!=pickArray){
						int pickALen = pickArray.length;
						if(pickALen>0){
							for(int){
								
							}
						}
					}
				}else{
					pickupAddressIds=pickupAddressIds.substring(1,pickupAddressIds.length()-1);
					pickIdStrList.add(pickupAddressIds);
				}
			}
			*/
			
			req.setAttribute("product", p);
			ProductContent pc = (ProductContent)aBiz.findObjectById("from ProductContent as pc where pc.productId=?", new Object[]{p.getId()});
			req.setAttribute("productContent", pc);
			
			//第一步
			List<PropertyDistribute> listALL = ExpandPropertyUseUtil.getPropertyDistributes(
					Product.class.getName(), "ALL", uICacheManager);
			req.setAttribute("propertyDistributeList_ALL", listALL);
			int productTypeId=p.getProductTypeId();
			if(productTypeId!=0){
				//ProductType pt = CacheQueryUtil.getProductType(productTypeId, uICacheManager);
				ProductType pt=(ProductType)CacheKey.CKProductType.ALLMAP.getObject(uICacheManager, productTypeId);
				if(null!=pt){
					HashMap<String,List<PropertyDistribute>> prodisMap = ProductTypeUtil.getPropertyDistributesList(uICacheManager, pt);
					List<PropertyDistribute> list_root=prodisMap.get("propertyDistributeList_root");
					List<PropertyDistribute> list_self=prodisMap.get("propertyDistributeList_self");
					req.setAttribute("propertyDistributeList_root", list_root);
					req.setAttribute("propertyDistributeList_self", list_self);
				}
			}
			
			
			//第二步
			HashMap<String, String> map = ExpandPropertyUseUtil.getPropertyValuesMap(uICacheManager, p);
			req.setAttribute("propertyValuesMap", map);
			
			
			//第三步
			/*String propertyValues_Ids=p.getPropertyValues_Ids();
			String propertyValue_Strs=p.getPropertyValues_Strs();
			String last = ExpandPropertyUseUtil.mergePvs(propertyValues_Ids, propertyValue_Strs);
			req.setAttribute("propertyValuesResultForm", last);
			*/
			
			
			/**
			 * 找出这个产品下的所有的价格组合
			 */
			List<ProductPrice> ppList = productBizI.getProductPriceListByProductId(id);
			req.setAttribute("productPriceList", ppList);
			
			
			
			
			
			
			
			
		}
		return getForward("product/productMod", req);
	}
	
	
	@RequestMapping(value="/admin/product/mod")
	public String mod(HttpServletRequest req,HttpServletResponse res) throws Exception{
		SDynaActionForm form = getSDynaActionForm(req);
		Integer id=form.getInteger("id");
		String name = form.getString("name");
		String redirect=form.getString(CommonField.redirect);
		
		String lunboImage1 = form.getString("lunboImage1");
		String lunboImage2 = form.getString("lunboImage2");
		String lunboImage3 = form.getString("lunboImage3");
		String lunboImage4 = form.getString("lunboImage4");
		String lunboImage5 = form.getString("lunboImage5");
		String content=form.getString("content");
		//Integer productCount = form.getInteger("productCount");
		/*Float price1=form.getFloat("price1");
		Float price2=form.getFloat("price2");
		Float toTjrTc=form.getFloat("toTjrTc");*/
		String[] pickupAddressIds = req.getParameterValues("pickupAddressIds");
		/*if(null==productCount || productCount.intValue()==0){
			throw new MyRefererException("请填写该产品的数量");
		}*/
		/*if(null==price1 || price1.floatValue()==0){
			throw new MyRefererException("请填写该产品的网店价格");
		}
		if(null==price2 || price2.floatValue()==0){
			throw new MyRefererException("请填写该产品的合伙人价格");
		}
		if(null==toTjrTc || toTjrTc.floatValue()==0){
			throw new MyRefererException("请填写该产品合伙人的提成百分比");
		}*/
		if(null==name || name.equals("")){
			throw new MyRefererException("请输入产品名称");
		}
		if(null==lunboImage1 || lunboImage1.equals("")){
			throw new MyRefererException("请上传第一幅图片");
		}
		
		if(null!=id && id.intValue()!=0){
			Product p = (Product)aBiz.findObjectById("from Product as p where p.id=?", new Object[]{id});
			if(null!=p){
				String oldMainImage=p.getMainImage();
				//p.setProductCount(productCount);
				//p.setPrice1(price1);
				//p.setPrice2(price2);
				String oldPickIds = p.getPickupAddressIds();
				String pickIds=null;
				if(null!=pickupAddressIds){
					int paLen = pickupAddressIds.length;
					if(paLen>0){
						StringBuffer picksb = new StringBuffer();
						for(int i=0;i<paLen;i++){
							picksb.append("[").append(pickupAddressIds[i]).append("]");
						}
						pickIds = picksb.toString();
						if(!pickIds.equals("")){
							p.setPickupAddressIds(pickIds);
						}
					}
				}
				
				
				
				//p.setToTjrTc(toTjrTc);
				p.setName(name);
				p.setLunboImage1(lunboImage1);
				p.setLunboImage2(lunboImage2);
				p.setLunboImage3(lunboImage3);
				p.setLunboImage4(lunboImage4);
				p.setLunboImage5(lunboImage5);
				
				p.setMainImage(lunboImage1);
				
				//第二）处理扩展属性
				String porpertyValues=ExpandPropertyUseUtil.getPropertyValuesFromArray(req);
				HashMap<String,String> map = ExpandPropertyUseUtil.getPropertyValues_Ids_Strs(porpertyValues);
				boolean expandModify=false;
				if(null!=map){
					String s1old=p.getPropertyValues_Ids();
					String s2old=p.getPropertyValues_Strs();
					if(null==s1old){
						s1old="";
					}
					if(null==s2old){
						s2old="";
					}
					String s1=map.get(CommonField.propertyValues_Ids);
					String s2=map.get(CommonField.propertyValues_Strs);
					if(null!=s1 && !s1.equals("")){
						if(!s1old.equals(s1)){
							p.setPropertyValues_Ids(s1);
							expandModify=true;
						}
					}
					if(null!=s2 && !s2.equals("")){
						if(!s2old.equals(s2)){
							p.setPropertyValues_Strs(s2);
							expandModify=true;
						}
					}
					
				}
				
				/*p.setPropertyValues_Ids(map.get(CommonField.propertyValues_Ids));
				p.setPropertyValues_Strs(map.get(CommonField.propertyValues_Strs));*/
				productBizI.modifyProduct(p,oldPickIds, content,expandModify);
			}
		}
		
		return redirect;
	}
	
	

	@RequestMapping(value="/admin/product/del")
	public void del(HttpServletRequest req,HttpServletResponse res) throws Exception{
		SDynaActionForm form = getSDynaActionForm(req);
		Integer id=form.getInteger("id");
		
		if(null!=id && id.intValue()!=0){
			Product p = (Product)aBiz.findObjectById("from Product as p where p.id=?", new Object[]{id});
			if(null!=p){
				productBizI.delProduct(p);
			}
		}
	}
	
	
	
	

	@RequestMapping(value={"/admin/product/findId","/wrap/product/findId"})
	public String findId(HttpServletRequest req,HttpServletResponse res) throws Exception{
		SDynaActionForm form = getSDynaActionForm(req);
		String toFW=form.getString("toFW");
			
		Integer id=form.getInteger("id");
		if(null!=id && id.intValue()!=0){
			Product product = (Product)aBiz.findObjectById("from Product as p where p.id=?", new Object[]{id});
			if(null!=product){
				req.setAttribute("product", product);
				
				
				int productContentId = product.getProductContentId();
				ProductContent pc=(ProductContent)aBiz.findObjectById("from ProductContent as pc where pc.id=?", new Object[]{productContentId});
				req.setAttribute("productContent", pc);
				
				
				
				//第一步
				List<PropertyDistribute> listALL = ExpandPropertyUseUtil.getPropertyDistributes(
						Product.class.getName(), "ALL", uICacheManager);
				req.setAttribute("propertyDistributeList_all", listALL);
				int productTypeId=product.getProductTypeId();
				if(productTypeId!=0){
					//{'key':'productTypeId','value':'1'}
					String dRange="{'key':'productTypeId','value':'"+productTypeId+"'}";
					List<PropertyDistribute> listPT = ExpandPropertyUseUtil.getPropertyDistributes(
							Product.class.getName(), dRange, uICacheManager);
					req.setAttribute("propertyDistributeList_dRangePT", listPT);
				}
				//第二步
				HashMap<String, String> map = ExpandPropertyUseUtil.getPropertyValuesMap(uICacheManager, product);
				req.setAttribute("propertyValuesMap", map);
				
				
				
				ProductType pt = CacheKey.CKProductType.ALLMAP.getObject(uICacheManager, productTypeId);
				req.setAttribute("productType", pt);
				
				
				
				User user = (User)req.getSession().getAttribute("myUser");
				if(null!=user){
					List<PickUpAddress> pickUpAddressList=
							PickUpAddressUtil.findPickUpAddressList(user.getAddressId(), product.getPickupAddressIds(), uICacheManager);
					req.setAttribute("pickUpAddressList", pickUpAddressList);
				}else{
					List<PickUpAddress> pickUpAddressList=
							PickUpAddressUtil.findPickUpAddressList(0, product.getPickupAddressIds(), uICacheManager);
					req.setAttribute("pickUpAddressList", pickUpAddressList);
				}
				
				
				
				
				
			}
		}
		if(null==toFW || toFW.equals("")){
			return "wrap/fg/product";	
		}else{
			return "wrap/fg/product1";
		}
	}
	
	
	

	

	
	
	
	
	
}
