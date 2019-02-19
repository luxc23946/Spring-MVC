<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>List emps</title>
<script type="text/javascript" src="${ pageContext.request.contextPath }/scripts/jquery-1.9.1.min.js"></script>
<script type="text/javascript">
  $(function(){
	  
	  $("table tr td:last-child a").click(function(){
		  $("form").attr("action",$(this).attr("href"))
		           .append("<input type='hidden' name='_method' value='DELETE'/>")
		           .submit();
		  return false;
	  });
  })
</script>
</head>
<body>
  <c:if test="${ empty requestScope.emps }">
       没有员工信息
  </c:if>
  <c:if test="${ ! empty requestScope.emps }">
  <table border="1" cellpadding="10" cellspacing="0">
  <tr>
    <th><fmt:message key="LastName"/></th>
    <th><fmt:message key="Email"/></th>
    <th><fmt:message key="Gender"/></th>
    <th><fmt:message key="Department"/></th>
    <th><fmt:message key="Edit"/></th>
    <th><fmt:message key="Delete"/></th>
  </tr>
  <c:forEach items="${requestScope.emps }" var="emp" >
    <tr>
      <td>${ emp.lastName }</td>
      <td>${ emp.email }</td>
      <td>${ emp.gender==1? 'male':'female' }</td>
      <td>${ emp.department.departmentName }</td>
      <td><a href="${ pageContext.request.contextPath }/curd/emp/${emp.id }">edit</a> </td>
      <td><a href="${ pageContext.request.contextPath }/curd/emp/${emp.id }">delete</a> </td>
    </tr>
  </c:forEach>
  </table>
  <a href="${ pageContext.request.contextPath }/curd/emp">add a new employee</a>
</c:if>
<form method="post"></form>
</body>
</html>