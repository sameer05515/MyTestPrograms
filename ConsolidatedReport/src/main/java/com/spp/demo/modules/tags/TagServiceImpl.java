package com.spp.demo.modules.tags;

import com.spp.demo.base.exception.CRDataNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {
  private final TagRepository tagRepository;

  @Override
  public List<Tag> findAllTagsForSectionList() {
    return tagRepository.findAllTagsForSectionList();
  }

  @Override
  public Tag findByUniqueId(String uniqueId) {
    return tagRepository
        .findByUniqueId(uniqueId)
        .orElseThrow(
            () -> new CRDataNotFoundException("No Tag found for given uniqueId: " + uniqueId));
  }
}
