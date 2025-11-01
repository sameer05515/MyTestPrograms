package com.p.api.node.service.nodes;

import java.util.HashMap;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "nodes")
public class Node {
  @Id private String id;

  @Indexed(unique = true) // Ensures unique constraint on this field
  private String uniqueId;

  private String type;
  private String name;
  private Map<String, String> metadata = new HashMap<>();

  //  @Builder
  //  public Node(String id, String uniqueId, String type, String name, Map<String, String>
  // metadata) {
  //    this.id = id;
  //    this.uniqueId = uniqueId;
  //    this.type = type;
  //    this.name = name;
  //    this.metadata = metadata;
  //  }
}
