<%--
  Created by IntelliJ IDEA.
  User: 52840
  Date: 2018/6/27 0027
  Time: 10:49
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

    <form action="${pageContext.request.contextPath}/student" method="post">
        <tr>
            <td>学生编号</td>
            <td>
                <label>
                    <input type="text" name="studentID" value="520"/>
                </label>
            </td>
        </tr>
        <tr>
            <td>学生姓名</td>
            <td>
                <label>
                    <input type="text" name="name" value="希尔瓦娜斯"/>
                </label>
            </td>
        </tr>
        <tr>
            <td>学生年龄</td>
            <td>
                <label>
                    <input type="text" name="age" value="18"/>
                </label>
            </td>
        </tr>
        <tr>
            <td>学生性别</td>
            <td>
                <label>
                    <input type="text" name="sex" value="女">
                </label>
            </td>
        </tr>
        <tr>
            <td>确认</td>
            <td>
                <label>
                    <input type="submit"value="提交" >
                </label>
            </td>
        </tr>

    </form>


</table>
</body>
</html>
