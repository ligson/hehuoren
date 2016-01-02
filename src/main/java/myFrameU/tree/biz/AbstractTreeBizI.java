package myFrameU.tree.biz;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import myFrameU.biz.AbstractBizI;
import myFrameU.tree.entity.AbstractTreeEntity;


public interface AbstractTreeBizI extends AbstractBizI {
	public void beforeDeleteTreeEntity(int id) throws Exception;
	
	//查找这个id的节点的所有的父亲。
	public List<? extends AbstractTreeEntity> findAllParents(AbstractTreeEntity tree) throws Exception;
	
	
	//查找一个节点下所有的子节点
	public List<? extends AbstractTreeEntity> findAllSons(AbstractTreeEntity tree) throws Exception;
	
	
	public void findSonsByROOT_02Num_jibie(Integer id,HttpServletRequest req,Class c) throws Exception;
	
	
	
}
