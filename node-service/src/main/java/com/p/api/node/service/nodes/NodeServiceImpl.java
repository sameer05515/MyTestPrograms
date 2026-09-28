package com.p.api.node.service.nodes;

import java.util.List;
import java.util.NoSuchElementException;
import javax.management.openmbean.KeyAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NodeServiceImpl implements NodeService {

  private final NodeRepository nodeRepository;

  public Node saveNode(Node node) {
    if (nodeRepository.findByUniqueId(node.getUniqueId()).isPresent()) {
      throw new KeyAlreadyExistsException("uniqueId Already Exists: " + node.getUniqueId());
    }
    return nodeRepository.save(node);
  }

  @Override
  public Node updateNode(Node node) {
    Node existingNode =
        nodeRepository
            .findByUniqueId(node.getUniqueId())
            .orElseThrow(
                () ->
                    new NoSuchElementException(
                        "No node found for uniqueId: " + node.getUniqueId()));

    // Update the existing node's fields based on the provided node's fields
    existingNode.setName(node.getName() != null ? node.getName() : existingNode.getName());
    existingNode.setType(node.getType() != null ? node.getType() : existingNode.getType());
    existingNode.setMetadata(
        node.getMetadata() != null ? node.getMetadata() : existingNode.getMetadata());

    // Save the updated node back to the repository
    return nodeRepository.save(existingNode);
  }

  public List<Node> getAllNodes() {
    return nodeRepository.findAllUniqueIds();
  }

  public Node getNodeById(String id) {
    return nodeRepository
        .findByUniqueId(id)
        .orElseThrow(() -> new NoSuchElementException("No node found for uniqueId: " + id));
  }

  public void deleteNode(String id) {
    nodeRepository.deleteById(id);
  }

  @Override
  public List<Node> getAllNodesByMetadataKeyValue(String key, String value) {
    return nodeRepository.findByMetadataKeyValue(key, value);
  }

  @Override
  public List<Node> getAllNodesByType(String type) {
    return nodeRepository.findAllByType(type);
  }
}
