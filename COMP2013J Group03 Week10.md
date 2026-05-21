# COMP2013J Group03 Week10

**To:** jie.chen1@ucdconnect.ie  
**Subject:** COMP2013J Group03 Week10  

---

## Self-assessment (Weeks 8–9)

Over the past two weeks, Group 03 established a shared Git workflow and agreed on the overall architecture for our Java web application backed by MySQL. We drafted an initial entity–relationship design and began implementing core tables and seed data, while scaffolding the Maven project structure and basic servlet routing. We held two short stand-ups per week to unblock integration issues early; progress is steady rather than rushed, and we identified a few risks (e.g. consistent error handling and session security) that we logged for the next sprint. Communication has been clear, though we still need to tighten our code-review habit before larger merges.

## Group plan (Weeks 11–12)

For the next two weeks we will keep a fixed weekly rhythm: mid-week integration on a shared branch and a short demo of working features each Friday. We aim to complete the remaining CRUD flows, refine authentication and filters, harden JDBC access patterns, and align JSP pages with the agreed layout. We will reserve the final days of Week 12 for cross-browser checks, basic load testing on local Tomcat, and updating our internal README so the final report appendix can include accurate Week 10 and Week 12 self-assessments. We are intentionally pacing work so that polish and documentation are not left until the last minute.

## Five-way division of labour (equal fifths)

To share responsibility fairly, we split the project into five parallel tracks of roughly equal scope; each member owns one fifth and still joins reviews for the others:

| Member      | Scope  |
|-------------|-------------------------------------|
| ChuTianze   | Database design, migrations, backup/restore scripts, and data integrity constraints |
| ZhengWeiwen | DAO layer and JDBC helpers (queries, transactions, pagination where needed) |
| CongZheming | Service layer and business rules (validation, orchestration between DAOs) |
| LiuZiming   | Web layer: servlets, filters, session/captcha handling, and `web.xml` configuration |
| HeSicheng   | JSP/views, shared layout, front-end assets, and end-to-end manual test checklists |

We rotate pairing on integration tasks so no single person becomes a bottleneck, and we will adjust names in the final report to match our official group roster.

---

