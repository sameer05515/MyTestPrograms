package com.p.problem.mgmt.service;

import com.p.problem.mgmt.entity.Problem;
import com.p.problem.mgmt.entity.ProblemDescription;
import com.p.problem.mgmt.repository.ProblemDescriptionRepository;
import com.p.problem.mgmt.repository.ProblemRepository;
import org.springframework.stereotype.Service;

@Service
public class ProblemDescriptionUpsertService {

  private final ProblemRepository problemRepository;
  private final ProblemDescriptionRepository descriptionRepository;

  public ProblemDescriptionUpsertService(
      ProblemRepository problemRepository, ProblemDescriptionRepository descriptionRepository) {
    this.problemRepository = problemRepository;
    this.descriptionRepository = descriptionRepository;
  }

  public ProblemDescription upsertDescription(Long id, Long problemId, String descriptionText) {
    Problem problem =
        problemRepository
            .findById(problemId)
            .orElseThrow(
                () ->
                    new IllegalArgumentException(
                        "Problem with id " + problemId + " does not exist."));

    ProblemDescription description =
        descriptionRepository.findById(id).orElse(new ProblemDescription());
    description.setId(id); // Optional, for clarity
    description.setProblem(problem);
    description.setDescription(descriptionText);

    return descriptionRepository.save(description);
  }
}
