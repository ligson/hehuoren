package myFrameU.util.commonUtil.text;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import net.paoding.analysis.analyzer.PaodingAnalyzer;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.Token;
import org.apache.lucene.analysis.TokenStream;

public class WordBreakerUtil {
	static Map map = new HashMap();
	static{
		map.put("PaodingAnalyzer", new PaodingAnalyzer());
	}
	public static List<String> wordBreaker(String s,String analyzers,int minLength){
		if(null!=s){
			 Analyzer analyzer= (Analyzer) map.get(analyzers);
			 try {
				return displayTokens(analyzer, s,minLength);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	
	
	
	
	

    public static List<String> displayTokens(Analyzer analyzer,
          String text,int minLength) throws IOException {
  	  	List<String> list = new ArrayList<String>();
               Token[] tokens = tokensFromAnalysis(analyzer, text);
               String s=null;
                for (int i = 0; i < tokens.length; i++) {
	                Token token = tokens[i];   
	                s=token.termText();
	                if(s.length()>=minLength){
	                	if(!list.contains(s)){
	                		if(!isNumeric1(s) && !isEnglish1(s)){
	                			list.add(s);
			                	System.out.print("minLength[" +s + "] ");
	                		}
	                	}
	                }
                }
                
                //如果按照minLength来获取(比如为3),到现在为止是0个的话,则摘选一个2个的.
                if(list.size()<=1){
                	for (int i = 0; i < tokens.length; i++) {
    	                Token token = tokens[i];   
    	                s=token.termText();
    	                if(s.length()>=(minLength-1)){
    	                	if(!list.contains(s)){
    	                		if(!isNumeric1(s)  && !isEnglish1(s)){
    	                			list.add(s);
        		                	System.out.print("minLength-1[" +s + "] ");
        		                	break;//摘取一个就行了，凑合凑合，不用那么多
    	                		}
    	                	}
    	                }
                    }
                }
                
                
                
                
				return list;
     }

   

    public static Token[] tokensFromAnalysis(Analyzer analyzer,

            String text) throws IOException {

                TokenStream stream =

                analyzer.tokenStream("contents", new StringReader(text));

                ArrayList tokenList = new ArrayList();

                while (true) {

                org.apache.lucene.analysis.Token token = stream.next();

                if (token == null) break;

                tokenList.add(token);

                }

                return (Token[]) tokenList.toArray(new Token[0]);

                }

	
	
	
	
	
	
    public static boolean isNumeric1(String str){
    	  Pattern pattern = Pattern.compile("[0-9]*");
    	  return pattern.matcher(str).matches();
    	 }
	
	
    public static boolean isEnglish1(String str){
  	  Pattern pattern = Pattern.compile("^[a-zA-Z]*");
  	  return pattern.matcher(str).matches();
  	 }
	
	
	
	
}
