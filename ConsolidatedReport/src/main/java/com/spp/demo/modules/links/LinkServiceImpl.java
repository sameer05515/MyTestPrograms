package com.spp.demo.modules.links;

import com.spp.demo.base.exception.CRDataNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LinkServiceImpl implements LinkService {
  private final LinkRepository linkRepository;

  @Override
  public List<Link> findAllLinksForSectionList() {
    return linkRepository.findAllLinksForSectionList();
  }

  @Override
  public Link findByUniqueId(String uniqueId) {
    return linkRepository
        .findByUniqueId(uniqueId)
        .orElseThrow(
            () -> new CRDataNotFoundException("No Link found for given uniqueId: " + uniqueId));
  }
}
