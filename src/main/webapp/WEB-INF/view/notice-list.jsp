<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.COMP2013J.assignment.entity.Notice" %>
<%@ page import="com.COMP2013J.assignment.utils.vo.PagerVO" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"/>
    <title>系统公告</title>
    <link rel="icon" href="${pageContext.request.contextPath}/favicon.ico" type="image/x-icon">
    <link href="${pageContext.request.contextPath}/assets/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/assets/css/materialdesignicons.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/assets/css/style.min.css" rel="stylesheet">
    <style>
        .notice-content-cell { max-width: 480px; white-space: pre-wrap; word-break: break-word; }
    </style>
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
                            <div class="card-header"><h4>系统公告</h4></div>
                            <div class="card-body">
                                <%
                                    String errorMsg = (String) request.getAttribute("errorMsg");
                                    PagerVO<Notice> pagerVO = (PagerVO<Notice>) request.getAttribute("pagerVO");
                                    List<Notice> noticeList = pagerVO == null ? null : pagerVO.getList();
                                    String publisherVal = (String) request.getAttribute("publisher");
                                    String keywordVal = (String) request.getAttribute("keyword");
                                    Integer sizeVal = (Integer) request.getAttribute("size");
                                    if (publisherVal == null) publisherVal = "";
                                    if (keywordVal == null) keywordVal = "";
                                    if (sizeVal == null || sizeVal <= 0) sizeVal = 10;
                                    Boolean canManageNotice = Boolean.TRUE.equals(request.getAttribute("canManageNotice"));
                                    java.text.SimpleDateFormat df = new java.text.SimpleDateFormat("yyyy-MM-dd");
                                %>
                                <% if (errorMsg != null && !errorMsg.isEmpty()) { %>
                                <div class="alert alert-danger" role="alert"><%= errorMsg %></div>
                                <% } %>
                                <% if (!canManageNotice) { %>
                                <p class="text-muted" style="margin-bottom:12px;">以下为管理员发布的公告，您可查看内容与发布人信息。</p>
                                <% } %>

                                <form style="margin-bottom: 20px" class="form-inline"
                                      action="${pageContext.request.contextPath}/notice" method="get" id="pageForm">
                                    <input type="hidden" name="current" id="current" value="<%= pagerVO == null ? 1 : pagerVO.getCurrent() %>">
                                    <input type="hidden" id="totalPages" value="<%= pagerVO == null ? 1 : pagerVO.getTotalPages() %>">
                                    <div class="form-group">
                                        <label>发布人</label>
                                        <input class="form-control" type="text" value="<%= publisherVal %>" name="publisher" placeholder="管理员账号">
                                    </div>
                                    <div class="form-group">
                                        <label>关键词</label>
                                        <input class="form-control" type="text" value="<%= keywordVal %>" name="keyword" placeholder="公告内容">
                                    </div>
                                    <div class="form-group">
                                        <button class="btn btn-brown btn-round" type="submit">查询</button>
                                        <% if (canManageNotice) { %>
                                        <button class="btn btn-brown btn-round" type="button" onclick="location.href='${pageContext.request.contextPath}/notice?r=add'">发布公告</button>
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

                                <table class="table table-hover table-bordered">
                                    <thead>
                                    <tr>
                                        <th width="60">#</th>
                                        <th width="100">发布人</th>
                                        <th width="110">发布日期</th>
                                        <th>公告内容</th>
                                        <% if (canManageNotice) { %><th width="140">操作</th><% } %>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <%
                                        if (noticeList == null || noticeList.isEmpty()) {
                                    %>
                                    <tr><td colspan="<%= canManageNotice ? 5 : 4 %>" class="text-center text-muted">暂无公告</td></tr>
                                    <%
                                        } else {
                                            for (int idx = 0; idx < noticeList.size(); idx++) {
                                                Notice n = noticeList.get(idx);
                                    %>
                                    <tr>
                                        <th scope="row"><%= n.getId() %></th>
                                        <td><%= n.getPublisher() == null ? "" : n.getPublisher() %></td>
                                        <td><%= n.getCreateTime() == null ? "" : df.format(n.getCreateTime()) %></td>
                                        <td class="notice-content-cell"><%= n.getContent() == null ? "" : n.getContent() %></td>
                                        <% if (canManageNotice) { %>
                                        <td>
                                            <button type="button" class="btn btn-xs btn-primary" onclick="editNotice(<%= n.getId() %>)">修改</button>
                                            <button type="button" class="btn btn-xs btn-danger" onclick="delNotice(<%= n.getId() %>)">删除</button>
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
        if (page < 1) page = 1;
        if (page > totalPages) page = totalPages;
        document.getElementById("current").value = page;
        document.getElementById("pageForm").submit();
    }
    function changePageSize() {
        document.getElementById("current").value = 1;
        document.getElementById("pageForm").submit();
    }
    function editNotice(id) {
        location.href = "${pageContext.request.contextPath}/notice?r=edit&id=" + id;
    }
    function delNotice(id) {
        if (!confirm("确定删除该公告吗？")) return;
        $.post("${pageContext.request.contextPath}/notice", {r: "del", id: id}, function (res) {
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
