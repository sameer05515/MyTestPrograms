package com.p.problem.mgmt.nosql.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProblemDescription {
  // Optional if using MongoDB; helps uniquely identify embedded documents
  @Id private String id;
  private String detail;
  //  private String language;
}
