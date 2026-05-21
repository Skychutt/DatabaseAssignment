<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.COMP2013J.assignment.entity.StuCourseView" %>
<%@ page import="com.COMP2013J.assignment.entity.Course" %>
<%@ page import="com.COMP2013J.assignment.utils.vo.PagerVO" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"/>
    <title>我的选课</title>
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
                <%
                    List<StuCourseView> myRows = (List<StuCourseView>) request.getAttribute("myRows");
                    PagerVO<Course> availPager = (PagerVO<Course>) request.getAttribute("availPager");
                    java.text.SimpleDateFormat df = new java.text.SimpleDateFormat("yyyy-MM-dd");
                %>
                <div class="row">
                    <div class="col-md-12">
                        <div class="card">
                            <div class="card-header"><h4>已选课程</h4></div>
                            <div class="card-body">
                                <p class="text-muted" style="margin-bottom:12px;">在选课时间内可对下方「可选课程」点击<strong>选课</strong>；已选课程可<strong>退选</strong>（退选后成绩清空）。</p>
                                <table class="table table-bordered">
                                    <thead>
                                    <tr>
                                        <th>课程号</th>
                                        <th>课程名</th>
                                        <th>选课时间</th>
                                        <th>成绩</th>
                                        <th>评价</th>
                                        <th>操作</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <% if (myRows == null || myRows.isEmpty()) { %>
                                    <tr><td colspan="6" class="text-center text-muted">暂无已选课程，请在下方选择课程。</td></tr>
                                    <% } else { for (StuCourseView v : myRows) { %>
                                    <tr>
                                        <td><%= v.getCno() %></td>
                                        <td><%= v.getCname() == null ? "" : v.getCname() %></td>
                                        <td><%= v.getChosetime() == null ? "" : df.format(v.getChosetime()) %></td>
                                        <td><%= v.getScore() == null ? "" : v.getScore() %></td>
                                        <td><%= v.getEvaluation() == null ? "" : v.getEvaluation() %></td>
                                        <td>
                                            <button type="button" class="btn btn-xs btn-warning" onclick="dropCourse('<%= v.getCno() %>')">退选</button>
                                        </td>
                                    </tr>
                                    <% } } %>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="col-md-12">
                        <div class="card">
                            <div class="card-header"><h4>可选课程（在选课时间内且未满员）</h4></div>
                            <div class="card-body">
                                <form class="form-inline" style="margin-bottom:15px;" method="get" action="${pageContext.request.contextPath}/stuCourse" id="availForm">
                                    <input type="hidden" name="availCurrent" id="availCurrent" value="<%= availPager == null ? 1 : availPager.getCurrent() %>">
                                    <input type="hidden" id="availTotalPages" value="<%= availPager == null ? 1 : availPager.getTotalPages() %>">
                                    <div class="form-group">
                                        <label>每页</label>
                                        <select class="form-control" name="availSize" id="availSize" onchange="document.getElementById('availCurrent').value=1;this.form.submit();">
                                            <option value="5" <%= availPager != null && availPager.getSize() == 5 ? "selected" : "" %>>5</option>
                                            <option value="8" <%= availPager != null && availPager.getSize() == 8 ? "selected" : "" %>>8</option>
                                            <option value="10" <%= availPager != null && availPager.getSize() == 10 ? "selected" : "" %>>10</option>
                                        </select>
                                    </div>
                                </form>
                                <table class="table table-hover">
                                    <thead>
                                    <tr>
                                        <th>课程号</th>
                                        <th>课程名</th>
                                        <th>教师工号</th>
                                        <th>选课起止</th>
                                        <th>已选/上限</th>
                                        <th>操作</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <%
                                        List<Course> alist = availPager == null ? null : availPager.getList();
                                        if (alist == null || alist.isEmpty()) {
                                    %>
                                    <tr><td colspan="6" class="text-center text-muted">当前没有可选课程（可能未到选课时间、已满员或已全部选过）。</td></tr>
                                    <%
                                        } else {
                                            for (Course c : alist) {
                                    %>
                                    <tr>
                                        <td><%= c.getCno() %></td>
                                        <td><%= c.getCname() == null ? "" : c.getCname() %></td>
                                        <td><%= c.getTno() == null ? "" : c.getTno() %></td>
                                        <td><%= c.getBegindate() == null ? "" : df.format(c.getBegindate()) %> ~ <%= c.getEnddate() == null ? "" : df.format(c.getEnddate()) %></td>
                                        <td><%= c.getCount() == null ? 0 : c.getCount() %> / <%= c.getMaximum() == null ? "不限" : c.getMaximum() %></td>
                                        <td>
                                            <button type="button" class="btn btn-xs btn-success" onclick="chooseCourse('<%= c.getCno() %>')">选课</button>
                                        </td>
                                    </tr>
                                    <% } } %>
                                    </tbody>
                                </table>
                                <% if (availPager != null) { %>
                                <div class="clearfix">
                                    <div class="pull-left" style="line-height:34px;">共 <%= availPager.getTotal() %> 门可选，第 <%= availPager.getCurrent() %> / <%= availPager.getTotalPages() %> 页</div>
                                    <nav class="pull-right">
                                        <ul class="pagination" style="margin:0;">
                                            <li class="<%= availPager.isShowLeft() ? "" : "disabled" %>">
                                                <a href="javascript:void(0)" onclick="gotoAvailPage(<%= availPager.getCurrent() - 1 %>)"><i class="mdi mdi-chevron-left"></i></a>
                                            </li>
                                            <li class="<%= availPager.isShowRight() ? "" : "disabled" %>">
                                                <a href="javascript:void(0)" onclick="gotoAvailPage(<%= availPager.getCurrent() + 1 %>)"><i class="mdi mdi-chevron-right"></i></a>
                                            </li>
                                        </ul>
                                    </nav>
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
    function gotoAvailPage(p) {
        var tp = parseInt(document.getElementById("availTotalPages").value, 10) || 1;
        if (p < 1) p = 1;
        if (p > tp) p = tp;
        document.getElementById("availCurrent").value = p;
        document.getElementById("availForm").submit();
    }
    function chooseCourse(cno) {
        $.post("${pageContext.request.contextPath}/stuCourse", {r: "choose", cno: cno}, function (res) {
            if (res.success) {
                alert("选课成功");
                location.reload();
            } else {
                alert(res.message || "选课失败");
            }
        }, "json");
    }
    function dropCourse(cno) {
        if (!confirm("确定退选 " + cno + " ？")) return;
        $.post("${pageContext.request.contextPath}/stuCourse", {r: "drop", cno: cno}, function (res) {
            if (res.success) {
                alert("已退选");
                location.reload();
            } else {
                alert(res.message || "退选失败");
            }
        }, "json");
    }
</script>
</body>
</html>
