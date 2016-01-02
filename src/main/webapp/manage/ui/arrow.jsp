<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String[] arrows=request.getParameterValues("arrows");
	List<String> list = new ArrayList<String>();
	int len = arrows.length;
	String arrow = null;
	String[] arrowArray=null;
	for(int i=0;i<len;i++){
		arrow=arrows[i];
		arrowArray=arrow.split("##");
		list.add(new Integer(arrowArray[1]), arrowArray[0]);
	}
	request.setAttribute("list", list);
	
%>

<script type="text/javascript">
function go_fa_step(id){
	 for (i=1;i<6;i++){
	  document.getElementById("fa_"+i).className='help_step_item';
	 }
	 document.getElementById("fa_"+id).className+=" help_step_set"
	}
</script>
<div class="help_wrap">
  	<div class="help_step_box fa">
  		<c:forEach items="${requestScope.list}" var="m" step="1" varStatus="vs">
  			<c:if test='${param.selectIndex==vs.index+1}'>
  				<div onclick="javascript:go_fa_step(1)" id="fa_1" class="help_step_item help_step_set">
	      			<div class="help_step_num">${vs.index+1}</div>
	      			${m}
	      			<div class="help_step_right"></div>
	    		</div>
  			</c:if>
  			<c:if test='${param.selectIndex!=vs.index+1}'>
  				<div onclick="javascript:go_fa_step(1)" id="fa_1" class="help_step_item">
	      			<div class="help_step_num">${vs.index+1}</div>
	      			${m}
	      			<div class="help_step_right"></div>
	    		</div>
  			</c:if>
  		</c:forEach>
    </div>
 </div>