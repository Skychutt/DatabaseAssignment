<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"/>
    <title>发布公告</title>
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
                            <div class="card-header"><h4>发布公告</h4></div>
                            <div class="card-body">
                                <p class="text-muted">发布人将自动记录为当前登录的管理员账号。</p>
                                <form id="noticeForm" onsubmit="return false;">
                                    <div class="form-group">
                                        <label>发布日期</label>
                                        <input class="form-control" type="date" id="createTime">
                                    </div>
                                    <div class="form-group">
                                        <label>公告内容</label>
                                        <textarea class="form-control" id="content" rows="8" placeholder="请输入公告内容"></textarea>
                                    </div>
                                    <div class="form-group">
                                        <button class="btn btn-primary" type="button" onclick="submitForm()">发布</button>
                                        <button class="btn btn-default" type="button" onclick="location.href='${pageContext.request.contextPath}/notice'">返回列表</button>
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
<script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/main.min.js"></script>
<script type="text/javascript">
    (function () {
        var d = new Date();
        var m = (d.getMonth() + 1).toString().padStart(2, "0");
        var day = d.getDate().toString().padStart(2, "0");
        document.getElementById("createTime").value = d.getFullYear() + "-" + m + "-" + day;
    })();
    function submitForm() {
        $.post("${pageContext.request.contextPath}/notice", {
            r: "add",
            createTime: $("#createTime").val(),
            content: $("#content").val()
        }, function (res) {
            if (res.success) {
                alert("发布成功");
                location.href = "${pageContext.request.contextPath}/notice";
            } else {
                alert(res.message || "发布失败");
            }
        }, "json");
    }
</script>
</body>
</html>
