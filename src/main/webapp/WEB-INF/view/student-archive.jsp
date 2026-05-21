<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.COMP2013J.assignment.entity.StudentArchive" %>
<%
    StudentArchive archive = (StudentArchive) request.getAttribute("archive");
    Boolean hasArchive = Boolean.TRUE.equals(request.getAttribute("hasArchive"));
    java.text.SimpleDateFormat dtf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String statusText = "";
    if (archive != null && archive.getArchiveStatus() != null) {
        statusText = archive.getArchiveStatus() == 1 ? "已提交" : "草稿";
    }
%>
<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"/>
    <title>个人档案</title>
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
                            <div class="card-header"><h4>个人档案</h4></div>
                            <div class="card-body">
                                <% if (!hasArchive) { %>
                                <div class="alert alert-info" role="alert">
                                    您尚未填写个人档案。点击下方按钮创建后，可进行查看、修改与删除。
                                </div>
                                <div class="text-center" style="padding: 24px 0;">
                                    <a class="btn btn-primary btn-lg" href="${pageContext.request.contextPath}/studentArchive?r=add">填写个人档案</a>
                                </div>
                                <% } else { %>
                                <table class="table table-bordered">
                                    <tr><th width="140">档案编号</th><td><%= archive.getArchiveId() %></td></tr>
                                    <tr><th>学号</th><td><%= archive.getSno() %></td></tr>
                                    <tr><th>身份证号</th><td><%= archive.getIdCard() %></td></tr>
                                    <tr><th>民族</th><td><%= archive.getNationality() == null ? "" : archive.getNationality() %></td></tr>
                                    <tr><th>籍贯</th><td><%= archive.getBirthPlace() == null ? "" : archive.getBirthPlace() %></td></tr>
                                    <tr><th>政治面貌</th><td><%= archive.getPoliticalStatus() == null ? "" : archive.getPoliticalStatus() %></td></tr>
                                    <tr><th>入学类别</th><td><%= archive.getEnrollmentType() == null ? "" : archive.getEnrollmentType() %></td></tr>
                                    <tr><th>专业</th><td><%= archive.getMajor() == null ? "" : archive.getMajor() %></td></tr>
                                    <tr><th>班级编号</th><td><%= archive.getClazzno() == null ? "" : archive.getClazzno() %></td></tr>
                                    <tr><th>毕业中学</th><td><%= archive.getGraduationSchool() == null ? "" : archive.getGraduationSchool() %></td></tr>
                                    <tr><th>档案状态</th><td><%= statusText %></td></tr>
                                    <tr><th>监护人姓名</th><td><%= archive.getGuardianName() == null ? "" : archive.getGuardianName() %></td></tr>
                                    <tr><th>监护人电话</th><td><%= archive.getGuardianPhone() == null ? "" : archive.getGuardianPhone() %></td></tr>
                                    <tr><th>创建时间</th><td><%= archive.getCreateTime() == null ? "" : dtf.format(archive.getCreateTime()) %></td></tr>
                                    <tr><th>更新时间</th><td><%= archive.getUpdateTime() == null ? "" : dtf.format(archive.getUpdateTime()) %></td></tr>
                                </table>
                                <div class="text-right">
                                    <a class="btn btn-primary" href="${pageContext.request.contextPath}/studentArchive?r=edit">修改档案</a>
                                    <button type="button" class="btn btn-danger" onclick="deleteArchive()">删除档案</button>
                                </div>
                                <% } %>
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
<script>
    function deleteArchive() {
        if (!confirm("确定删除个人档案吗？删除后可重新填写。")) return;
        $.post("${pageContext.request.contextPath}/studentArchive", {
            r: "del",
            archiveId: "<%= archive == null ? "" : archive.getArchiveId() %>"
        }, function (res) {
            if (res.success) {
                alert("删除成功");
                location.href = "${pageContext.request.contextPath}/studentArchive";
            } else {
                alert(res.message || "删除失败");
            }
        }, "json");
    }
</script>
</body>
</html>
