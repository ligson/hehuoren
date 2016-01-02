package myFrameU.expand.libraryProperty.biz;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import myFrameU.biz.AbstractBizImpl;
import myFrameU.expand.libraryProperty.entity.SystemLibraryProperty;
import myFrameU.expand.libraryProperty.entity.SystemLibraryPropertyValue;
import myFrameU.expand.libraryProperty.util.GetConstant;
import myFrameU.util.commonUtil.collection.MyCollectionUtils;
import myFrameU.util.commonUtil.enumU.EnumUtil;
@Service("slpBizI")
public class SystemLibraryPropertyBizImpl extends AbstractBizImpl implements
		SystemLibraryPropertyBizI {

	@Override
	public String addSystemLibraryProperty(
			SystemLibraryProperty p) throws Exception {
		String svc=GetConstant.SystemLibraryProperty_ValueAlternative_Connect;
		String svdc=GetConstant.SystemLibraryProperty_ValueDefault_Connect;
		/**
		 * 根据p，生成相应的value
		 * 		1)处理生成备选value-valueAlternative
		 * 				去掉重复的
		 * 		2)对比默认value，将该value设置为default
		 */
		String addType=p.getAddType();
		if(!EnumUtil.equalsE(SystemLibraryProperty.ADDTYPE.INPUT, addType)){
			//如果添加类型不是输入类型的，那么就需要处理备选value
			String value_alter=p.getValueAlternative();
			String[] value_alter_array=null;
			if(!value_alter.contains(svc)){
				//如果备选值之间不存在连接符，说明就一个备选值。
				value_alter_array=new String[1];
				value_alter_array[0]=value_alter;
			}else{
				value_alter_array=value_alter.split(svc);
			}
			//去重
			value_alter_array=MyCollectionUtils.array_unique(value_alter_array);
			
			
			
			//将默认值分割为一个list
			String valueDefault=p.getValueDefault();
			List<String> valueDefaultList=null;
			if(null!=valueDefault && !valueDefault.equals("")){
				String[] valueDefaultArray=null;
				if(!valueDefault.contains(svdc)){
					valueDefaultArray=new String[1];
					valueDefaultArray[0]=valueDefault;
				}else{
					valueDefaultArray=valueDefault.split(svdc);
				}
				
				if(null!=valueDefaultArray){
					valueDefaultList=Arrays.asList(valueDefaultArray);
				}
			}
			
			Set<SystemLibraryPropertyValue> sysLibraryPropertyValueSet=
					new HashSet<SystemLibraryPropertyValue>();
			
			int len=value_alter_array.length;
			String v=null;
			for(int i=0;i<len;i++){
				v=value_alter_array[i];
				
				SystemLibraryPropertyValue slpv=new SystemLibraryPropertyValue();
				slpv.setPvalue(v);
				slpv.setSysLibraryProperty(p);
				//判断是不是默认值
				if(null!=valueDefaultList){
					/**
					 * 单选：只有一个默认值
					 * 下拉：只有一个默认值
					 * 复选：可以多个默认值
					 * 		红色，黄色，蓝色，黑色，分红
					 * 	其中		黄色，蓝色 两个为默认值
					 */
					
					if(EnumUtil.equalsE(SystemLibraryProperty.ADDTYPE.RADIO, addType) || EnumUtil.equalsE(SystemLibraryProperty.ADDTYPE.SELECT, addType)){
						if(valueDefault.equals(v)){
							slpv.setDefaultValue(true);
						}
					}else if(EnumUtil.equalsE(SystemLibraryProperty.ADDTYPE.CHECKBOX, addType)){
						if(valueDefaultList.contains(v)){
							slpv.setDefaultValue(true);
						}
					}
				}
				sysLibraryPropertyValueSet.add(slpv);
			}
			
			p.setSysLibraryPropertyValueSet(sysLibraryPropertyValueSet);
			aDao.insertObject(p);
		}else{
			//添加input
			aDao.insertObject(p);
		}
		return null;
	}

	@Override
	public String deleteSystemLibraryProperty(
			SystemLibraryProperty sysLibraryProperty) throws Exception {
		/**
		 * 如果这个property被用着呢，所以就不能删除
		 * 如果可以删除，则级联删除
		 */
		return null;
	}

	@Override
	public String addSLPropertyValues(
			SystemLibraryProperty p,String values)
			throws Exception {
		String svc=GetConstant.SystemLibraryProperty_ValueAlternative_Connect;
		/**
		 * 一次添加多个value
		 * 
		 * 判断是否已经存在
		 * 
		 * 有可能需要级联修改property
		 * 1)addType是非Input输入型的，需要级联修改property的备选值
		 */
		if(null!=p){
			String addType=p.getAddType();
			if(!EnumUtil.equalsE(SystemLibraryProperty.ADDTYPE.INPUT, addType)){
				String newValueAlternative=p.getValueAlternative()+svc+values;
				aDao.j_execute("update expand_systemlibraryproperty set valueAlternative=? where id=?", new Object[]{newValueAlternative,p.getId()});
				String[] values_array=null;
				if(values.contains(svc)){
					values_array=values.split(svc);
				}else{
					values_array=new String[1];
					values_array[0]=values;
				}
				
				int len=values_array.length;
				String v=null;
				SystemLibraryPropertyValue pv = null;
				for(int i=0;i<len;i++){
					v=values_array[i];
					pv = (SystemLibraryPropertyValue) aDao.queryObjectById("from SystemLibraryPropertyValue as pv where pv.pvalue=?", new Object[]{v});
					if(null==pv){
						pv=new SystemLibraryPropertyValue();
						pv.setDefaultValue(false);
						pv.setPvalue(v);
						pv.setSysLibraryProperty(p);
						aDao.insertObject(pv);
					}
				}
				
			}
		}
		return null;
	}

	@Override
	public String modifySLPropertyValues(int id, String newPValue,
			boolean defaultValue,String modifyValueOrDefault) throws Exception {
		//if(null!=newPValue){
			SystemLibraryPropertyValue pv = (SystemLibraryPropertyValue)aDao.queryObjectById("from SystemLibraryPropertyValue where id=?", new Object[]{id});
			if(null!=pv){
				if(modifyValueOrDefault.equals("pvalue")){
					//只修改pvalue
					boolean modifyP_pva=false;
					boolean modifyP_pvd=false;
					Object[] property_values_o=(Object[]) aDao.j_queryObject("select valueAlternative,valueDefault from expand_systemLibraryProperty where id=?", new Object[]{pv.getSysLibraryProperty().getId()});
					String property_valueAlter=(String) property_values_o[0];
					String property_valueDefa=(String) property_values_o[1];
					//判断pvalue是不是一样，如果一样就级联修改p的
					String oldPValue = pv.getPvalue();
					if(oldPValue.equals(newPValue)){
						//相同不能修改
					}else{
						modifyP_pva=true;
						pv.setPvalue(newPValue);
						String pva_suf=property_valueAlter+",";
						property_valueAlter=pva_suf.replace(oldPValue+",", newPValue+",");
						if(property_valueAlter.endsWith(",")){
							property_valueAlter=property_valueAlter.substring(0,property_valueAlter.length()-1);
						}
					}
					
					if(pv.getDefaultValue()){
						//原先是默认值
						modifyP_pvd=true;
						String pvd_suf=property_valueDefa+",";
						property_valueDefa=pvd_suf.replace(oldPValue+",", newPValue+",");
						if(property_valueDefa.endsWith(",")){
							property_valueDefa=property_valueDefa.substring(0,property_valueDefa.length()-1);
						}
					}
					StringBuffer sb=new StringBuffer();
					if(modifyP_pva && modifyP_pvd){
						//都要修改
						sb.append("update expand_systemLibraryProperty set valueAlternative='").append(property_valueAlter).append("',valueDefault='").append(property_valueDefa).append("' where id=").append(pv.getSysLibraryProperty().getId());
					}else if(modifyP_pva && !modifyP_pvd){
						//只修改备选
						sb.append("update expand_systemLibraryProperty set valueAlternative='").append(property_valueAlter).append("' where id=").append(pv.getSysLibraryProperty().getId());
					}else{
						//只修改默认值-不可能
						//都不修改-不可能。
					}
					aDao.j_execute(sb.toString(), null);
					aDao.j_execute("update expand_systemLibraryPropertyValue set pvalue=? where id=?", new Object[]{newPValue,id});
				}else{
					//只修改默认不默认
					if(pv.getDefaultValue() && defaultValue){
						//如果以前和现在都是默认值的，都不用修改
					}else if(!pv.getDefaultValue() && !defaultValue){
						//以前和现在都不是默认的，都不用修改
					}else{
						boolean new_pv_default=true;
						String property_valueDefault=(String) aDao.j_queryObject("select valueDefault from expand_systemLibraryProperty where id=?", new Object[]{pv.getSysLibraryProperty().getId()});
						String property_valueDefault_suf=property_valueDefault+",";
						 if(pv.getDefaultValue() && !defaultValue){
							//以前是默认的，现在取消默认
							 new_pv_default=false;
							 property_valueDefault=property_valueDefault_suf.replace(pv.getPvalue()+",", "");
							 if(property_valueDefault.endsWith(",")){
								 property_valueDefault=property_valueDefault.substring(0,property_valueDefault.length()-1);
							 }
						 }else if(!pv.getDefaultValue() && defaultValue){
							 //以前不是默认的，现在要默认
							 new_pv_default=true;
							 property_valueDefault=property_valueDefault+","+pv.getPvalue();
						 }
						aDao.j_execute("update expand_systemLibraryPropertyValue set defaultValue=? where id=?", new Object[]{new_pv_default,id});
						aDao.j_execute("update expand_systemLibraryProperty set valueDefault=? where id=?", new Object[]{property_valueDefault,pv.getSysLibraryProperty().getId()});
					}
					
				}
			}
		//}
		return null;
	}

	/**
	 * 修改property
	 * 第一部分属性：name，key，dataType，dataFrom,dataFromUrl，addType，showType。这些不级联操作其他的
	 * 注意：不操作备选，默认值
	 */
	@Override
	public String modifySLProperty(SystemLibraryProperty oldP) throws Exception {
		if(null!=oldP){
			//如果选择了dattaFrom的WEB，就必须填写url
			if(EnumUtil.equalsE(SystemLibraryProperty.DATAFROM.WEB, oldP.getDataFrom())){
				if(null==oldP.getDataFromWebUrl() || oldP.getDataFromWebUrl().equals("")){
					oldP.setDataFrom(SystemLibraryProperty.DATAFROM.MANUAL.toString());
				}
			}
			//级联操作其他的
			//------------------------------------------------------
			aDao.updateObject(oldP);
		}
		return null;
	}

}
