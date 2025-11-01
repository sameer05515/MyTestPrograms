package com.spp.demo.modules.tags;

import java.util.List;

public interface TagService {

  public List<Tag> findAllTagsForSectionList();

  public Tag findByUniqueId(String uniqueId);
}
