package com.spp.demo.modules.links;

import java.util.List;
import java.util.Optional;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface LinkRepository extends MongoRepository<Link, ObjectId> {

  @Query(value = "{}", fields = "{ 'uniqueId' : 1, 'name' : 1, 'parentId': 1 }")
  List<Link> findAllLinksForSectionList();

  // Find a topic by its unique identifier
  Optional<Link> findByUniqueId(String uniqueId);
}
