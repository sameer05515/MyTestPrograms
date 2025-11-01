package com.p.node.service.nodes;

import java.util.HashMap;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "nodes")
public class Node {
  @Id private String uniqueId;
  @Builder.Default private Map<String, String> metadata = new HashMap<>();
}
