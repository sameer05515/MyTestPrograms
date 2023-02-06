/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.basic.common.access;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

import java.io.Serializable;

/**
 * @author root
 */
@Configuration
@MapperScan("com.ils.ibatis")
public class AccessPoint implements Serializable {

//    private static SqlSessionFactory sqlmapper;
//    private static Reader reader;
//
//    static {
//        try {
//            reader = Resources.getResourceAsReader("SqlMapConfig.xml");
//            //System.out.println("---------------------------------+++++++++++++++++"+reader.read());
//            sqlmapper = new SqlSessionFactoryBuilder().build(reader);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static SqlSessionFactory getDBTemplate()  {
//        System.out.println("----------------------------------"+sqlmapper);
//        return sqlmapper;
//    }

    public static SqlSessionFactory getDBTemplate() {

        return ApplicationContextUtil.getBean(SqlSessionFactory.class);
    }
}