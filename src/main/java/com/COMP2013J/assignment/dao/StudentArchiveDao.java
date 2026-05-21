package com.COMP2013J.assignment.dao;

import com.COMP2013J.assignment.entity.StudentArchive;
import com.COMP2013J.assignment.utils.JdbcHelper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class StudentArchiveDao {

    public StudentArchive getBySno(String sno) {
        JdbcHelper helper = new JdbcHelper();
        ResultSet rs = helper.executeQuery("select * from tb_student_archive where sno = ?", sno);
        try {
            if (rs != null && rs.next()) {
                return toEntity(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            helper.closeDB();
        }
        return null;
    }

    public StudentArchive getByArchiveId(String archiveId) {
        JdbcHelper helper = new JdbcHelper();
        ResultSet rs = helper.executeQuery("select * from tb_student_archive where archive_id = ?", archiveId);
        try {
            if (rs != null && rs.next()) {
                return toEntity(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            helper.closeDB();
        }
        return null;
    }

    public int insert(StudentArchive archive) {
        JdbcHelper helper = new JdbcHelper();
        int res = helper.executeUpdate(
                "insert into tb_student_archive (archive_id, sno, id_card, nationality, birth_place, "
                        + "political_status, enrollment_type, major, clazzno, graduation_school, archive_status, "
                        + "create_time, update_time, guardian_name, guardian_phone) "
                        + "values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
                archive.getArchiveId(),
                archive.getSno(),
                archive.getIdCard(),
                archive.getNationality(),
                archive.getBirthPlace(),
                archive.getPoliticalStatus(),
                archive.getEnrollmentType(),
                archive.getMajor(),
                archive.getClazzno(),
                archive.getGraduationSchool(),
                archive.getArchiveStatus(),
                archive.getCreateTime(),
                archive.getUpdateTime(),
                archive.getGuardianName(),
                archive.getGuardianPhone());
        helper.closeDB();
        return res;
    }

    public int update(StudentArchive archive) {
        JdbcHelper helper = new JdbcHelper();
        int res = helper.executeUpdate(
                "update tb_student_archive set id_card = ?, nationality = ?, birth_place = ?, "
                        + "political_status = ?, enrollment_type = ?, major = ?, clazzno = ?, "
                        + "graduation_school = ?, archive_status = ?, update_time = ?, "
                        + "guardian_name = ?, guardian_phone = ? where archive_id = ? and sno = ?",
                archive.getIdCard(),
                archive.getNationality(),
                archive.getBirthPlace(),
                archive.getPoliticalStatus(),
                archive.getEnrollmentType(),
                archive.getMajor(),
                archive.getClazzno(),
                archive.getGraduationSchool(),
                archive.getArchiveStatus(),
                archive.getUpdateTime(),
                archive.getGuardianName(),
                archive.getGuardianPhone(),
                archive.getArchiveId(),
                archive.getSno());
        helper.closeDB();
        return res;
    }

    public int delete(String archiveId, String sno) {
        JdbcHelper helper = new JdbcHelper();
        int res = helper.executeUpdate(
                "delete from tb_student_archive where archive_id = ? and sno = ?", archiveId, sno);
        helper.closeDB();
        return res;
    }

    private StudentArchive toEntity(ResultSet rs) throws SQLException {
        StudentArchive a = new StudentArchive();
        a.setArchiveId(rs.getString("archive_id"));
        a.setSno(rs.getString("sno"));
        a.setIdCard(rs.getString("id_card"));
        a.setNationality(rs.getString("nationality"));
        a.setBirthPlace(rs.getString("birth_place"));
        a.setPoliticalStatus(rs.getString("political_status"));
        a.setEnrollmentType(rs.getString("enrollment_type"));
        a.setMajor(rs.getString("major"));
        a.setClazzno(rs.getString("clazzno"));
        a.setGraduationSchool(rs.getString("graduation_school"));
        a.setArchiveStatus(rs.getInt("archive_status"));
        Timestamp ct = rs.getTimestamp("create_time");
        if (ct != null) {
            a.setCreateTime(new java.util.Date(ct.getTime()));
        }
        Timestamp ut = rs.getTimestamp("update_time");
        if (ut != null) {
            a.setUpdateTime(new java.util.Date(ut.getTime()));
        }
        a.setGuardianName(rs.getString("guardian_name"));
        a.setGuardianPhone(rs.getString("guardian_phone"));
        return a;
    }
}
