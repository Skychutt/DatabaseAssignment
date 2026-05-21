<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.COMP2013J.assignment.entity.Teacher" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"/>
    <title>通讯录</title>
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
                    <div class="col-md-6">
                        <div class="card">
                            <div class="card-header"><h4>管理员账号</h4></div>
                            <div class="card-body">
                                <ul class="list-group">
                                    <%
                                        List<String> names = (List<String>) request.getAttribute("adminNames");
                                        if (names != null) {
                                            for (String u : names) {
                                    %>
                                    <li class="list-group-item"><%= u %></li>
                                    <% } } %>
                                </ul>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div class="card">
                            <div class="card-header"><h4>教师工号与姓名</h4></div>
                            <div class="card-body">
                                <table class="table table-bordered">
                                    <thead><tr><th>工号</th><th>姓名</th></tr></thead>
                                    <tbody>
                                    <%
                                        List<Teacher> teachers = (List<Teacher>) request.getAttribute("teachers");
                                        if (teachers != null) {
                                            for (Teacher t : teachers) {
                                    %>
                                    <tr>
                                        <td><%= t.getTno() %></td>
                                        <td><%= t.getTname() == null ? "" : t.getTname() %></td>
                                    </tr>
                                    <% } } %>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </main>
    </div>
</div>
<script src="${pageContext.request.contextPath}/assets/js/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/assets/js/bootstrap.min.js"></script>
</body>
</html>
