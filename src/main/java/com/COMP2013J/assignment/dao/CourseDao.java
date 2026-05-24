package com.COMP2013J.assignment.dao;

import com.COMP2013J.assignment.entity.Course;
import com.COMP2013J.assignment.utils.JdbcHelper;
import com.COMP2013J.assignment.utils.vo.PagerVO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CourseDao {

    public int insert(Course course) {
        JdbcHelper helper = new JdbcHelper();
        int res = helper.executeUpdate(
                "insert into tb_course (cno,tno,cname,begindate,enddate,credits,maximum,`count`,content) values(?,?,?,?,?,?,?,?,?)",
                course.getCno(),
                course.getTno(),
                course.getCname(),
                course.getBegindate(),
                course.getEnddate(),
                course.getCredits(),
                course.getMaximum(),
                course.getCount(),
                course.getContent()
        );
        helper.closeDB();
        return res;
    }

    public int update(Course course) {
        JdbcHelper helper = new JdbcHelper();
        String sql = "update tb_course set ";
        List<Object> params = new ArrayList<>();
        if (course.getTno() != null) {
            sql += "tno = ?,";
            params.add(course.getTno());
        }
        if (course.getCname() != null) {
            sql += "cname = ?,";
            params.add(course.getCname());
        }
        if (course.getBegindate() != null) {
            sql += "begindate = ?,";
            params.add(course.getBegindate());
        }
        if (course.getEnddate() != null) {
            sql += "enddate = ?,";
            params.add(course.getEnddate());
        }
        if (course.getCredits() != null) {
            sql += "credits = ?,";
            params.add(course.getCredits());
        }
        if (course.getMaximum() != null) {
            sql += "maximum = ?,";
            params.add(course.getMaximum());
        }
        if (course.getCount() != null) {
            sql += "`count` = ?,";
            params.add(course.getCount());
        }
        if (course.getContent() != null) {
            sql += "content = ?,";
            params.add(course.getContent());
        }
        if (params.isEmpty()) {
            helper.closeDB();
            return 0;
        }
        sql = sql.substring(0, sql.length() - 1);
        sql += " where cno = ?";
        params.add(course.getCno());
        int res = helper.executeUpdate(sql, params.toArray());
        helper.closeDB();
        return res;
    }

    public int delete(String cno) {
        JdbcHelper helper = new JdbcHelper();
        int res = helper.executeUpdate("delete from tb_course where cno = ?", cno);
        helper.closeDB();
        return res;
    }

    /** 在未满员时原子增加已选人数，返回是否成功 */
    public boolean tryIncrementEnrollment(String cno) {
        JdbcHelper helper = new JdbcHelper();
        int res = helper.executeUpdate(
                "update tb_course set `count` = IFNULL(`count`,0) + 1 where cno = ? "
                        + "and (maximum is null or maximum <= 0 or IFNULL(`count`,0) < maximum)",
                cno);
        helper.closeDB();
        return res == 1;
    }

    public PagerVO<Course> page(int current, int size, String whereSql, Object... whereParams) {
        PagerVO<Course> pagerVO = new PagerVO<>();
        pagerVO.setCurrent(current);
        pagerVO.setSize(size);
        JdbcHelper helper = new JdbcHelper();
        if (whereSql == null) {
            whereSql = " ";
        }
        try {
            ResultSet resultSet = helper.executeQuery("select count(1) from tb_course " + whereSql, whereParams);
            resultSet.next();
            pagerVO.setTotal(resultSet.getInt(1));

            Object[] listParams = appendLimitParams(whereParams, (current - 1) * size, size);
            resultSet = helper.executeQuery(
                    "select * from tb_course " + whereSql + " limit ?, ?", listParams);
            List<Course> list = new ArrayList<>();
            while (resultSet.next()) {
                list.add(toEntity(resultSet));
            }
            pagerVO.setList(list);
            return pagerVO;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            helper.closeDB();
        }
        return null;
    }

    private Object[] appendLimitParams(Object[] whereParams, int offset, int limit) {
        Object[] listParams = new Object[(whereParams == null ? 0 : whereParams.length) + 2];
        if (whereParams != null) {
            System.arraycopy(whereParams, 0, listParams, 0, whereParams.length);
        }
        listParams[listParams.length - 2] = offset;
        listParams[listParams.length - 1] = limit;
        return listParams;
    }

    private Course toEntity(ResultSet resultSet) throws SQLException {
        Course course = new Course();
        course.setCno(resultSet.getString("cno"));
        course.setTno(resultSet.getString("tno"));
        course.setCname(resultSet.getString("cname"));
        course.setBegindate(resultSet.getDate("begindate"));
        course.setEnddate(resultSet.getDate("enddate"));
        double cred = resultSet.getDouble("credits");
        course.setCredits(resultSet.wasNull() ? null : cred);
        int max = resultSet.getInt("maximum");
        course.setMaximum(resultSet.wasNull() ? null : max);
        int cnt = resultSet.getInt("count");
        course.setCount(resultSet.wasNull() ? null : cnt);
        course.setContent(resultSet.getString("content"));
        return course;
    }

    public int count() {
        JdbcHelper helper = new JdbcHelper();
        ResultSet resultSet = helper.executeQuery("select count(1) from tb_course");
        try {
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            helper.closeDB();
        }
        return 0;
    }

    public int countStuCourseByCno(String cno) {
        JdbcHelper helper = new JdbcHelper();
        ResultSet resultSet = helper.executeQuery("select count(1) from tb_stu_course where cno = ?", cno);
        try {
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            helper.closeDB();
        }
        return 0;
    }

    public Course getByCno(String cno) {
        JdbcHelper helper = new JdbcHelper();
        ResultSet resultSet = helper.executeQuery("select * from tb_course where cno = ?", cno);
        try {
            if (resultSet.next()) {
                return toEntity(resultSet);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            helper.closeDB();
        }
        return null;
    }

    public int countByTno(String tno) {
        JdbcHelper helper = new JdbcHelper();
        ResultSet resultSet = helper.executeQuery("select count(1) from tb_course where tno = ?", tno);
        try {
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            helper.closeDB();
        }
        return 0;
    }

    public int changeEnrollmentCount(String cno, int delta) {
        JdbcHelper helper = new JdbcHelper();
        int res = helper.executeUpdate(
                "update tb_course set `count` = GREATEST(0, IFNULL(`count`,0) + ?) where cno = ?",
                delta, cno);
        helper.closeDB();
        return res;
    }

    /**
     * 学生可选课程：未选、在选课日期内、未满员
     */
    public PagerVO<Course> pageChoosableForStudent(String sno, int current, int size) {
        PagerVO<Course> pagerVO = new PagerVO<>();
        pagerVO.setCurrent(current);
        pagerVO.setSize(size);
        String where = " FROM tb_course c WHERE NOT EXISTS (SELECT 1 FROM tb_stu_course sc WHERE sc.cno = c.cno AND sc.sno = ?) "
                + " AND (c.begindate IS NULL OR c.begindate <= CURDATE()) "
                + " AND (c.enddate IS NULL OR c.enddate >= CURDATE()) "
                + " AND (c.maximum IS NULL OR c.maximum <= 0 OR IFNULL(c.`count`,0) < c.maximum)";
        int total = 0;
        JdbcHelper h1 = new JdbcHelper();
        try {
            ResultSet rs = h1.executeQuery("select count(1) " + where, sno);
            if (rs != null && rs.next()) {
                total = rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            h1.closeDB();
        }
        pagerVO.setTotal(total);
        JdbcHelper h2 = new JdbcHelper();
        try {
            ResultSet rs = h2.executeQuery(
                    "select c.* " + where + " order by c.cno limit " + ((current - 1) * size) + "," + size,
                    sno);
            List<Course> list = new ArrayList<>();
            while (rs != null && rs.next()) {
                list.add(toEntity(rs));
            }
            pagerVO.setList(list);
            return pagerVO;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            h2.closeDB();
        }
        return null;
    }
}
