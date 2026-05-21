<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.COMP2013J.assignment.entity.Admin" %>
<%@ page import="com.COMP2013J.assignment.entity.Student" %>
<%@ page import="com.COMP2013J.assignment.entity.Teacher" %>
<%
    Student student = (Student) request.getAttribute("student");
    Admin admin = (Admin) request.getAttribute("admin");
    Teacher teacher = (Teacher) request.getAttribute("teacher");
%>
<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"/>
    <title>个人信息</title>
    <link rel="icon" href="${pageContext.request.contextPath}/favicon.ico" type="image/x-icon">
    <link href="${pageContext.request.contextPath}/assets/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/assets/css/materialdesignicons.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/assets/css/style.min.css" rel="stylesheet">
</head>
<body>
<div class="lyear-layout-web">
    <div class="lyear-layout-container">
        <jsp:include page="_aside_header.jsp"/>
        <main class="lyear-layout-content">
            <div class="container-fluid">
                <div class="row">
                    <div class="col-md-8">
                        <div class="card">
                            <div class="card-header"><h4>个人信息</h4></div>
                            <div class="card-body">
                                <%
                                    if (student != null) {
                                %>
                                <table class="table table-bordered">
                                    <tr><th>角色</th><td>学生</td></tr>
                                    <tr><th>学号</th><td><%= student.getSno() %></td></tr>
                                    <tr><th>姓名</th><td><%= student.getName() %></td></tr>
                                    <tr><th>性别</th><td><%= "m".equals(student.getGender()) ? "男" : ("w".equals(student.getGender()) ? "女" : "") %></td></tr>
                                    <tr><th>年龄</th><td><%= student.getAge() == null ? "" : student.getAge() %></td></tr>
                                    <tr><th>电话</th><td><%= student.getTele() == null ? "" : student.getTele() %></td></tr>
                                    <tr><th>入学时间</th><td><%= student.getEnterdate() == null ? "" : new java.text.SimpleDateFormat("yyyy-MM-dd").format(student.getEnterdate()) %></td></tr>
                                    <tr><th>地址</th><td><%= student.getAddress() == null ? "" : student.getAddress() %></td></tr>
                                    <tr><th>班级编号</th><td><%= student.getClazzno() == null ? "" : student.getClazzno() %></td></tr>
                                </table>
                                <div class="text-right">
                                    <a class="btn btn-default" href="${pageContext.request.contextPath}/editPassword">修改密码</a>
                                </div>
                                <%
                                    } else if (teacher != null) {
                                %>
                                <table class="table table-bordered">
                                    <tr><th>角色</th><td>教师</td></tr>
                                    <tr><th>工号</th><td><%= teacher.getTno() %></td></tr>
                                    <tr><th>姓名</th><td><%= teacher.getTname() == null ? "" : teacher.getTname() %></td></tr>
                                </table>
                                <div class="text-right">
                                    <a class="btn btn-default" href="${pageContext.request.contextPath}/editPassword">修改密码</a>
                                </div>
                                <%
                                    } else if (admin != null) {
                                %>
                                <table class="table table-bordered">
                                    <tr><th>角色</th><td>管理员</td></tr>
                                    <tr><th>用户名</th><td><%= admin.getUsername() %></td></tr>
                                </table>
                                <div class="text-right">
                                    <a class="btn btn-default" href="${pageContext.request.contextPath}/editPassword">修改密码</a>
                                </div>
                                <%
                                    } else {
                                %>
                                <div class="alert alert-danger" role="alert">未获取到用户信息。</div>
                                <%
                                    }
                                %>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </main>
    </div>
</div>

<script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/perfect-scrollbar.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/main.min.js"></script>
</body>
</html>

