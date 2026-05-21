# 学生管理系统 DatabaseAssignment

## 项目介绍

本项目是基于 **Maven + JDK17 + Tomcat10 + MySQL + JDBC（Servlet/JSP）** 的 Java Web 学生管理项目，打包格式为 war 包。团队成员拉取项目后，按照本文档步骤即可完整运行项目。

## 技术栈

- 构建工具：Maven
- JDK 版本：17
- 运行服务器：Tomcat 10+
- 数据库：MySQL 8.0
- 持久层：JDBC + DAO（`JdbcHelper`，未使用 Hibernate 等 ORM）

## 环境必备

电脑必须提前安装配置好以下环境：

1. JDK 17
2. Maven 3.6+
3. MySQL 8.0
4. Tomcat 10 及以上（**不支持 Tomcat9 及以下**）
5. IDEA 开发工具

---

## 一、数据库配置（所有使用者必须第一步操作）

### 1. 项目数据库文件说明

项目根目录 **sql/** 下的 SQL 备份文件为数据库脚本（包含表结构与测试数据）。若使用 `stu_manage_localhost-2026_05_15_20_11_38-dump.sql`，请只导入其中 `stu_manage` 库相关业务表，或自行导出精简版 `stu_manage_backup.sql`。

### 2. 本地创建数据库

打开数据库工具或 IDEA Database 控制台，执行 SQL：

```sql
CREATE DATABASE IF NOT EXISTS stu_manage DEFAULT CHARACTER SET utf8mb4;
```

### 3. 导入数据库（二选一）

**方式1：终端一键导入（推荐）**

在项目根目录 PowerShell 执行（把密码替换成自己的 MySQL 密码）：

```bash
mysql -u root -p --default-character-set=utf8mb4 stu_manage < sql/stu_manage_backup.sql
```

（若本地文件名为其他 dump，请替换路径。）

**方式2：IDEA 可视化导入**

1. IDEA 右侧 Database 连接本地 MySQL
2. 右键 `stu_manage` 数据库
3. 选择 `Run SQL Script`
4. 选中项目内 SQL 文件运行

### 补充：导出数据库方法

若需更新数据库并分享给队友，在项目根目录执行：

```bash
mysqldump -u root -p --default-character-set=utf8mb4 stu_manage > sql/stu_manage_backup.sql
```

---

## 二、修改本地数据库连接配置

找到 `src/main/java/com/COMP2013J/assignment/utils/JdbcHelper.java`，修改数据库账号密码：

```
private static final String user = "root";
private static final String pass = "你mysql数据库的密码"; // 自行修改
```

---

## 三、Maven 构建项目

1. IDEA 打开项目，等待 Maven 加载完毕
2. 终端执行：

```bash
mvn clean package
```

出现 **BUILD SUCCESS** 代表构建成功。

---

## 四、Tomcat 配置与运行项目

1. IDEA：`Add Configuration`
2. 新建 `Tomcat Server - Local`，选择 Tomcat 10+
3. Deployment 添加：`DatabaseAssignment:war exploded`
4. 运行

## 五、项目访问地址

默认：[http://localhost:8080/](http://localhost:8080/)

---

## 常见报错解决方案

- **Tomcat 启动报错**：本项目使用 Servlet 6，**仅支持 Tomcat 10 及以上**
- **数据库连接失败**：检查 MySQL 是否启动、`JdbcHelper.java` 账号密码是否正确、`stu_manage` 是否已导入
- **Maven 依赖异常**：刷新 Maven，必要时配置国内镜像
- **JDK 版本报错**：请使用 JDK 17
