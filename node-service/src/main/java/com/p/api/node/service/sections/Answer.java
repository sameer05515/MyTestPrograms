package com.p.api.node.service.sections;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Answer {
  private String uniqueId;
  private String name;
  private String questionId;
  private String text;
  @Builder.Default private String softDelete = "no";
}
