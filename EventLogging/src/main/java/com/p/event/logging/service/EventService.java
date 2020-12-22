package com.p.event.logging.service;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.p.event.logging.controller.EventController;
import com.p.event.logging.exception.InvalidInputSuppliedException;
import com.p.event.logging.jpa.JDBCTemplateRepository;
import com.p.event.logging.pojo.Event;
import com.p.event.logging.pojo.EventLabelRelation;
import com.p.event.logging.pojo.Label;

@Service
public class EventService {

	private static final Logger log = LoggerFactory.getLogger(EventController.class);

	@Autowired
	JDBCTemplateRepository jdbcTemplateRepository;

	public Map<String, Object> addEvent(Event event) throws InvalidInputSuppliedException {

		return null;
	}

	public List<Event> allEvent() throws InvalidInputSuppliedException {

		return null;
	}

	public Event getEventById(String id) throws InvalidInputSuppliedException {

		return null;
	}

	public Event updateEventById(String id, Event word) throws InvalidInputSuppliedException {

		return null;
	}

	public Map<String, Integer> addLabel(Label word) throws InvalidInputSuppliedException {

		return null;
	}

	public List<Label> allLabel() throws InvalidInputSuppliedException {

		return null;
	}

	public Label getLabelById(String id) throws InvalidInputSuppliedException {

		return null;
	}

	public Label updateLabelById(String id, Event word) throws InvalidInputSuppliedException {

		return null;
	}

	public Map<String, Object> addEventLabelRelation(EventLabelRelation word) throws InvalidInputSuppliedException {

		return null;
	}

	public List<EventLabelRelation> allEventLabelRelation() throws InvalidInputSuppliedException {

		return null;
	}

	public EventLabelRelation getEventLabelRelationById(String eventId, String labelId)
			throws InvalidInputSuppliedException {

		return null;
	}

	public List<EventLabelRelation> getEventLabelRelationByEventId(String eventId)
			throws InvalidInputSuppliedException {

		return null;
	}

	public List<EventLabelRelation> getEventLabelRelationByLabelId(String labelId)
			throws InvalidInputSuppliedException {

		return null;
	}

	public EventLabelRelation updateEventLabelRelationById(String eventId, String labelId, EventLabelRelation relation)
			throws InvalidInputSuppliedException {

		return null;
	}

}
