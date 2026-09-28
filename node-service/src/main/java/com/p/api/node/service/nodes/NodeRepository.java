package com.p.api.node.service.nodes;

import java.util.List;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface NodeRepository extends MongoRepository<Node, String> {
  Optional<Node> findByUniqueId(String uniqueId);

  @Query(value = "{}", fields = "{ 'uniqueId' : 1 }")
  List<Node> findAllUniqueIds();

  @Query(value = "{ 'uniqueId': { $in: ?0 } }")
  List<Node> findAllByUniqueIds(List<String> uniqueIds);

  @Query(value = "{ 'type': ?0 }")
  List<Node> findAllByType(String type);

  /**
   * Finds all nodes where metadata contains the given key-value pair.
   *
   * @param key the metadata key to search for
   * @param value the metadata value to search for
   * @return list of matching nodes
   */
  @Query("{ 'metadata.?0': ?1 }")
  List<Node> findByMetadataKeyValue(String key, String value);
}
