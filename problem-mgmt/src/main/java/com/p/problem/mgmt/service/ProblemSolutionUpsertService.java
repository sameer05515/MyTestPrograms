package com.p.problem.mgmt.service;

import com.p.problem.mgmt.entity.Problem;
import com.p.problem.mgmt.entity.ProblemPossibleSolution;
import com.p.problem.mgmt.repository.ProblemPossibleSolutionRepository;
import com.p.problem.mgmt.repository.ProblemRepository;
import org.springframework.stereotype.Service;

@Service
public class ProblemSolutionUpsertService {

  private final ProblemRepository problemRepository;
  private final ProblemPossibleSolutionRepository solutionRepository;

  public ProblemSolutionUpsertService(
      ProblemRepository problemRepository, ProblemPossibleSolutionRepository solutionRepository) {
    this.problemRepository = problemRepository;
    this.solutionRepository = solutionRepository;
  }

  public ProblemPossibleSolution upsertSolution(Long id, Long problemId, String solutionText) {
    Problem problem =
        problemRepository
            .findById(problemId)
            .orElseThrow(
                () ->
                    new IllegalArgumentException(
                        "Problem with id " + problemId + " does not exist."));

    ProblemPossibleSolution solution =
        solutionRepository.findById(id).orElse(new ProblemPossibleSolution());
    solution.setId(id); // Optional, for clarity
    solution.setProblem(problem);
    solution.setSolution(solutionText);

    return solutionRepository.save(solution);
  }
}
