package com.spp.demo.modules.topics;

import java.util.List;
import java.util.Optional;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface TopicRepository extends MongoRepository<Topic, ObjectId> {

  @Query(value = "{}", fields = "{ 'uniqueId' : 1, 'name' : 1, 'parentId': 1 }")
  List<Topic> findAllTopicsForSectionList();

  // Find a topic by its unique identifier
  Optional<Topic> findByUniqueId(String uniqueId);
}
