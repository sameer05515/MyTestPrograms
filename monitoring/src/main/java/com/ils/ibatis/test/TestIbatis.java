package com.ils.ibatis.test;

import com.pojo.TestPojo;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface TestIbatis {
    @Select("SELECT id,name,email FROM test")
    public List<TestPojo> getAll();
}
