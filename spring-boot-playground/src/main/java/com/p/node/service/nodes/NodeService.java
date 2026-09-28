package com.p.node.service.nodes;

import java.util.List;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NodeService {

  private final NodeRepository nodeRepository;

  public Node saveNode(Node node) {
    return nodeRepository.save(node);
  }

  public List<Node> getAllNodes() {
    return nodeRepository.findAll();
  }

  public Node getNodeById(String id) {
    return nodeRepository
        .findById(id)
        .orElseThrow(() -> new NoSuchElementException("No data found for id: " + id));
  }

  public void deleteNode(String id) {
    nodeRepository.deleteById(id);
  }
}
