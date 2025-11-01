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
public class NodeDetailsServiceV1 {
  private final TaskService taskService;
  private final TopicService topicService;
  private final TagService tagService; // New dependency
  private final LinkService linkService;

  public Node findNodeByUniqueIdAndType(String uniqueId, String type) {
    if (uniqueId == null || uniqueId.trim().isEmpty() || type == null || type.trim().isEmpty()) {
      throw new CRValidationException("uniqueId or type is not a valid value");
    }

    if (type.equalsIgnoreCase("TOPIC")) {
      return topicService.findByUniqueId(uniqueId);
    }

    throw new CRValidationException("No data found for type: " + type);
  }
}
