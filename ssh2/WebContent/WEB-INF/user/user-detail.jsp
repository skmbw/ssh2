<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户详情</title>
</head>
<body>
<table style="border:1px #e8e8e8 solid" border="1" cellspacing="1" cellpadding="1">
<tr>
<td>用户ID</td>
<td>${model.id}</td>
</tr>
<tr>
<td>用户名</td>
<td>${model.userName}</td>
</tr>
<tr>
<td>用户账户</td>
<td>${model.userAccount}</td>
</tr>
<tr>
<td>公司</td>
<td>${model.company}</td>
</tr>
<tr>
<td>用户电话</td>
<td>${model.telephone}</td>
</tr>
<tr>
<td>注册时间</td>
<td><fmt:formatDate value="${model.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
</tr>
<tr>
<td>权限</td>
<td>${model.superAdmin}</td>
</tr>
<tr>
<td>状态</td>
<td><c:if test="${model.state == 1}">正常</c:if><c:if test="${model.state == 0}">禁用</c:if></td>
</tr>
</table>
<a href="${ctx}/user/initial.htm">返回列表页</a>
</body>
</html>