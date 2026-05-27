<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.COMP2013J.assignment.entity.Clazz" %>
<%@ page import="com.COMP2013J.assignment.utils.vo.PagerVO" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" />
    <title>班级信息</title>
    <link rel="icon" href="${pageContext.request.contextPath}/favicon.ico" type="image/x-icon">
    <link href="${pageContext.request.contextPath}/assets/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/assets/css/materialdesignicons.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/assets/css/style.min.css" rel="stylesheet">
</head>

<body>
<div class="lyear-layout-web">
    <div class="lyear-layout-container">

        <%-- 修复：多余 > 符号、写法规范 --%>
        <jsp:include page="_aside_header.jsp"/>

        <!--页面主要内容-->
        <main class="lyear-layout-content">

            <div class="container-fluid">
                <div class="row">
                    <div class="col-md-12">
                        <div class="card">
                    <div class="card-header"><h4>班级列表</h4></div>
                    <div class="card-body">
                        <%
                            String errorMsg = (String) request.getAttribute("errorMsg");
                            PagerVO<Clazz> pagerVO = (PagerVO<Clazz>) request.getAttribute("pagerVO");
                            List<Clazz> clazzList = pagerVO == null ? null : pagerVO.getList();
                            String clazznoVal = (String) request.getAttribute("clazzno");
                            String nameVal = (String) request.getAttribute("name");
                            Integer sizeVal = (Integer) request.getAttribute("size");
                            if (clazznoVal == null) {
                                clazznoVal = "";
                            }
                            if (nameVal == null) {
                                nameVal = "";
                            }
                            if (sizeVal == null || sizeVal <= 0) {
                                sizeVal = 10;
                            }
                            Boolean canManageClazz = Boolean.TRUE.equals(request.getAttribute("canManageClazz"));
                        %>
                        <% if (errorMsg != null && !errorMsg.isEmpty()) { %>
                            <div class="alert alert-danger" role="alert">
                                <%= errorMsg %>
                            </div>
                        <% } %>
                        <% if (!canManageClazz) { %>
                            <div class="alert alert-info">浏览模式：仅可查询班级信息，不能新增、修改或删除。</div>
                        <% } %>

                        <form style="margin-bottom: 20px" class="form-inline" action="${pageContext.request.contextPath}/clazz" method="get" id="pageForm">
                            <input type="hidden" name="current" id="current" value="<%= pagerVO == null ? 1 : pagerVO.getCurrent() %>">
                            <input type="hidden" id="totalPages" value="<%= pagerVO == null ? 1 : pagerVO.getTotalPages() %>">
                            <div class="form-group">
                                <label>班级编号</label>
                                <input class="form-control" type="text" value="<%= clazznoVal %>" name="clazzno" placeholder="请输入班级编号">
                            </div>
                            <div class="form-group">
                                <label>班级名</label>
                                <input class="form-control" type="text" value="<%= nameVal %>" name="name" placeholder="请输入班级名">
                            </div>
                            <div class="form-group">
                                <button class="btn btn-brown btn-round" type="submit">查询</button>
                                <% if (canManageClazz) { %>
                                <button class="btn btn-brown btn-round" type="button" onclick="location.href='${pageContext.request.contextPath}/clazz?r=add'">新增</button>
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
                                <th>班级编号</th>
                                <th>班级名称</th>
                                <% if (canManageClazz) { %><th>操作</th><% } %>
                            </tr>
                            </thead>
                            <tbody>
                            <%
                                if (clazzList != null) {
                                    for (int idx = 0; idx < clazzList.size(); idx++) {
                                        Clazz i = clazzList.get(idx);
                            %>
                                <tr>
                                    <th scope="row"><%= (pagerVO.getCurrent() - 1) * pagerVO.getSize() + idx + 1 %></th>
                                    <td><%= i.getClazzno() %></td>
                                    <td><%= i.getName() %></td>
                                    <% if (canManageClazz) { %>
                                    <td>
                                        <button type="button" class="btn btn-xs btn-primary" onclick="editClazz('<%= i.getClazzno() %>')">修改</button>
                                        <button type="button" class="btn btn-xs btn-danger" onclick="delClazz('<%= i.getClazzno() %>')">删除</button>
                                    </td>
                                    <% } %>
                                </tr>
                            <%
                                    }
                                }
                            %>
                            </tbody>
                        </table>
                        <%-- 可替换表格形式 --%>
                        <%-- <table class="table table-bordered">
                </table> --%>
                        <%-- <table class="table table-striped">
                </table> --%>

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
        <!--End 页面主要内容-->
    </div>
</div>

<script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/perfect-scrollbar.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/main.min.js"></script>
<!--图表插件-->
<script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/Chart.js"></script>

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

    function editClazz(clazzno) {
        location.href = "${pageContext.request.contextPath}/clazz?r=edit&clazzno=" + encodeURIComponent(clazzno);
    }

    function delClazz(clazzno) {
        if (!confirm("确定删除班级 " + clazzno + " 吗？")) {
            return;
        }
        $.post("${pageContext.request.contextPath}/clazz", {r: "del", clazzno: clazzno}, function (res) {
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