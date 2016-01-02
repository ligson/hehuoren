package myFrameU.tree.biz.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import myFrameU.biz.AbstractBizImpl;
import myFrameU.biz.util.MyActionUtil;
import myFrameU.tree.biz.AbstractTreeBizI;
import myFrameU.tree.entity.AbstractTreeEntity;
import myFrameU.tree.util.GetConstant;
import myFrameU.util.commonUtil.text.PinyinUtil;

public class AbstractTreeBizImpl extends AbstractBizImpl implements
		AbstractTreeBizI {
	
	private String tableName;
	private String entityName;
	
	public String getEntityName() {
		return entityName;
	}
	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public AbstractTreeBizImpl(){ }
	public AbstractTreeBizImpl(String tableName,String entityName){
		this.tableName=tableName;
		this.entityName=entityName;
	}
	@Override
	public void removeObject(Object o) throws Exception{
		if(null!=o){
			Class c = Class.forName(entityName);
			AbstractTreeEntity nt = (AbstractTreeEntity)o;
			Integer ntId = nt.getId();
			int isLeaf = nt.getIsLeaf();
			int isROOT = nt.getIsROOT();
			int jibie = nt.getJibie();
			String treeId = nt.getTreeId();
			AbstractTreeEntity parent = (AbstractTreeEntity) nt.getParent();
			if(null!=parent){
				parent = (AbstractTreeEntity)aDao.queryObjectById("get",c, nt.getParent().getId(), null);
			}
			

			
			//2)操作父节点
			if(isLeaf==GetConstant.Tree_Leaf && null!=parent){
				Integer parentId = ((AbstractTreeEntity) parent).getId();
				StringBuffer sb = new StringBuffer();
				sb.append("%").append("[").append(parentId).append("]").append("%");
				List<Integer> idsList = (List<Integer>)aDao.j_queryObjectList("select id from "+tableName+" where treeId like ? and id!=? and id!=?", new Object[]{sb.toString(),parentId,nt.getId()});
				boolean b=true;
				if(null!=idsList){
					if(idsList.size()==0){
						b=true;//如果该节点的父节点只有它这么一个孩子
					}else{
						b=false;//如果该节点的父节点还有其他子节点
					}
				}else{
					b=true;//如果该节点的父节点只有它这么一个孩子
				}
				if(b){
					//将父节点改成叶子节点
					aDao.j_execute("update "+tableName+" set isLeaf=1 where id=?",new Object[]{parentId});
				}
			}
			//1)先删除本身.由于子节点set的配置为cascade="all" .所以也级联删除了子节点
			aDao.getHt().clear();
			
			
			beforeDeleteTreeEntity(nt.getId());
			/**			
			 * Cannot delete or update a parent row: a foreign key constraint fails (`usedcar`.`address`, CONSTRAINT `address_parent_id` FOREIGN KEY (`parent_id`) REFERENCES `address` (`id`))
			 * 貌似看起来是address这个单表中,当您删除辽宁的时候,辽宁下面有儿子,有儿子指向老爹,你删除不了啊,但是一想不对啊,我对childs进行了casecade为all了,应该级联删除儿子啊.
			 * 但是其实是address还和shop有级联关系,关键是单向的级联,在删除辽宁的时候,肯定要删除沈阳,但是沈阳是店铺A的地址啊,您还没说要级联删除shop啊,那只能报错,但是他
			 * 报不了shop_address_id这个外键，只能报本表即address的外键address_parent_id，让我们很迷惑，hibernate的报错机制这么落后？
			 * 
			 * 
			 * 所以鉴于用address的地方太多，你总不能都在address的配置文件中一一双向配置吧。那只能是不做级联，而是在shop里设置addressId和addressName
			 */
			/*StringBuffer sb = new StringBuffer();
			sb.append("%").append("[").append(nt.getId()).append("]").append("%");
			
			aDao.j_execute("update shop set address_id=null where address_id in (select id from address where treeId like ?)", new Object[]{sb.toString()});
			*/
			aDao.deleteObject(nt);
		}
	}
	@Override
	public void addObject(Object o) throws Exception{
		
		AbstractTreeEntity nt = (AbstractTreeEntity)o;
		String getName = nt.getName();
		StringBuffer trreId = new StringBuffer();
		Integer parentId = null;
		AbstractTreeEntity parent = null;
		//nt.setStatus(GetConstantAdre.News_status_ok);
		int jibie=1;
		int isROOT=0;
		//1)找父类
		boolean hasParent=false;
		AbstractTreeEntity parentOld=nt.getParent();
		System.out.println("11111111111111111111111111111111111111111");
		if(null!=parentOld){
			System.out.println("22222222222222222222222222222222222222222222");
			parentId = nt.getParent().getId();
			
			System.out.println("aaaaaaaaaaaaaaaaaa=parentId="+parentId);
			
			
			parent = (AbstractTreeEntity)aDao.queryObjectById("get",o.getClass(), parentId, null);
			
			System.out.println("bbbbbbbbbbbbbbbbbbbbbbbb=parentId="+parent);
			if(null!=parent){
				System.out.println("333333333333333333333333333333333333");
				hasParent=true;
				//2)通过父节点的级别来确定自己的级别
				jibie=((AbstractTreeEntity) parent).getJibie()+1;
				//4)isROOT
				isROOT=1;
				trreId.append(((AbstractTreeEntity) parent).getTreeId());
				System.out.println("444444444444444=="+trreId.toString());
				nt.setParent((AbstractTreeEntity) parent);
				//处理 自己的根目录列
				nt.setRootTypeId(((AbstractTreeEntity) parent).getRootTypeId());
			}
		}else{
			jibie=1;
			
		}
		if(hasParent){
			nt.setFatherId(parentId);
		}else{
			nt.setFatherId(0);
		}
		nt.setJibie(jibie);
		nt.setIsROOT(isROOT);
		nt.setIsLeaf(GetConstant.Tree_Leaf);
		//设置url
		String py = PinyinUtil.getPingYin(getName);
		if(null!=py){
			if(py.length()>12){
				py=PinyinUtil.getPinYinHeadChar(getName);
			}
		}
		Integer hasPyId=(Integer)aDao.j_queryObject("select id from "+tableName+" where url=?",new Object[]{py});
		if(null!=hasPyId){
			//如果已经有了这个拼音的话
			py=py+"1";
		}
		
		nt.setUrl(py);
		nt.setTreeId("[1]");//先随便设置一个值，因为数据库要求不能为空，因为这一列很重要想要用索引。所以必须设置不能为空。
		aDao.insertObject(nt);
		
		
		
		
		//treeId.通过父节点的treeId来确定自己的treeId.重新发一条update sql
		trreId=trreId.append("[").append(nt.getId()).append("]");
		nt.setTreeId(trreId.toString());
		
		System.out.println("55555555555555555555555==="+trreId.toString());
		
		
		//如果有父亲,就修改它父亲的叶子节点,因为刚添加上，所以这个nt肯定是一个叶子节点
		if(hasParent){
			if(((AbstractTreeEntity) parent).getIsLeaf()==GetConstant.Tree_Leaf){
				//如果同时父节点也是叶子节点
				aDao.j_execute("update "+tableName+" set isLeaf=? where id=?", new Object[]{GetConstant.Tree_NOLeaf,((AbstractTreeEntity) parent).getId()});
			}
			//
		}else{
			//如果没有父亲，也就是如果添加的是根节点，那么一开始在insert的时候，根节点字段为-1，然后现在发现在将-1改变成她自己的id值。
			nt.setRootTypeId(nt.getId());
			//没有父亲，那么根目录就是自己的ID
		}
	}
	@Override
	public void beforeDeleteTreeEntity(int id) throws Exception {
		
	}
	
	@Override
	public List<? extends AbstractTreeEntity> findAllParents(AbstractTreeEntity tree)
			throws Exception {
		int ntId=tree.getId();
		int isROOT=tree.getIsROOT();
		String treeId = tree.getTreeId();//[1][3]
		if(null!=treeId){
			if(isROOT==GetConstant.Tree_ROOT){
				//如果是根节点,则没有父亲.就没有必要再做任何操作了
			}else if(isROOT==GetConstant.Tree_NOROOT){
				//如果非根节点
				StringBuffer sb_sql_in=new StringBuffer();
				String cur_id="["+ntId+"]";
				int indexId=treeId.indexOf(cur_id);
				if(indexId>0){
					treeId=treeId.substring(1, indexId-1);
					String[] ids_array=treeId.split("\\]\\[");
					if(null!=ids_array){
						if(ids_array.length>0){
							int every_id=0;
							for(int i=0;i<ids_array.length;i++ ){
								every_id=new Integer(ids_array[i]).intValue();
								//in("+oper_ids+")
								sb_sql_in.append(every_id).append(",");
							}
							String s_sql_in=sb_sql_in.toString();
							s_sql_in=s_sql_in.substring(0,s_sql_in.length()-1);
							
							List<? extends AbstractTreeEntity> address_parentsList=(List<? extends AbstractTreeEntity>)aDao.queryObjectList(AbstractTreeEntity.class, null, "from "+entityName+" as a where a.id in("+s_sql_in+") order by a.id", null, false, 0, 0);
							return address_parentsList;
						}
					}
				}
			}
		}
		return null;
	}
	@Override
	public List<? extends AbstractTreeEntity> findAllSons(
			AbstractTreeEntity tree) throws Exception {
		Integer parentId=tree.getId();
		List<? extends AbstractTreeEntity> newsTypeList = null;
		if(null!=parentId){
			Object[] parent_os = (Object[])aDao.j_queryObject("select isROOT,isLeaf from "+tableName+" where id=?", new Object[]{parentId});
			AbstractTreeEntity parent_newsType=(AbstractTreeEntity)MyActionUtil.array2Object_no_jl(parent_os, new String[]{"isROOT","isLeaf"}, AbstractTreeEntity.class);
			if(null!=parent_newsType){
				if(parent_newsType.getIsLeaf()==GetConstant.Tree_Leaf){
					//如果是叶子节点,则不用查询了
				}else if(parent_newsType.getIsLeaf()==GetConstant.Tree_NOLeaf){
					//如果不是叶子节点
					if(parent_newsType.getIsROOT()==GetConstant.Tree_ROOT){
						//如果还是根节点
						newsTypeList = (List<AbstractTreeEntity>)aDao.queryObjectList(AbstractTreeEntity.class, new Object[]{parentId}, "from "+entityName+" as nt where nt.rootTypeId=?", null, false, 0, 0);
					}else{
						//如果不是叶子节点也不是根节点
						newsTypeList = (List<AbstractTreeEntity>)aDao.queryObjectList(AbstractTreeEntity.class, new Object[]{"%["+parentId+"]%",parentId}, "from "+entityName+" as nt where nt.treeId like ? and nt.id!=?", null, false, 0,0);
					}
				}
			}
			MyActionUtil.printlnList(newsTypeList,null);
		}
		return newsTypeList;
	}
	@Override
	public void findSonsByROOT_02Num_jibie(Integer id,
			HttpServletRequest req,Class c) throws Exception {
		if(null!=id){
			
			AbstractTreeEntity nt = (AbstractTreeEntity)aDao.queryObjectById("get", c, id, null);
			req.setAttribute("tree_self", nt);
			if(null!=nt){
				Integer rootId=null;
				//如果这个类型确实存在，这个类型不一定是哪个级别的，可能是叶子节点也可能是中间的，也可能是根节点
				if(nt.getIsROOT()==GetConstant.Tree_ROOT){
					//如果是根节点
					rootId=nt.getId();
				}else{
					//如果不是根节点
					rootId=nt.getRootTypeId();
				}
				//根据rootId查询这个根节点下的级别从0--num的所有子节点,这个num就是id的newsType所在的级别
				int end_jb=nt.getJibie();
				req.setAttribute("end_jb", end_jb);
				if(end_jb!=0){
					List<AbstractTreeEntity> ntList = (List<AbstractTreeEntity>)aDao.queryObjectList(AbstractTreeEntity.class, new Object[]{rootId,end_jb}, "from "+entityName+" as nt where nt.rootTypeId=? and nt.jibie>=0 and nt.jibie<=?", null, false, 0, 0);
					req.setAttribute("ntList", ntList);
					//遍历，找出这根主线来
					//List<NewsType> zhixi_parentNTs=new ArrayList<NewsType>();
					//1）找出这个newsType的treeId，通过分析treeId字符串，来得到所有的直系亲戚ID
					List<Integer> zhixi_idList = new ArrayList<Integer>();
					String treeId=nt.getTreeId();
					treeId=treeId.substring(1,treeId.length()-1);
					String[] treeIds=treeId.split("\\]\\[");
					if(null!=treeIds){
						int size = treeIds.length;
						Integer zx_nt_id=null;
						for(int i=0;i<size;i++ ){
							zx_nt_id=new Integer(treeIds[i]);
							zhixi_idList.add(zx_nt_id);
						}
					}
					//2)通过直系亲戚ID得到相应的newsType s
					int size=ntList.size();
					AbstractTreeEntity every_nt=null;
					for(int j=0;j<size;j++ ){
						every_nt=ntList.get(j);
						if(zhixi_idList.contains(every_nt.getId())){
							//zhixi_parentNTs.add(every_nt);
							every_nt.setZhixi(GetConstant.Tree_zhixi_ok);
						}else{
							every_nt.setZhixi(GetConstant.Tree_zhixi_no);
						}
					}
					//req.setAttribute("zhixi_parentNTs", zhixi_parentNTs);
				}
			}
		}
	}
	
}
