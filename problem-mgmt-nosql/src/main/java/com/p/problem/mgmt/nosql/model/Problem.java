package com.p.problem.mgmt.nosql.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "problems")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class Problem {
  @Id private String id;
  private String title;
  private LocalDateTime createdOn;
  private Status status;
  @Builder.Default private List<ProblemDescription> descriptions = new ArrayList<>();
  @Builder.Default private List<ProblemPossibleSolution> solutions = new ArrayList<>();

  // Getters and Setters
}
