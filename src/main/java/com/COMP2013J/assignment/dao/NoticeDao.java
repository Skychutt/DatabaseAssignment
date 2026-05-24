package com.COMP2013J.assignment.dao;

import com.COMP2013J.assignment.entity.Notice;
import com.COMP2013J.assignment.utils.JdbcHelper;
import com.COMP2013J.assignment.utils.vo.PagerVO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NoticeDao {

    public int insert(Notice notice) {
        JdbcHelper helper = new JdbcHelper();
        int res = helper.executeUpdate(
                "insert into tb_notice (publisher, create_time, content) values (?,?,?)",
                notice.getPublisher(), notice.getCreateTime(), notice.getContent());
        helper.closeDB();
        return res;
    }

    public int update(Notice notice) {
        JdbcHelper helper = new JdbcHelper();
        int res = helper.executeUpdate(
                "update tb_notice set create_time = ?, content = ? where id = ?",
                notice.getCreateTime(), notice.getContent(), notice.getId());
        helper.closeDB();
        return res;
    }

    public int delete(long id) {
        JdbcHelper helper = new JdbcHelper();
        int res = helper.executeUpdate("delete from tb_notice where id = ?", id);
        helper.closeDB();
        return res;
    }

    public Notice getById(long id) {
        JdbcHelper helper = new JdbcHelper();
        ResultSet rs = helper.executeQuery("select * from tb_notice where id = ?", id);
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

    public PagerVO<Notice> page(int current, int size, String whereSql, Object... whereParams) {
        PagerVO<Notice> pagerVO = new PagerVO<>();
        pagerVO.setCurrent(current);
        pagerVO.setSize(size);
        JdbcHelper helper = new JdbcHelper();
        if (whereSql == null) {
            whereSql = " ";
        }
        try {
            ResultSet resultSet = helper.executeQuery("select count(1) from tb_notice " + whereSql, whereParams);
            resultSet.next();
            int total = resultSet.getInt(1);
            pagerVO.setTotal(total);

            Object[] listParams = appendLimitParams(whereParams, (current - 1) * size, size);
            resultSet = helper.executeQuery(
                    "select * from tb_notice " + whereSql + " order by create_time desc, id desc limit ?, ?",
                    listParams);
            List<Notice> list = new ArrayList<>();
            while (resultSet.next()) {
                list.add(toEntity(resultSet));
            }
            pagerVO.setList(list);
        } catch (SQLException e) {
            e.printStackTrace();
            pagerVO.setList(new ArrayList<>());
            pagerVO.setTotal(0);
        } finally {
            helper.closeDB();
        }
        return pagerVO;
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

    private Notice toEntity(ResultSet rs) throws SQLException {
        Notice n = new Notice();
        n.setId(rs.getLong("id"));
        n.setPublisher(rs.getString("publisher"));
        n.setCreateTime(rs.getDate("create_time"));
        n.setContent(rs.getString("content"));
        return n;
    }
}
