<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.COMP2013J.assignment.entity.Student" %>
<%
    Student student = (Student) request.getAttribute("student");
%>
<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" />
    <title>编辑学生</title>
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
                            <div class="card-header"><h4>编辑学生</h4></div>
                            <div class="card-body">
                                <form id="studentForm" onsubmit="return false;">
                                    <div class="row">
                                        <div class="col-md-6 form-group">
                                            <label>学号</label>
                                            <input class="form-control" type="text" id="sno" value="<%= student == null ? "" : student.getSno() %>" readonly>
                                        </div>
                                        <div class="col-md-6 form-group">
                                            <label>密码</label>
                                            <input class="form-control" type="password" id="password" value="" placeholder="留空则不修改密码">
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-md-6 form-group">
                                            <label>姓名</label>
                                            <input class="form-control" type="text" id="name" value="<%= student == null || student.getName() == null ? "" : student.getName() %>">
                                        </div>
                                        <div class="col-md-6 form-group">
                                            <label>电话</label>
                                            <input class="form-control" type="text" id="tele" value="<%= student == null || student.getTele() == null ? "" : student.getTele() %>">
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-md-6 form-group">
                                            <label>入学日期</label>
                                            <input class="form-control" type="date" id="enterdate" value="<%= student == null || student.getEnterdate() == null ? "" : new java.text.SimpleDateFormat("yyyy-MM-dd").format(student.getEnterdate()) %>">
                                        </div>
                                        <div class="col-md-6 form-group">
                                            <label>年龄</label>
                                            <input class="form-control" type="number" id="age" value="<%= student == null || student.getAge() == null ? "" : student.getAge() %>">
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-md-6 form-group">
                                            <label>性别</label>
                                            <select class="form-control" id="gender">
                                                <option value="">请选择</option>
                                                <option value="m" <%= student != null && "m".equals(student.getGender()) ? "selected" : "" %>>男</option>
                                                <option value="w" <%= student != null && "w".equals(student.getGender()) ? "selected" : "" %>>女</option>
                                            </select>
                                        </div>
                                        <div class="col-md-6 form-group">
                                            <label>班级编号</label>
                                            <input class="form-control" type="text" id="clazzno" value="<%= student == null || student.getClazzno() == null ? "" : student.getClazzno() %>">
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label>地址</label>
                                        <input class="form-control" type="text" id="address" value="<%= student == null || student.getAddress() == null ? "" : student.getAddress() %>">
                                    </div>
                                    <div class="form-group">
                                        <button class="btn btn-primary" type="button" onclick="submitForm()">保存</button>
                                        <button class="btn btn-default" type="button" onclick="location.href='${pageContext.request.contextPath}/student'">返回列表</button>
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
        $.post("${pageContext.request.contextPath}/student", {
            r: "edit",
            sno: $("#sno").val(),
            password: $("#password").val(),
            name: $("#name").val(),
            tele: $("#tele").val(),
            enterdate: $("#enterdate").val(),
            age: $("#age").val(),
            gender: $("#gender").val(),
            address: $("#address").val(),
            clazzno: $("#clazzno").val()
        }, function (res) {
            if (res.success) {
                alert("保存成功");
                location.href = "${pageContext.request.contextPath}/student";
            } else {
                alert(res.message || "保存失败");
            }
        }, "json");
    }
</script>
</body>
</html>
