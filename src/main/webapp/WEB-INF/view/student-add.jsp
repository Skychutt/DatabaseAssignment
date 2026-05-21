<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" />
    <title>新增学生</title>
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
                            <div class="card-header"><h4>新增学生</h4></div>
                            <div class="card-body">
                                <form id="studentForm" onsubmit="return false;">
                                    <div class="row">
                                        <div class="col-md-6 form-group">
                                            <label>学号</label>
                                            <input class="form-control" type="text" id="sno" placeholder="请输入学号">
                                        </div>
                                        <div class="col-md-6 form-group">
                                            <label>密码</label>
                                            <input class="form-control" type="text" id="password" placeholder="请输入密码">
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-md-6 form-group">
                                            <label>姓名</label>
                                            <input class="form-control" type="text" id="name" placeholder="请输入姓名">
                                        </div>
                                        <div class="col-md-6 form-group">
                                            <label>电话</label>
                                            <input class="form-control" type="text" id="tele" placeholder="请输入电话">
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-md-6 form-group">
                                            <label>入学日期</label>
                                            <input class="form-control" type="date" id="enterdate">
                                        </div>
                                        <div class="col-md-6 form-group">
                                            <label>年龄</label>
                                            <input class="form-control" type="number" id="age" placeholder="请输入年龄">
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-md-6 form-group">
                                            <label>性别</label>
                                            <select class="form-control" id="gender">
                                                <option value="">请选择</option>
                                                <option value="m">男</option>
                                                <option value="w">女</option>
                                            </select>
                                        </div>
                                        <div class="col-md-6 form-group">
                                            <label>班级编号</label>
                                            <input class="form-control" type="text" id="clazzno" placeholder="请输入班级编号">
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label>地址</label>
                                        <input class="form-control" type="text" id="address" placeholder="请输入地址">
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
            r: "add",
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
