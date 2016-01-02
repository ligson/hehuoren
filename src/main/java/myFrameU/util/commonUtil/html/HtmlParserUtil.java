package myFrameU.util.commonUtil.html;


import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.Tag;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.tags.InputTag;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;
import org.htmlparser.visitors.NodeVisitor;

public class HtmlParserUtil {
	public static Class getTagClass(String tagClassStr){
		if(tagClassStr.equals("input")){
			return InputTag.class;
		}else if(tagClassStr.equals("link")){
			return LinkTag.class;
		}
		return null;
	}
	public static NodeList parserContent_tagType(String content,String tagClassStr) {
		NodeList list = null;
		Parser parser = null;
		try {
			parser = new Parser(content);
			parser.setEncoding(parser.getEncoding());
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		try {
			Class tagClass = getTagClass(tagClassStr);
			NodeFilter filters = new NodeClassFilter(tagClass);
			list = parser.extractAllNodesThatMatch(filters);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
