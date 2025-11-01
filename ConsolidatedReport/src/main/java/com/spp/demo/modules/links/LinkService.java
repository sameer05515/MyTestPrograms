package com.spp.demo.modules.links;

import java.util.List;

public interface LinkService {

  public List<Link> findAllLinksForSectionList();

  public Link findByUniqueId(String uniqueId);
}
