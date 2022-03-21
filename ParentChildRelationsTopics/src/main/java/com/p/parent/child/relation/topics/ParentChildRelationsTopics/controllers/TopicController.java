package com.p.parent.child.relation.topics.ParentChildRelationsTopics.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.p.parent.child.relation.topics.ParentChildRelationsTopics.dto.TopicNode;
import com.p.parent.child.relation.topics.ParentChildRelationsTopics.repository.TopicNodeRepository;
import com.p.parent.child.relation.topics.ParentChildRelationsTopics.response.ResponseHandler;

@RestController
public class TopicController {
	
	private static final Logger log = LoggerFactory.getLogger(TopicController.class);
	
	@Autowired
	TopicNodeRepository topicNodeRepository;

	@GetMapping("/")
	public String healthCheck() {
		return "OK";
	}
	
	@GetMapping("/topics/tree/data/with/path")
	public ResponseEntity<Object> findAll(HttpServletRequest req, HttpServletResponse resp) {
		ResponseEntity<Object> response = null;
		try {
			log.info("Inside com.p.db.backup.word.meaning.controller.ReportController.findByName(String) method ...");
			List<TopicNode> listWords = topicNodeRepository.findAll();
			response = ResponseHandler.generateResponse(HttpStatus.OK, false, "Success", listWords);
		} catch (Exception e) {
				response = ResponseHandler.generateResponse(HttpStatus.INTERNAL_SERVER_ERROR, true, "Fail",
						"Error in operation : null");
			e.printStackTrace();
		}
		return response;	
	}
	
	
	@PostMapping("/topics/tree/data")
	public ResponseEntity<Object> saveTopicNode(@RequestBody TopicNode customer){
		ResponseEntity<Object> response = null;
		
		try {
			log.info("Inside com.p.parent.child.relation.topics.ParentChildRelationsTopics.controllers.TopicController.findAllWithPath(HttpServletRequest, HttpServletResponse) method ...");
			int i=topicNodeRepository.save(customer);
			response = ResponseHandler.generateResponse(HttpStatus.OK, false, "Success", i);
		} catch (Exception e) {
				response = ResponseHandler.generateResponse(HttpStatus.INTERNAL_SERVER_ERROR, true, "Fail",
						e.getMessage());
			e.printStackTrace();
		}
		
		return response;
	}
	
	
	@GetMapping("/topics/tree/data")
	public ResponseEntity<Object> findAllWithPath(HttpServletRequest req, HttpServletResponse resp) {
		ResponseEntity<Object> response = null;
		try {
			log.info("Inside com.p.parent.child.relation.topics.ParentChildRelationsTopics.controllers.TopicController.findAllWithPath(HttpServletRequest, HttpServletResponse) method ...");
			List<TopicNode> listWords = topicNodeRepository.findTopElements();
			for(TopicNode child:listWords) {
				List<TopicNode> children=getChildrenTopicNodes(child);
				child.setChildren(children);
			}
			response = ResponseHandler.generateResponse(HttpStatus.OK, false, "Success", listWords);
		} catch (Exception e) {
				response = ResponseHandler.generateResponse(HttpStatus.INTERNAL_SERVER_ERROR, true, "Fail",
						e.getMessage());
			e.printStackTrace();
		}
		return response;	
	}

	private List<TopicNode> getChildrenTopicNodes(TopicNode child) {
		List<TopicNode> children=topicNodeRepository.findChildrenByParentId(child.getId());
		for(TopicNode ch:children) {
			List<TopicNode> chs=getChildrenTopicNodes(ch);
			ch.setChildren(chs);
		}
		return children;
	}
}
