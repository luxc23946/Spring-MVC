<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
   <h1>Success</h1>
   <br><br>
   requestScope.time: ${requestScope.time }
   <br><br>
   requestScope.names: ${requestScope.names }
   <br><br>
   sessionScope.user:${sessionScope.user }   
   <br><br>
   sessionScope.school:${sessionScope.school } 
   <br><br>
   testConverter.user:${requestScope.user }
   <br><br>
   <form:form modelAttribute="user">
      <form:errors title="错误"  path="*"/> 
   </form:form>
   <br><br>
   <fmt:message key="i18n.hello"/>
   <br><br>
   <a href="${pageContext.request.contextPath }/springmvc/testI18n?locale=zh_CN">中文</a>
   <a href="${pageContext.request.contextPath }/springmvc/testI18n?locale=en_US">英文</a>
   <br><br>
   ${requestScope.exception }
   <br><br>
   ${requestScope.ex }
</body>
</html>