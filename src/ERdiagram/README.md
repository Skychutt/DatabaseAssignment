# stu_manage 概念 ER / EER 图

依据 **COMP2013J** 课件：

- Lecture 8 — Chen ER（实体矩形、属性椭圆、联系菱形、基数、双线/部分参与）
- Lecture 9 — EER（不相交特化 `d`、ISA 三角、子类继承）
- Lecture 10 — 概念模型到关系模式的映射说明

数据内容与 `stu_manage.png` / `sql/...-dump.sql` 中 **8 张表** 一致。

## 文件

| 文件 | 说明 |
|------|------|
| `ER_Diagram.png` / `.svg` | **概念 ER 图**（Chen 记法） |
| `EER_Diagram.png` / `.svg` | **EER 图**（含 USER 超类与 ADMIN / TEACHER / STUDENT 子类） |
| `stu_manage.png` | 逻辑层表结构参考图（Navicat 风格） |
| `generate_diagrams.py` | 重新生成：`python generate_diagrams.py` |

## 实体与属性（与表对应）

| 概念实体 | 物理表 | 主键 |
|----------|--------|------|
| ADMIN | tb_admin | username |
| TEACHER | tb_teacher | tno |
| CLASS | tb_clazz | clazzno |
| STUDENT | tb_student | sno |
| COURSE | tb_course | cno |
| NOTICE | tb_notice | id |
| STUDENT_ARCHIVE | tb_student_archive | archive_id |
| （联系）Enrolls | tb_stu_course | (cno, sno) |

`tb_stu_course` 在概念层建模为 **Enrolls** 联系及其属性 `chosetime`、`score`、`evaluation`（Lecture 8：联系可有属性；Lecture 10 步骤 5 映射为独立关系表）。

## 联系与参与（ER 图）

| 联系 | 基数 | 参与（双线=完全，虚线=部分） |
|------|------|------------------------------|
| Publishes | ADMIN 1 — N NOTICE | 公告端完全（必有发布人） |
| Teaches | TEACHER 1 — N COURSE | 两端均可选（课程可先无教师） |
| Belongs to | CLASS 1 — N STUDENT | 学生端完全（须属一班） |
| Enrolls | STUDENT M — N COURSE | 两端均可选 |
| Has Archive | STUDENT 1 — 1 STUDENT_ARCHIVE | 档案端完全；学生端部分（可无档案） |

## EER 扩展

- **USER** 超类：`login_id`、`password`（对应三种登录账号概念）
- **ISA + d**：子类 **ADMIN / TEACHER / STUDENT** 互斥（disjoint）
- 双线：每个 USER 必须且仅能属于一个子类（与系统角色一致）
- 子类各自保留特有属性；仅 **STUDENT** 参与 Belongs to、Enrolls、Has Archive；**TEACHER** 参与 Teaches；**ADMIN** 参与 Publishes

## 映射到逻辑模式（Lecture 10）

见 ER 图左下角黄框：

1. 强实体 → 各一张表  
2. 1:N → N 端加 FK（如 `tb_student.clazzno`）  
3. M:N Enrolls → `tb_stu_course`  
4. 1:1 Has Archive → `tb_student_archive.sno` UNIQUE FK  
5. 特化 → 实现为 `tb_admin`、`tb_teacher`、`tb_student` 三张独立表（无 ISA 表）
