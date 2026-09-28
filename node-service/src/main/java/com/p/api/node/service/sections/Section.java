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
public class Section {
  protected String uniqueId;
  protected String name;
  protected String parentId;
  protected String softDelete = "no";

  @Builder
  public Section(String uniqueId, String name, String parentId, String softDelete) {
    this.uniqueId = uniqueId;
    this.name = name;
    this.parentId = parentId;
    this.softDelete = softDelete;
  }
}
