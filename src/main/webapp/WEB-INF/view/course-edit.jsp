<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.COMP2013J.assignment.entity.Course" %>
<%
    Course course = (Course) request.getAttribute("course");
    Boolean courseAdminMode = Boolean.TRUE.equals(request.getAttribute("courseAdminMode"));
    java.text.SimpleDateFormat df = new java.text.SimpleDateFormat("yyyy-MM-dd");
%>
<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"/>
    <title>编辑课程</title>
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
                    <div class="col-md-10">
                        <div class="card">
                            <div class="card-header"><h4>编辑课程</h4></div>
                            <div class="card-body">
                                <form id="courseForm" onsubmit="return false;">
                                    <div class="row">
                                        <div class="col-md-4 form-group">
                                            <label>课程号</label>
                                            <input class="form-control" type="text" id="cno" value="<%= course == null ? "" : course.getCno() %>" readonly>
                                        </div>
                                        <div class="col-md-4 form-group">
                                            <label>教师工号</label>
                                            <input class="form-control" type="text" id="tno" value="<%= course == null || course.getTno() == null ? "" : course.getTno() %>" <%= courseAdminMode ? "" : "readonly" %>>
                                        </div>
                                        <div class="col-md-4 form-group">
                                            <label>课程名</label>
                                            <input class="form-control" type="text" id="cname" value="<%= course == null || course.getCname() == null ? "" : course.getCname() %>">
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-md-4 form-group">
                                            <label>选课开始</label>
                                            <input class="form-control" type="date" id="begindate" value="<%= course == null || course.getBegindate() == null ? "" : df.format(course.getBegindate()) %>">
                                        </div>
                                        <div class="col-md-4 form-group">
                                            <label>选课结束</label>
                                            <input class="form-control" type="date" id="enddate" value="<%= course == null || course.getEnddate() == null ? "" : df.format(course.getEnddate()) %>">
                                        </div>
                                        <div class="col-md-4 form-group">
                                            <label>学分</label>
                                            <input class="form-control" type="text" id="credits" value="<%= course == null || course.getCredits() == null ? "" : course.getCredits() %>">
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-md-4 form-group">
                                            <label>人数上限</label>
                                            <input class="form-control" type="number" id="maximum" value="<%= course == null || course.getMaximum() == null ? "" : course.getMaximum() %>">
                                        </div>
                                        <% if (courseAdminMode) { %>
                                        <div class="col-md-4 form-group">
                                            <label>已选人数</label>
                                            <input class="form-control" type="number" id="count" value="<%= course == null || course.getCount() == null ? "" : course.getCount() %>">
                                        </div>
                                        <% } else { %>
                                        <div class="col-md-4 form-group">
                                            <label>已选人数</label>
                                            <input class="form-control" type="text" readonly value="<%= course == null || course.getCount() == null ? "0" : course.getCount() %>（系统维护）">
                                        </div>
                                        <% } %>
                                    </div>
                                    <div class="form-group">
                                        <label>课程内容</label>
                                        <textarea class="form-control" id="content" rows="3"><%= course == null || course.getContent() == null ? "" : course.getContent() %></textarea>
                                    </div>
                                    <div class="form-group">
                                        <button class="btn btn-primary" type="button" onclick="submitForm()">保存</button>
                                        <button class="btn btn-default" type="button" onclick="location.href='${pageContext.request.contextPath}/course'">返回列表</button>
                                    </div>
                                </form>
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
<script type="text/javascript">
    function submitForm() {
        $.post("${pageContext.request.contextPath}/course", {
            r: "edit",
            cno: $("#cno").val(),
            tno: $("#tno").val(),
            cname: $("#cname").val(),
            begindate: $("#begindate").val(),
            enddate: $("#enddate").val(),
            credits: $("#credits").val(),
            maximum: $("#maximum").val(),
            count: $("#count").length ? $("#count").val() : undefined,
            content: $("#content").val()
        }, function (res) {
            if (res.success) {
                alert("保存成功");
                location.href = "${pageContext.request.contextPath}/course";
            } else {
                alert(res.message || "保存失败");
            }
        }, "json");
    }
</script>
</body>
</html>
