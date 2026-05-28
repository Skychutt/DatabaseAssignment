/**
 * 全站中英文切换（登录页 + 后台各页）
 * 用法：页面引入 i18n.js 与 _lang_switcher.jsp；文本节点按中文原文替换为英文
 *
 * @file i18n.js
 * @global jQuery 由页面在 i18n.js 之后加载（见 _lang_switcher.jsp / 各页 script 顺序）
 */
/* global jQuery, NodeFilter, CustomEvent */
// noinspection JSNonAsciiCharacters
(function (window) {
    "use strict";

    var STORAGE_KEY = "app_lang";
    var currentLang = "en";
    var isApplyingLanguage = false;
    var langApplyTimer = null;

    function readStoredLang() {
        try {
            var stored = sessionStorage.getItem(STORAGE_KEY);
            if (stored === "zh" || stored === "en") {
                return stored;
            }
        } catch (e) { /* ignore */ }
        return "en";
    }

    /** 中文 -> 英文（按长度降序替换，避免短词误伤） */
    var TEXT_MAP = {
        "登录页面 - 楚天泽的班级管理系统": "Login - Student Management System",
        "楚天泽的学生管理项目": "Student Management Project",
        "作者b站账号": "Author Bilibili",
        "请输入您的用户名": "Username",
        "请输入密码": "Password",
        "验证码": "Captcha",
        "点击刷新": "Click to refresh",
        "管理员": "Admin",
        "立即登录": "Sign in",
        "注册用户": "Register",
        "用户名（管理员）/ 学号（学生）/ 工号（教师）": "Username / Student ID / Teacher ID",
        "教师姓名": "Teacher name",
        "班级编号": "Class ID",
        "立即注册": "Register now",
        "注册成功，请登录！": "Registered. Please sign in.",
        "请求服务器失败": "Server request failed",
        "请选择登录角色": "Please select a role",
        "验证码错误！": "Invalid captcha",
        "用户不存在": "User not found",
        "密码错误，登录失败": "Wrong password",
        "登录成功": "Signed in",
        "未知登录角色": "Unknown role",
        "权限不足": "Permission denied",
        "权限不足，仅管理员可发布公告": "Admins only",
        "权限不足，仅学生可管理个人档案": "Students only",
        "请先登录": "Please sign in first",
        "未知请求": "Unknown request",
        "保存成功": "Saved",
        "保存失败": "Save failed",
        "删除成功": "Deleted",
        "删除失败": "Delete failed",
        "发布成功": "Published",
        "发布失败": "Publish failed",
        "创建成功": "Created",
        "选课成功": "Enrolled",
        "选课失败": "Enroll failed",
        "已退选": "Dropped",
        "退选失败": "Drop failed",
        "已保存": "Saved",
        "后台首页": "Dashboard",
        "课程展示": "Course presentation",
        "功能": "Menu",
        "班级信息": "Classes",
        "学生信息": "Students",
        "教师管理": "Teachers",
        "教师名册": "Teacher list",
        "课程信息": "Courses",
        "课程浏览": "Course catalog",
        "个人档案": "My archive",
        "我的选课": "My enrollment",
        "选课管理": "Enrollment admin",
        "选课与打分": "Grading",
        "通讯录": "Directory",
        "公告管理": "Notices",
        "系统公告": "Announcements",
        "未登录": "Not signed in",
        "个人信息": "Profile",
        "修改密码": "Change password",
        "清空缓存": "Clear cache",
        "退出登录": "Sign out",
        "主题": "Theme",
        "头部": "Header",
        "侧边栏": "Sidebar",
        "班级数量": "Classes",
        "学生数量": "Students",
        "教师数量": "Teachers",
        "课程数量": "Courses",
        "选课系统": "Enrollment",
        "当前选课记录共": "Total enrollments:",
        "条（含所有学生的选课）。": " records (all students).",
        "进入我的选课（选课 / 退选 / 查成绩）": "My enrollment (enroll / drop / grades)",
        "选课与打分（仅本课程学生）": "Grade students in my courses",
        "选课管理（查看全部、维护成绩、移除记录）": "Manage all enrollments",
        "登录后可使用选课相关功能。": "Sign in to use enrollment features.",
        "班级学生数量": "Students per class",
        "教师与课程数量": "Teachers & courses",
        "学生数": "Students",
        "数量": "Count",
        "教师": "Teacher",
        "课程": "Course",
        "首页": "Home",
        "学生列表": "Student list",
        "浏览模式：可查询学生信息，已隐藏密码；修改本人资料请用右上角「个人信息」。": "Browse only: password hidden; edit profile via top-right menu.",
        "请输入学号": "Student ID",
        "请输入姓名": "Name",
        "请输入班级编号": "Class ID",
        "全部": "All",
        "男": "Male",
        "女": "Female",
        "查询": "Search",
        "新增": "Add",
        "每页显示": "Per page",
        "学号": "Student ID",
        "姓名": "Name",
        "性别": "Gender",
        "年龄": "Age",
        "电话": "Phone",
        "密码": "Password",
        "密码（隐藏）": "Password (hidden)",
        "入学时间": "Enroll date",
        "地址": "Address",
        "操作": "Actions",
        "修改": "Edit",
        "删除": "Delete",
        "共": "Total",
        "条，": " records, ",
        "当前第": "Page",
        "页": "",
        "确定删除学生": "Delete student",
        "吗？": "?",
        "教师列表": "Teacher list",
        "工号": "Teacher ID",
        "新增教师": "Add teacher",
        "编辑教师": "Edit teacher",
        "请输入工号": "Teacher ID",
        "返回列表": "Back to list",
        "确定删除教师": "Delete teacher",
        "班级列表": "Class list",
        "班级名": "Class name",
        "新增班级": "Add class",
        "编辑班级": "Edit class",
        "课程列表": "Course list",
        "课程号": "Course ID",
        "课程名": "Course name",
        "教师工号": "Teacher ID",
        "新增课程": "Add course",
        "编辑课程": "Edit course",
        "选课开始": "Enroll from",
        "选课结束": "Enroll until",
        "学分": "Credits",
        "人数上限": "Capacity",
        "已选人数": "Enrolled",
        "课程内容": "Description",
        "须已在教师表中存在": "Must exist in teacher table",
        "可选": "Optional",
        "如 3 或 2.5": "e.g. 3 or 2.5",
        "已选课程": "Enrolled courses",
        "在选课时间内可对下方「可选课程」点击": "During the enrollment window, click ",
        "选课": "Enroll",
        "；已选课程可": "; enrolled courses can ",
        "退选": "Drop",
        "（退选后成绩清空）。": " (grade cleared after drop).",
        "选课时间": "Enrolled at",
        "成绩": "Score",
        "评价": "Comment",
        "暂无已选课程，请在下方选择课程。": "No courses yet. Pick one below.",
        "可选课程（在选课时间内且未满员）": "Available courses",
        "当前没有可选课程（可能未到选课时间、已满员或已全部选过）。": "No courses available.",
        "已选/上限": "Enrolled / cap",
        "门可选，第": " available, page ",
        "确定退选": "Drop course",
        "？": "?",
        "我任课课程的选课学生（打分）": "Students in my courses (grading)",
        "仅列出": "Only shows ",
        "您作为任课教师": "courses you teach",
        "（课程表中教师工号与当前登录一致）的选课学生。修改成绩后点击「保存」。": ". Save after editing scores.",
        "暂无学生选您的课程，或课程尚未在系统中关联到您的工号。": "No students enrolled in your courses.",
        "保存": "Save",
        "选课记录管理（管理员）": "Enrollment records (admin)",
        "发布人": "Publisher",
        "发布日期": "Publish date",
        "公告内容": "Content",
        "关键词": "Keyword",
        "发布公告": "Post notice",
        "发布人将自动记录为当前登录的管理员账号。": "Publisher is the signed-in admin.",
        "请输入公告内容": "Notice content",
        "返回": "Back",
        "编辑公告": "Edit notice",
        "确定删除该公告吗？": "Delete this notice?",
        "填写个人档案": "Create archive",
        "修改个人档案": "Edit archive",
        "学号由系统自动关联，每人仅可拥有一份档案。": "Student ID is auto-linked; one archive per student.",
        "身份证号": "ID card",
        "民族": "Ethnicity",
        "籍贯": "Birth place",
        "政治面貌": "Political status",
        "入学类别": "Admission type",
        "专业": "Major",
        "毕业中学": "High school",
        "档案状态": "Status",
        "草稿": "Draft",
        "已提交": "Submitted",
        "监护人姓名": "Guardian",
        "监护人电话": "Guardian phone",
        "18位身份证号": "18-digit ID",
        "11位手机号": "11-digit phone",
        "如：共青团员、群众": "e.g. League member",
        "如：统招、专升本": "e.g. Regular admission",
        "档案编号": "Archive ID",
        "您尚未填写个人档案。点击下方按钮创建后，可进行查看、修改与删除。": "No archive yet. Click below to create.",
        "确定删除个人档案吗？删除后可重新填写。": "Delete archive? You can create again.",
        "角色": "Role",
        "学生": "Student",
        "用户名": "Username",
        "未获取到用户信息。": "User info not found.",
        "修改登录密码": "Change password",
        "原密码": "Current password",
        "新密码": "New password",
        "确认新密码": "Confirm password",
        "两次新密码不一致": "Passwords do not match",
        "管理员账号": "Admin accounts",
        "教师工号与姓名": "Teacher ID & name",
        "管理员通讯录": "Admin contacts",
        "教师通讯录": "Teacher directory",
        "学生通讯录": "Student contacts",
        "需要": "To ",
        "选课或退选": "enroll or drop",
        "？请打开左侧「": ", open ",
        "」：在选课时间内、未满员的课程可一键选课；已选课程可退选并查看教师给出的成绩与评价。": " in the sidebar: enroll during the window if not full; drop enrolled courses and view grades.",
        "上限": "Cap",
        "已选": "Enrolled",
        "内容摘要": "Summary",
        "旧密码": "Current password",
        "请输入旧密码": "Current password",
        "请输入新密码": "New password",
        "修改成功": "Password updated",
        "修改失败": "Update failed",
        "请求服务器失败，状态码：": "Server request failed, status: ",
        "公告编号无效": "Invalid notice ID",
        "无法获取当前管理员账号": "Cannot get admin account",
        "发布日期格式错误": "Invalid publish date",
        "用户类型不能为空！": "User type required",
        "注册成功": "Registered",
        "未知用户类型！": "Unknown user type",
        "原密码错误": "Wrong current password",
        "新密码不能与旧密码相同": "New password must differ",
        "参数不完整": "Incomplete parameters",
        "课程不存在": "Course not found",
        "当前不在该课程的选课时间内": "Outside enrollment period",
        "您已选过该课程": "Already enrolled",
        "课程人数已满": "Course is full",
        "未找到选课记录": "Enrollment not found",
        "选课记录不存在": "Record not found",
        "您不是该课程的任课教师，无权打分": "Not your course",
        "记录不存在": "Record not found",
        "您已存在个人档案，请使用修改功能": "Archive exists; use edit",
        "创建档案失败，请检查身份证号是否重复": "Create failed; check ID card",
        "档案编号无效": "Invalid archive ID",
        "档案不存在": "Archive not found",
        "无权修改他人档案": "Cannot edit others' archive",
        "无权删除他人档案": "Cannot delete others' archive",
        "身份证号不能为空": "ID card required",
        "身份证号须为18位": "ID card must be 18 digits",
        "档案状态无效": "Invalid status",
        "监护人电话须为11位": "Guardian phone must be 11 digits",
        "课程号不可为空！": "Course ID required",
        "教师工号不可为空！": "Teacher ID required",
        "课程名不可为空！": "Course name required",
        "教师工号不存在，请先在教师表中添加该教师。": "Teacher ID not found",
        "课程号已存在！": "Course ID exists",
        "删除失败：该课程已有": "Cannot delete: ",
        "条选课记录。": " enrollment(s)",
        "班级编号不可为空！": "Class ID required",
        "班级名不可为空！": "Class name required",
        "班级编号已存在！": "Class ID exists",
        "删除失败，该班级已有": "Cannot delete: class has ",
        "人": " student(s)",
        "工号不可为空": "Teacher ID required",
        "该教师仍有关联课程，无法删除。": "Teacher has courses",
        "工号已存在！": "Teacher ID exists",
        "学生学号不可为空！": "Student ID required",
        "班级不可为空！": "Class required",
        "学号已存在！": "Student ID exists",
        "新密码不可为空！": "New password required",
        "用户已存在！": "User exists",
        "无限制": "Unlimited",
        "不限": "No limit",
        "确定删除": "Confirm delete",
        "移除选课": "Remove enrollment",
        "保存成绩": "Save grade",
        "移除": "Remove",
        "任课教师": "Instructor",
        "开设课程数": "Courses taught",
        "开设课程": "Courses offered",
        "以下为可公开的教师信息，便于了解任课教师。密码等敏感信息不会展示。": "Public teacher information. Passwords are not shown.",
        "暂无教师信息。": "No teachers found.",
        "暂无课程": "No courses",
        "学号（学生）/ 工号（教师）": "Student ID / Teacher ID",
        "仅支持学生、教师自助注册；管理员账号由系统维护，不可在此注册。": "Students and teachers may self-register. Admin accounts are managed by the system.",
        "不允许自助注册管理员，请联系系统管理员。": "Admin self-registration is not allowed. Contact the system administrator.",
        "不允许自助注册管理员，请联系系统管理员添加账号。": "Admin self-registration is not allowed. Contact the system administrator.",
        "验证码错误，请按图片重新输入（点击图片可刷新）。": "Invalid captcha. Re-enter from the image (click to refresh).",
        "每页": "Per page",
        "请选择": "Please select",
        "留空则不修改密码": "Leave blank to keep password",
        "密码由本人通过右上角「修改密码」维护，管理员不可查看或修改。": "Passwords are changed by the user via Change password in the top menu; admins cannot view or change them.",
        "编辑学生": "Edit student",
        "教师信息": "Teachers",
        "密码状态": "Password status",
        "已设置（加密存储）": "Set (encrypted)",
        "未设置": "Not set",
        "入学日期": "Enrollment date",
        "班级管理": "Class management",
        " 门可选，第 ": " available, page ",
        " 条（含所有学生的选课）。": " enrollments (all students).",
        "（系统维护）": " (system-managed)",
        "确定删除教师 ": "Delete teacher ",
        "确定删除学生 ": "Delete student ",
        "确定删除班级 ": "Delete class ",
        "确定移除选课记录 ": "Remove enrollment ",
        "退选成功": "Dropped",
        "已移除选课记录": "Enrollment removed",
        "已移除": "Removed",
        "档案创建成功": "Archive created",
        "未知角色": "Unknown role",
        "会话用户无效": "Invalid session user",
        "新密码长度至少 6 位": "New password must be at least 6 characters",
        "开始日期格式错误": "Invalid start date",
        "结束日期格式错误": "Invalid end date",
        "年龄格式错误": "Invalid age",
        "入学日期格式错误": "Invalid enrollment date",
        "分数格式错误": "Invalid score",
        "人数上限格式错误": "Invalid capacity",
        "已选人数格式错误": "Invalid enrolled count",
        "您只能修改自己任教的课程": "You may only edit your own courses",
        "您只能删除自己任教的课程": "You may only delete your own courses",
        "仅管理员可维护班级信息": "Admins only can manage classes",
        "班级编号不存在，请填写正确的班级编号。": "Class ID not found. Enter a valid class ID.",
        "删除失败：该学生已有": "Cannot delete: student has ",
        "条选课记录，请先退选后再删除。": " enrollment(s). Drop courses first.",
        "删除失败：该学生仍有个人档案，请先删除档案后再删除学生。": "Cannot delete: student has an archive. Delete the archive first.",
        "删除失败，请稍后重试。": "Delete failed. Please try again.",
        "数据库连接失败，请检查 MySQL 是否启动，并确认 src/main/resources/jdbc.properties 中的账号密码正确。": "Database connection failed. Check MySQL and jdbc.properties.",
        "登录处理异常，请查看 Tomcat 控制台日志。": "Login error. Check Tomcat logs.",
        "以下为管理员发布的公告，您可查看内容与发布人信息。": "Notices posted by administrators. View content and publisher.",
        "暂无公告": "No notices",
        "全部选课记录": "All enrollment records",
        "条记录。可查看成绩、修改成绩与评价，或移除异常选课（移除后会同步减少课程已选人数）。": " record(s). View or edit scores and comments, or remove invalid enrollments (course enrollment count will decrease).",
        " 条记录。可查看成绩、修改成绩与评价，或移除异常选课（移除后会同步减少课程已选人数）。": " record(s). View or edit scores and comments, or remove invalid enrollments (course enrollment count will decrease).",
        "暂无选课数据": "No enrollment data",
        "失败": "Failed",
        "可选课程": "Available courses",
        "「可选课程」": "Available courses",
        "确定删除班级": "Delete class",
        "创建个人档案": "Create archive",
        "查看/修改档案": "View / edit archive",
        "删除档案": "Delete archive",
        "修改档案": "Edit archive",
        "浏览模式：仅可查询班级信息，不能新增、修改或删除。": "Browse only: view classes; cannot add, edit, or delete.",
        "班级名称": "Class name",
        "请输入班级名": "Enter class name",
        "请输入班级名称": "Enter class name",
        "请输入电话": "Phone",
        "请输入年龄": "Age",
        "请输入地址": "Address",
        "编号": "No.",
        "发布": "Publish",
        "选课起止": "Enroll period",
        "新增学生": "Add student",
        " 当前第": " Page ",
        " 条，\n                                ": " records, ",
        " 条，\n                                        ": " records, ",
        " 页": "",
        "创建时间": "Created at",
        "更新时间": "Updated at",
        "共 ": "Total ",
        " 条记录。": " record(s). ",
        " 吗？": "?",
        "返回档案页": "Back to archive",
        "提交": "Submit",
        "取消": "Cancel",
        "加载中…": "Loading…",
        "加载中...": "Loading...",
        "学生不存在": "Student not found",
        "被修改学生学号不可为空！": "Student ID required",
        "班级编号不存在。": "Class ID not found",
        "班级编号不存在，请先在班级表中添加该班级。": "Class ID not found. Add the class first.",
        " 人": " student(s)",
        "密码不可为空！": "Password required",
        "密码不可为空": "Password required",
        "姓名不可为空！": "Name required",
        "学号不可为空": "Student ID required",
        "旧密码错误": "Wrong current password",
        "新密码不可为空": "New password required",
        "仅学生可管理个人档案": "Students only",
        "学分格式错误": "Invalid credits",
        "当前不在该课程的退选时间内": "Outside drop period for this course",
        "教师工号不存在。": "Teacher ID not found",
        "教师数据加载失败，请检查数据库连接配置。": "Failed to load teachers. Check database configuration.",
        "学生数据加载失败，请检查数据库连接配置。": "Failed to load students. Check database configuration.",
        "班级数据加载失败，请检查数据库连接配置。": "Failed to load classes. Check database configuration.",
        "课程数据加载失败，请检查数据库连接配置。": "Failed to load courses. Check database configuration.",
        "公告数据加载失败，请检查数据库连接配置。": "Failed to load notices. Check database configuration.",
        "学生数据加载异常，请稍后重试。": "Failed to load students. Please try again.",
        "班级数据加载异常，请稍后重试。": "Failed to load classes. Please try again.",
        "课程数据加载异常，请稍后重试。": "Failed to load courses. Please try again.",
        "公告数据加载异常：": "Failed to load notices: ",
        "教师数据加载异常：": "Failed to load teachers: ",
        "发布人不能为空": "Publisher required",
        "公告内容不能为空": "Notice content required",
        "未登录或学号无效": "Not signed in or invalid student ID",
        "保存失败，请检查身份证号是否与其他档案重复": "Save failed. Check for duplicate ID card.",
        "删除失败，请检查身份证号是否与其他档案重复": "Delete failed. Check ID card.",
        "选课失败，请重试": "Enroll failed. Please try again.",
        "班级不存在": "Class not found",
        "用户名不可为空！": "Username required",
        "成功": "Success",
        "服务器异常": "Server error"
    };

    var TEXT_PATTERNS = [
        [/^共\s+(\d+)\s+门可选，第\s+(\d+)\s+\/\s+(\d+)\s+页$/, "Total $1 available, page $2 / $3"],
        [/^删除失败：该学生已有\s+(\d+)\s+条选课记录，请先退选后再删除。$/, "Cannot delete: student has $1 enrollment(s). Drop courses first."],
        [/^删除失败：该课程已有\s+(\d+)\s+条选课记录。$/, "Cannot delete: course has $1 enrollment(s)."],
        [/^删除失败，该班级已有\s+(\d+)\s+人$/, "Cannot delete: class has $1 student(s)."]
    ];

    var TITLE_MAP = {
        "首页": "Home",
        "学生信息": "Students",
        "登录页面 - 楚天泽的班级管理系统": "Login",
        "我的选课": "My enrollment",
        "教师通讯录": "Teacher directory",
        "个人信息": "Profile",
        "修改个人档案": "Edit archive",
        "填写个人档案": "Create archive",
        "修改密码": "Change password",
        "通讯录": "Directory",
        "课程信息": "Courses",
        "班级信息": "Classes",
        "教师管理": "Teachers",
        "公告管理": "Notices",
        "系统公告": "Announcements",
        "个人档案": "My archive",
        "选课管理": "Enrollment admin",
        "选课与打分": "Grading",
        "新增课程": "Add course",
        "编辑课程": "Edit course",
        "新增学生": "Add student",
        "编辑学生": "Edit student",
        "新增班级": "Add class",
        "编辑班级": "Edit class",
        "新增教师": "Add teacher",
        "编辑教师": "Edit teacher",
        "发布公告": "Post notice",
        "编辑公告": "Edit notice",
        "教师信息": "Teachers",
        "课程展示": "Course showcase"
    };

    var TEXT_KEYS_SORTED = Object.keys(TEXT_MAP).sort(function (a, b) {
        return b.length - a.length;
    });

    function getLang() {
        return currentLang;
    }

    function setLang(lang) {
        if (lang !== "zh" && lang !== "en") {
            return;
        }
        currentLang = lang;
        try {
            sessionStorage.setItem(STORAGE_KEY, lang);
        } catch (e) { /* ignore */ }
        try {
            localStorage.removeItem(STORAGE_KEY);
        } catch (e) { /* ignore */ }
    }

    function replaceTrimmed(str, trimmed, replacement) {
        if (trimmed === str) {
            return replacement;
        }
        var start = str.indexOf(trimmed);
        if (start === -1) {
            return str;
        }
        return str.substring(0, start) + replacement + str.substring(start + trimmed.length);
    }

    function translateString(str, lang) {
        if (!str || lang !== "en") {
            return str;
        }
        var trimmed = str.trim();
        if (TEXT_MAP[trimmed]) {
            return replaceTrimmed(str, trimmed, TEXT_MAP[trimmed]);
        }
        if (TEXT_MAP[str]) {
            return TEXT_MAP[str];
        }
        return str;
    }

    function containsChinese(text) {
        return /[\u4e00-\u9fff]/.test(text || "");
    }

    function replaceByDictionaryLongest(text) {
        if (!text || !containsChinese(text)) {
            return text;
        }
        var out = text;
        for (var i = 0; i < TEXT_KEYS_SORTED.length; i++) {
            var zh = TEXT_KEYS_SORTED[i];
            if (zh.length < 2) {
                continue;
            }
            if (out.indexOf(zh) !== -1) {
                out = out.split(zh).join(TEXT_MAP[zh]);
            }
        }
        return out;
    }

    var MSG_PATTERNS = [
        [/^确定删除班级\s+(.+?)\s+吗？$/, "Delete class $1?"],
        [/^确定删除学生\s+(.+?)\s+吗？$/, "Delete student $1?"],
        [/^确定删除教师\s+(.+?)\s+吗？$/, "Delete teacher $1?"],
        [/^确定删除课程\s+(.+?)\s+吗？$/, "Delete course $1?"],
        [/^确定退选\s+(.+?)\s+？$/, "Drop course $1?"],
        [/^确定移除选课记录\s+(.+?)\s+？$/, "Remove enrollment $1?"],
        [/^(.+?)（系统维护）$/, "$1 (system-managed)"]
    ];

    function translateDynamicMsg(msg) {
        for (var i = 0; i < MSG_PATTERNS.length; i++) {
            var m = msg.match(MSG_PATTERNS[i][0]);
            if (m) {
                return MSG_PATTERNS[i][1].replace("$1", m[1]);
            }
        }
        for (var j = 0; j < TEXT_PATTERNS.length; j++) {
            var m2 = msg.match(TEXT_PATTERNS[j][0]);
            if (m2) {
                var out = TEXT_PATTERNS[j][1];
                for (var k = 1; k < m2.length; k++) {
                    out = out.replace("$" + k, m2[k]);
                }
                return out;
            }
        }
        return null;
    }

    function shouldSkipNode(node) {
        var p = node.parentElement;
        if (!p) {
            return true;
        }
        var tag = p.tagName;
        if (tag === "SCRIPT" || tag === "STYLE" || tag === "NOSCRIPT") {
            return true;
        }
        if (p.closest && p.closest("#lang-switcher")) {
            return true;
        }
        return false;
    }

    function walkTextNodes(root, callback) {
        var walker = document.createTreeWalker(root, NodeFilter.SHOW_TEXT, null);
        var n;
        while ((n = walker.nextNode())) {
            if (!shouldSkipNode(n) && n.nodeValue && n.nodeValue.trim()) {
                callback(n);
            }
        }
    }

    function translateNodeText(orig, lang) {
        if (!orig || lang !== "en") {
            return orig;
        }
        var trimmed = orig.trim();
        var dynamic = translateDynamicMsg(trimmed);
        if (dynamic) {
            return orig.replace(trimmed, dynamic);
        }
        var translated = translateString(orig, "en");
        if (translated !== orig) {
            return translated;
        }
        if (trimmed !== orig && trimmed.length > 0) {
            var coreTr = translateString(trimmed, "en");
            if (coreTr !== trimmed) {
                return orig.replace(trimmed, coreTr);
            }
        }
        if (containsChinese(trimmed)) {
            var fallback = replaceByDictionaryLongest(trimmed);
            if (fallback !== trimmed) {
                return orig.replace(trimmed, fallback);
            }
        }
        return orig;
    }

    function applyTextNodes(lang) {
        walkTextNodes(document.body, function (node) {
            if (!node._i18nOrig) {
                node._i18nOrig = node.nodeValue;
            }
            if (lang === "zh") {
                node.nodeValue = node._i18nOrig;
            } else {
                node.nodeValue = translateNodeText(node._i18nOrig, "en");
            }
        });
    }

    function applySelectOptions(lang) {
        var options = document.querySelectorAll("select option");
        for (var i = 0; i < options.length; i++) {
            var opt = options[i];
            if (!opt._i18nOrig) {
                opt._i18nOrig = opt.textContent;
            }
            if (lang === "zh") {
                opt.textContent = opt._i18nOrig;
            } else {
                opt.textContent = translateNodeText(opt._i18nOrig, "en");
            }
        }
    }

    function applyAltAttributes(lang) {
        var list = document.querySelectorAll("[alt]");
        for (var i = 0; i < list.length; i++) {
            var el = list[i];
            if (el.closest && el.closest("#lang-switcher")) {
                continue;
            }
            if (!el._i18nAltOrig) {
                el._i18nAltOrig = el.getAttribute("alt");
            }
            if (lang === "zh") {
                el.setAttribute("alt", el._i18nAltOrig);
            } else {
                el.setAttribute("alt", translateString(el._i18nAltOrig, "en"));
            }
        }
    }

    function applyPlaceholders(lang) {
        var list = document.querySelectorAll("input[placeholder], textarea[placeholder]");
        for (var i = 0; i < list.length; i++) {
            var el = list[i];
            if (!el._i18nPhOrig) {
                el._i18nPhOrig = el.getAttribute("placeholder");
            }
            if (lang === "zh") {
                el.setAttribute("placeholder", el._i18nPhOrig);
            } else {
                el.setAttribute("placeholder", translateString(el._i18nPhOrig, "en"));
            }
        }
    }

    function applyInputValues(lang) {
        var list = document.querySelectorAll("input[value], textarea");
        for (var i = 0; i < list.length; i++) {
            var el = list[i];
            if (!el.getAttribute("value") && el.tagName !== "TEXTAREA") {
                continue;
            }
            if (!el._i18nValOrig) {
                el._i18nValOrig = el.getAttribute("value") || el.value;
            }
            if (lang === "zh") {
                el.value = el._i18nValOrig;
                el.setAttribute("value", el._i18nValOrig);
            } else {
                var translated = translateDynamicMsg(el._i18nValOrig) || translateString(el._i18nValOrig, "en");
                el.value = translated;
                el.setAttribute("value", translated);
            }
        }
    }

    function applyTitles(lang) {
        var list = document.querySelectorAll("[title]");
        for (var i = 0; i < list.length; i++) {
            var el = list[i];
            if (el.closest && el.closest("#lang-switcher")) {
                continue;
            }
            if (!el._i18nTitleOrig) {
                el._i18nTitleOrig = el.getAttribute("title");
            }
            if (lang === "zh") {
                el.setAttribute("title", el._i18nTitleOrig);
            } else {
                el.setAttribute("title", translateString(el._i18nTitleOrig, "en"));
            }
        }
    }

    function applyDataI18n(lang) {
        document.querySelectorAll("[data-i18n]").forEach(function (el) {
            var key = el.getAttribute("data-i18n");
            if (!key) {
                return;
            }
            if (!el._i18nHtmlOrig) {
                el._i18nHtmlOrig = el.innerHTML;
            }
            if (lang === "zh" && el._i18nHtmlOrig) {
                el.innerHTML = el._i18nHtmlOrig;
            }
        });
    }

    function applyDocumentTitle(lang) {
        if (!document._i18nTitleOrig) {
            document._i18nTitleOrig = document.title;
        }
        if (lang === "zh") {
            document.title = document._i18nTitleOrig;
            return;
        }
        var t = document._i18nTitleOrig;
        document.title = TITLE_MAP[t] || translateString(t, "en");
    }

    function updateSwitcherButtons(lang) {
        var zhBtn = document.getElementById("lang-btn-zh");
        var enBtn = document.getElementById("lang-btn-en");
        if (zhBtn) {
            zhBtn.classList.toggle("active", lang === "zh");
        }
        if (enBtn) {
            enBtn.classList.toggle("active", lang === "en");
        }
    }

    function applyLanguage(lang) {
        if (isApplyingLanguage) {
            return;
        }
        if (lang !== "zh" && lang !== "en") {
            lang = "en";
        }
        isApplyingLanguage = true;
        if (document.body) {
            document.body.classList.add("lang-switching");
        }
        setLang(lang);
        document.documentElement.lang = lang === "en" ? "en" : "zh";
        applyDataI18n(lang);
        applyTextNodes(lang);
        applySelectOptions(lang);
        applyPlaceholders(lang);
        applyInputValues(lang);
        applyTitles(lang);
        applyAltAttributes(lang);
        applyDocumentTitle(lang);
        updateSwitcherButtons(lang);
        try {
            document.dispatchEvent(new CustomEvent("app-lang-change", { detail: lang }));
        } catch (e) { /* ignore */ }
        if (langApplyTimer) {
            clearTimeout(langApplyTimer);
        }
        langApplyTimer = setTimeout(function () {
            if (document.body) {
                document.body.classList.remove("lang-switching");
            }
            isApplyingLanguage = false;
        }, 16);
    }

    function translateMsg(msg) {
        if (!msg || getLang() !== "en") {
            return msg;
        }
        var s = String(msg);
        var trimmed = s.trim();
        if (TEXT_MAP[trimmed]) {
            return TEXT_MAP[trimmed];
        }
        var dynamic = translateDynamicMsg(trimmed);
        if (dynamic) {
            return dynamic;
        }
        var exact = translateString(s, "en");
        if (exact !== s) {
            return exact;
        }
        return replaceByDictionaryLongest(s);
    }

    function initSwitcher() {
        document.addEventListener("click", function (e) {
            if (!e || !e.target || !e.target.id) {
                return;
            }
            if (e.target.id === "lang-btn-zh") {
                applyLanguage("zh");
                return;
            }
            if (e.target.id === "lang-btn-en") {
                applyLanguage("en");
            }
        });
    }

    function patchDialogs() {
        var _alert = window.alert;
        var _confirm = window.confirm;
        window.alert = function (msg) {
            return _alert(translateMsg(msg));
        };
        window.confirm = function (msg) {
            return _confirm(translateMsg(msg));
        };
    }

    function patchJQueryText() {
        var $ = window.jQuery;
        if (!$ || $.fn._i18nTextPatched) {
            return;
        }
        var origText = $.fn.text;
        $.fn.text = function (value) {
            if (value !== undefined && typeof value === "string" && getLang() === "en") {
                value = translateMsg(value);
            }
            return origText.apply(this, arguments.length ? [value] : []);
        };
        $.fn._i18nTextPatched = true;
    }

    function init() {
        currentLang = readStoredLang();
        try {
            var u = new URL(window.location.href);
            var qLang = u.searchParams.get("_lang");
            if (qLang === "zh" || qLang === "en") {
                currentLang = qLang;
                setLang(qLang);
                u.searchParams.delete("_lang");
                window.history.replaceState({}, "", u.toString());
            }
        } catch (e0) { /* ignore */ }
        try {
            localStorage.removeItem(STORAGE_KEY);
        } catch (e) { /* ignore */ }
        patchDialogs();
        initSwitcher();
        patchJQueryText();
        observeDomChanges();
        applyLanguage(getLang());
    }

    function observeDomChanges() {
        if (!window.MutationObserver || observeDomChanges._started) {
            return;
        }
        observeDomChanges._started = true;
        var timer = null;
        var observer = new MutationObserver(function (mutations) {
            if (isApplyingLanguage) {
                return;
            }
            if (getLang() !== "en") {
                return;
            }
            for (var i = 0; i < mutations.length; i++) {
                if (mutations[i].type === "childList" && mutations[i].addedNodes.length > 0) {
                    clearTimeout(timer);
                    timer = setTimeout(function () {
                        applyLanguage("en");
                    }, 30);
                    return;
                }
            }
        });
        if (document.body) {
            observer.observe(document.body, { childList: true, subtree: true });
        }
    }

    function scheduleInit() {
        init();
        setTimeout(function () {
            applyLanguage(getLang());
        }, 0);
        setTimeout(function () {
            applyLanguage(getLang());
        }, 120);
    }

    if (document.readyState === "loading") {
        document.addEventListener("DOMContentLoaded", scheduleInit);
    } else {
        scheduleInit();
    }

    window.addEventListener("load", function () {
        applyLanguage(getLang());
    });

    window.I18N = {
        getLang: getLang,
        setLang: setLang,
        applyLanguage: applyLanguage,
        translateMsg: translateMsg,
        translateString: translateString
    };
})(window);
