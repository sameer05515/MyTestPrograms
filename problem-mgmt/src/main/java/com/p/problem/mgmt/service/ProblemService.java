package com.p.problem.mgmt.service;

import com.p.problem.mgmt.entity.Problem;
import com.p.problem.mgmt.repository.ProblemRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class ProblemService {

  private final ProblemRepository problemRepository;

  public ProblemService(ProblemRepository problemRepository) {
    this.problemRepository = problemRepository;
  }

  public Problem saveProblem(Problem problem) {
    return problemRepository.save(problem);
  }

  public List<Problem> getAllProblems() {
    return problemRepository.findAll();
  }

  public Optional<Problem> getProblemById(Long id) {
    return problemRepository.findById(id);
  }

  public Problem upsertProblem(Long id, String title, Problem.Status status) {
    Problem problem = problemRepository.findById(id).orElse(new Problem());
    problem.setId(id); // Optional, for clarity; not needed for new entities
    problem.setTitle(title);
    problem.setStatus(status);
    problem.setCreatedOn(
        problem.getCreatedOn() != null ? problem.getCreatedOn() : LocalDateTime.now());

    return problemRepository.save(problem);
  }
}
