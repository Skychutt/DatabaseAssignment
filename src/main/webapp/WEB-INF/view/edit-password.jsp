<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"/>
    <title>修改密码</title>
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
                            <div class="card-header"><h4>修改密码</h4></div>
                            <div class="card-body">
                                <div class="form-group">
                                    <label>旧密码</label>
                                    <input class="form-control" type="password" id="oldPassword" placeholder="请输入旧密码">
                                </div>
                                <div class="form-group">
                                    <label>新密码</label>
                                    <input class="form-control" type="password" id="newPassword" placeholder="请输入新密码">
                                </div>
                                <div class="form-group">
                                    <button class="btn btn-primary" type="button" onclick="submitPassword()">保存</button>
                                    <button class="btn btn-default" type="button" onclick="location.href='${pageContext.request.contextPath}/profile'">返回</button>
                                </div>
                                <div id="msg" class="text-danger"></div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </main>
    </div>
</div>

<script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/jquery.min.js"></script>
<script type="text/javascript">
    function submitPassword() {
        $("#msg").text("");
        $.ajax({
            type: "post",
            url: "${pageContext.request.contextPath}/editPassword",
            dataType: "json",
            data: {
                oldPassword: $("#oldPassword").val(),
                newPassword: $("#newPassword").val()
            },
            success: function (res) {
                if (res.success) {
                    alert("修改成功");
                    location.href = "${pageContext.request.contextPath}/profile";
                } else {
                    $("#msg").text(res.message || "修改失败");
                }
            },
            error: function () {
                $("#msg").text("请求服务器失败");
            }
        });
    }
</script>
</body>
</html>

