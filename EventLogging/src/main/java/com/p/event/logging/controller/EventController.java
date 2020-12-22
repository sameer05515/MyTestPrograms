package com.p.event.logging.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.p.event.logging.service.EventService;


@RestController
public class EventController {
	
	private static final Logger log = LoggerFactory.getLogger(EventController.class);
	
	
	@Autowired
	EventService eventService;
	
	

}
