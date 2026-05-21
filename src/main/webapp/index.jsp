<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"/>
    <title>首页</title>
    <link rel="icon" href="${pageContext.request.contextPath}/favicon.ico" type="image/x-icon">
    <link href="${pageContext.request.contextPath}/assets/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/assets/css/materialdesignicons.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/assets/css/style.min.css" rel="stylesheet">
</head>

<body>
<%
    String idxRole = session.getAttribute("role") == null ? "" : String.valueOf(session.getAttribute("role"));
    String idxCp = request.getContextPath();
%>
<div class="lyear-layout-web">
    <div class="lyear-layout-container">

        <jsp:include page="WEB-INF/view/_aside_header.jsp"/>

        <main class="lyear-layout-content">

            <div class="container-fluid">

                <div class="row">
                    <div class="col-sm-6 col-lg-3">
                        <div class="card bg-primary">
                            <div class="card-body clearfix">
                                <div class="pull-right">
                                    <p class="h6 text-white m-t-0">班级数量</p>
                                    <p class="h3 text-white m-b-0 fa-1-5x" id="clazzCount">0</p>
                                </div>
                                <div class="pull-left">
                                    <span class="img-avatar img-avatar-48 bg-translucent">
                                        <i class="mdi mdi-home fa-1-5x"></i>
                                    </span>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="col-sm-6 col-lg-3">
                        <div class="card bg-danger">
                            <div class="card-body clearfix">
                                <div class="pull-right">
                                    <p class="h6 text-white m-t-0">学生数量</p>
                                    <p class="h3 text-white m-b-0 fa-1-5x" id="studentCount">0</p>
                                </div>
                                <div class="pull-left">
                                    <span class="img-avatar img-avatar-48 bg-translucent">
                                        <i class="mdi mdi-account fa-1-5x"></i>
                                    </span>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="col-sm-6 col-lg-3">
                        <div class="card bg-warning">
                            <div class="card-body clearfix">
                                <div class="pull-right">
                                    <p class="h6 text-white m-t-0">教师数量</p>
                                    <p class="h3 text-white m-b-0 fa-1-5x" id="teacherCount">0</p>
                                </div>
                                <div class="pull-left">
                                    <span class="img-avatar img-avatar-48 bg-translucent">
                                        <i class="mdi mdi-account fa-1-5x"></i>
                                    </span>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="col-sm-6 col-lg-3">
                        <div class="card bg-success">
                            <div class="card-body clearfix">
                                <div class="pull-right">
                                    <p class="h6 text-white m-t-0">课程数量</p>
                                    <p class="h3 text-white m-b-0 fa-1-5x" id="courseCount">0</p>
                                </div>
                                <div class="pull-left">
                                    <span class="img-avatar img-avatar-48 bg-translucent">
                                        <i class="mdi mdi-book-minus fa-1-5x"></i>
                                    </span>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="col-md-12">
                        <div class="card">
                            <div class="card-header"><h4>选课系统</h4></div>
                            <div class="card-body">
                                <p>当前选课记录共 <strong id="enrollmentCount">0</strong> 条（含所有学生的选课）。</p>
                                <% if ("student".equals(idxRole)) { %>
                                <a href="<%= idxCp %>/stuCourse" class="btn btn-success"><i class="mdi mdi-book-plus"></i> 进入我的选课（选课 / 退选 / 查成绩）</a>
                                <% } else if ("teacher".equals(idxRole)) { %>
                                <a href="<%= idxCp %>/stuCourse" class="btn btn-warning"><i class="mdi mdi-clipboard-text"></i> 选课与打分（仅本课程学生）</a>
                                <% } else if ("admin".equals(idxRole)) { %>
                                <a href="<%= idxCp %>/stuCourse" class="btn btn-primary"><i class="mdi mdi-table-large"></i> 选课管理（查看全部、维护成绩、移除记录）</a>
                                <% } else { %>
                                <span class="text-muted">登录后可使用选课相关功能。</span>
                                <% } %>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="col-lg-6">
                        <div class="card">
                            <div class="card-header">
                                <h4>班级学生数量</h4>
                            </div>
                            <div class="card-body">
                                <canvas class="js-chartjs-clazz"></canvas>
                            </div>
                        </div>
                    </div>
                    <div class="col-lg-6">
                        <div class="card">
                            <div class="card-header">
                                <h4>教师与课程数量</h4>
                            </div>
                            <div class="card-body">
                                <canvas class="js-chartjs-teacher-course"></canvas>
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
<script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/Chart.js"></script>

<script type="text/javascript">
    $(document).ready(function () {
        $.ajax({
            type: "get",
            url: "${pageContext.request.contextPath}/index",
            dataType: 'json',
            success: function (data) {
                if (!data.success) {
                    alert(data.message);
                    return;
                }
                var clazzCount = data.data.clazzCount;
                var studentCount = data.data.studentCount;
                var teacherCount = data.data.teacherCount;
                var courseCount = data.data.courseCount;
                var enrollmentCount = data.data.enrollmentCount != null ? data.data.enrollmentCount : 0;
                var clazzes = data.data.clazzes;

                $("#clazzCount").text(clazzCount);
                $("#studentCount").text(studentCount);
                $("#teacherCount").text(teacherCount);
                $("#courseCount").text(courseCount);
                $("#enrollmentCount").text(enrollmentCount);

                var chartLabels = [];
                var chartData = [];
                for (var i = 0; i < clazzes.length; i++) {
                    chartLabels.push(clazzes[i].name);
                    chartData.push(clazzes[i].stuCount);
                }

                var barOpts = {
                    scales: {
                        yAxes: [{
                            ticks: { min: 0 },
                            display: true
                        }]
                    }
                };

                var ctxClazz = $('.js-chartjs-clazz')[0].getContext('2d');
                new Chart(ctxClazz, {
                    type: 'bar',
                    data: {
                        labels: chartLabels,
                        datasets: [{
                            label: '学生数',
                            borderWidth: 1,
                            borderColor: 'rgba(0,0,0,0)',
                            backgroundColor: 'rgba(51,202,185,0.5)',
                            hoverBackgroundColor: "rgba(51,202,185,0.7)",
                            hoverBorderColor: "rgba(0,0,0,0)",
                            data: chartData
                        }]
                    },
                    options: barOpts
                });

                var ctxTc = $('.js-chartjs-teacher-course')[0].getContext('2d');
                new Chart(ctxTc, {
                    type: 'bar',
                    data: {
                        labels: ['教师', '课程'],
                        datasets: [{
                            label: '数量',
                            borderWidth: 1,
                            borderColor: 'rgba(0,0,0,0)',
                            backgroundColor: [
                                'rgba(255,193,7,0.65)',
                                'rgba(76,175,80,0.65)'
                            ],
                            hoverBackgroundColor: [
                                'rgba(255,193,7,0.85)',
                                'rgba(76,175,80,0.85)'
                            ],
                            data: [teacherCount, courseCount]
                        }]
                    },
                    options: barOpts
                });
            },
            error: function (xhr) {
                alert("请求服务器失败，状态码：" + xhr.status);
            }
        });
    });
</script>
</body>
</html>
