package com.ils.ibatis.user;

import com.pojo.UserPojo;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface UserIbatis {
    @Select("SELECT id,name,email FROM users")
    public List<UserPojo> getAll();
}
