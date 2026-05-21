# COMP2013J Group03 Week12

**To:** jie.chen1@ucdconnect.ie  
**Subject:** COMP2013J Group03 Week12  

---

## Self-assessment (Weeks 11–12)

Over Weeks 11–12, Group 03 delivered the main features planned in our Week 10 update. We completed role-based access for **administrator**, **teacher**, and **student** logins (captcha, session handling, and `AuthFilter` protection of backend routes). CRUD and query flows are in place for **classes**, **students**, **teachers** (admin-managed), and **courses** (admin/teacher manage; students browse read-only). We implemented the **course enrolment** module (`tb_stu_course`): students can enrol and drop courses within the allowed window; teachers grade students on their own courses; administrators view and manage all enrolment records. We added the **notice** module (`tb_notice`, `publisher` linked to `tb_admin.username`): administrators publish, edit, and delete announcements; teachers and students can only view content and publisher information. Supporting features include the **directory**, **profile**, **password change**, registration, and a **dashboard** with counts and charts loaded via `IndexServlet`.

We followed our agreed rhythm: mid-week merges on a shared branch and short Friday demos. In the final days of Week 12 we ran manual checks on local Tomcat (Chrome and Edge), verified pagination and JSON responses for AJAX actions, and confirmed `mvn compile` succeeds. JDBC access remains centralised through `JdbcHelper` and DAO classes; business rules sit in the service layer with validation before writes. JSP pages share `_aside_header.jsp` and role-specific navigation labels (e.g. “我的选课” vs “选课与打分” vs “选课管理”). Communication stayed consistent; code review before merge improved, though we still want stricter review on SQL string building in a few DAO filters.

**Compared with our Week 10 plan:** remaining CRUD flows, authentication/filters, and layout alignment are largely done. Cross-browser smoke tests were completed locally; formal load testing was light (manual only, not automated). Internal README/documentation was updated enough to support deployment and demo, but the full final report appendix may still need a final polish pass before submission.

**Risks / lessons:** (1) Foreign keys (e.g. notice `publisher`, course `tno`) require seed data to match login accounts—integration tests should use consistent test admins. (2) Role checks must be enforced in both servlets (POST) and JSP (UI); we fixed a few cases where students could see management buttons they could not use. (3) We will keep pairing on integration to avoid one person owning all servlet–JSP wiring at the end.

---

## Deliverables summary (current system)

| Area | Status | Notes |
|------|--------|--------|
| Login / logout / captcha / register | Done | Admin, teacher, student roles |
| Auth filter & session | Done | Unauthenticated users redirected to `login.jsp` |
| Class & student management | Done | Teachers/students: list/query; write ops per role rules |
| Teacher management | Done | Admin only |
| Course management | Done | Admin + teacher; student read-only list |
| Enrolment & grading (`/stuCourse`) | Done | Student choose/drop; teacher grade; admin oversee |
| Notices (`/notice`) | Done | Admin CRUD; teacher/student read-only |
| Directory & profile | Done | All logged-in roles |
| Dashboard statistics | Done | Counts and chart data from `/index` |
| Database script | Done | `sql/stu_manage_localhost-2026_05_15_20_11_38-dump.sql` includes `tb_notice` |

---

## Five-way division of labour (equal fifths)

We kept the Week 10 split; below is what each member primarily delivered during Weeks 11–12 (all members still participated in reviews and integration):

| Member | Scope | Weeks 11–12 contribution (summary) |
|--------|--------|-------------------------------------|
| ChuTianze | Database design, migrations, backup/restore scripts, and data integrity constraints | `tb_stu_course` and `tb_notice` schema; FK to `tb_admin`; seed data and dump for local MySQL |
| ZhengWeiwen | DAO layer and JDBC helpers | `StuCourseDao`, `NoticeDao`, pagination queries, enrolment count updates on courses |
| CongZheming | Service layer and business rules | `StuCourseService`, `NoticeService`; enrolment window/capacity checks; grade permissions |
| LiuZiming | Web layer: servlets, filters, session/captcha | `StuCourseServlet`, `NoticeServlet`, `RoleHelper`, role guards on POST; `AuthFilter` tweaks |
| HeSicheng | JSP/views, shared layout, front-end assets | Enrolment and notice JSPs; sidebar links; dashboard enrolment block; manual test checklists |

We rotate pairing on integration tasks so no single person becomes a bottleneck. Names will be aligned with the official group roster in the final report.

---

## Outlook (submission)

For the remaining days before the assignment deadline we will freeze feature scope, run a full role-based walkthrough (admin / teacher / student), capture screenshots for the report, and complete the final written submission and appendix (including this Week 12 self-assessment alongside Week 10). Any non-critical UI polish will only be done if it does not risk regression before submission.

---
