package com.spp.demo.modules.topics;

import java.util.List;

public interface TopicService {

  public List<Topic> findAllTopicsForSectionList();

  public Topic findByUniqueId(String uniqueId);
}
