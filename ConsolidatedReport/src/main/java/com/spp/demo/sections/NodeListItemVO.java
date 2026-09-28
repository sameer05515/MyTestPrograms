package com.spp.demo.sections;

import com.spp.demo.modules.common.Node;
import java.util.function.Supplier;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NodeListItemVO {
  private String uniqueId;
  private String name;
  private String type;
  private String parentId;

  /**
   * Creates a NodeListItemVO instance from a given Node.
   *
   * @param node the source Node to convert
   * @return a NodeListItemVO instance containing data from the Node // * @throws
   *     IllegalArgumentException if the input Node is null
   */
  public static NodeListItemVO buildFromNode(Node node) {
    return buildFromNode(node, () -> builder().build()); // Default strategy
  }

  private static NodeListItemVO buildFromNode(Node node, Supplier<NodeListItemVO> strategy) {
    if (node == null) {
      return strategy.get(); // Apply the strategy for null handling
    }
    return builder()
        .type(node.getType())
        .name(node.getName())
        .uniqueId(node.getUniqueId())
        .parentId(node.getParentId())
        .build();
  }
}
