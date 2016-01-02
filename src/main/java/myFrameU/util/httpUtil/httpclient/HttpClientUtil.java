package myFrameU.util.httpUtil.httpclient;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.security.cert.CertificateException;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLHandshakeException;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.NoHttpResponseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.protocol.ExecutionContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;


/**
 * @className:HttpClientUtil.java
 * @classDescription:HttpClient工具类//待完善模拟登录，cookie,证书登录
 * @author:xiayingjie
 * @createTime:2011-8-31
 */

public class HttpClientUtil {
	private static DefaultHttpClient httpclient=null;
	public static String CHARSET_ENCODING = "UTF-8";
	//private static String host="61.183.15.250";
	//private static String Referer="http://61.183.15.250:82/djhzsjs/page/anonymous/login.aspx";
	//private static String CookieStr="ASP.NET_SessionId=4qnc1kqxuiztz445pt2pfmnj;.djhslzsjs=97725F0B4818B6C8A7C332A4F020AEE4311BE9235528DB0D2897957B85FB8ABBF3C976E3DC7D656BED5A3563DA811C02B74CA14787EA53E7239A111129C8D0DEABE55A32BFF1B56CE723447DCFD6D65865458EDDE65ADA8F19E89A326F1DB59A3470F5E2FAC37524DB4320D714A5369773A4E5278D12B6572845DEE42F1BCFD27E70C7F7;_uid=845350517@qq.com";
	// private static String
	// USER_AGENT="Mozilla/4.0 (compatible; MSIE 6.0; Win32)";//ie6
	public static String USER_AGENT = "Mozilla/4.0 (compatible; MSIE 7.0; Win32)";// ie7

	// private static String
	// USER_AGENT="Mozilla/4.0 (compatible; MSIE 8.0; Win32)";//ie8

	/**
	 * 获取DefaultHttpClient对象
	 * 
	 * @param charset
	 *            字符编码
	 * @return DefaultHttpClient对象
	 */
	private static DefaultHttpClient getDefaultHttpClient(final String charset) {
		if(null==httpclient){
			httpclient = new DefaultHttpClient();
			// 模拟浏览器，解决一些服务器程序只允许浏览器访问的问题
			httpclient.getParams().setParameter(CoreProtocolPNames.USER_AGENT,
					USER_AGENT);
			httpclient.getParams().setParameter(HttpMethodParams.SINGLE_COOKIE_HEADER, true);
			httpclient.getParams().setParameter(
					CoreProtocolPNames.USE_EXPECT_CONTINUE, Boolean.FALSE);
			httpclient.getParams().setParameter(
					CoreProtocolPNames.HTTP_CONTENT_CHARSET,
					charset == null ? CHARSET_ENCODING : charset);
			
			// 浏览器兼容性
			httpclient.getParams().setParameter(ClientPNames.COOKIE_POLICY,
					CookiePolicy.BROWSER_COMPATIBILITY);
			// 定义重试策略
			httpclient.setHttpRequestRetryHandler(requestRetryHandler);
		}else{
			//System.out.println(httpclient.toString()+"~~~~~~~~~~~~");
		}
		return httpclient;
	}
	/**
	 * 访问https的网站
	 * @param httpclient
	 */
	private static void enableSSL(DefaultHttpClient httpclient){
		//调用ssl
		 try {
	            SSLContext sslcontext = SSLContext.getInstance("TLS");
	            sslcontext.init(null, new TrustManager[] { truseAllManager }, null);
	            SSLSocketFactory sf = new SSLSocketFactory(sslcontext);
	            sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
	            Scheme https = new Scheme("https", sf, 443);
	            httpclient.getConnectionManager().getSchemeRegistry()
	                    .register(https);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	}
	/**
	 * 重写验证方法，取消检测ssl
	 */
    private static TrustManager truseAllManager = new X509TrustManager(){

		public void checkClientTrusted(
				java.security.cert.X509Certificate[] arg0, String arg1)
				throws CertificateException {
			// TODO Auto-generated method stub
			
		}

		public void checkServerTrusted(
				java.security.cert.X509Certificate[] arg0, String arg1)
				throws CertificateException {
			// TODO Auto-generated method stub
			
		}

		public java.security.cert.X509Certificate[] getAcceptedIssuers() {
			// TODO Auto-generated method stub
			return null;
		}
    	
    } ;

	/**
	 * 异常自动恢复处理, 使用HttpRequestRetryHandler接口实现请求的异常恢复
	 */
	private static HttpRequestRetryHandler requestRetryHandler = new HttpRequestRetryHandler() {
		// 自定义的恢复策略
		public boolean retryRequest(IOException exception, int executionCount,
				HttpContext context) {
			// 设置恢复策略，在发生异常时候将自动重试3次
			if (executionCount >= 3) {
				// 如果连接次数超过了最大值则停止重试
				return false;
			}
			if (exception instanceof NoHttpResponseException) {
				// 如果服务器连接失败重试
				return true;
			}
			if (exception instanceof SSLHandshakeException) {
				// 不要重试ssl连接异常
				return false;
			}
			HttpRequest request = (HttpRequest) context
					.getAttribute(ExecutionContext.HTTP_REQUEST);
			boolean idempotent = (request instanceof HttpEntityEnclosingRequest);
			if (!idempotent) {
				// 重试，如果请求是考虑幂等
				return true;
			}
			return false;
		}
	};

	/**
	 * 使用ResponseHandler接口处理响应，HttpClient使用ResponseHandler会自动管理连接的释放，解决了对连接的释放管理
	 */
	private static ResponseHandler<String> responseHandler = new ResponseHandler<String>() {
		// 自定义响应处理
		public String handleResponse(HttpResponse response)
				throws ClientProtocolException, IOException {
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				String charset = EntityUtils.getContentCharSet(entity) == null ? CHARSET_ENCODING
						: EntityUtils.getContentCharSet(entity);
				return new String(EntityUtils.toByteArray(entity), charset);
			} else {
				return null;
			}
		}
	};

	/**
	 * 使用post方法获取相关的数据
	 * 
	 * @param url
	 * @param paramsList
	 * @return
	 */
	public static String post(String url, List<NameValuePair> paramsList) {
		return httpRequest(url, paramsList, "POST", null);
	}

	/**
	 * 使用post方法并且通过代理获取相关的数据
	 * 
	 * @param url
	 * @param paramsList
	 * @param proxy
	 * @return
	 */
	public static String post(String url, List<NameValuePair> paramsList,
			HttpHost proxy) {
		return httpRequest(url, paramsList, "POST", proxy);
	}

	/**
	 * 使用get方法获取相关的数据
	 * 
	 * @param url
	 * @param paramsList
	 * @return
	 */
	public static String get(String url, List<NameValuePair> paramsList) {
		return httpRequest(url, paramsList, "GET", null);
	}

	/**
	 * 使用get方法并且通过代理获取相关的数据
	 * 
	 * @param url
	 * @param paramsList
	 * @param proxy
	 * @return
	 */
	public static String get(String url, List<NameValuePair> paramsList,
			HttpHost proxy) {
		return httpRequest(url, paramsList, "GET", proxy);
	}

	/**
	 * 提交数据到服务器
	 * 
	 * @param url
	 * @param params
	 * @param authenticated
	 * @throws IOException
	 * @throws ClientProtocolException
	 */
	public static String httpRequest(String url,
			List<NameValuePair> paramsList, String method, HttpHost proxy) {
		String responseStr = null;
		// 判断输入的值是是否为空
		if (null == url || "".equals(url)) {
			return null;
		}
		
		// 创建HttpClient实例
		DefaultHttpClient httpclient = getDefaultHttpClient(CHARSET_ENCODING);
		
		//判断是否是https请求
		if(url.startsWith("https")){
			enableSSL(httpclient);
		}
		String formatParams = null;
		// 将参数进行utf-8编码
		if (null != paramsList && paramsList.size() > 0) {
			//formatParams=NameValuePair2String.nvp2String(paramsList);
			formatParams = URLEncodedUtils.format(paramsList, CHARSET_ENCODING);
			if(formatParams.contains("%40")){
				formatParams=formatParams.replaceAll("%40", "@");
			}
			if(formatParams.contains("%2F")){
				formatParams=formatParams.replaceAll("%2F", "/");
			}
			if(formatParams.contains("%3D")){
				formatParams=formatParams.replaceAll("%3D", "=");
			}
			if(formatParams.contains("%2B")){
				formatParams=formatParams.replaceAll("%2B", "+");
			}
			if(formatParams.contains("%22")){
				formatParams=formatParams.replaceAll("%22", "\"");
			}
			if(formatParams.contains("%7B")){
				formatParams=formatParams.replaceAll("%7B", "{");
			}
			if(formatParams.contains("%3A")){
				formatParams=formatParams.replaceAll("%3A", ":");
			}
			System.out.println("=========formatParams="+formatParams);
		}
		// 如果代理对象不为空则设置代理
		if (null != proxy) {
			httpclient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY,
					proxy);
		}
		try {
			// 如果方法为Get
			if ("GET".equalsIgnoreCase(method)) {
				if (formatParams != null) {
					url = (url.indexOf("?")) < 0 ? (url + "?" + formatParams)
							: (url.substring(0, url.indexOf("?") + 1) + formatParams);
				}
				HttpGet hg = new HttpGet(url);
				
				responseStr = httpclient.execute(hg, responseHandler);

				
				
				// 如果方法为Post
			} else if ("POST".equalsIgnoreCase(method)) {
				HttpPost hp = new HttpPost(url);
				if (formatParams != null) {
					StringEntity entity = new StringEntity(formatParams,"UTF-8");
					entity.setContentType("application/x-www-form-urlencoded");
					hp.setEntity(entity);
					System.out.println("entity="+entity.toString());
				}
				responseStr = httpclient.execute(hp, responseHandler);
				
			}

		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return responseStr;
	}


	/**
	 * 提交数据到服务器
	 * 
	 * @param url
	 * @param params
	 * @param authenticated
	 * @throws IOException
	 * @throws ClientProtocolException
	 */
	public static String httpFileRequest(String url,
			 Map<String, String> fileMap,Map<String, String> stringMap,int type, HttpHost proxy) {
		String responseStr = null;
		// 判断输入的值是是否为空
		if (null == url || "".equals(url)) {
			return null;
		}
		// 创建HttpClient实例
		DefaultHttpClient httpclient = getDefaultHttpClient(CHARSET_ENCODING);
		
		//判断是否是https请求
		if(url.startsWith("https")){
			enableSSL(httpclient);
		}
		
		// 如果代理对象不为空则设置代理
		if (null != proxy) {
			httpclient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY,
					proxy);
		}
		//发送文件
		HttpPost hp = new HttpPost(url);
		MultipartEntity multiEntity = new MultipartEntity();
		try {
			//type=0是本地路径，否则是网络路径
			if(type==0){
				for (String key : fileMap.keySet()) {
					multiEntity.addPart(key, new FileBody(new File(fileMap.get(key))));
				}
			}else{
				for (String key : fileMap.keySet()) {				
					multiEntity.addPart(key,new ByteArrayBody(getUrlFileBytes(fileMap.get(key)),key));
				}
			}
			// 加入相关参数 默认编码为utf-8
			for (String key : stringMap.keySet()) {
				multiEntity.addPart(key, new StringBody(stringMap.get(key),Charset.forName(CHARSET_ENCODING)));
			}
			hp.setEntity(multiEntity);
			responseStr = httpclient.execute(hp, responseHandler);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return responseStr;
	}
	
	/**
	 * 将相关文件和参数提交到相关服务器
	 * @param url
	 * @param fileMap
	 * @param StringMap
	 * @return
	 */
	public static String postFile(String url, Map<String, String> fileMap,
			Map<String, String> stringMap) {
		return httpFileRequest( url,fileMap,stringMap,0, null); 
	}
	/**
	 * 将相关文件和参数提交到相关服务器
	 * @param url
	 * @param fileMap
	 * @param StringMap
	 * @return
	 */
	public static String postUrlFile(String url, Map<String, String> urlMap,
			Map<String, String> stringMap) {
		return httpFileRequest( url,urlMap,stringMap,1, null);
	}

	/**
	 * 获取网络文件的字节数组
	 * 
	 * @param url
	 * @return
	 * @throws IOException
	 * @throws ClientProtocolException
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static byte[] getUrlFileBytes(String url) throws ClientProtocolException,
			IOException {
		
		byte[] bytes = null;
		// 创建HttpClient实例
		DefaultHttpClient httpclient = getDefaultHttpClient(CHARSET_ENCODING);
		// 获取url里面的信息
		HttpGet hg = new HttpGet(url);
		HttpResponse hr = httpclient.execute(hg);
		bytes = EntityUtils.toByteArray(hr.getEntity());
		// 转换内容为字节
		return bytes;
	}

	/**
	 * 获取图片的字节数组
	 * 
	 * @createTime 2011-11-24
	 * @param url
	 * @return
	 * @throws IOException
	 * @throws ClientProtocolException
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static byte[] getImg(String url) throws ClientProtocolException,
			IOException {
		byte[] bytes = null;
		// 创建HttpClient实例
		DefaultHttpClient httpclient = getDefaultHttpClient(CHARSET_ENCODING);
		// 获取url里面的信息
		HttpGet hg = new HttpGet(url);
		HttpResponse hr = httpclient.execute(hg);
		bytes = EntityUtils.toByteArray(hr.getEntity());
		// 转换内容为字节
		return bytes;
	}

	
	
	
	/*public static void main(String[] args) throws URISyntaxException, ClientProtocolException, IOException {
		List<NameValuePair> paramsList =  new ArrayList<NameValuePair>();
		paramsList.add(new MyNameValuePair("method","login"));
		paramsList.add(new MyNameValuePair("name","xuxianlin"));
		paramsList.add(new MyNameValuePair("password","xuxianlin"));
		String result = HttpClientUtil.post("http://www.024pm.com/uf_user.do", paramsList);
		
		System.out.println(result);
	}
	*/
	
	
	
	
	
	
	public static void main(String[] args) throws URISyntaxException, ClientProtocolException, IOException {

		/*String url="http://61.183.15.250:82/djhzsjs/page/anonymous/login.aspx";
		String str=HttpClientUtil.get(url, null);
		//System.out.println(str);
		
		NodeList nlist = HtmlParserUtil.parserContent(str, "input");
		
		String inputId=null;
		String inputValue__VIEWSTATE=null;
		String inputValue__EVENTVALIDATION=null;
		InputTag inputTag=null;
		for(int i=0;i<nlist.size();i++){
			inputTag = (InputTag) nlist.elementAt(i);
			inputId=inputTag.getAttribute("id");
			if(null!=inputId){
				if(inputId.equals("__VIEWSTATE")){
					inputValue__VIEWSTATE=inputTag.getAttribute("value");
					System.out.println(inputValue__VIEWSTATE);
				}else if(inputId.equals("__EVENTVALIDATION")){
					inputValue__EVENTVALIDATION=inputTag.getAttribute("value");
					System.out.println(inputValue__EVENTVALIDATION);
				}
			}
		}
		List<NameValuePair> paramsList =  new ArrayList<NameValuePair>();
		paramsList.add(new MyNameValuePair("strAccoun","845350517@qq.com"));
		paramsList.add(new MyNameValuePair("strPassword","xuxianlin"));
		paramsList.add(new MyNameValuePair("__VIEWSTATE",inputValue__VIEWSTATE));
		paramsList.add(new MyNameValuePair("__EVENTVALIDATION",inputValue__EVENTVALIDATION));
		paramsList.add(new MyNameValuePair("checkboxs","1"));
		String result = HttpClientUtil.httpRequest("http://61.183.15.250:82/djhzsjs/page/anonymous/login.aspx", paramsList,"POST",null);
		System.out.println(result);
		*/
		
		/*String url1="http://61.183.15.250:82/djhzsjs/page/anonymous/login.aspx?strAccoun=845350517@qq.com&strPassword=xuxianlin&__VIEWSTATE="+inputValue__VIEWSTATE+"&__EVENTVALIDATION="+inputValue__EVENTVALIDATION+"&checkboxs=1";
		System.out.println(url1);
		String str1=HttpClientUtil.get(url1, null);
		System.out.println(str1);*/
		//20e41e1d696a40bb92e3f95d5f42efd3.html
	/*	String url2="http://61.183.15.250:82/djhzsjs/page/newmain/mainleft.aspx?pEvent=Study";
		String str2=HttpClientUtil.get(url2, null);
		System.out.println(str2);
		
		*/
		

	}
	
	
	
	
}


	
	
	
	

