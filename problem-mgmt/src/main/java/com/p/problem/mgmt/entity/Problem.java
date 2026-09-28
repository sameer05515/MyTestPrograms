package com.p.problem.mgmt.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Problem {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String title;

  private LocalDateTime createdOn;

  @Enumerated(EnumType.STRING)
  private Status status;

  @OneToMany(mappedBy = "problem", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<ProblemDescription> descriptions;

  @OneToMany(mappedBy = "problem", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<ProblemPossibleSolution> solutions;

  // Getters and Setters
  public enum Status {
    OPEN,
    WIP,
    ON_HOLD,
    CLOSED
  }
}
