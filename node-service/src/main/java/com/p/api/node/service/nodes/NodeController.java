package com.p.api.node.service.nodes;

import com.p.api.node.service.base.response.ResponseMapper;
import com.p.api.node.service.base.response.StandardResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/nodes")
@RequiredArgsConstructor
public class NodeController {

  private final NodeService nodeService;

  @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<StandardResponse<Node>> createNode(@RequestBody Node node) {
    return ResponseMapper.createSuccessResponse(
        "Successfully created new Node", nodeService.saveNode(node), HttpStatus.OK);
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<StandardResponse<List<Node>>> getAllNodes() {
    return ResponseMapper.createSuccessResponse(
        "Successfully retrieved all Nodes", nodeService.getAllNodes(), HttpStatus.OK);
  }

  @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<StandardResponse<Node>> getNodeById(@PathVariable String id) {
    return ResponseMapper.createSuccessResponse(nodeService.getNodeById(id), HttpStatus.OK);
  }

  @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<StandardResponse<String>> deleteNode(@PathVariable String id) {
    nodeService.deleteNode(id);
    return ResponseMapper.createSuccessResponse(
        "Successfully deleted data for id: " + id, id, HttpStatus.OK);
  }
}
