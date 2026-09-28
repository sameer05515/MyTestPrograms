package com.p.api.node.service.nodes;

import java.util.List;

public interface NodeService {

  public Node saveNode(Node node);

  public Node updateNode(Node node);

  public List<Node> getAllNodes();

  public Node getNodeById(String id);

  public void deleteNode(String id);

  public List<Node> getAllNodesByMetadataKeyValue(String key, String value);

  public List<Node> getAllNodesByType(String type);
}
