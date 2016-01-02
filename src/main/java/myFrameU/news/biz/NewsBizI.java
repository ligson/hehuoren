package myFrameU.news.biz;

import myFrameU.biz.AbstractBizI;
import myFrameU.news.entity.News;
import myFrameU.product.entity.Product;

public interface NewsBizI extends AbstractBizI {
	public void addNews(News n,String content) throws Exception;
	
	public void modifyNews(News n ,String content) throws Exception;
	
	public void delNews(News n) throws Exception;
	
	
	public News findNewsById(int pId,boolean modifyViewCount) throws Exception;
	
}
