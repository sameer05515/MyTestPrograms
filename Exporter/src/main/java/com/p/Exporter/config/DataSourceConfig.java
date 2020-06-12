package com.p.Exporter.config;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.jdbc.core.JdbcTemplate;

import com.p.Exporter.pojo.DataSourcePojo;

public class DataSourceConfig {
	
	

	//@Bean(name="dsCustom")
    private static DataSource dataSource(DataSourcePojo objDataSourceConfig) {
//        return DataSourceBuilder
//                .create()
//                .username("root")
//                .password("123456")
//                .url("jdbc:mysql://localhost:3306/test?useSSL=false&useUnicode=true&characterEncoding=UTF-8")
//                .driverClassName("com.mysql.jdbc.Driver")
//                .build();
    	
    	
    	return DataSourceBuilder
                .create()
                .username(objDataSourceConfig.getUserName())
                .password(objDataSourceConfig.getPassword())
                .url(objDataSourceConfig.getUrl())
                .driverClassName(objDataSourceConfig.getDriverClassName())
                .build();
    }

   // @Bean(name = "jdbcCustom")
//    @Autowired
    private static JdbcTemplate jdbcTemplate(DataSource dsCustom) {
        return new JdbcTemplate(dsCustom);
    }
    
    public static Connection getConnection(DataSourcePojo objDataSourceConfig) throws SQLException {
    	DataSource ds=dataSource(objDataSourceConfig);
    	JdbcTemplate jd=jdbcTemplate(ds);
    	
    	Connection con=jd.getDataSource().getConnection();
    	
    	return con;
    }
}
