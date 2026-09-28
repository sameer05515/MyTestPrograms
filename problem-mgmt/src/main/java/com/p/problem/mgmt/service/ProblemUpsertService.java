package com.p.problem.mgmt.service;

import com.p.problem.mgmt.repository.ProblemRepository;
import org.springframework.stereotype.Service;

@Service
public class ProblemUpsertService {

  private final ProblemRepository problemRepository;

  public ProblemUpsertService(ProblemRepository problemRepository) {
    this.problemRepository = problemRepository;
  }

  //  public Problem upsertProblem(Long id, String title, Problem.Status status) {
  //    Problem problem = problemRepository.findById(id).orElse(new Problem());
  //    problem.setId(id); // Optional, for clarity; not needed for new entities
  //    problem.setTitle(title);
  //    problem.setStatus(status);
  //    problem.setCreatedOn(
  //        problem.getCreatedOn() != null ? problem.getCreatedOn() : LocalDateTime.now());
  //
  //    return problemRepository.save(problem);
  //  }
}
