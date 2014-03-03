<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>新增用户</title>
</head>
<body>
<form action="${ctx}/user/doAdd.htm" method="post">
用户名：
<input name="userName" type="text">
用户账户：
<input name="userAccount" type="text"><Br/>
公司：
<input name="company" type="text">
密码：
<input name="password" type="password"><Br/>
手机：
<input name="mobilePhone" type="text">
电话：
<input name="telephone" type="text"><Br/>
用户权限：
<select name="superAdmin">
	<option value="admin">管理员</option>
	<option value="user">普通用户</option>	
</select>
用户状态：
<select name="state">
	<option value="1">正常</option>	
	<option value="0">禁用</option>	
</select>
<Br/>
<button type="submit" name="sub">提交</button>
</form>
<a href="${ctx}/user/initial.htm">返回列表页</a>
</body>
</html>