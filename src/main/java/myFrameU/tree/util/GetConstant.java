package myFrameU.tree.util;

import java.util.List;

import myFrameU.tree.entity.AbstractTreeEntity;



public final class GetConstant {
	
	
	//新闻类型查询的时候，是否级联查询孩子
	//不级联孩子
	public final static int Tree_children_no=1;
	//级联查询直接子类
	public final static int Tree_children_yes=2;
	
	
	
	
	//新闻类型根节点
	public final static int Tree_ROOT=0;
	public final static int Tree_NOROOT=1;
	
	
	//新闻类型叶子节点
	public final static int Tree_Leaf=1;
	public final static int Tree_NOLeaf=2;
	
	
	//新闻类型--查询直接孩子节点
	public final static int Tree_findZJ_returnType_tree=1;
	public final static int Tree_findZJ_returnType_select=2;
	public final static int Tree_findZJ_returnType_jsp=3;
	
	
	
	//newsType 直系
	public final static int Tree_zhixi_ok=1;
	public final static int Tree_zhixi_no=2;
	
	
	
	
	
	public static StringBuffer createEveryLi(List<? extends AbstractTreeEntity> addList,AbstractTreeEntity parentType,StringBuffer sb){
		Integer parentId = parentType.getId();
		//StringBuffer sb = new StringBuffer();
		StringBuffer kongge_sb=new StringBuffer("");
		for(int j=0;j<parentType.getJibie()+1;j++ ){
			kongge_sb.append("&nbsp;&nbsp;&nbsp;&nbsp;");
			
		}
		int nameWidth=400-30*(parentType.getJibie()+1);
		int size = addList.size();
		AbstractTreeEntity nt = null;
		//sb.append("<ul class=\"childUl\">");
		for(int i=0;i<size;i++ ){
			nt = addList.get(i);
			
			sb.append("<li selfId=\"").append(nt.getId()).append("\"").append(" class=\"t_li noROOT\"")
			.append(" parentId=\"").append(parentId).append("\"").append(" isOpen=\"no\"").append(" isLeaf=\"")
			.append(nt.getIsLeaf()).append("\"").append(" id=\"treeId").append(nt.getId()).append("\">")
			.append("<div class=\"kongge\">").append(kongge_sb).append("</div>");
			if(nt.getIsLeaf()==Tree_Leaf){
				//如果是叶子节点
				sb.append("<div class=\"t_head t_head_leaf\"></div>");
			}else{
				sb.append("<div class=\"t_head\"></div>");
			}
			//<div class="t_name"><a href="<%=request.getContextPath()%>/b_news.do?method=findNewsByType&newsTypeId=${nt.id}" target="indexRightRight">${nt.newsTypeName}</a></div>
			sb.append("<div class=\"t_name\" style=\"width:").append(nameWidth).append("px\">").append("<a href=\"javascript:void(0);\"").append(" onClick=\"queryExpandProperty(this);\"").append(" rel=\"").append(nt.getId()).append("\" ").append(">").append(nt.getName()).append("</a>").append("</div>")
			.append("<div class=\"t_right\">").append("<div class=\"t_right_add\"></div>").append("<div class=\"t_right_mod\"></div>")
			.append("<div class=\"t_right_del\"></div>").append("</div>").append("</li>");
		}
		//sb.append("</ul>");
		return sb;
	}
	
	
	
	/*
	 public static String Print(AbstractTreeEntity pt,int level,StringBuffer preLevel) {
		 if(null!=pt.getParent()){
			 if(pt.getIsLeaf()==1){
				
		       for (int i = 0; i < level; i++) {
		       }
		       for (AbstractTreeEntity pt1:pt.getChilds()) {
		           Print(pt1,level+1,preLevel);
		       }
		 }
	       return preLevel.toString();
	    }
		return null;
	 }*/
	
	 
}
