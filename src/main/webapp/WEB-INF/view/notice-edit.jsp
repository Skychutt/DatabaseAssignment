<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.COMP2013J.assignment.entity.Notice" %>
<%
    Notice notice = (Notice) request.getAttribute("notice");
    String createTimeVal = "";
    if (notice != null && notice.getCreateTime() != null) {
        java.text.SimpleDateFormat df = new java.text.SimpleDateFormat("yyyy-MM-dd");
        createTimeVal = df.format(notice.getCreateTime());
    }
%>
<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"/>
    <title>编辑公告</title>
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
                            <div class="card-header"><h4>编辑公告</h4></div>
                            <div class="card-body">
                                <div class="form-group">
                                    <label>编号</label>
                                    <input class="form-control" type="text" id="id" value="<%= notice == null ? "" : notice.getId() %>" readonly>
                                </div>
                                <div class="form-group">
                                    <label>发布人</label>
                                    <input class="form-control" type="text" value="<%= notice == null || notice.getPublisher() == null ? "" : notice.getPublisher() %>" readonly>
                                </div>
                                <div class="form-group">
                                    <label>发布日期</label>
                                    <input class="form-control" type="date" id="createTime" value="<%= createTimeVal %>">
                                </div>
                                <div class="form-group">
                                    <label>公告内容</label>
                                    <textarea class="form-control" id="content" rows="8"><%= notice == null || notice.getContent() == null ? "" : notice.getContent() %></textarea>
                                </div>
                                <div class="form-group">
                                    <button class="btn btn-primary" type="button" onclick="submitForm()">保存</button>
                                    <button class="btn btn-default" type="button" onclick="location.href='${pageContext.request.contextPath}/notice'">返回列表</button>
                                </div>
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
<script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/main.min.js"></script>
<script type="text/javascript">
    function submitForm() {
        $.post("${pageContext.request.contextPath}/notice", {
            r: "edit",
            id: $("#id").val(),
            createTime: $("#createTime").val(),
            content: $("#content").val()
        }, function (res) {
            if (res.success) {
                alert("保存成功");
                location.href = "${pageContext.request.contextPath}/notice";
            } else {
                alert(res.message || "保存失败");
            }
        }, "json");
    }
</script>
</body>
</html>
