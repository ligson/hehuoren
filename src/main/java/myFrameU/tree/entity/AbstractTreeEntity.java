package myFrameU.tree.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;



public abstract class AbstractTreeEntity  implements Serializable{
	
	
	protected int id;
	protected String name;//20个字
	private AbstractTreeEntity parent;
	private int rootTypeId;//根目录的ID
	//private String childrenTypeIds;//[32][21].这个字段的目的是为了找出这个节点下所有的叶子节点。不用进行like查询了。
	//但是操作太过于麻烦，而且一开始数据量并不大，所以like也无所谓
	protected int isROOT;//是否根目录
	private int isLeaf;//是否叶子
	private int jibie;//级别
	private String url;//url结构。32个字
	private String treeId;//综合的id比如：[1][28][40]。100个字
	////////////private Set<AbstractTreeEntity> childs=new HashSet<AbstractTreeEntity>();
	//private Set<News> newsSet = new HashSet<News>();
	//private int status;//状态，和标签的status目的相同
	private int zhixi;//不持久化
	
	private int fatherId;
	
	
	
	private String allName;
	
	
	
	
	
	public AbstractTreeEntity(){}
	
	public AbstractTreeEntity(int id){
		this.id=id;
	}
	public AbstractTreeEntity(int id,String name){
		this.id=id;
		this.name=name;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public AbstractTreeEntity getParent() {
		return parent;
	}
	public void setParent(AbstractTreeEntity parent) {
		this.parent = parent;
	}
	public int getRootTypeId() {
		return rootTypeId;
	}
	public void setRootTypeId(int rootTypeId) {
		this.rootTypeId = rootTypeId;
	}
	public int getIsROOT() {
		return isROOT;
	}
	public void setIsROOT(int isROOT) {
		this.isROOT = isROOT;
	}
	public int getIsLeaf() {
		return isLeaf;
	}
	public void setIsLeaf(int isLeaf) {
		this.isLeaf = isLeaf;
	}
	public int getJibie() {
		return jibie;
	}
	public void setJibie(int jibie) {
		this.jibie = jibie;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getTreeId() {
		return treeId;
	}
	public void setTreeId(String treeId) {
		this.treeId = treeId;
	}

	public int getZhixi() {
		return zhixi;
	}
	public void setZhixi(int zhixi) {
		this.zhixi = zhixi;
	}
	public int getFatherId() {
		return fatherId;
	}
	public void setFatherId(int fatherId) {
		this.fatherId = fatherId;
	}

	public String getAllName() {
		return allName;
	}

	public void setAllName(String allName) {
		this.allName = allName;
	}

	
	
	
	
	
	
	
}
