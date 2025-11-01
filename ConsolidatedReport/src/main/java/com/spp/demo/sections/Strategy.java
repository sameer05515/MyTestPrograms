// package com.spp.demo.sections;
//
// import com.spp.demo.modules.common.Node;
//
//// Enum-based strategy with a functional interface
// public enum Strategy {
//  DEFAULT(() -> builder().build()), // Default: Empty object
//  NULL_RETURN(node -> null), // Return null
//  FALLBACK_TO_DEFAULT_NAME(
//      node -> // Fallback to default name
//      NodeListItemVO.builder()
//              .type(node != null ? node.getType() : "DefaultType")
//              .name(node != null ? node.getName() : "DefaultName")
//              .uniqueId(node != null ? node.getUniqueId() : "DefaultId")
//              .parentId(node != null ? node.getParentId() : "DefaultParentId")
//              .build());
//
//  private final Function<Node, NodeListItemVO> strategyFunction;
//
//  Strategy(Function<Node, NodeListItemVO> strategyFunction) {
//    this.strategyFunction = strategyFunction;
//  }
//
//  public NodeListItemVO apply(Node node) {
//    return strategyFunction.apply(node);
//  }
// }
