package com.spp.demo.modules.topics;

import com.spp.demo.base.exception.CRDataNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TopicServiceImpl implements TopicService {
  private final TopicRepository topicRepository;

  @Override
  public List<Topic> findAllTopicsForSectionList() {
    return topicRepository.findAllTopicsForSectionList();
  }

  @Override
  public Topic findByUniqueId(String uniqueId) {
    return topicRepository
        .findByUniqueId(uniqueId)
        .orElseThrow(
            () -> new CRDataNotFoundException("No Topic found for given uniqueId: " + uniqueId));
  }
}
