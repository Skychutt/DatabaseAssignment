package com.COMP2013J.assignment.service;

import com.COMP2013J.assignment.dao.StudentArchiveDao;
import com.COMP2013J.assignment.entity.StudentArchive;

import java.util.Date;

public class StudentArchiveService {

    private final StudentArchiveDao dao = new StudentArchiveDao();

    public StudentArchive getBySno(String sno) {
        if (!hasText(sno)) {
            return null;
        }
        return dao.getBySno(sno.trim());
    }

    public String insert(StudentArchive archive, String loginSno) {
        if (!hasText(loginSno)) {
            return "未登录或学号无效";
        }
        if (archive == null) {
            return "档案数据为空";
        }
        archive.setSno(loginSno.trim());
        if (dao.getBySno(loginSno.trim()) != null) {
            return "您已存在个人档案，请使用修改功能";
        }
        String msg = validate(archive, true);
        if (msg != null) {
            return msg;
        }
        archive.setArchiveId(buildArchiveId(loginSno.trim()));
        Date now = new Date();
        archive.setCreateTime(now);
        archive.setUpdateTime(now);
        if (archive.getArchiveStatus() == null) {
            archive.setArchiveStatus(0);
        }
        if (dao.insert(archive) <= 0) {
            return "创建档案失败，请检查身份证号是否重复";
        }
        return null;
    }

    public String update(StudentArchive archive, String loginSno) {
        if (!hasText(loginSno)) {
            return "未登录或学号无效";
        }
        if (archive == null || !hasText(archive.getArchiveId())) {
            return "档案编号无效";
        }
        StudentArchive existing = dao.getByArchiveId(archive.getArchiveId().trim());
        if (existing == null) {
            return "档案不存在";
        }
        if (!loginSno.trim().equals(existing.getSno())) {
            return "无权修改他人档案";
        }
        archive.setSno(existing.getSno());
        String msg = validate(archive, false);
        if (msg != null) {
            return msg;
        }
        archive.setUpdateTime(new Date());
        if (archive.getArchiveStatus() == null) {
            archive.setArchiveStatus(existing.getArchiveStatus());
        }
        if (dao.update(archive) <= 0) {
            return "保存失败，请检查身份证号是否与其他档案重复";
        }
        return null;
    }

    public String delete(String archiveId, String loginSno) {
        if (!hasText(loginSno)) {
            return "未登录或学号无效";
        }
        if (!hasText(archiveId)) {
            return "档案编号无效";
        }
        StudentArchive existing = dao.getByArchiveId(archiveId.trim());
        if (existing == null) {
            return "档案不存在";
        }
        if (!loginSno.trim().equals(existing.getSno())) {
            return "无权删除他人档案";
        }
        if (dao.delete(archiveId.trim(), loginSno.trim()) <= 0) {
            return "删除失败";
        }
        return null;
    }

    private String validate(StudentArchive archive, boolean isNew) {
        if (!hasText(archive.getIdCard())) {
            return "身份证号不能为空";
        }
        String idCard = archive.getIdCard().trim();
        if (idCard.length() != 18) {
            return "身份证号须为18位";
        }
        if (archive.getArchiveStatus() != null
                && archive.getArchiveStatus() != 0
                && archive.getArchiveStatus() != 1) {
            return "档案状态无效";
        }
        if (hasText(archive.getGuardianPhone()) && archive.getGuardianPhone().trim().length() != 11) {
            return "监护人电话须为11位";
        }
        return null;
    }

    private String buildArchiveId(String sno) {
        String id = "ARC" + sno;
        if (id.length() > 20) {
            id = id.substring(0, 20);
        }
        return id;
    }

    private boolean hasText(String value) {
        return value != null && !value.trim().isEmpty();
    }
}
