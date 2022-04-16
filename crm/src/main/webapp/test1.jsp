<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2022/4/13
  Time: 18:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
$.ajax({
url:"",
data:{

},
type:"",
dataType:"json",
success:function (data){

}
})

//创建时间：当前系统时间
String createTime = DateTimeUtil.getSysTime();
//创建人：当前登录用户
String createBy = ((User)request.getSession().getAttribute("user")).getName();
</body>
</html>
