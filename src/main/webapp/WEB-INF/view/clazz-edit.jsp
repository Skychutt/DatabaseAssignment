<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.COMP2013J.assignment.entity.Clazz" %>
<%
    Clazz clazz = (Clazz) request.getAttribute("clazz");
%>
<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" />
    <title>编辑班级</title>
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
                            <div class="card-header"><h4>编辑班级</h4></div>
                            <div class="card-body">
                                <form id="clazzForm" onsubmit="return false;">
                                    <div class="form-group">
                                        <label>班级编号</label>
                                        <input class="form-control" type="text" name="clazzno" id="clazzno"
                                               value="<%= clazz == null ? "" : clazz.getClazzno() %>" readonly>
                                    </div>
                                    <div class="form-group">
                                        <label>班级名称</label>
                                        <input class="form-control" type="text" name="name" id="name"
                                               value="<%= clazz == null ? "" : clazz.getName() %>" placeholder="请输入班级名称">
                                    </div>
                                    <div class="form-group">
                                        <button class="btn btn-primary" type="button" onclick="submitForm()">保存</button>
                                        <button class="btn btn-default" type="button" onclick="location.href='${pageContext.request.contextPath}/clazz'">返回列表</button>
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
        $.post("${pageContext.request.contextPath}/clazz", {
            r: "edit",
            clazzno: $("#clazzno").val(),
            name: $("#name").val()
        }, function (res) {
            if (res.success) {
                alert("保存成功");
                location.href = "${pageContext.request.contextPath}/clazz";
            } else {
                alert(res.message || "保存失败");
            }
        }, "json");
    }
</script>
</body>
</html>
