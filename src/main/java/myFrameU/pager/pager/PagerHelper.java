package myFrameU.pager.pager;
import javax.servlet.http.HttpServletRequest;

public class PagerHelper {
	public static Pager getPager(HttpServletRequest req, int totalRows) {
		String pagerMethod = req.getParameter("pageMethod");
		String currentPage = req.getParameter("currentPage");
		if(null==pagerMethod){
			pagerMethod=(String) req.getAttribute("pagerMethod");
		}
		if(null==currentPage){
			currentPage=(String)req.getAttribute("currentPage");
		}
		
		
		Pager pager = new Pager(totalRows,req);
		if (currentPage != null) {
			pager.refresh(Integer.parseInt(currentPage));
		}
		if (pagerMethod != null) {
			if (pagerMethod.equals("first")) {
				pager.first();
			} else if (pagerMethod.equals("previous")) {
				pager.previous();
			} else if (pagerMethod.equals("next")) {
				pager.next();
			} else if (pagerMethod.equals("last")) {
				pager.last();
			}else if (pagerMethod.equals("number")) {
				pager.number(new Integer(currentPage).intValue());
			}
		}else{
			if (currentPage != null) {
				pagerMethod="number";
				pager.number(new Integer(currentPage).intValue());
			}
		}
		return pager;
	}
	public static Pager getPager(String pagerMethod,String currentPage, int totalRows) {
		Pager pager = new Pager(totalRows);
		if (currentPage != null) {
			pager.refresh(Integer.parseInt(currentPage));
		}
		if (pagerMethod != null) {
			if (pagerMethod.equals("first")) {
				
				pager.first();
			} else if (pagerMethod.equals("previous")) {
				pager.previous();
			} else if (pagerMethod.equals("next")) {
				pager.next();
			} else if (pagerMethod.equals("last")) {
				pager.last();
			}else if (pagerMethod.equals("number")) {
				pager.number(new Integer(currentPage).intValue());
			}
		}
		return pager;
	}
	public static Pager getPagerFirst(int totalRows) {
		String pagerMethod = "first";
		String currentPage = "1";
		Pager pager = new Pager(totalRows);
		if (currentPage != null) {
			pager.refresh(Integer.parseInt(currentPage));
		}
		if (pagerMethod != null) {
			if (pagerMethod.equals("first")) {
				
				pager.first();
			} else if (pagerMethod.equals("previous")) {
				pager.previous();
			} else if (pagerMethod.equals("next")) {
				pager.next();
			} else if (pagerMethod.equals("last")) {
				pager.last();
			}else if (pagerMethod.equals("number")) {
				pager.number(new Integer(currentPage).intValue());
			}
		}
		return pager;
	}
}