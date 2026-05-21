<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.COMP2013J.assignment.entity.StuCourseView" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"/>
    <title>选课管理</title>
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
                <div class="card">
                    <div class="card-header"><h4>全部选课记录</h4></div>
                    <div class="card-body">
                        <%
                            List<StuCourseView> rows = (List<StuCourseView>) request.getAttribute("allRows");
                            java.text.SimpleDateFormat df = new java.text.SimpleDateFormat("yyyy-MM-dd");
                            int n = rows == null ? 0 : rows.size();
                        %>
                        <p class="text-muted">共 <strong><%= n %></strong> 条记录。可查看成绩、修改成绩与评价，或移除异常选课（移除后会同步减少课程已选人数）。</p>
                        <div class="table-responsive">
                            <table class="table table-hover table-bordered">
                                <thead>
                                <tr>
                                    <th>课程号</th>
                                    <th>课程名</th>
                                    <th>学号</th>
                                    <th>姓名</th>
                                    <th>选课时间</th>
                                    <th>成绩</th>
                                    <th>评价</th>
                                    <th width="200">操作</th>
                                </tr>
                                </thead>
                                <tbody>
                                <% if (rows == null || rows.isEmpty()) { %>
                                <tr><td colspan="8" class="text-center text-muted">暂无选课数据</td></tr>
                                <% } else { for (StuCourseView v : rows) { %>
                                <tr data-cno="<%= v.getCno() %>" data-sno="<%= v.getSno() %>">
                                    <td><%= v.getCno() %></td>
                                    <td><%= v.getCname() == null ? "" : v.getCname() %></td>
                                    <td><%= v.getSno() %></td>
                                    <td><%= v.getStudentName() == null ? "" : v.getStudentName() %></td>
                                    <td><%= v.getChosetime() == null ? "" : df.format(v.getChosetime()) %></td>
                                    <td><input type="text" class="form-control input-sm ad-score" style="width:88px;" value="<%= v.getScore() == null ? "" : v.getScore() %>"></td>
                                    <td><input type="text" class="form-control input-sm ad-eval" value="<%= v.getEvaluation() == null ? "" : v.getEvaluation() %>"></td>
                                    <td>
                                        <button type="button" class="btn btn-xs btn-primary" onclick="saveGrade(this)">保存成绩</button>
                                        <button type="button" class="btn btn-xs btn-danger" onclick="adminDrop('<%= v.getCno() %>','<%= v.getSno() %>')">移除</button>
                                    </td>
                                </tr>
                                <% } } %>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </main>
    </div>
</div>
<script src="${pageContext.request.contextPath}/assets/js/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/assets/js/bootstrap.min.js"></script>
<script>
    function saveGrade(btn) {
        var tr = $(btn).closest("tr");
        $.post("${pageContext.request.contextPath}/stuCourse", {
            r: "grade",
            cno: tr.data("cno"),
            sno: tr.data("sno"),
            score: tr.find(".ad-score").val(),
            evaluation: tr.find(".ad-eval").val()
        }, function (res) {
            if (res.success) alert("保存成功"); else alert(res.message || "保存失败");
        }, "json");
    }
    function adminDrop(cno, sno) {
        if (!confirm("确定移除选课记录 " + cno + " / " + sno + " ？")) return;
        $.post("${pageContext.request.contextPath}/stuCourse", {r: "adminDrop", cno: cno, sno: sno}, function (res) {
            if (res.success) {
                alert("已移除");
                location.reload();
            } else {
                alert(res.message || "失败");
            }
        }, "json");
    }
</script>
</body>
</html>
