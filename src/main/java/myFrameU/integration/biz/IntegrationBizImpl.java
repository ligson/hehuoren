package myFrameU.integration.biz;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import myFrameU.address.util.AddressUtil;
import myFrameU.biz.AbstractBizImpl;
import myFrameU.integration.entity.Integration;
import myFrameU.integration.entity.IntegrationItem;
import myFrameU.integration.init.InitConfig;
import myFrameU.integration.init.RoleCaseFieldEntity;
import myFrameU.util.commonUtil.reflect.MyReflect;
import myFrameU.util.sshUtil.hibernate.EntityTableUtil;

public class IntegrationBizImpl extends AbstractBizImpl implements
		IntegrationBizI {
	
	@Override
	public Integration addIntegration(Object who) throws Exception {
		Integration i=null;
		if(null!=who){
			Class c = who.getClass();
			
			i = new Integration();
			i.setWho(c.getName());
			i.setWhoId(MyReflect.getId(who));
			i.setTotal(0);
			//address
			i=(Integration) AddressUtil.setAddress(i, who);
			aDao.insertObject(i);
		}
		return i;
	}

	
	
	
	public int getGrade(float total){
		if(total>=0 && total<=4){
			return 0;
		}else if(total>4 && total<=10){
			return 1;
		}else if(total>10 && total<=40){
			return 2;
		}else if(total>40 && total<=90){
			return 3;
		}else if(total>90 && total<=150){
			return 4;
		}else if(total>150 && total<=250){
			return 5;
		}else if(total>250 && total<=500){
			return 6;
		}else if(total>500 && total<=1000){
			return 7;
		}else if(total>1000 && total<=2000){
			return 8;
		}else if(total>2000 && total<=5000){
			return 9;
		}else if(total>5000 && total<=10000){
			return 10;
		}else if(total>10000 && total<=20000){
			return 11;
		}else if(total>20000 && total<=50000){
			return 12;
		}else if(total>50000 && total<=100000){
			return 13;
		}else if(total>100000 && total<=200000){
			return 14;
		}else if(total>200000 && total<=500000){
			return 15;
		}else if(total>500000 && total<=1000000){
			return 16;
		}else if(total>1000000 && total<=2000000){
			return 17;
		}else if(total>2000000 && total<=5000000){
			return 18;
		}else if(total>5000000 && total<=10000000){
			return 19;
		}else if(total>10000000){
			return 20;
		}
		return 0;
	}
	
	
	
	
	
	
	/**
	 * 
	 */
	@Override
	public void addIntegrationItem(Object who, int addOrMinus, float fraction, String itemEvent) throws Exception {
		
		if(null!=who){
			Class c = who.getClass();
			String whoName=c.getName();
			int whoId=MyReflect.getId(who);
			
			Integration i = (Integration)aDao.queryObjectById("from Integration as i where i.who=? and i.whoId=?", new Object[]{whoName,whoId});
			if(null!=i){
				IntegrationItem ii = new IntegrationItem();
				ii.setAddOrMinus(addOrMinus);
				ii.setEvent(itemEvent);
				ii.setFraction(fraction);
				ii.setIntegration(i);
				ii.setRelDate(new Date());
				aDao.insertObject(ii);
				
				float newTotal=0;
				if(addOrMinus==1){
					//加
					newTotal=i.getTotal()+fraction;
				}else if(addOrMinus==2){
					//减
					newTotal=i.getTotal()-fraction;
				}
				i.setTotal(newTotal);
				aDao.updateObject(i);
				
				//级联操作shop user对象里的Grade totalScore
				InitConfig initConfig=myFrameU.integration.init.InitMavenImpl.ic;
				Map<String,RoleCaseFieldEntity> rcfeMap=initConfig.getRcfeMap();
				int grade=getGrade(newTotal);
				StringBuffer updateSQL=new StringBuffer("update ");
				updateSQL.append(EntityTableUtil.tableName(c.getName())).append(" set ")
				.append((rcfeMap.get(c.getName())).getGradeField()).append("=?,")
				.append((rcfeMap.get(c.getName())).getTotalField()).append("=?")
				.append("where id=?");
				System.out.println(updateSQL.toString()+"============================");
				
				
				aDao.j_execute(updateSQL.toString(), new Object[]{grade,newTotal,whoId});
				
				
				
			}
		}
	}

}
