package com.COMP2013J.assignment.dao;

import com.COMP2013J.assignment.entity.StuCourse;
import com.COMP2013J.assignment.entity.StuCourseView;
import com.COMP2013J.assignment.utils.JdbcHelper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StuCourseDao {

    public boolean exists(String cno, String sno) {
        JdbcHelper helper = new JdbcHelper();
        ResultSet rs = helper.executeQuery(
                "select 1 from tb_stu_course where cno = ? and sno = ? limit 1", cno, sno);
        try {
            return rs != null && rs.next();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            helper.closeDB();
        }
    }

    public int insert(StuCourse row) {
        JdbcHelper helper = new JdbcHelper();
        int res = helper.executeUpdate(
                "insert into tb_stu_course (cno,sno,chosetime,score,evaluation) values(?,?,?,?,?)",
                row.getCno(), row.getSno(), row.getChosetime(), row.getScore(), row.getEvaluation());
        helper.closeDB();
        return res;
    }

    public int delete(String cno, String sno) {
        JdbcHelper helper = new JdbcHelper();
        int res = helper.executeUpdate("delete from tb_stu_course where cno = ? and sno = ?", cno, sno);
        helper.closeDB();
        return res;
    }

    public int updateGrade(String cno, String sno, Double score, String evaluation) {
        JdbcHelper helper = new JdbcHelper();
        int res = helper.executeUpdate(
                "update tb_stu_course set score = ?, evaluation = ? where cno = ? and sno = ?",
                score, evaluation, cno, sno);
        helper.closeDB();
        return res;
    }

    public List<StuCourseView> listByStudent(String sno) {
        String sql = "select sc.cno, sc.sno, sc.chosetime, sc.score, sc.evaluation, c.cname, c.tno as teacher_tno, "
                + "t.tname as teacher_name, s.name as stu_name "
                + "from tb_stu_course sc "
                + "inner join tb_course c on sc.cno = c.cno "
                + "left join tb_teacher t on c.tno = t.tno "
                + "left join tb_student s on sc.sno = s.sno "
                + "where sc.sno = ? order by sc.chosetime desc, sc.cno";
        return queryViewList(sql, sno);
    }

    public List<StuCourseView> listForTeacher(String tno) {
        String sql = "select sc.cno, sc.sno, sc.chosetime, sc.score, sc.evaluation, c.cname, s.name as stu_name "
                + "from tb_stu_course sc "
                + "inner join tb_course c on sc.cno = c.cno "
                + "inner join tb_student s on sc.sno = s.sno "
                + "where c.tno = ? order by c.cno, sc.sno";
        return queryViewList(sql, tno);
    }

    public List<StuCourseView> listAll() {
        String sql = "select sc.cno, sc.sno, sc.chosetime, sc.score, sc.evaluation, c.cname, s.name as stu_name "
                + "from tb_stu_course sc "
                + "inner join tb_course c on sc.cno = c.cno "
                + "left join tb_student s on sc.sno = s.sno "
                + "order by sc.cno, sc.sno";
        return queryViewList(sql);
    }

    public int countAll() {
        JdbcHelper helper = new JdbcHelper();
        ResultSet rs = helper.executeQuery("select count(1) from tb_stu_course");
        try {
            if (rs != null && rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            helper.closeDB();
        }
        return 0;
    }

    private List<StuCourseView> queryViewList(String sql, Object... params) {
        List<StuCourseView> list = new ArrayList<>();
        JdbcHelper helper = new JdbcHelper();
        try {
            ResultSet rs = helper.executeQuery(sql, params);
            while (rs != null && rs.next()) {
                list.add(mapView(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            helper.closeDB();
        }
        return list;
    }

    private StuCourseView mapView(ResultSet rs) throws SQLException {
        StuCourseView v = new StuCourseView();
        v.setCno(rs.getString("cno"));
        v.setSno(rs.getString("sno"));
        v.setCname(rs.getString("cname"));
        v.setStudentName(rs.getString("stu_name"));
        v.setChosetime(rs.getDate("chosetime"));
        double sc = rs.getDouble("score");
        v.setScore(rs.wasNull() ? null : sc);
        v.setEvaluation(rs.getString("evaluation"));
        try {
            v.setTeacherTno(rs.getString("teacher_tno"));
            v.setTeacherName(rs.getString("teacher_name"));
        } catch (SQLException ignored) {
            // 非学生选课列表查询无教师列
        }
        return v;
    }
}
