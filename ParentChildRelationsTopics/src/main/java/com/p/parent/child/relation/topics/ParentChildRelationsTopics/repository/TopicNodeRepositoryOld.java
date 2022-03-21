package com.p.parent.child.relation.topics.ParentChildRelationsTopics.repository;

import java.io.File;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.p.parent.child.relation.topics.ParentChildRelationsTopics.dto.TopicNode;

public interface TopicNodeRepositoryOld {
	int count();

    int save(TopicNode book);

    int update(TopicNode book);

    int deleteById(Long id);

    List<TopicNode> findAll();

    List<TopicNode> findByNameAndPrice(String name, BigDecimal price);

    Optional<TopicNode> findById(Long id);

    String findNameById(Long id);

    int[] batchInsert(List<TopicNode> books);

    int[][] batchInsert(List<TopicNode> books, int batchSize);

    int[] batchUpdate(List<TopicNode> books);

    int[][] batchUpdate(List<TopicNode> books, int batchSize);

    void saveImage(Long bookId, File image);

    List<Map<String, InputStream>> findImageByTopicNodeId(Long bookId);

}
