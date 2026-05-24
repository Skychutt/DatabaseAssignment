package com.COMP2013J.assignment.security;

import com.COMP2013J.assignment.entity.Course;
import com.COMP2013J.assignment.entity.Teacher;
import jakarta.servlet.http.HttpSession;

public final class CourseAccessHelper {

    private CourseAccessHelper() {
    }

    public static boolean canManageCourse(HttpSession session) {
        return RoleHelper.isAdmin(session) || RoleHelper.isTeacher(session);
    }

    public static boolean isCourseOwner(HttpSession session, Course course) {
        if (course == null) {
            return false;
        }
        if (RoleHelper.isAdmin(session)) {
            return true;
        }
        if (!RoleHelper.isTeacher(session)) {
            return false;
        }
        Object user = session.getAttribute("user");
        if (!(user instanceof Teacher)) {
            return false;
        }
        String tno = ((Teacher) user).getTno();
        return tno != null && tno.equals(course.getTno());
    }
}
