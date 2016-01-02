package myFrameU.pager.pager;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;

import myFrameU.pager.init.InitMavenImpl;


public class Pager  implements Serializable{
	private int totalRows; // ������
	private int pageSize = InitMavenImpl.ic.getPageSize(); 
	private int currentPage; // ��ǰҳ��
	private int totalPages; // ��ҳ��
	private int startRow; // ��ǰҳ����ݿ��е���ʼ��

	private String url;//翻页的地址比如：b_news.do?method=findAllNews
	
	private String nextUrl;//下一页的地址
	private String prevUrl;//上一页的地址
	private String indexUrl;//首页
	private String lastUrl;//尾页
	
	public Pager() {
	}

	public Pager(int _totalRows,HttpServletRequest req) {
		Integer returnListSize = (Integer)req.getAttribute("returnListSize");
		if(null!=returnListSize){
			if(returnListSize.intValue()!=0){
				pageSize=returnListSize;
			}
		}
		totalRows = _totalRows;
		totalPages = totalRows / pageSize;
		int mod = totalRows % pageSize;
		if (mod > 0) {
			totalPages++;
		}
		currentPage = 1;
		startRow = 0;
	}
	public Pager(int _totalRows) {
		totalRows = _totalRows;
		totalPages = totalRows / pageSize;
		int mod = totalRows % pageSize;
		if (mod > 0) {
			totalPages++;
		}
		currentPage = 1;
		startRow = 0;
	}

	public int getStartRow() {
		return startRow;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setTotalRows(int totalRows) {
		this.totalRows = totalRows;
	}

	public void setStartRow(int startRow) {
		this.startRow = startRow;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getTotalRows() {
		return totalRows;
	}


	
	public void first() { // ��ҳ
		currentPage = 1;
		startRow = 0;
		//System.out.println("---------- Pager ---------- first");
	}

	public void previous() { // ��һҳ
		if (currentPage == 1) {
			return;
		}
		currentPage--;
		startRow = (currentPage - 1) * pageSize;
		//System.out.println("---------- Pager ---------- previous");
	}

	public void next() { // ��һҳ
		if (currentPage < totalPages) {
			currentPage++;
		}
		startRow = (currentPage - 1) * pageSize;
		//System.out.println("---------- Pager ---------- next");
	}

	public void last() { // βҳ
		currentPage = totalPages;
		startRow = (currentPage - 1) * pageSize;
//System.out.println("---------- Pager ---------- last");
	}

	public void number(int num){

		currentPage = num;
		startRow = (currentPage-1)  * pageSize;
		//System.out.println("=============------------------==================---------------------================"+num+"startRow="+startRow);
	}
	
	public void refresh(int _currentPage) {
		currentPage = _currentPage;
		if (currentPage > totalPages) {
			last();
		}
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getNextUrl() {
		return nextUrl;
	}

	public void setNextUrl(String nextUrl) {
		this.nextUrl = nextUrl;
	}

	public String getPrevUrl() {
		return prevUrl;
	}

	public void setPrevUrl(String prevUrl) {
		this.prevUrl = prevUrl;
	}

	public String getIndexUrl() {
		return indexUrl;
	}

	public void setIndexUrl(String indexUrl) {
		this.indexUrl = indexUrl;
	}

	public String getLastUrl() {
		return lastUrl;
	}

	public void setLastUrl(String lastUrl) {
		this.lastUrl = lastUrl;
	}
	
}