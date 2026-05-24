<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.COMP2013J.assignment.entity.Student" %>
<%@ page import="com.COMP2013J.assignment.utils.vo.PagerVO" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"/>
    <title>学生信息</title>
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
                            <div class="card-header"><h4>学生列表</h4></div>
                            <div class="card-body">
                                <%
                                    String errorMsg = (String) request.getAttribute("errorMsg");
                                    PagerVO<Student> pagerVO = (PagerVO<Student>) request.getAttribute("pagerVO");
                                    List<Student> studentList = pagerVO == null ? null : pagerVO.getList();
                                    String snoVal = (String) request.getAttribute("sno");
                                    String nameVal = (String) request.getAttribute("name");
                                    String genderVal = (String) request.getAttribute("gender");
                                    String clazznoVal = (String) request.getAttribute("clazzno");
                                    Integer sizeVal = (Integer) request.getAttribute("size");
                                    if (snoVal == null) snoVal = "";
                                    if (nameVal == null) nameVal = "";
                                    if (genderVal == null) genderVal = "";
                                    if (clazznoVal == null) clazznoVal = "";
                                    if (sizeVal == null || sizeVal <= 0) sizeVal = 10;
                                    Boolean canManageStudent = Boolean.TRUE.equals(request.getAttribute("canManageStudent"));
                                %>
                                <% if (errorMsg != null && !errorMsg.isEmpty()) { %>
                                <div class="alert alert-danger" role="alert"><%= errorMsg %></div>
                                <% } %>
                                <% if (!canManageStudent) { %>
                                <div class="alert alert-info">浏览模式：可查询学生信息，已隐藏密码；修改本人资料请用右上角「个人信息」。</div>
                                <% } %>

                                <form style="margin-bottom: 20px" class="form-inline"
                                      action="${pageContext.request.contextPath}/student" method="get" id="pageForm">
                                    <input type="hidden" name="current" id="current" value="<%= pagerVO == null ? 1 : pagerVO.getCurrent() %>">
                                    <input type="hidden" id="totalPages" value="<%= pagerVO == null ? 1 : pagerVO.getTotalPages() %>">
                                    <div class="form-group">
                                        <label>学号</label>
                                        <input class="form-control" type="text" value="<%= snoVal %>" name="sno" placeholder="请输入学号">
                                    </div>
                                    <div class="form-group">
                                        <label>姓名</label>
                                        <input class="form-control" type="text" value="<%= nameVal %>" name="name" placeholder="请输入姓名">
                                    </div>
                                    <div class="form-group">
                                        <label>性别</label>
                                        <select class="form-control" name="gender">
                                            <option value="" <%= "".equals(genderVal) ? "selected" : "" %>>全部</option>
                                            <option value="m" <%= "m".equals(genderVal) ? "selected" : "" %>>男</option>
                                            <option value="w" <%= "w".equals(genderVal) ? "selected" : "" %>>女</option>
                                        </select>
                                    </div>
                                    <div class="form-group">
                                        <label>班级编号</label>
                                        <input class="form-control" type="text" value="<%= clazznoVal %>" name="clazzno"
                                               placeholder="请输入班级编号">
                                    </div>
                                    <div class="form-group">
                                        <button class="btn btn-brown btn-round" type="submit">查询</button>
                                        <% if (canManageStudent) { %>
                                        <button class="btn btn-brown btn-round" type="button" onclick="location.href='${pageContext.request.contextPath}/student?r=add'">新增</button>
                                        <% } %>
                                    </div>
                                    <div class="form-group" style="margin-left: 15px;">
                                        <label>每页显示</label>
                                        <select class="form-control" name="size" id="size" onchange="changePageSize()">
                                            <option value="5" <%= sizeVal == 5 ? "selected" : "" %>>5</option>
                                            <option value="10" <%= sizeVal == 10 ? "selected" : "" %>>10</option>
                                            <option value="20" <%= sizeVal == 20 ? "selected" : "" %>>20</option>
                                            <option value="50" <%= sizeVal == 50 ? "selected" : "" %>>50</option>
                                        </select>
                                    </div>
                                </form>

                                <table class="table table-hover">
                                    <thead>
                                    <tr>
                                        <th>#</th>
                                        <th>学号</th>
                                        <th>姓名</th>
                                        <th>性别</th>
                                        <th>年龄</th>
                                        <th>电话</th>
                                        <% if (canManageStudent) { %><th>密码状态</th><% } %>
                                        <th>入学时间</th>
                                        <th>地址</th>
                                        <th>班级编号</th>
                                        <% if (canManageStudent) { %><th>操作</th><% } %>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <%
                                        if (studentList != null) {
                                            for (int idx = 0; idx < studentList.size(); idx++) {
                                                Student s = studentList.get(idx);
                                                String sex = "m".equals(s.getGender()) ? "男" : ("w".equals(s.getGender()) ? "女" : "");
                                    %>
                                    <tr>
                                        <th scope="row"><%= (pagerVO.getCurrent() - 1) * pagerVO.getSize() + idx + 1 %></th>
                                        <td><%= s.getSno() %></td>
                                        <td><%= s.getName() %></td>
                                        <td><%= sex %></td>
                                        <td><%= s.getAge() == null ? "" : s.getAge() %></td>
                                        <td><%= s.getTele() == null ? "" : s.getTele() %></td>
                                        <% if (canManageStudent) { %>
                                        <td><%= s.getPassword() == null || s.getPassword().isEmpty() ? "未设置" : "已设置（加密存储）" %></td>
                                        <% } %>
                                        <td><%= s.getEnterdate() == null ? "" : new java.text.SimpleDateFormat("yyyy-MM-dd").format(s.getEnterdate()) %></td>
                                        <td><%= s.getAddress() == null ? "" : s.getAddress() %></td>
                                        <td><%= s.getClazzno() == null ? "" : s.getClazzno() %></td>
                                        <% if (canManageStudent) { %>
                                        <td>
                                            <button type="button" class="btn btn-xs btn-primary" onclick="editStudent('<%= s.getSno() %>')">修改</button>
                                            <button type="button" class="btn btn-xs btn-danger" onclick="delStudent('<%= s.getSno() %>')">删除</button>
                                        </td>
                                        <% } %>
                                    </tr>
                                    <%
                                            }
                                        }
                                    %>
                                    </tbody>
                                </table>
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
        if (page < 1) {
            page = 1;
        }
        if (page > totalPages) {
            page = totalPages;
        }
        document.getElementById("current").value = page;
        document.getElementById("pageForm").submit();
    }

    function changePageSize() {
        document.getElementById("current").value = 1;
        document.getElementById("pageForm").submit();
    }

    function editStudent(sno) {
        location.href = "${pageContext.request.contextPath}/student?r=edit&sno=" + encodeURIComponent(sno);
    }

    function delStudent(sno) {
        if (!confirm("确定删除学生 " + sno + " 吗？")) {
            return;
        }
        $.post("${pageContext.request.contextPath}/student", {r: "del", sno: sno}, function (res) {
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
