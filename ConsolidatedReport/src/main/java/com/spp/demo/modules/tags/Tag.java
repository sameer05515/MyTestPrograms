package com.spp.demo.modules.tags;

import com.spp.demo.modules.common.Node;
import com.spp.demo.modules.common.SmartDescription;
import java.time.Instant;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@Document(collection = "tags")
public class Tag implements Node {
  @Id private ObjectId id; // Maps to "_id" in MongoDB

  private String name;
  private String parentId;
  private String uniqueId;
  private Instant createdDate;
  private Instant updatedDate;
  private String description;
  private SmartDescription smartContent;
  private boolean softDelete;
  private int __v; // Mongo version field
}
