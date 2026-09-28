package com.spp.demo.sections;

import com.spp.demo.base.exception.CRValidationException;
import com.spp.demo.modules.common.Node;
import com.spp.demo.modules.links.LinkService;
import com.spp.demo.modules.tags.TagService;
import com.spp.demo.modules.tasks.TaskService;
import com.spp.demo.modules.topics.TopicService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NodeDetailsServiceV2 {
  private final TaskService taskService;
  private final TopicService topicService;
  private final TagService tagService; // New dependency
  private final LinkService linkService;

  public Node findNodeByUniqueIdAndType(String uniqueId, String type) {
    // Validate input
    if (uniqueId == null || uniqueId.isBlank() || type == null || type.isBlank()) {
      throw new CRValidationException("uniqueId or type is not a valid value");
    }

    // Use switch expression for type handling
    return switch (type.toUpperCase()) {
      case "TOPIC" -> topicService.findByUniqueId(uniqueId);
      case "TASK" -> taskService.findByUniqueId(uniqueId);
      case "TAG" -> tagService.findByUniqueId(uniqueId);
      case "LINK" -> linkService.findByUniqueId(uniqueId);
      default -> throw new CRValidationException("No data found for type: " + type);
    };
  }
}
