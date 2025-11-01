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
public class QuestionDetails extends Question {
  private List<Answer> answers = new ArrayList<>();

  @Builder(builderMethodName = "questionDetailsBuilder")
  public QuestionDetails(
      String uniqueId, String name, String sectionId, String softDelete, List<Answer> answers) {
    super(uniqueId, name, sectionId, softDelete);
    this.answers = answers;
  }
}
