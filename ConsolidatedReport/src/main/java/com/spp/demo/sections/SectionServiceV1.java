package com.spp.demo.sections;

import com.spp.demo.modules.common.Node;
import com.spp.demo.modules.tasks.TaskService;
import com.spp.demo.modules.topics.TopicService;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SectionServiceV1 {

  private final TaskService taskService;
  private final TopicService topicService;

  public List<NodeListItemVO> fetchSectionsV4() {
    //    return taskService.findAllTasksForSectionList().stream()
    //        .map(
    //            t ->
    //                SectionListItemPojo.builder()
    //                    .type(t.getType())
    //                    .name(t.getName())
    //                    .uniqueId(t.getUniqueId())
    //                    .build())
    //        .toList();
    List<NodeListItemVO> itemPojoList = new ArrayList<>();
    itemPojoList.addAll(convertNodesToSectionList(topicService.findAllTopicsForSectionList()));
    itemPojoList.addAll(convertNodesToSectionList(taskService.findAllTasksForSectionList()));
    return itemPojoList;
  }

  private List<NodeListItemVO> convertNodesToSectionList(List<? extends Node> list) {
    if (list == null) return List.of();

    return list.stream()
        .map(
            t ->
                NodeListItemVO.builder()
                    .type(t.getType())
                    .name(t.getName())
                    .uniqueId(t.getUniqueId())
                    .parentId(t.getParentId())
                    .build())
        .toList();
  }
}
