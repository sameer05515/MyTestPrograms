package com.p.topic.mgmt.service.impl;

import com.p.topic.mgmt.pojo.Topic;
import com.p.topic.mgmt.repository.TopicRepository;
import com.p.topic.mgmt.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class TopicServiceImpl implements TopicService {

    @Autowired
    TopicRepository topicRepository;
    @Override
    public List<Topic> find(int page, int size) {
        Page<Topic> topicPage = topicRepository.findAll(PageRequest.of(page, size));
        return topicPage.get().collect(Collectors.toList());

//        .map(topic -> {
//            TopicVO topicVO = new TopicVO();
//            topicVO.setId(topic.getId());
//            topicVO.setTitle(topic.getTitle());
//            return topicVO;
//        })
    }

    @Override
    public List<Topic> getAll() {
        return null;
    }

    @Override
    public int create(Topic lob) {
        return 0;
    }

    @Override
    public Topic update(Topic lob) {
        return topicRepository.save(lob);
//        return lob;
    }

    @Override
    public List<Topic> updateAll(List<Topic> lob) {
        List<Topic> topics=new ArrayList<>();
        topicRepository.saveAll(lob).forEach(topics::add);
        return topics;
    }

    @Override
    public Topic get(Integer id) {
        return null;
    }

    @Override
    public Topic get(String uniqueStrid) {
        return null;
    }
}
