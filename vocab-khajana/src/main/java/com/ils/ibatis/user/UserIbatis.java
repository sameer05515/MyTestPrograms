package com.ils.ibatis.user;

import com.pojo.UserObjective;
import com.pojo.UserPojo;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface UserIbatis {
    @Select("SELECT id,name,email FROM users")
    public List<UserPojo> getAll();

    @Select("SELECT users.id,users.name,users.email FROM users where users.id=#{id}")
    public UserPojo getById(Map<String, Object> sKey);

    @Select("SELECT uo.id,uo.userid,uo.objective, uo.active FROM users_objectives as uo where uo.userid=#{id}")
    public List<UserObjective> getObjectivesById(Map<String, Object> sKey);
}
