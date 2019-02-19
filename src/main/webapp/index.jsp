<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="${ pageContext.request.contextPath }/scripts/jquery-1.9.1.min.js"></script>
<script type="text/javascript">
  $(function(){
	  $("#testJson").click(function(){
		  $.post($(this).attr("href"), {},function(data){
			  console.log(data);
		  });
		  return false;
	  });
  })
</script>
</head>
<body>
  <a href="${pageContext.request.contextPath }/springmvc/helloWorld">SpringMVC HelloWorld</a>
  <br><br>
  
  
  <form action="${pageContext.request.contextPath }/springmvc/testMethod" method="post">
     <input type="submit" value="testMethod">
  </form>
  <br><br>
  
  
  <a href="${pageContext.request.contextPath }/springmvc/testParamsAndHeaders?name=tom&age=11">TestParamsAndHeaders</a>
  <br><br>
  
  <a href="${pageContext.request.contextPath }/springmvc/testAntPath/aaa/abc">TestAntPath</a>
  <br><br>
  
  <a href="${pageContext.request.contextPath }springmvc/testPathVariable/10">testPathVariable</a>
  <br><br>
  <strong>testRestful</strong>
  <form action="${pageContext.request.contextPath }/springmvc/testRest/110" method="post">
     <input type="submit" value="POST">
  </form>
  
  <form action="${pageContext.request.contextPath }/springmvc/testRest/110" method="GET">
     <input type="submit" value="GET">
  </form>
  
  <form action="${pageContext.request.contextPath }/springmvc/testRest/110" >
     <input type="hidden" name="_method" value="PUT">
     <input type="submit" value="PUT">
  </form>
  
  <form action="${pageContext.request.contextPath }/springmvc/testRest/110" >
     <input type="hidden" name="_method" value="DELETE">
     <input type="submit" value="DELETE">
  </form>
  <br><br>
  
  <a href="${pageContext.request.contextPath }/springmvc/testRequestParam?name=tom&age=10">testRequestParam</a>
  <br><br>
  
  <a href="${pageContext.request.contextPath }/springmvc/testRequestHeader">testRequestHeader</a>
  <br><br>
  
  <a href="${pageContext.request.contextPath }/springmvc/testCookieValue">testCookieValue</a>
  <br><br>
  
  <strong>testPojo</strong>
  <form action="${pageContext.request.contextPath }/springmvc/testPojo">
    username: <input type="text" name="username">
    <br/>
    password: <input type="password" name="password">
    <br/>
    email: <input type="text" name="email">
    <br/>
    age: <input type="text" name="age">
    <br/>
    province: <input type="text" name="address.province">
    <br/>
    city: <input type="text" name="address.city">
    <br/>
    <input type="submit" value="testPojo">
  </form>
  <br><br>
  
  
  <a href="${pageContext.request.contextPath }/springmvc/testServletApi">testServletApi</a>
  <br><br>
  
  <a href="${pageContext.request.contextPath }/springmvc/testModelAndView">testModelAndView</a>
  <br><br>
  
  <a href="${pageContext.request.contextPath }/springmvc/testMap">testMap</a>
  <br><br>
  
  <a href="${pageContext.request.contextPath }/springmvc/testSessionAttributes">testSessionAttributes</a>
  <br><br>
  

  <a href="${pageContext.request.contextPath }/springmvc/testRedirect">Test Redirect</a>
  <br><br>
  
  <a href="${pageContext.request.contextPath }/springmvc/nameView">Test nameView</a>
  <br><br>

  <br><br>
  <strong>testModelAttribute</strong>
  <form action="${pageContext.request.contextPath }/springmvc/testModelAttribute" method="post">
        <input type="hidden" name="id" value="1">
		username: <input type="text" name="username" value="abc"/>
		<br>
		password: <input type="password" name="password" value="123456"/>
		<br>
		email: <input type="text" name="email" value="abc@cba.com"/>
		<br>
		age: <input type="text" name="age" value="20"/>
		<br>
		city: <input type="text" name="address.city" value="XuChang"/>
		<br>
		province: <input type="text" name="address.province" value="HeNan"/>
		<br>
		birth:<input type="text" name="birth" value="1993/07/20">
		<br>
		salary:<input type="text" name="salary" value="1,234,567.8">
		<br>
		<input type="submit" value="Submit"/>
  </form>
  <br><br>
  
  <strong>testConverter</strong>
  <form action="${pageContext.request.contextPath }/springmvc/testConverter" method="post">
     <input type="text" name="user" value="1001---Tom---123456---123@123.com---20" readonly="readonly">
     <input type="submit" value=testConverter/>
  </form>
  <br><br>
  
  <strong>testValidate</strong>
  <form action="${pageContext.request.contextPath }/springmvc/testValidate" method="post">
        <input type="hidden" name="id" value="1">
		username: <input type="text" name="username" value="abc"/>
		<br>
		password: <input type="password" name="password" value="123456"/>
		<br>
		email: <input type="text" name="email" value="abc@cba.com"/>
		<br>
		age: <input type="text" name="age" value="20"/>
		<br>
		city: <input type="text" name="address.city" value="XuChang"/>
		<br>
		province: <input type="text" name="address.province" value="HeNan"/>
		<br>
		birth:<input type="text" name="birth" value="1993/07/20">
		<br>
		salary:<input type="text" name="salary" value="1,234,567.8">
		<br>
		<input type="submit" value="Submit"/>
  </form>
  <br><br>
  <a id="testJson" href="${pageContext.request.contextPath }/springmvc/testJson">testJson</a>
  <br><br>
  <strong>testHttpMessageConverter</strong>
  <form action="${pageContext.request.contextPath }/springmvc/testHttpMessageConverter" method="post" enctype="multipart/form-data">
     Desc:<input type="text" name="desc"><br/>
     File:<input type="file" name="txt"><br/>
     <input type="submit" value="Submit">
  </form>
  <br><br>
  <a id="testJson" href="${pageContext.request.contextPath }/springmvc/testDownload">testDownload</a>
  <br><br>
  <a href="${pageContext.request.contextPath }/springmvc/testI18n">testI18n</a>
  <br><br>
  
  
  <strong>testFileUpload</strong>
  <form action="${pageContext.request.contextPath }/springmvc/testFileUpload" method="post" enctype="multipart/form-data">
     Desc:<input type="text" name="desc"><br/>
     File:<input type="file" name="file"><br/>
     <input type="submit" value="Submit">
  </form>	
  <br><br>
  <a href="${pageContext.request.contextPath }/springmvc/testExceptionHandlerExceptionResolver">tesExceptionHandlerExceptionResolver(异常处理方式1)</a>
  <br><br>
  <a href="${pageContext.request.contextPath }/springmvc/testResponseStatusExceptionResolver?i=10">testResponseStatusExceptionResolver(异常处理方式2)</a>
  <br><br>
  <a href="${pageContext.request.contextPath }/springmvc/testSimpleMappingExceptionResolver?i=10">testSimpleMappingExceptionResolver(异常处理方式3)</a>
  <br><br>
	
  <hr>
  <a href="${pageContext.request.contextPath }/curd/emps">List All Employees</a>
  <br><br>
  
</body>
</html>