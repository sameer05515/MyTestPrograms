package com.spp.demo.sections;

import com.spp.demo.modules.common.Node;
import com.spp.demo.modules.tasks.TaskService;
import com.spp.demo.modules.topics.TopicService;
import java.util.List;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SectionServiceV3 {
  private final TaskService taskService;
  private final TopicService topicService;

  public List<NodeListItemVO> fetchSectionsV6() {
    // Combine and convert nodes from topics and tasks into a single list
    return Stream.concat(
            convertNodesToSectionList(topicService.findAllTopicsForSectionList()),
            convertNodesToSectionList(taskService.findAllTasksForSectionList()))
        .toList();
  }

  private Stream<NodeListItemVO> convertNodesToSectionList(List<? extends Node> nodes) {
    // Handle null or empty lists gracefully
    return nodes == null
        ? Stream.empty()
        : nodes.stream()
            .parallel()
            .map(
                node ->
                    NodeListItemVO.builder()
                        .type(node.getType())
                        .name(node.getName())
                        .uniqueId(node.getUniqueId())
                        .parentId(node.getParentId())
                        .build());
  }
}
