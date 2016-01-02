package myFrameU.util.httpUtil.filter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * 
 * 如何获取。
 * HttpServletRequest request = (HttpServletRequest) RequestFilter.threadLocal.get().get("request");
 *
 */
public class RequestFilter implements Filter {
	//public static ThreadLocal<HttpServletRequest> threadLocal = new ThreadLocal<HttpServletRequest>();
	public static ThreadLocal<Map<String,Object>> threadLocal = new ThreadLocal<Map<String,Object>>();
	public static Map<String,Object> map=new HashMap<String,Object>();
	@Override
	public void destroy() {
		
	}

	@Override
	public void doFilter(ServletRequest req1, ServletResponse res1,
			FilterChain chain) throws IOException, ServletException {
		if (req1 instanceof HttpServletRequest && res1 instanceof HttpServletResponse) {
			HttpServletRequest req=(HttpServletRequest)req1;
			HttpServletResponse res=(HttpServletResponse)res1;
			map.put("request", req);
			map.put("response", res);
			threadLocal.set(map);
			chain.doFilter(req1, res1);
        }
		
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		
	}
	
}
