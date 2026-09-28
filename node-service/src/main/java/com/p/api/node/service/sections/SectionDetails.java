package com.p.api.node.service.sections;

import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class SectionDetails extends Section {
  private List<Question> questions = new ArrayList<>();

  @Builder(builderMethodName = "sectionDetailsBuilder")
  public SectionDetails(
      String uniqueId, String name, String parentId, String softDelete, List<Question> questions) {
    super(uniqueId, name, parentId, softDelete);
    this.questions = questions;
  }
}
