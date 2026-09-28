package com.p.node.service.nodes;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/nodes")
@RequiredArgsConstructor
public class NodeController {

  private final NodeService nodeService;

  @PostMapping("")
  public ResponseEntity<Node> createNode(@RequestBody Node node) {
    //    return ResponseMapper.createSuccessResponse(
    //        "Successfully created new Node", nodeService.saveNode(node), HttpStatus.OK);
    return ResponseEntity.ok(nodeService.saveNode(node));
  }

  @GetMapping("")
  public ResponseEntity<List<Node>> getAllNodes() {
    //    return ResponseMapper.createSuccessResponse(
    //        "Successfully retrieved all Nodes", nodeService.getAllNodes(), HttpStatus.OK);
    return ResponseEntity.ok(nodeService.getAllNodes());
  }

  @GetMapping("/{id}")
  public ResponseEntity<Node> getNodeById(@PathVariable String id) {
    //    return ResponseMapper.createSuccessResponse(nodeService.getNodeById(id), HttpStatus.OK);
    return ResponseEntity.ok(nodeService.getNodeById(id));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<String> deleteNode(@PathVariable String id) {
    nodeService.deleteNode(id);
    //    return ResponseMapper.createSuccessResponse(
    //        "Successfully deleted data for id: " + id, id, HttpStatus.OK);
    return ResponseEntity.ok("Successfully deleted data for id: " + id);
  }
}
