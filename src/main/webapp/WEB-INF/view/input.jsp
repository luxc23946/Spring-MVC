<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>input emp</title>
</head>
<body>
<!--  
	1. WHY 使用 form 标签呢 ?
	可以更快速的开发出表单页面, 而且可以更方便的进行表单值的回显
	2. 注意:
	可以通过 modelAttribute 属性指定绑定的模型属性,
	若没有指定该属性，则默认从 request 域对象中读取 command 的表单 bean
	如果该属性值也不存在，则会发生错误。
	-->
 <!-- path 属性对应 html 表单标签的 name 属性值 -->
 <form:form action="${pageContext.request.contextPath }/curd/emp" method="PUT" modelAttribute="emp">
   <c:if test="${emp.id == null }">
		<fmt:message key="LastName"/> : <form:input path="lastName"/><br>
   </c:if>
   <c:if test="${emp.id != null }">
     <input type="hidden" name="id" value="${ emp.id }">
   </c:if>
   <fmt:message key="Email"/>:<form:input path="email"/><br/>
   <% 
	Map<String, String> map = new HashMap<String, String>();
    map.put("1", "Male");
    map.put("0", "Female");
	request.setAttribute("genders", map);
	%>
   <fmt:message key="Gender"/>:<form:radiobuttons path="gender" items="${ genders }" /><br/>
   <fmt:message key="Department"/>:<form:select path="department.id" items="${ depts }" itemLabel="departmentName" itemValue="id"/><br/>
   <input type="submit" value="Submit">
 </form:form>
</body>
</html>