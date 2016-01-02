package myFrameU.user.biz.impl;


import java.util.HashMap;

import myFrameU.biz.AbstractBizImpl;
import myFrameU.user.biz.ShoucangBizI;
import myFrameU.user.entity.Shoucang;
import myFrameU.user.init.InitConfig;
import myFrameU.util.sshUtil.hibernate.EntityTableUtil;

import org.springframework.stereotype.Service;
@Service("shoucangBiz")
public class ShoucangBizImpl extends AbstractBizImpl implements ShoucangBizI {
	@Override
	public void removeSc(Shoucang sc) throws Exception {
		aDao.deleteObject(sc);
		String scEntity=sc.getScEntity();
		String scTable=EntityTableUtil.tableName(scEntity);
		if(null!=scTable){
			int scEntityId=sc.getScEntityId();
			//aDao.j_execute("delete from shoucang where scEntityId=? and scTable=?", new Object[]{scEntityId,scTable});
			
			InitConfig initConfig = myFrameU.user.init.InitMavenImpl.ic;
			 HashMap<String,String> shoucangMap=initConfig.getShoucangMap();
			if(null!=shoucangMap){
				String shoucangField = shoucangMap.get(scEntity);
				if(null!=shoucangField && !shoucangField.equals("")){
					//处理shop和product的收藏数目
					StringBuffer sb  = new StringBuffer();
					sb.append("update ").append(scTable).append(" set ").append(shoucangField).append("=").append(shoucangField).append("-1").append(" where id=?");
					aDao.j_execute(sb.toString(),  new Object[]{scEntityId});
				}
			}
		}
	}

	@Override
	public void shoucang(Shoucang sc) throws Exception {
		aDao.insertObject(sc);
		String scEntity=sc.getScEntity();
		String scTable=EntityTableUtil.tableName(scEntity);
		int scEntityId=sc.getScEntityId();
		if(null!=scTable){
			InitConfig initConfig = myFrameU.user.init.InitMavenImpl.ic;
			 HashMap<String,String> shoucangMap=initConfig.getShoucangMap();
			if(null!=shoucangMap){
				String shoucangField = shoucangMap.get(scEntity);
				if(null!=shoucangField && !shoucangField.equals("")){
					//处理shop和product的收藏数目
					StringBuffer sb  = new StringBuffer();
					sb.append("update ").append(scTable).append(" set ").append(shoucangField).append("=").append(shoucangField).append("+1").append(" where id=?");
					aDao.j_execute(sb.toString(),  new Object[]{scEntityId});
				}
			}
		}
	}
	
}
