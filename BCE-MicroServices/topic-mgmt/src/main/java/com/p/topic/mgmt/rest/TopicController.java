package com.p.topic.mgmt.rest;

import com.p.topic.mgmt.exception.ExceptionHandling;
import com.p.topic.mgmt.pojo.Topic;
import com.p.topic.mgmt.repository.TopicRepository;
import com.p.topic.mgmt.service.TopicService;
import com.p.topic.mgmt.vo.TopicVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/topics")
public class TopicController extends ExceptionHandling {


    @Autowired
    private TopicService topicService;
    @GetMapping("/find")
    public List<TopicVO> find(@RequestParam(name = "page", required = false, defaultValue = "0") int page, @RequestParam(name = "size", required = false, defaultValue = "10") int size) {
        return topicService.find(page,size).stream().map(topic -> {
            TopicVO topicVO = new TopicVO();
            topicVO.setId(topic.getId());
            topicVO.setTitle(topic.getTitle());
            topicVO.setUniqueStrid(topic.getUniqueStrid());
            return topicVO;
        }).collect(Collectors.toList());
    }
}
