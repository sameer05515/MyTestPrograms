package com.p.api.node.service.sections;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode
@Data
// @AllArgsConstructor
@NoArgsConstructor
// @Builder
public class Question {
  protected String uniqueId;
  protected String name;
  protected String sectionId;
  protected String softDelete = "no";

  @Builder
  public Question(String uniqueId, String name, String sectionId, String softDelete) {
    this.uniqueId = uniqueId;
    this.name = name;
    this.sectionId = sectionId;
    this.softDelete = softDelete;
  }
}
