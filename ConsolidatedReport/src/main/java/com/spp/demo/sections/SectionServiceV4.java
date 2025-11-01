package com.spp.demo.sections;

// public class SectionServiceV4 {}

import com.spp.demo.modules.common.Node;
import com.spp.demo.modules.links.LinkService;
import com.spp.demo.modules.tags.TagService; // New service
import com.spp.demo.modules.tasks.TaskService;
import com.spp.demo.modules.topics.TopicService;
import java.util.List;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SectionServiceV4 {

  private final TaskService taskService;
  private final TopicService topicService;
  private final TagService tagService; // New dependency
  private final LinkService linkService;

  public List<NodeListItemVO> fetchSectionsV7() {
    // Combine streams from task, topic, and tag services
    return Stream.of(
            convertNodesToSectionList(topicService.findAllTopicsForSectionList()),
            convertNodesToSectionList(taskService.findAllTasksForSectionList()),
            convertNodesToSectionList(tagService.findAllTagsForSectionList()), // New stream
            convertNodesToSectionList(linkService.findAllLinksForSectionList()) // New stream
            )
        .flatMap(s -> s)
        .toList(); // Flatten the streams and collect into a list
  }

  private Stream<NodeListItemVO> convertNodesToSectionList(List<? extends Node> nodes) {
    return nodes == null ? Stream.empty() : nodes.stream().map(NodeListItemVO::buildFromNode);
  }
}
