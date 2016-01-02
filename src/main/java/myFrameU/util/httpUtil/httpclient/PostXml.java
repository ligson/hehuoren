package myFrameU.util.httpUtil.httpclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;

/**
 * @author yhcui
 * @version 创建时间：Jul 10, 2011 5:38:14 PM 类说明
 */
public class PostXml {

	public static String postXml(String url, String xml) {
		String txt = null;
		PostMethod post = new PostMethod(url);// 请求地址
		post.setRequestBody(xml);// 这里添加xml字符串

		// 指定请求内容的类型
		post.setRequestHeader("Content-type", "text/xml; charset=iso-8859-1");
		HttpClient httpclient = new HttpClient();// 创建 HttpClient 的实例
		int result;
		try {
			result = httpclient.executeMethod(post);
			System.out.println("Response status code: " + result);// 返回200为成功
			System.out.println("Response body: ");

			BufferedReader reader = new BufferedReader(new InputStreamReader(
					post.getResponseBodyAsStream(), "ISO-8859-1"));
			String tmp = null;
			String htmlRet = "";
			while ((tmp = reader.readLine()) != null) {
				htmlRet += tmp + "\r\n";
			}
			txt=new String(htmlRet.getBytes("ISO-8859-1"),
					"UTF-8");
			System.out.println(txt);

			//txt = post.getResponseBodyAsString();
			//System.out.println(post.getResponseBodyAsString());// 返回的内容
			post.releaseConnection();// 释放连接
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return txt;
	}
}