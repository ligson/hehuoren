package myFrameU.news.biz;

import org.springframework.stereotype.Service;

import myFrameU.biz.AbstractBizImpl;
import myFrameU.news.entity.News;
import myFrameU.news.entity.NewsContent;
import myFrameU.product.entity.ProductContent;
import myFrameU.util.commonUtil.enumU.EnumUtil;
import myFrameU.util.commonUtil.text.WebFormatter;
@Service("newsBiz")
public class NewsBizImpl extends AbstractBizImpl implements NewsBizI {

	@Override
	public void addNews(News n, String content) throws Exception {
		//第一）处理p和pc
		String info = null;
		if(null!=content && !content.equals("")){
			info = WebFormatter.html2text(content);
			if(null!=info && !info.equals("")){
				int infoLen = info.length();
				if(infoLen>120){
					info=info.substring(0,119);
				}
			}
		}
		n.setInfo(info);
		
				aDao.insertObject(n);
				NewsContent nc = new NewsContent();
				nc.setContentName1("文章内容");
				nc.setContentValue1(content);
				nc.setNewsId(n.getId());
				aDao.insertObject(nc);
				n.setNewsContentId(nc.getId());
				aDao.updateObject(n);
	}

	@Override
	public void modifyNews(News n, String content) throws Exception {
		int newsContentId = n.getNewsContentId();
		NewsContent nc = (NewsContent)aDao.queryObjectById("from NewsContent as pc where pc.id=?", new Object[]{newsContentId});
		if(null!=nc){
			String contentOld = nc.getContentValue1();
			if(null!=contentOld && null!=content){
				if(!contentOld.equals(content)){
					nc.setContentValue1(content);
					aDao.updateObject(nc);
				}
			}
		}
		aDao.updateObject(n);
	}

	@Override
	public void delNews(News n) throws Exception {
		String canDel=n.getCanDel();
		if(EnumUtil.equalsE(News.CANDEL.YES, canDel)){
			aDao.deleteObject(n);
		}
	}

	@Override
	public News findNewsById(int pId,boolean modifyViewCount) throws Exception {
		News n = (News)aDao.queryObjectById("from News as n where n.id=?", new Object[]{pId});
		if(null!=n){
			if(modifyViewCount){
				n.setViewsCount(n.getViewsCount()+1);
				aDao.updateObject(n);
			}
		}
		return n;
	}

}
