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
<title>用户列表</title>
<script type="text/javascript" src="${ctx}/js/jquery-1.8.3.min.js"></script>
<script type="text/javascript">
	function deleteUser(id) {
		var del = confirm('你确定要删除该用户？');
		if (del) {
			$.ajax({
	            type:"post",
	            dataType:"json",
	            url: '${ctx}/user/delete.htm?id=' + id,
	            success: function(msg){
	                alert("删除成功");
	                window.location.reload();
	            },
	            error: function (msg) {
	                alert(msg.responseText);
	            }
	        });
		}
	}
</script>
</head>
<body>
<table>
	<tr><td><a href="${ctx}/user/add.htm">新增用户</a></td><td></td><td></td></tr>
</table>
<table style="border:1px #e8e8e8 solid" border="1" cellspacing="1" cellpadding="1">
<tr>
<td>用户ID</td>
<td>用户名</td>
<td>用户账户</td>
<td>用户电话</td>
<td>注册时间</td>
<td>权限</td>
<td>操作</td>
</tr>
<c:forEach items="${listResult}" var="user" varStatus="i">
<tr style="${i.count%2==0?'background:#f3f3f3':''}">
<td>${user.id}</td>
<td>${user.userName}</td>
<td>${user.userAccount}</td>
<td>${user.telephone}</td>
<td><fmt:formatDate value="${user.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/> </td>
<td>${user.superAdmin}</td>
<td><a href="${ctx}/user/detail.htm?id=${user.id}">详情</a> <a href="${ctx}/user/edit.htm?id=${user.id}">编辑</a> <a href="javascript:void(0);" onclick="javascript:deleteUser('${user.id}');">删除</a></td>
</tr>

</c:forEach>
</table>
</body>
</html>