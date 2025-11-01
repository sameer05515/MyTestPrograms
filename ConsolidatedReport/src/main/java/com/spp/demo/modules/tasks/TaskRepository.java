package com.spp.demo.modules.tasks;

import java.util.List;
import java.util.Optional;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends MongoRepository<Task, ObjectId> {

  @Query(value = "{}", fields = "{ 'uniqueId' : 1 }")
  List<Task> findAllUniqueIds();

  @Query(value = "{}", fields = "{ 'uniqueId' : 1, 'name' : 1, 'parentId': 1 }")
  List<Task> findAllTasksForSectionList();

  // Find a task by its unique identifier
  Optional<Task> findByUniqueId(String uniqueId);

  // Find tasks by their status
  List<Task> findByTaskStatus(String taskStatus);

  // Find tasks linked to a specific task ID
  List<Task> findByLinkedTasksContaining(String linkedTaskId);

  // Custom query for searching by tags
  List<Task> findByTagsContaining(String tag);

  // Custom query for finding tasks with a specific name
  List<Task> findByNameContainingIgnoreCase(String name);
}
