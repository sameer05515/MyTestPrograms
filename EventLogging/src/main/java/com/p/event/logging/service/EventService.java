package com.p.event.logging.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.p.event.logging.controller.EventController;
import com.p.event.logging.jpa.JDBCTemplateRepository;

@Service
public class EventService {
	
	private static final Logger log = LoggerFactory.getLogger(EventController.class);
	
	@Autowired
	JDBCTemplateRepository jdbcTemplateRepository;

}
