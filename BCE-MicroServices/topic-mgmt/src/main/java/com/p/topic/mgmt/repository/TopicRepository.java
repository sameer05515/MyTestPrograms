package com.p.topic.mgmt.repository;

import com.p.topic.mgmt.pojo.Topic;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface TopicRepository extends PagingAndSortingRepository<Topic, Integer> {
}
