package com.p.event.logging.jpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class JDBCTemplateRepository {
	
	@Autowired
	JdbcTemplate jdbcTemplate;

}
