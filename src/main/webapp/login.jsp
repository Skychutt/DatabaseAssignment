<%--
  Created by IntelliJ IDEA.
  User: Lenovo
  Date: 2026/4/22
  Time: 21:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" />
    <title>登录页面 - 楚天泽的班级管理系统</title>
    <link rel="icon" href="${pageContext.request.contextPath}/assets/favicon.ico" type="image/ico">

    <link href="${pageContext.request.contextPath}/assets/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/assets/css/materialdesignicons.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/assets/css/style.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/assets/css/lang-switcher.css" rel="stylesheet">
    <style>
        .lyear-wrapper {
            position: relative;
        }
        .lyear-login {
            display: flex !important;
            min-height: 100vh;
            align-items: center !important;
            justify-content: center !important;
        }
        .lyear-login:after{
            content: '';
            min-height: inherit;
            font-size: 0;
        }
        .login-center {
            background: #fff;
            min-width: 29.25rem;
            padding: 2.14286em 3.57143em;
            border-radius: 3px;
            margin: 2.85714em;
        }
        .login-header {
            margin-bottom: 1.5rem !important;
        }
        .login-center .has-feedback.feedback-left .form-control {
            padding-left: 38px;
            padding-right: 12px;
        }
        .login-center .has-feedback.feedback-left .form-control-feedback {
            left: 0;
            right: auto;
            width: 38px;
            height: 38px;
            line-height: 38px;
            z-index: 4;
            color: #dcdcdc;
        }
        .login-center .has-feedback.feedback-left.row .form-control-feedback {
            left: 15px;
        }
    </style>
</head>

<body>
<div class="row lyear-wrapper" style="background-image: url(${pageContext.request.contextPath}/assets/images/img-slide-4.jpg); background-size: cover;">
    <div class="lyear-login">
        <div class="login-center"> 
            <div class="login-header text-center">
                <a href="${pageContext.request.contextPath}/"> <img alt="light year admin" src="${pageContext.request.contextPath}/assets/images/img.png"> </a>
            </div>
            <form id="loginForm" autocomplete="off" onsubmit="return false;">
                <div class="form-group has-feedback feedback-left">
                    <input type="text" placeholder="请输入您的用户名" class="form-control" id="username"
                           autocomplete="off" autocapitalize="off" spellcheck="false" value=""/>
                    <span class="mdi mdi-account form-control-feedback" aria-hidden="true"></span>
                </div>
                <div class="form-group has-feedback feedback-left">
                    <input type="password" placeholder="请输入密码" class="form-control" id="password"
                           autocomplete="new-password" value=""/>
                    <span class="mdi mdi-lock form-control-feedback" aria-hidden="true"></span>
                </div>
                <div class="form-group has-feedback feedback-left row">
                    <div class="col-xs-7">
                        <input type="text" id="captcha" class="form-control" placeholder="验证码"
                               autocomplete="off" autocorrect="off" value=""/>
                        <span class="mdi mdi-check-all form-control-feedback" aria-hidden="true"></span>
                    </div>
                    <div class="col-xs-5">
                        <img id="captchaImg" alt="captcha" class="pull-right" style="cursor: pointer; width: 120px; height: 38px; background: #f5f5f5;" onclick="refreshCaptcha()" title="点击刷新">
                    </div>
                </div>
                <div class="form-group" style="text-align: center">
                    <input checked type="radio" name="usertype" value="admin">管理员
                    <input type="radio" name="usertype" value="emp">学生
                    <input type="radio" name="usertype" value="teacher">教师
                </div>

                <div class="form-group">
                    <button class="btn btn-block btn-primary" type="button" onclick="login()">立即登录</button>
                </div>
            </form>

            <hr/>
            <form id="registerForm" autocomplete="off" onsubmit="return false;">
                <div class="form-group" style="text-align:center;">
                    <h4 style="margin: 0;">注册用户</h4>
                </div>

                <div class="form-group" style="text-align:center;">
                    <small class="text-muted">仅支持学生、教师自助注册；管理员账号由系统维护，不可在此注册。</small>
                </div>
                <div class="form-group has-feedback feedback-left">
                    <input type="text" placeholder="学号（学生）/ 工号（教师）" class="form-control" id="reg_username"
                           autocomplete="off" value=""/>
                </div>
                <div class="form-group has-feedback feedback-left">
                    <input type="password" placeholder="密码" class="form-control" id="reg_password"
                           autocomplete="new-password" value=""/>
                </div>
                <div id="studentRegFields" style="display:none;">
                    <div class="form-group has-feedback feedback-left">
                        <input type="text" placeholder="姓名" class="form-control" id="reg_name"/>
                    </div>
                    <div class="form-group has-feedback feedback-left">
                        <input type="text" placeholder="班级编号" class="form-control" id="reg_clazzno"/>
                    </div>
                </div>

                <div id="teacherRegFields" style="display:none;">
                    <div class="form-group has-feedback feedback-left">
                        <input type="text" placeholder="教师姓名" class="form-control" id="reg_teacher_name"/>
                    </div>
                </div>

                <div class="form-group">
                    <button class="btn btn-block btn-success" type="button" onclick="registerUser()">立即注册</button>
                </div>
            </form>
            <hr>
            <footer class="col-sm-12 text-center">
                <p class="m-b-0">楚天泽的学生管理项目 <a href="https://space.bilibili.com/1517539211?spm_id_from=333.1007.0.0">作者b站账号</a></p>
            </footer>
        </div>
    </div>
</div>
<jsp:include page="/WEB-INF/view/_lang_switcher.jsp"/>
<script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/bootstrap.min.js"></script>
<script type="text/javascript">
    function toggleRegisterFields() {
        let usertype = $("input[name=usertype]:checked").val();
        if (usertype === "emp") {
            $("#studentRegFields").show();
            $("#teacherRegFields").hide();
        } else if (usertype === "teacher") {
            $("#studentRegFields").hide();
            $("#teacherRegFields").show();
        } else {
            $("#studentRegFields").hide();
            $("#teacherRegFields").hide();
        }
        if (usertype === "admin") {
            $("#reg_username,#reg_password").prop("disabled", true);
        } else {
            $("#reg_username,#reg_password").prop("disabled", false);
        }
    }

    function refreshCaptcha() {
        var img = document.getElementById("captchaImg");
        if (!img) {
            return;
        }
        img.src = "${pageContext.request.contextPath}/captcha?t=" + Date.now();
    }

    function onAuthFailure() {
        refreshCaptcha();
        $("#captcha").val("");
    }

    function clearLoginFields() {
        $("#username, #password, #captcha").val("").prop("defaultValue", "");
        $("#reg_username, #reg_password, #reg_name, #reg_clazzno, #reg_teacher_name").val("");
    }

    $(function () {
        clearLoginFields();
        refreshCaptcha();
        toggleRegisterFields();
        $("input[name=usertype]").change(function () {
            toggleRegisterFields();
        });
        // 部分浏览器会在页面加载后延迟自动填充，再清一次
        setTimeout(clearLoginFields, 100);
        setTimeout(clearLoginFields, 500);
    });

    function registerUser() {
        let usertype = $("input[name=usertype]:checked").val();
        if (usertype === "admin") {
            alert("不允许自助注册管理员，请联系系统管理员。");
            return;
        }
        let username = $("#reg_username").val();
        let password = $("#reg_password").val();
        let captcha = $("#captcha").val();

        let payload = {
            usertype: usertype,
            username: username,
            password: password,
            captcha: captcha
        };

        if (usertype === "emp") {
            payload.name = $("#reg_name").val();
            payload.clazzno = $("#reg_clazzno").val();
        }
        if (usertype === "teacher") {
            payload.name = $("#reg_teacher_name").val();
        }

        $.ajax({
            type: "post",
            url: "${pageContext.request.contextPath}/register",
            dataType: "json",
            data: payload,
            success: function (data) {
                if (data.success) {
                    alert("注册成功，请登录！");
                    location.href = "${pageContext.request.contextPath}/login.jsp";
                } else {
                    alert(data.message);
                    onAuthFailure();
                }
            },
            error: function () {
                alert("请求服务器失败");
                onAuthFailure();
            }
        });
    }

    function login() {
        let username = $("#username").val()
        let password = $("#password").val()
        let captcha = $("#captcha").val()
        let usertype = $("input[name=usertype]:checked").val()
        $.ajax({
            type: "post",
            url: "${pageContext.request.contextPath}/login",
            dataType: 'json',
            data: {
                username: username,
                password: password,
                captcha: captcha,
                usertype: usertype,
            },
            success: function (data) {
                if(data.success){
                    location.href="${pageContext.request.contextPath}/index.jsp";
                }else{
                    alert(data.message);
                    onAuthFailure();
                }
            },
            error: function () {
                alert("请求服务器失败");
                onAuthFailure();
            }
        });
    }


</script>
</body>
</html>