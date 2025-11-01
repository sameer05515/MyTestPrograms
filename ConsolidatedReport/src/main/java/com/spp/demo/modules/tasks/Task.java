package com.spp.demo.modules.tasks;

import com.spp.demo.modules.common.Node;
import com.spp.demo.modules.common.SmartDescription;
import java.time.Instant;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@Document(collection = "tasks")
public class Task implements Node {

  @Id private ObjectId id; // Maps to "_id" in MongoDB

  private String name;
  private String parentId;
  private String uniqueId;
  private String taskStatus;
  private Instant createdDate;
  private Instant updatedDate;
  private List<String> tags; // List of tag UUIDs
  private List<String> linkedTasks; // List of linked task UUIDs
  private List<SmartDescription> descriptions; // List of descriptions
  private List<Activity> activities; // List of activities
  private int __v; // Mongo version field
}
