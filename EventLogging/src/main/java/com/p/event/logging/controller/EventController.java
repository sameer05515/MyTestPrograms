package com.p.event.logging.controller;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.p.event.logging.exception.InvalidInputSuppliedException;
import com.p.event.logging.pojo.Event;
import com.p.event.logging.pojo.EventLabelRelation;
import com.p.event.logging.pojo.Label;
import com.p.event.logging.response.ResponseHandler;
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
	public ResponseEntity<Object> addEvent(@RequestBody Event event) {
		ResponseEntity<Object> response = null;
		try {
			Map<String, Object> res = eventService.addEvent(event);
			response = ResponseHandler.generateResponse(HttpStatus.OK, false, "Success", res);
		} catch (InvalidInputSuppliedException e) {
			response = ResponseHandler.generateResponse(HttpStatus.PRECONDITION_FAILED, true, "Fail",
					e.getCustomMessage());
			e.printStackTrace();
		} catch (Exception e) {
			response = ResponseHandler.generateResponse(HttpStatus.INTERNAL_SERVER_ERROR, true, "Fail",
					"Please contact administrator!");
			e.printStackTrace();
		}
		return response;
	}

	@GetMapping("/event-logging/event/all")
	public ResponseEntity<Object> allEvent() {
		ResponseEntity<Object> response = null;
		try {
			List<Event> res = eventService.allEvent();
			response = ResponseHandler.generateResponse(HttpStatus.OK, false, "Success", res);
		} catch (InvalidInputSuppliedException e) {
			response = ResponseHandler.generateResponse(HttpStatus.PRECONDITION_FAILED, true, "Fail",
					e.getCustomMessage());
			e.printStackTrace();
		} catch (Exception e) {
			response = ResponseHandler.generateResponse(HttpStatus.INTERNAL_SERVER_ERROR, true, "Fail",
					"Please contact administrator!");
			e.printStackTrace();
		}
		return response;
	}

	@GetMapping("/event-logging/event/{id}")
	public ResponseEntity<Object> getEventById(@PathVariable("id") String id) {
		ResponseEntity<Object> response = null;
		try {
			Event res = eventService.getEventById(id);
			response = ResponseHandler.generateResponse(HttpStatus.OK, false, "Success", res);
		} catch (InvalidInputSuppliedException e) {
			response = ResponseHandler.generateResponse(HttpStatus.PRECONDITION_FAILED, true, "Fail",
					e.getCustomMessage());
			e.printStackTrace();
		} catch (Exception e) {
			response = ResponseHandler.generateResponse(HttpStatus.INTERNAL_SERVER_ERROR, true, "Fail",
					"Please contact administrator!");
			e.printStackTrace();
		}
		return response;
	}

	@PutMapping("/event-logging/event/{id}")
	public ResponseEntity<Object> updateEventById(@PathVariable("id") String id, @RequestBody Event word) {
		ResponseEntity<Object> response = null;
		try {
			Event res = eventService.updateEventById(id,word);
			response = ResponseHandler.generateResponse(HttpStatus.OK, false, "Success", res);
		} catch (InvalidInputSuppliedException e) {
			response = ResponseHandler.generateResponse(HttpStatus.PRECONDITION_FAILED, true, "Fail",
					e.getCustomMessage());
			e.printStackTrace();
		} catch (Exception e) {
			response = ResponseHandler.generateResponse(HttpStatus.INTERNAL_SERVER_ERROR, true, "Fail",
					"Please contact administrator!");
			e.printStackTrace();
		}
		return response;
	}

	/**
	 * Label
	 */

	@PostMapping("/event-logging/label/")
	public ResponseEntity<Object> addLabel(@RequestBody Label label) {
		ResponseEntity<Object> response = null;
		try {
			Map<String, Object> res = eventService.addLabel(label);
			response = ResponseHandler.generateResponse(HttpStatus.OK, false, "Success", res);
		} catch (InvalidInputSuppliedException e) {
			response = ResponseHandler.generateResponse(HttpStatus.PRECONDITION_FAILED, true, "Fail",
					e.getCustomMessage());
			e.printStackTrace();
		} catch (Exception e) {
			response = ResponseHandler.generateResponse(HttpStatus.INTERNAL_SERVER_ERROR, true, "Fail",
					"Please contact administrator!");
			e.printStackTrace();
		}
		return response;
	}

	@GetMapping("/event-logging/label/all")
	public ResponseEntity<Object> allLabel() {
		ResponseEntity<Object> response = null;
		try {
			List<Label> res = eventService.allLabel();
			response = ResponseHandler.generateResponse(HttpStatus.OK, false, "Success", res);
		} catch (InvalidInputSuppliedException e) {
			response = ResponseHandler.generateResponse(HttpStatus.PRECONDITION_FAILED, true, "Fail",
					e.getCustomMessage());
			e.printStackTrace();
		} catch (Exception e) {
			response = ResponseHandler.generateResponse(HttpStatus.INTERNAL_SERVER_ERROR, true, "Fail",
					"Please contact administrator!");
			e.printStackTrace();
		}
		return response;
	}

	@GetMapping("/event-logging/label/{id}")
	public ResponseEntity<Object> getLabelById(@PathVariable("id") String id) {
		ResponseEntity<Object> response = null;
		try {
			Label res = eventService.getLabelById(id);
			response = ResponseHandler.generateResponse(HttpStatus.OK, false, "Success", res);
		} catch (InvalidInputSuppliedException e) {
			response = ResponseHandler.generateResponse(HttpStatus.PRECONDITION_FAILED, true, "Fail",
					e.getCustomMessage());
			e.printStackTrace();
		} catch (Exception e) {
			response = ResponseHandler.generateResponse(HttpStatus.INTERNAL_SERVER_ERROR, true, "Fail",
					"Please contact administrator!");
			e.printStackTrace();
		}
		return response;
	}

	@PutMapping("/event-logging/label/{id}")
	public ResponseEntity<Object> updateLabelById(@PathVariable("id") String id, @RequestBody Label event) {
		ResponseEntity<Object> response = null;
		try {
			Label res = eventService.updateLabelById(id,event);
			response = ResponseHandler.generateResponse(HttpStatus.OK, false, "Success", res);
		} catch (InvalidInputSuppliedException e) {
			response = ResponseHandler.generateResponse(HttpStatus.PRECONDITION_FAILED, true, "Fail",
					e.getCustomMessage());
			e.printStackTrace();
		} catch (Exception e) {
			response = ResponseHandler.generateResponse(HttpStatus.INTERNAL_SERVER_ERROR, true, "Fail",
					"Please contact administrator!");
			e.printStackTrace();
		}
		return response;
	}

	/**
	 * Event Label Relation
	 */

	@PostMapping("/event-logging/event-label-relaion/")
	public ResponseEntity<Object> addEventLabelRelation(@RequestBody EventLabelRelation relation) {
		ResponseEntity<Object> response = null;
		try {
			Map<String, Object> res = eventService.addEventLabelRelation(relation);
			response = ResponseHandler.generateResponse(HttpStatus.OK, false, "Success", res);
		} catch (InvalidInputSuppliedException e) {
			response = ResponseHandler.generateResponse(HttpStatus.PRECONDITION_FAILED, true, "Fail",
					e.getCustomMessage());
			e.printStackTrace();
		} catch (Exception e) {
			response = ResponseHandler.generateResponse(HttpStatus.INTERNAL_SERVER_ERROR, true, "Fail",
					"Please contact administrator!");
			e.printStackTrace();
		}
		return response;
	}

	@GetMapping("/event-logging/event-label-relaion/all")
	public ResponseEntity<Object> allEventLabelRelation() {
		ResponseEntity<Object> response = null;
		try {
			List<EventLabelRelation> res = eventService.allEventLabelRelation();
			response = ResponseHandler.generateResponse(HttpStatus.OK, false, "Success", res);
		} catch (InvalidInputSuppliedException e) {
			response = ResponseHandler.generateResponse(HttpStatus.PRECONDITION_FAILED, true, "Fail",
					e.getCustomMessage());
			e.printStackTrace();
		} catch (Exception e) {
			response = ResponseHandler.generateResponse(HttpStatus.INTERNAL_SERVER_ERROR, true, "Fail",
					"Please contact administrator!");
			e.printStackTrace();
		}
		return response;
	}

	@GetMapping("/event-logging/event-label-relaion/event/{event_id}/label/{label_id}")
	public ResponseEntity<Object> getEventLabelRelationById(@PathVariable("event_id") String eventId,
			@PathVariable("label_id") String labelId) {
		ResponseEntity<Object> response=null;
		try {
			EventLabelRelation res = eventService.getEventLabelRelationById(eventId,labelId);
			response = ResponseHandler.generateResponse(HttpStatus.OK, false, "Success", res);
		} catch (InvalidInputSuppliedException e) {
			response = ResponseHandler.generateResponse(HttpStatus.PRECONDITION_FAILED, true, "Fail",
					e.getCustomMessage());
			e.printStackTrace();
		}catch (Exception e) {
			response = ResponseHandler.generateResponse(HttpStatus.INTERNAL_SERVER_ERROR, true, "Fail",
					"Please contact administrator!");
			e.printStackTrace();
		}		
		return response;
	}

	@GetMapping("/event-logging/event-label-relaion/event/{event_id}")
	public ResponseEntity<Object> getEventLabelRelationByEventId(@PathVariable("event_id") String eventId) {
		ResponseEntity<Object> response = null;
		try {
			List<EventLabelRelation> res = eventService.getEventLabelRelationByEventId(eventId);
			response = ResponseHandler.generateResponse(HttpStatus.OK, false, "Success", res);
		} catch (InvalidInputSuppliedException e) {
			response = ResponseHandler.generateResponse(HttpStatus.PRECONDITION_FAILED, true, "Fail",
					e.getCustomMessage());
			e.printStackTrace();
		} catch (Exception e) {
			response = ResponseHandler.generateResponse(HttpStatus.INTERNAL_SERVER_ERROR, true, "Fail",
					"Please contact administrator!");
			e.printStackTrace();
		}
		return response;
	}

	@GetMapping("/event-logging/event-label-relaion/label/{label_id}")
	public ResponseEntity<Object> getEventLabelRelationByLabelId(@PathVariable("label_id") String labelId) {
		ResponseEntity<Object> response = null;
		try {
			List<EventLabelRelation> res = eventService.getEventLabelRelationByLabelId(labelId);
			response = ResponseHandler.generateResponse(HttpStatus.OK, false, "Success", res);
		} catch (InvalidInputSuppliedException e) {
			response = ResponseHandler.generateResponse(HttpStatus.PRECONDITION_FAILED, true, "Fail",
					e.getCustomMessage());
			e.printStackTrace();
		} catch (Exception e) {
			response = ResponseHandler.generateResponse(HttpStatus.INTERNAL_SERVER_ERROR, true, "Fail",
					"Please contact administrator!");
			e.printStackTrace();
		}
		return response;
	}

	@PutMapping("/event-logging/event-label-relaion/event/{event_id}/label/{label_id}")
	public ResponseEntity<Object> updateEventLabelRelationById(@PathVariable("event_id") String eventId,
			@PathVariable("label_id") String labelId, @RequestBody EventLabelRelation relation) {
		ResponseEntity<Object> response = null;
		try {
			EventLabelRelation res = eventService.updateEventLabelRelationById(eventId, labelId, relation);
			response = ResponseHandler.generateResponse(HttpStatus.OK, false, "Success", res);
		} catch (InvalidInputSuppliedException e) {
			response = ResponseHandler.generateResponse(HttpStatus.PRECONDITION_FAILED, true, "Fail",
					e.getCustomMessage());
			e.printStackTrace();
		} catch (Exception e) {
			response = ResponseHandler.generateResponse(HttpStatus.INTERNAL_SERVER_ERROR, true, "Fail",
					"Please contact administrator!");
			e.printStackTrace();
		}
		return response;
	}

}
