package myFrameU.expand.use.test.initData;

import java.io.Serializable;
import java.util.List;

import javax.servlet.ServletContext;

import myFrameU.biz.AbstractBizI;
import myFrameU.ehcache.initData.AbstractWebInit;
import myFrameU.ehcache.util.UICacheManager;
import myFrameU.expand.use.test.entity.TestProduct;
import myFrameU.expand.use.test.entity.TestProductType;

public class LoadExpandTestProduct extends AbstractWebInit{
	
	@Override
	public void initData(ServletContext sc,UICacheManager uICacheManager,AbstractBizI aBiz) throws Exception {
		
		List<TestProductType> ptList = (List<TestProductType>)aBiz.findObjectList(TestProductType.class, null, "from TestProductType ", null, false, 0, 0);
		sc.setAttribute("expandTest_testProductTypeList", ptList);
		uICacheManager.putObjectCached("web", "expandTest_testProductTypeList", (Serializable) ptList);
		List<TestProduct> pList = (List<TestProduct>)aBiz.findObjectList(TestProduct.class, null, "from TestProduct ", null, false, 0, 0);
		sc.setAttribute("expandTest_testProductList", pList);
		
		/*TestProductType pt1 = new TestProductType();
		pt1.setName("产品类别1");
		
		TestProductType pt2 = new TestProductType();
		pt2.setName("产品类别2");
		
		TestProductType pt3 = new TestProductType();
		pt3.setName("产品类别3");
		
		TestProductType pt4 = new TestProductType();
		pt4.setName("产品类别4");
		
		aBiz.addObject(pt1);
		aBiz.addObject(pt2);
		aBiz.addObject(pt3);
		aBiz.addObject(pt4);
		
		
		*//**
		 * 类别1	【产品1，产品2，产品3】
		 * 类别2【产品4，产品5】
		 * 类别3【产品6】
		 *//*
		TestProduct p1=new TestProduct();
		p1.setName("产品1");
		p1.setProductTypeId(pt1.getId());
		p1.setProductTypeTreeIds("["+pt1.getId()+"]");
		aBiz.addObject(p1);
		
		TestProduct p2=new TestProduct();
		p2.setName("产品2");
		p2.setProductTypeId(pt1.getId());
		p2.setProductTypeTreeIds("["+pt1.getId()+"]");
		aBiz.addObject(p2);
		
		
		TestProduct p3=new TestProduct();
		p3.setName("产品3");
		p3.setProductTypeId(pt1.getId());
		p3.setProductTypeTreeIds("["+pt1.getId()+"]");
		aBiz.addObject(p3);
		
		
		TestProduct p4=new TestProduct();
		p4.setName("产品4");
		p4.setProductTypeId(pt2.getId());
		p4.setProductTypeTreeIds("["+pt2.getId()+"]");
		aBiz.addObject(p4);
		
		
		TestProduct p5=new TestProduct();
		p5.setName("产品5");
		p5.setProductTypeId(pt2.getId());
		p5.setProductTypeTreeIds("["+pt2.getId()+"]");
		aBiz.addObject(p5);
		
		
		TestProduct p6=new TestProduct();
		p6.setName("产品6");
		p6.setProductTypeId(pt3.getId());
		p6.setProductTypeTreeIds("["+pt3.getId()+"]");
		aBiz.addObject(p6);*/
	}
	
	
	
	
	
}
