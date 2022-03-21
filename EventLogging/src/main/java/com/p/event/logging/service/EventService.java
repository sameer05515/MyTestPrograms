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

		if (event == null) {
			log.debug("Invalid event object ( " + event + " ) supplied. ");
			throw new InvalidInputSuppliedException("Invalid event object ( " + event + " ) supplied. ");
		}

		return jdbcTemplateRepository.addEvent(event);
	}

	public List<Event> allEvent() throws InvalidInputSuppliedException {

		return jdbcTemplateRepository.allEvent();
	}

	public Event getEventById(String id) throws InvalidInputSuppliedException {

		return jdbcTemplateRepository.getEventById(id);
	}

	public Event updateEventById(String id, Event event) throws InvalidInputSuppliedException {

		return jdbcTemplateRepository.updateEventById(id,event);
	}

	public Map<String, Object> addLabel(Label label) throws InvalidInputSuppliedException {

		return jdbcTemplateRepository.addLabel(label);
	}

	public List<Label> allLabel() throws InvalidInputSuppliedException {

		return jdbcTemplateRepository.allLabel();
	}

	public Label getLabelById(String id) throws InvalidInputSuppliedException {

		return jdbcTemplateRepository.getLabelById(id);
	}

	public Label updateLabelById(String id, Label label) throws InvalidInputSuppliedException {

		return jdbcTemplateRepository.updateLabelById(id,label);
	}

	public Map<String, Object> addEventLabelRelation(EventLabelRelation relation) throws InvalidInputSuppliedException {

		return jdbcTemplateRepository.addEventLabelRelation(relation);
	}
	
	public EventLabelRelation getEventLabelRelationById(String eventId, String labelId)
			throws InvalidInputSuppliedException {

		return jdbcTemplateRepository.getEventLabelRelationById(eventId,labelId);
	}

	public List<EventLabelRelation> allEventLabelRelation() throws InvalidInputSuppliedException {

		return jdbcTemplateRepository.allEventLabelRelation();
	}	

	public List<EventLabelRelation> getEventLabelRelationByEventId(String eventId)
			throws InvalidInputSuppliedException {

		return jdbcTemplateRepository.getEventLabelRelationByEventId(eventId);
	}

	public List<EventLabelRelation> getEventLabelRelationByLabelId(String labelId)
			throws InvalidInputSuppliedException {

		return jdbcTemplateRepository.getEventLabelRelationByLabelId(labelId);
	}

	public EventLabelRelation updateEventLabelRelationById(String eventId, String labelId, EventLabelRelation relation)
			throws InvalidInputSuppliedException {

		return jdbcTemplateRepository.updateEventLabelRelationById(eventId,labelId,relation);
	}

}
