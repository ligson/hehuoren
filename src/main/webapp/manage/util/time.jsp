<%@ page language="java" import="java.util.Date" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
        <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<select name="${param.mname}" id="${param.mname}">
<c:forEach begin="0" end="23" step="1" varStatus="vs">
	<c:if test='${empty param.mvalue}'>
		<c:if test='${vs.index<10}'>
			<option value="0${vs.index}">0${vs.index}点</option>
		</c:if>
		<c:if test='${vs.index>=10}'>
			<option value="${vs.index}">${vs.index}点</option>
		</c:if>
	</c:if>
	<c:if test='${!empty param.mvalue}'>
		<c:if test='${vs.index<10}'>
			<c:set var="jialvs" value="0${vs.index}" scope="request"></c:set>
			<c:if test='${param.mvalue==requestScope.jialvs}'>
				<option value="0${vs.index}" selected="selected">0${vs.index}点</option>
			</c:if>
			<c:if test='${param.mvalue!=requestScope.jialvs}'>
				<option value="0${vs.index}" >0${vs.index}点</option>
			</c:if>
		</c:if>
		<c:if test='${vs.index>=10}'>
			<c:if test='${param.mvalue==vs.index}'>
				<option selected="selected" value="${vs.index}">${vs.index}点</option>
			</c:if>
			<c:if test='${param.mvalue!=vs.index}'>
				<option value="${vs.index}">${vs.index}点</option>
			</c:if>
		</c:if>
	</c:if>
</c:forEach>
</select>
<select name="${param.sname}"  id="${param.sname}">
<c:forEach begin="0" end="59" step="1" varStatus="vs">
	<c:if test='${empty param.svalue}'>
		<c:if test='${vs.index<10}'>
			<option value="0${vs.index}">0${vs.index}分</option>
		</c:if>
		<c:if test='${vs.index>=10}'>
			<option value="${vs.index}">${vs.index}分</option>
		</c:if>
	</c:if>
	<c:if test='${!empty param.svalue}'>
		<c:if test='${vs.index<10}'>
			<c:set var="jialvss" value="0${vs.index}" scope="request"></c:set>
			<c:if test='${param.svalue==requestScope.jialvss}'>
				<option value="0${vs.index}" selected="selected">0${vs.index}分</option>
			</c:if>
			<c:if test='${param.svalue!=requestScope.jialvss}'>
				<option value="0${vs.index}" >0${vs.index}分</option>
			</c:if>
		</c:if>
		<c:if test='${vs.index>=10}'>
			<c:if test='${param.svalue==vs.index}'>
				<option value="${vs.index}" selected="selected">${vs.index}分</option>
			</c:if>
			<c:if test='${param.svalue!=vs.index}'>
				<option value="${vs.index}" >${vs.index}分</option>
			</c:if>
		</c:if>
	</c:if>
</c:forEach>
</select>