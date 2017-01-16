package com.exmybatis.dao;

import com.exmybatis.domain.User;
import org.springframework.stereotype.Repository;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



@Repository
public class MyDAO {

    @Autowired
    private SqlSession sqlSession;

    public String getSuperAdmin(String userType) throws SQLException {
        return sqlSession.selectOne("db.getSuperAdmin", userType);
    }

    public List<User> getAllUser() throws SQLException {
        return sqlSession.selectList("db.getAllUser");
    }

    public List<User> getPagingUserList(long offset, long noOfRecords) throws SQLException {
        Map<String, Long> param = new HashMap<String, Long>();

        param.put("offset", new Long(offset));
        param.put("noOfRecords", new Long(noOfRecords));

        return sqlSession.selectList("db.getPagingUserList", param);
    }

    public List<String> getAdminUserNameList() throws SQLException {

        Map<String, String> param = new HashMap<String, String>();
        param.put("one", "ADMIN");
        param.put("two", "SA");
        return sqlSession.selectList("db.getAdminUserNameList", param);
    }

    public List<String> getAllUserNameList() throws SQLException {
        return sqlSession.selectList("db.getAllUserNameList");
    }

    public void updateAdminName() throws SQLException {

        Map<String, String> param = new HashMap<String, String>();
        param.put("findAdminName", "루트 관리자");
        param.put("newAdminName", "root 관리자");

        sqlSession.update("db.updateAdminName", param);
    }

    public List<String> findAdminEMail() throws SQLException {
        User admin = new User();
        admin.setUserId("root");
        admin.setUserType("ADMIN");
        List<String> adminEmails = sqlSession.selectList("db.findAdminEMail", admin);
        return adminEmails;
    }

    public void insertAdmin(String userId, String userName, String userType) throws SQLException {
        Map<String, String> param = new HashMap<String, String>();
        param.put("user_id", userId);
        param.put("user_name", userName);
        param.put("user_type", userType);
        sqlSession.insert("db.insertAdmin", param);
    }
}

