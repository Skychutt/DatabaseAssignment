<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.COMP2013J.assignment.entity.TeacherPublicProfile" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"/>
    <title>教师通讯录</title>
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
                    <div class="col-md-12">
                        <div class="card">
                            <div class="card-header"><h4>教师通讯录</h4></div>
                            <div class="card-body">
                                <p class="text-muted" style="margin-bottom:12px;">以下为可公开的教师信息，便于了解任课教师。密码等敏感信息不会展示。</p>
                                <table class="table table-bordered table-hover">
                                    <thead>
                                    <tr>
                                        <th>工号</th>
                                        <th>姓名</th>
                                        <th>开设课程数</th>
                                        <th>开设课程</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <%
                                        List<TeacherPublicProfile> teacherProfiles =
                                                (List<TeacherPublicProfile>) request.getAttribute("teacherProfiles");
                                        if (teacherProfiles == null || teacherProfiles.isEmpty()) {
                                    %>
                                    <tr><td colspan="4" class="text-center text-muted">暂无教师信息。</td></tr>
                                    <%
                                        } else {
                                            for (TeacherPublicProfile tp : teacherProfiles) {
                                    %>
                                    <tr>
                                        <td><%= tp.getTno() == null ? "" : tp.getTno() %></td>
                                        <td><%= tp.getTname() == null || tp.getTname().isEmpty() ? "—" : tp.getTname() %></td>
                                        <td><%= tp.getCourseCount() %></td>
                                        <td><%= tp.getCourseNames() == null || tp.getCourseNames().isEmpty() ? "暂无课程" : tp.getCourseNames() %></td>
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
<script src="${pageContext.request.contextPath}/assets/js/main.min.js"></script>
</body>
</html>
