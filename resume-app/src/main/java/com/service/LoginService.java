package com.service;

import com.basic.common.access.AccessPoint;
import com.ils.ibatis.user.UserIbatis;
import com.pojo.UserPojo;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoginService {
    public boolean validateUser(String userid, String password) {
        return userid.equalsIgnoreCase("jbk") && password.equalsIgnoreCase("jbk");
    }

    public List<UserPojo> getAllUsers() {
        SqlSession session = AccessPoint.getDBTemplate().openSession();
        List<UserPojo> list = null;
        try {
            UserIbatis mapper = session.getMapper(UserIbatis.class);
            list = mapper.getAll();
        } catch (Exception e) {
            session.rollback();
        } finally {
            session.close();
        }
        return list;
    }
}