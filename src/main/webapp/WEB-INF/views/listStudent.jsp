<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: 52840
  Date: 2018/6/26 0026
  Time: 14:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java"
         pageEncoding="utf-8" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<table align="center" border="1" cellspacing="0">
    <tr>
        <td>id</td>
        <td>编号</td>
        <td>姓名</td>
        <td>年龄</td>
        <td>性别</td>
        <td>删除</td>
    </tr>
    <c:forEach items="${students}" var="s" varStatus="st">
        <tr>
            <td>${s.id}</td>
            <td>${s.studentID}</td>
            <td>${s.name}</td>
            <td>${s.age}</td>
            <td>${s.sex}</td>
            <td>
                <form action="${pageContext.request.contextPath}/onestudent" method="post">
                    <input type="hidden" name="id" value="${s.id}">
                    <input type="submit" value="删除">
                </form>
            </td>
        </tr>
    </c:forEach>
    <tr>
        <td>
            <a href="${pageContext.request.contextPath}/student">
                点击新增学员
            </a>
        </td>

    </tr>
</table>


</body>
</html>
