package com.p.event.logging.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.p.event.logging.pojo.Event;
import com.p.event.logging.pojo.EventLabelRelation;
import com.p.event.logging.pojo.Label;
import com.p.event.logging.service.EventService;

@RestController
public class EventController {

	private static final Logger log = LoggerFactory.getLogger(EventController.class);

	@Autowired
	EventService eventService;

	@GetMapping("/")
	public String healthCheck() {
		log.debug("checking healthcheck!");
		return "OK";
	}

	/**
	 * Event
	 */

	@PostMapping("/event-logging/event/")
	public ResponseEntity<Object> addEvent(@RequestBody Event word) {
		return null;
	}

	@GetMapping("/event-logging/event/all")
	public ResponseEntity<Object> allEvent() {
		return null;
	}

	@GetMapping("/event-logging/event/{id}")
	public ResponseEntity<Object> getEventById(@PathVariable("id") String id) {
		return null;
	}

	@PutMapping("/event-logging/event/{id}")
	public ResponseEntity<Object> updateEventById(@PathVariable("id") String id, @RequestBody Event word) {
		return null;
	}

	/**
	 * Label
	 */

	@PostMapping("/event-logging/label/")
	public ResponseEntity<Object> addLabel(@RequestBody Label word) {
		return null;
	}

	@GetMapping("/event-logging/label/all")
	public ResponseEntity<Object> allLabel() {
		return null;
	}

	@GetMapping("/event-logging/label/{id}")
	public ResponseEntity<Object> getLabelById(@PathVariable("id") String id) {
		return null;
	}

	@PutMapping("/event-logging/label/{id}")
	public ResponseEntity<Object> updateLabelById(@PathVariable("id") String id, @RequestBody Event word) {
		return null;
	}

	/**
	 * Event Label Relation
	 */

	@PostMapping("/event-logging/event-label-relaion/")
	public ResponseEntity<Object> addEventLabelRelation(@RequestBody EventLabelRelation word) {
		return null;
	}

	@GetMapping("/event-logging/event-label-relaion/all")
	public ResponseEntity<Object> allEventLabelRelation() {
		return null;
	}

	@GetMapping("/event-logging/event-label-relaion/event/{event_id}/label/{label_id}")
	public ResponseEntity<Object> getEventLabelRelationById() {
		return null;
	}

	@GetMapping("/event-logging/event-label-relaion/event/{event_id}")
	public ResponseEntity<Object> getEventLabelRelationByEventId(@PathVariable("event_id") String eventId) {
		return null;
	}

	@GetMapping("/event-logging/event-label-relaion/label/{label_id}")
	public ResponseEntity<Object> getEventLabelRelationByLabelId(@PathVariable("label_id") String labelId) {
		return null;
	}

	@PutMapping("/event-logging/event-label-relaion/event/{event_id}/label/{label_id}")
	public ResponseEntity<Object> updateEventLabelRelationById(@PathVariable("event_id") String eventId,
			@PathVariable("label_id") String labelId, @RequestBody EventLabelRelation word) {
		return null;
	}

}
