<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.COMP2013J.assignment.entity.Course" %>
<%@ page import="com.COMP2013J.assignment.utils.vo.PagerVO" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"/>
    <title>课程信息</title>
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
                    <div class="col-md-12">
                        <div class="card">
                            <div class="card-header"><h4>课程列表</h4></div>
                            <div class="card-body">
                                <%
                                    String errorMsg = (String) request.getAttribute("errorMsg");
                                    PagerVO<Course> pagerVO = (PagerVO<Course>) request.getAttribute("pagerVO");
                                    List<Course> list = pagerVO == null ? null : pagerVO.getList();
                                    String cnoVal = (String) request.getAttribute("cno");
                                    String cnameVal = (String) request.getAttribute("cname");
                                    String tnoVal = (String) request.getAttribute("tno");
                                    Integer sizeVal = (Integer) request.getAttribute("size");
                                    if (cnoVal == null) cnoVal = "";
                                    if (cnameVal == null) cnameVal = "";
                                    if (tnoVal == null) tnoVal = "";
                                    if (sizeVal == null || sizeVal <= 0) sizeVal = 10;
                                    Boolean canManageCourse = Boolean.TRUE.equals(request.getAttribute("canManageCourse"));
                                %>
                                <% if (errorMsg != null && !errorMsg.isEmpty()) { %>
                                <div class="alert alert-danger" role="alert"><%= errorMsg %></div>
                                <% } %>
                                <% if (!canManageCourse) { %>
                                <div class="alert alert-info">
                                    需要<strong>选课或退选</strong>？请打开左侧「<a href="${pageContext.request.contextPath}/stuCourse">我的选课</a>」：在选课时间内、未满员的课程可一键选课；已选课程可退选并查看教师给出的成绩与评价。
                                </div>
                                <% } %>

                                <form style="margin-bottom: 20px" class="form-inline"
                                      action="${pageContext.request.contextPath}/course" method="get" id="pageForm">
                                    <input type="hidden" name="current" id="current" value="<%= pagerVO == null ? 1 : pagerVO.getCurrent() %>">
                                    <input type="hidden" id="totalPages" value="<%= pagerVO == null ? 1 : pagerVO.getTotalPages() %>">
                                    <div class="form-group">
                                        <label>课程号</label>
                                        <input class="form-control" type="text" value="<%= cnoVal %>" name="cno" placeholder="课程号">
                                    </div>
                                    <div class="form-group">
                                        <label>课程名</label>
                                        <input class="form-control" type="text" value="<%= cnameVal %>" name="cname" placeholder="课程名">
                                    </div>
                                    <div class="form-group">
                                        <label>教师工号</label>
                                        <input class="form-control" type="text" value="<%= tnoVal %>" name="tno" placeholder="教师工号">
                                    </div>
                                    <div class="form-group">
                                        <button class="btn btn-brown btn-round" type="submit">查询</button>
                                        <% if (canManageCourse) { %>
                                        <button class="btn btn-brown btn-round" type="button" onclick="location.href='${pageContext.request.contextPath}/course?r=add'">新增</button>
                                        <% } %>
                                    </div>
                                    <div class="form-group" style="margin-left: 15px;">
                                        <label>每页显示</label>
                                        <select class="form-control" name="size" id="size" onchange="changePageSize()">
                                            <option value="5" <%= sizeVal == 5 ? "selected" : "" %>>5</option>
                                            <option value="10" <%= sizeVal == 10 ? "selected" : "" %>>10</option>
                                            <option value="20" <%= sizeVal == 20 ? "selected" : "" %>>20</option>
                                        </select>
                                    </div>
                                </form>

                                <div class="table-responsive">
                                    <table class="table table-hover">
                                        <thead>
                                        <tr>
                                            <th>#</th>
                                            <th>课程号</th>
                                            <th>教师工号</th>
                                            <th>课程名</th>
                                            <th>选课开始</th>
                                            <th>选课结束</th>
                                            <th>学分</th>
                                            <th>上限</th>
                                            <th>已选</th>
                                            <th>内容摘要</th>
                                            <% if (canManageCourse) { %><th>操作</th><% } %>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <%
                                            if (list != null) {
                                                java.text.SimpleDateFormat df = new java.text.SimpleDateFormat("yyyy-MM-dd");
                                                for (int idx = 0; idx < list.size(); idx++) {
                                                    Course c = list.get(idx);
                                        %>
                                        <tr>
                                            <th scope="row"><%= (pagerVO.getCurrent() - 1) * pagerVO.getSize() + idx + 1 %></th>
                                            <td><%= c.getCno() %></td>
                                            <td><%= c.getTno() == null ? "" : c.getTno() %></td>
                                            <td><%= c.getCname() == null ? "" : c.getCname() %></td>
                                            <td><%= c.getBegindate() == null ? "" : df.format(c.getBegindate()) %></td>
                                            <td><%= c.getEnddate() == null ? "" : df.format(c.getEnddate()) %></td>
                                            <td><%= c.getCredits() == null ? "" : c.getCredits() %></td>
                                            <td><%= c.getMaximum() == null ? "" : c.getMaximum() %></td>
                                            <td><%= c.getCount() == null ? "" : c.getCount() %></td>
                                            <td style="max-width:180px;overflow:hidden;text-overflow:ellipsis;white-space:nowrap;" title="<%= c.getContent() == null ? "" : c.getContent() %>"><%= c.getContent() == null ? "" : c.getContent() %></td>
                                            <% if (canManageCourse) { %>
                                            <td>
                                                <button type="button" class="btn btn-xs btn-primary" onclick="editCourse('<%= c.getCno() %>')">修改</button>
                                                <button type="button" class="btn btn-xs btn-danger" onclick="delCourse('<%= c.getCno() %>')">删除</button>
                                            </td>
                                            <% } %>
                                        </tr>
                                        <%
                                                }
                                            }
                                        %>
                                        </tbody>
                                    </table>
                                </div>
                                <hr>
                                <% if (pagerVO != null) { %>
                                <div class="clearfix">
                                    <div class="pull-left" style="line-height: 34px;">
                                        共 <strong><%= pagerVO.getTotal() %></strong> 条，
                                        当前第 <strong><%= pagerVO.getCurrent() %></strong> / <strong><%= pagerVO.getTotalPages() %></strong> 页
                                    </div>
                                    <nav class="pull-right">
                                        <ul class="pagination" style="margin: 0;">
                                            <li class="<%= pagerVO.isShowLeft() ? "" : "disabled" %>">
                                                <a href="javascript:void(0)" data-page="<%= pagerVO.getCurrent() - 1 %>" onclick="gotoPage(parseInt(this.dataset.page, 10))">
                                                    <span><i class="mdi mdi-chevron-left"></i></span>
                                                </a>
                                            </li>
                                            <%
                                                int[] pageNums = pagerVO.getPageNums();
                                                if (pageNums != null) {
                                                    for (int pageNum : pageNums) {
                                            %>
                                            <li class="<%= pageNum == pagerVO.getCurrent() ? "active" : "" %>">
                                                <a href="javascript:void(0)" data-page="<%= pageNum %>" onclick="gotoPage(parseInt(this.dataset.page, 10))"><%= pageNum %></a>
                                            </li>
                                            <%
                                                    }
                                                }
                                            %>
                                            <li class="<%= pagerVO.isShowRight() ? "" : "disabled" %>">
                                                <a href="javascript:void(0)" data-page="<%= pagerVO.getCurrent() + 1 %>" onclick="gotoPage(parseInt(this.dataset.page, 10))">
                                                    <span><i class="mdi mdi-chevron-right"></i></span>
                                                </a>
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

<script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/perfect-scrollbar.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/main.min.js"></script>
<script type="text/javascript">
    function gotoPage(page) {
        var totalPages = parseInt(document.getElementById("totalPages").value, 10) || 1;
        if (page < 1) page = 1;
        if (page > totalPages) page = totalPages;
        document.getElementById("current").value = page;
        document.getElementById("pageForm").submit();
    }
    function changePageSize() {
        document.getElementById("current").value = 1;
        document.getElementById("pageForm").submit();
    }
    function editCourse(cno) {
        location.href = "${pageContext.request.contextPath}/course?r=edit&cno=" + encodeURIComponent(cno);
    }
    function delCourse(cno) {
        if (!confirm("确定删除课程 " + cno + " 吗？")) return;
        $.post("${pageContext.request.contextPath}/course", {r: "del", cno: cno}, function (res) {
            if (res.success) {
                alert("删除成功");
                location.reload();
            } else {
                alert(res.message || "删除失败");
            }
        }, "json");
    }
</script>
</body>
</html>
