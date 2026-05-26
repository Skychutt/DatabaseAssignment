<%--
  Created by IntelliJ IDEA.
  User: Lenovo
  Date: 2026/4/22
  Time: 22:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.COMP2013J.assignment.security.CsrfUtil" %>
<%
    String csrfToken = CsrfUtil.ensureToken(session);
    String navRole = session.getAttribute("role") == null ? "" : String.valueOf(session.getAttribute("role"));
    boolean navAdmin = "admin".equals(navRole);
    boolean navTeacher = "teacher".equals(navRole);
    boolean navStudent = "student".equals(navRole);
%>
<!--左侧导航-->
<aside class="lyear-layout-sidebar">

    <!-- logo -->
    <div id="logo" class="sidebar-header">
        <a href="${pageContext.request.contextPath}/index.jsp"><img src="${pageContext.request.contextPath}/assets/images/left.png"/></a>
    </div>
    <div class="lyear-layout-sidebar-scroll">

        <nav class="sidebar-main">
            <ul class="nav nav-drawer">
                <li class="nav-item active"> <a href="${pageContext.request.contextPath}/index.jsp"><i class="mdi mdi-home"></i> 后台首页</a> </li>
                <li class="nav-item nav-item-has-subnav open">
                    <a href="javascript:void(0)"><i class="mdi mdi-format-align-justify"></i>功能</a>
                    <ul class="nav nav-subnav">
                        <% if (navAdmin || navTeacher || navStudent) { %>
                        <li><a href="${pageContext.request.contextPath}/clazz">班级信息</a></li>
                        <li><a href="${pageContext.request.contextPath}/student">学生信息</a></li>
                        <% } %>
                        <% if (navAdmin) { %>
                        <li><a href="${pageContext.request.contextPath}/teacher">教师管理</a></li>
                        <% } %>
                        <% if (navTeacher) { %>
                        <li><a href="${pageContext.request.contextPath}/teacher">教师名册</a></li>
                        <% } %>
                        <% if (navAdmin || navTeacher) { %>
                        <li><a href="${pageContext.request.contextPath}/course">课程信息</a></li>
                        <% } %>
                        <% if (navStudent) { %>
                        <li><a href="${pageContext.request.contextPath}/course">课程浏览</a></li>
                        <li><a href="${pageContext.request.contextPath}/studentArchive">个人档案</a></li>
                        <li><a href="${pageContext.request.contextPath}/teacherDirectory">教师通讯录</a></li>
                        <% } %>
                        <% if (navAdmin || navTeacher || navStudent) { %>
                        <li><a href="${pageContext.request.contextPath}/stuCourse"><%= navStudent ? "我的选课" : (navAdmin ? "选课管理" : "选课与打分") %></a></li>
                        <% } %>
                        <% if (navAdmin || navTeacher) { %>
                        <li><a href="${pageContext.request.contextPath}/directory">通讯录</a></li>
                        <% } %>
                        <% if (navAdmin || navTeacher || navStudent) { %>
                        <li><a href="${pageContext.request.contextPath}/notice"><%= navAdmin ? "公告管理" : "系统公告" %></a></li>
                        <% } %>
                    </ul>
                </li>
            </ul>
        </nav>


    </div>

</aside>
<!--End 左侧导航-->

<!--头部信息-->
<header class="lyear-layout-header">

    <nav class="navbar navbar-default">
        <div class="topbar">

            <div class="topbar-left">
                <div class="lyear-aside-toggler">
                    <span class="lyear-toggler-bar"></span>
                    <span class="lyear-toggler-bar"></span>
                    <span class="lyear-toggler-bar"></span>
                </div>
                <span class="navbar-page-title"> 后台首页 </span>
            </div>

            <ul class="topbar-right">
                <li class="dropdown dropdown-profile">
                    <a href="javascript:void(0)" data-toggle="dropdown">
                        <img class="img-avatar img-avatar-48 m-r-10" src="${pageContext.request.contextPath}/assets/images/my/img.jpg" alt="班级管理" />
                        <span>
                            ${empty sessionScope.user ? '未登录' : (sessionScope.role == 'admin' ? sessionScope.user.username : (sessionScope.role == 'teacher' ? sessionScope.user.tno : sessionScope.user.sno))}
                            <span class="caret"></span>
                        </span>
                    </a>
                    <ul class="dropdown-menu dropdown-menu-right">
                        <li> <a href="${pageContext.request.contextPath}/profile"><i class="mdi mdi-account"></i> 个人信息</a> </li>
                        <li> <a href="${pageContext.request.contextPath}/editPassword"><i class="mdi mdi-lock-outline"></i> 修改密码</a> </li>
                        <li> <a href="javascript:void(0)"><i class="mdi mdi-delete"></i> 清空缓存</a></li>
                        <li class="divider"></li>
                        <li> <a href="${pageContext.request.contextPath}/logout"><i class="mdi mdi-logout-variant"></i> 退出登录</a> </li>
                    </ul>
                </li>
                <!--切换主题配色-->
                <li class="dropdown dropdown-skin">
                    <span data-toggle="dropdown" class="icon-palette"><i class="mdi mdi-palette"></i></span>
                    <ul class="dropdown-menu dropdown-menu-right" data-stopPropagation="true">
                        <li class="drop-title"><p>主题</p></li>
                        <li class="drop-skin-li clearfix">
                  <span class="inverse">
                    <input type="radio" name="site_theme" value="default" id="site_theme_1" checked>
                    <label for="site_theme_1"></label>
                  </span>
                            <span>
                    <input type="radio" name="site_theme" value="dark" id="site_theme_2">
                    <label for="site_theme_2"></label>
                  </span>
                            <span>
                    <input type="radio" name="site_theme" value="translucent" id="site_theme_3">
                    <label for="site_theme_3"></label>
                  </span>
                        </li>
                        <li class="drop-title"><p>LOGO</p></li>
                        <li class="drop-skin-li clearfix">
                  <span class="inverse">
                    <input type="radio" name="logo_bg" value="default" id="logo_bg_1" checked>
                    <label for="logo_bg_1"></label>
                  </span>
                            <span>
                    <input type="radio" name="logo_bg" value="color_2" id="logo_bg_2">
                    <label for="logo_bg_2"></label>
                  </span>
                            <span>
                    <input type="radio" name="logo_bg" value="color_3" id="logo_bg_3">
                    <label for="logo_bg_3"></label>
                  </span>
                            <span>
                    <input type="radio" name="logo_bg" value="color_4" id="logo_bg_4">
                    <label for="logo_bg_4"></label>
                  </span>
                            <span>
                    <input type="radio" name="logo_bg" value="color_5" id="logo_bg_5">
                    <label for="logo_bg_5"></label>
                  </span>
                            <span>
                    <input type="radio" name="logo_bg" value="color_6" id="logo_bg_6">
                    <label for="logo_bg_6"></label>
                  </span>
                            <span>
                    <input type="radio" name="logo_bg" value="color_7" id="logo_bg_7">
                    <label for="logo_bg_7"></label>
                  </span>
                            <span>
                    <input type="radio" name="logo_bg" value="color_8" id="logo_bg_8">
                    <label for="logo_bg_8"></label>
                  </span>
                        </li>
                        <li class="drop-title"><p>头部</p></li>
                        <li class="drop-skin-li clearfix">
                  <span class="inverse">
                    <input type="radio" name="header_bg" value="default" id="header_bg_1" checked>
                    <label for="header_bg_1"></label>
                  </span>
                            <span>
                    <input type="radio" name="header_bg" value="color_2" id="header_bg_2">
                    <label for="header_bg_2"></label>
                  </span>
                            <span>
                    <input type="radio" name="header_bg" value="color_3" id="header_bg_3">
                    <label for="header_bg_3"></label>
                  </span>
                            <span>
                    <input type="radio" name="header_bg" value="color_4" id="header_bg_4">
                    <label for="header_bg_4"></label>
                  </span>
                            <span>
                    <input type="radio" name="header_bg" value="color_5" id="header_bg_5">
                    <label for="header_bg_5"></label>
                  </span>
                            <span>
                    <input type="radio" name="header_bg" value="color_6" id="header_bg_6">
                    <label for="header_bg_6"></label>
                  </span>
                            <span>
                    <input type="radio" name="header_bg" value="color_7" id="header_bg_7">
                    <label for="header_bg_7"></label>
                  </span>
                            <span>
                    <input type="radio" name="header_bg" value="color_8" id="header_bg_8">
                    <label for="header_bg_8"></label>
                  </span>
                        </li>
                        <li class="drop-title"><p>侧边栏</p></li>
                        <li class="drop-skin-li clearfix">
                  <span class="inverse">
                    <input type="radio" name="sidebar_bg" value="default" id="sidebar_bg_1" checked>
                    <label for="sidebar_bg_1"></label>
                  </span>
                            <span>
                    <input type="radio" name="sidebar_bg" value="color_2" id="sidebar_bg_2">
                    <label for="sidebar_bg_2"></label>
                  </span>
                            <span>
                    <input type="radio" name="sidebar_bg" value="color_3" id="sidebar_bg_3">
                    <label for="sidebar_bg_3"></label>
                  </span>
                            <span>
                    <input type="radio" name="sidebar_bg" value="color_4" id="sidebar_bg_4">
                    <label for="sidebar_bg_4"></label>
                  </span>
                            <span>
                    <input type="radio" name="sidebar_bg" value="color_5" id="sidebar_bg_5">
                    <label for="sidebar_bg_5"></label>
                  </span>
                            <span>
                    <input type="radio" name="sidebar_bg" value="color_6" id="sidebar_bg_6">
                    <label for="sidebar_bg_6"></label>
                  </span>
                            <span>
                    <input type="radio" name="sidebar_bg" value="color_7" id="sidebar_bg_7">
                    <label for="sidebar_bg_7"></label>
                  </span>
                            <span>
                    <input type="radio" name="sidebar_bg" value="color_8" id="sidebar_bg_8">
                    <label for="sidebar_bg_8"></label>
                  </span>
                        </li>
                    </ul>
                </li>
                <!--切换主题配色-->
            </ul>

        </div>
    </nav>

</header>
<!--End 头部信息-->
<meta name="csrf-token" content="<%= csrfToken %>">
<script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/csrf-ajax.js"></script>
<jsp:include page="_lang_switcher.jsp"/>