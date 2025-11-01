package com.spp.demo.sections;

import com.spp.demo.modules.common.Node;
import com.spp.demo.modules.tasks.TaskService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/sections/v1")
@RequiredArgsConstructor
public class SectionControllerV1 {

  private final TaskService taskService;
  private final SectionServiceV1 sectionServiceV1;
  private final SectionServiceV2 sectionServiceV2;
  private final SectionServiceV3 sectionServiceV3;
  private final SectionServiceV4 sectionServiceV4;
  private final NodeDetailsServiceV1 nodeDetailsServiceV1;
  private final NodeDetailsServiceV2 nodeDetailsServiceV2;

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/fetch-sections-v1")
  public ResponseEntity<List<? extends Node>> fetchSectionsV1() {
    return ResponseEntity.ok(taskService.findAllUniqueIds());
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/fetch-sections-v2")
  public ResponseEntity<List<NodeListItemVO>> fetchSectionsV2() {
    return ResponseEntity.ok(
        taskService.findAllUniqueIds().stream()
            .map(
                t ->
                    NodeListItemVO.builder()
                        .type(t.getType())
                        .name(t.getName())
                        .uniqueId(t.getUniqueId())
                        .parentId(t.getParentId())
                        .build())
            .toList());
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/fetch-sections-v3")
  public ResponseEntity<List<NodeListItemVO>> fetchSectionsV3() {
    return ResponseEntity.ok(
        taskService.findAllTasksForSectionList().stream()
            .map(
                t ->
                    NodeListItemVO.builder()
                        .type(t.getType())
                        .name(t.getName())
                        .uniqueId(t.getUniqueId())
                        .parentId(t.getParentId())
                        .build())
            .toList());
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/fetch-sections-v4")
  public ResponseEntity<List<NodeListItemVO>> fetchSectionsV4() {
    return ResponseEntity.ok(sectionServiceV1.fetchSectionsV4());
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/fetch-sections-v5")
  public ResponseEntity<List<NodeListItemVO>> fetchSectionsV5() {
    return ResponseEntity.ok(sectionServiceV2.fetchSectionsV5());
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/fetch-sections-v6")
  public ResponseEntity<List<NodeListItemVO>> fetchSectionsV6() {
    return ResponseEntity.ok(sectionServiceV3.fetchSectionsV6());
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/fetch-sections-v7")
  public ResponseEntity<List<NodeListItemVO>> fetchSectionsV7() {
    return ResponseEntity.ok(sectionServiceV4.fetchSectionsV7());
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/fetch-section-details-v1")
  public ResponseEntity<? extends Node> fetchSectionDetailsV1(
      @RequestParam String uniqueId, @RequestParam String type) {
    return ResponseEntity.ok(nodeDetailsServiceV1.findNodeByUniqueIdAndType(uniqueId, type));
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/fetch-section-details-v2")
  public ResponseEntity<? extends Node> fetchSectionDetailsV2(
      @RequestParam String uniqueId, @RequestParam String type) {
    return ResponseEntity.ok(nodeDetailsServiceV2.findNodeByUniqueIdAndType(uniqueId, type));
  }
}
