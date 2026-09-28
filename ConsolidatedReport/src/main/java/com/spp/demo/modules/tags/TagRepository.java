package com.spp.demo.modules.tags;


import java.util.List;
import java.util.Optional;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface TagRepository extends MongoRepository<Tag, ObjectId> {

  @Query(value = "{}", fields = "{ 'uniqueId' : 1, 'name' : 1, 'parentId': 1 }")
  List<Tag> findAllTagsForSectionList();

  // Find a topic by its unique identifier
  Optional<Tag> findByUniqueId(String uniqueId);
}
