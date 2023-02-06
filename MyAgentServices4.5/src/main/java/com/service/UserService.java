package com.service;

import com.basic.common.access.AccessPoint;
import com.ils.ibatis.user.UserIbatis;
import com.pojo.UserPojo;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService {
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

    public UserPojo getUserById(int id) {
        SqlSession session = AccessPoint.getDBTemplate().openSession();
        UserPojo list = null;
        try {
            UserIbatis mapper = session.getMapper(UserIbatis.class);
            Map<String,Object> sKey=new HashMap<>();
            sKey.put("id",id);
            list = mapper.getById(sKey);
            list.setObjectives(mapper.getObjectivesById(sKey));
        } catch (Exception e) {
            session.rollback();
        } finally {
            session.close();
        }
        return list;
    }
}