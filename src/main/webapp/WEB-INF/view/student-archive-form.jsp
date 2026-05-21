<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.COMP2013J.assignment.entity.StudentArchive" %>
<%
    String formMode = request.getAttribute("formMode") == null ? "add" : String.valueOf(request.getAttribute("formMode"));
    boolean isEdit = "edit".equals(formMode);
    StudentArchive archive = (StudentArchive) request.getAttribute("archive");
    String prefillSno = request.getAttribute("prefillSno") == null ? "" : String.valueOf(request.getAttribute("prefillSno"));
    String prefillClazzno = request.getAttribute("prefillClazzno") == null ? "" : String.valueOf(request.getAttribute("prefillClazzno"));
    if (isEdit && archive != null) {
        prefillSno = archive.getSno();
        prefillClazzno = archive.getClazzno() == null ? "" : archive.getClazzno();
    }
%>
<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"/>
    <title><%= isEdit ? "修改个人档案" : "填写个人档案" %></title>
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
                            <div class="card-header"><h4><%= isEdit ? "修改个人档案" : "填写个人档案" %></h4></div>
                            <div class="card-body">
                                <p class="text-muted">学号由系统自动关联，每人仅可拥有一份档案。</p>
                                <form id="archiveForm" onsubmit="return false;">
                                    <% if (isEdit) { %>
                                    <input type="hidden" id="archiveId" value="<%= archive.getArchiveId() %>">
                                    <% } %>
                                    <div class="row">
                                        <div class="col-md-6 form-group">
                                            <label>学号</label>
                                            <input class="form-control" type="text" value="<%= prefillSno %>" readonly>
                                        </div>
                                        <div class="col-md-6 form-group">
                                            <label>身份证号 <span class="text-danger">*</span></label>
                                            <input class="form-control" type="text" id="idCard" maxlength="18"
                                                   value="<%= isEdit && archive != null && archive.getIdCard() != null ? archive.getIdCard() : "" %>"
                                                   placeholder="18位身份证号">
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-md-6 form-group">
                                            <label>民族</label>
                                            <input class="form-control" type="text" id="nationality"
                                                   value="<%= isEdit && archive != null && archive.getNationality() != null ? archive.getNationality() : "" %>">
                                        </div>
                                        <div class="col-md-6 form-group">
                                            <label>籍贯</label>
                                            <input class="form-control" type="text" id="birthPlace"
                                                   value="<%= isEdit && archive != null && archive.getBirthPlace() != null ? archive.getBirthPlace() : "" %>">
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-md-6 form-group">
                                            <label>政治面貌</label>
                                            <input class="form-control" type="text" id="politicalStatus"
                                                   value="<%= isEdit && archive != null && archive.getPoliticalStatus() != null ? archive.getPoliticalStatus() : "" %>"
                                                   placeholder="如：共青团员、群众">
                                        </div>
                                        <div class="col-md-6 form-group">
                                            <label>入学类别</label>
                                            <input class="form-control" type="text" id="enrollmentType"
                                                   value="<%= isEdit && archive != null && archive.getEnrollmentType() != null ? archive.getEnrollmentType() : "" %>"
                                                   placeholder="如：统招、专升本">
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-md-6 form-group">
                                            <label>专业</label>
                                            <input class="form-control" type="text" id="major"
                                                   value="<%= isEdit && archive != null && archive.getMajor() != null ? archive.getMajor() : "" %>">
                                        </div>
                                        <div class="col-md-6 form-group">
                                            <label>班级编号</label>
                                            <input class="form-control" type="text" id="clazzno" value="<%= prefillClazzno %>">
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label>毕业中学</label>
                                        <input class="form-control" type="text" id="graduationSchool"
                                               value="<%= isEdit && archive != null && archive.getGraduationSchool() != null ? archive.getGraduationSchool() : "" %>">
                                    </div>
                                    <div class="row">
                                        <div class="col-md-6 form-group">
                                            <label>档案状态</label>
                                            <select class="form-control" id="archiveStatus">
                                                <option value="0" <%= isEdit && archive != null && archive.getArchiveStatus() != null && archive.getArchiveStatus() == 0 ? "selected" : (!isEdit ? "selected" : "") %>>草稿</option>
                                                <option value="1" <%= isEdit && archive != null && archive.getArchiveStatus() != null && archive.getArchiveStatus() == 1 ? "selected" : "" %>>已提交</option>
                                            </select>
                                        </div>
                                        <div class="col-md-6 form-group">
                                            <label>监护人姓名</label>
                                            <input class="form-control" type="text" id="guardianName"
                                                   value="<%= isEdit && archive != null && archive.getGuardianName() != null ? archive.getGuardianName() : "" %>">
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label>监护人电话</label>
                                        <input class="form-control" type="text" id="guardianPhone" maxlength="11"
                                               value="<%= isEdit && archive != null && archive.getGuardianPhone() != null ? archive.getGuardianPhone() : "" %>"
                                               placeholder="11位手机号">
                                    </div>
                                    <div class="form-group">
                                        <button class="btn btn-primary" type="button" onclick="submitForm()">保存</button>
                                        <button class="btn btn-default" type="button" onclick="location.href='${pageContext.request.contextPath}/studentArchive'">返回</button>
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

<script src="${pageContext.request.contextPath}/assets/js/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/assets/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/assets/js/main.min.js"></script>
<script>
    function submitForm() {
        var data = {
            r: "<%= isEdit ? "edit" : "add" %>",
            idCard: $("#idCard").val(),
            nationality: $("#nationality").val(),
            birthPlace: $("#birthPlace").val(),
            politicalStatus: $("#politicalStatus").val(),
            enrollmentType: $("#enrollmentType").val(),
            major: $("#major").val(),
            clazzno: $("#clazzno").val(),
            graduationSchool: $("#graduationSchool").val(),
            archiveStatus: $("#archiveStatus").val(),
            guardianName: $("#guardianName").val(),
            guardianPhone: $("#guardianPhone").val()
        };
        <% if (isEdit) { %>
        data.archiveId = $("#archiveId").val();
        <% } %>
        $.post("${pageContext.request.contextPath}/studentArchive", data, function (res) {
            if (res.success) {
                alert("<%= isEdit ? "保存成功" : "创建成功" %>");
                location.href = "${pageContext.request.contextPath}/studentArchive";
            } else {
                alert(res.message || "保存失败");
            }
        }, "json");
    }
</script>
</body>
</html>
