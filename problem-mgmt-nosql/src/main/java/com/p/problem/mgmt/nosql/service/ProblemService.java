package com.p.problem.mgmt.nosql.service;

import com.p.problem.mgmt.nosql.exception.CustomValidationException;
import com.p.problem.mgmt.nosql.model.Problem;
import com.p.problem.mgmt.nosql.model.ProblemDescription;
import com.p.problem.mgmt.nosql.model.ProblemPossibleSolution;
import com.p.problem.mgmt.nosql.repository.ProblemRepository;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProblemService {

  private final ProblemRepository problemRepository;

  private static final Function<Problem, Void> validateProblem =
      problem -> {
        if (problem == null || StringUtils.isBlank(problem.getTitle())) {
          throw new CustomValidationException("Problem title should not be empty or null");
        }
        return null;
      };

  private static final Function<ProblemDescription, Void> validateDescription =
      description -> {
        if (description == null || StringUtils.isBlank(description.getDetail())) {
          throw new CustomValidationException(
              "ProblemDescription detail should not be empty or null");
        }
        return null;
      };

  private static final Function<ProblemPossibleSolution, Void> validateSolution =
      solution -> {
        if (solution == null || StringUtils.isBlank(solution.getSolutionDetail())) {
          throw new CustomValidationException("ProblemSolution detail should not be empty or null");
        }
        return null;
      };

  public List<Problem> getAllProblems() {
    return problemRepository.findAll();
  }

  public Optional<Problem> getProblemById(String id) {
    return problemRepository.findById(id);
  }

  public Problem upsertProblem(Problem problem) {
    validateProblem.apply(problem);

    Problem existingProblem = problemRepository.findById(problem.getId()).orElse(new Problem());
    existingProblem.setTitle(
        Optional.ofNullable(problem.getTitle()).orElse(existingProblem.getTitle()));
    existingProblem.setStatus(
        Optional.ofNullable(problem.getStatus()).orElse(existingProblem.getStatus()));

    return problemRepository.save(existingProblem);
  }

  public Optional<Problem> upsertDescriptionToProblem(
      String problemId, ProblemDescription updatedDescription) {
    validateDescription.apply(updatedDescription);

    return problemRepository
        .findById(problemId)
        .map(
            problem -> {
              updateOrAdd(
                  problem.getDescriptions(),
                  updatedDescription,
                  ProblemDescription::getId,
                  existing -> existing.setDetail(updatedDescription.getDetail()));
              problemRepository.save(problem);
              return problem;
            });
  }

  public Optional<Problem> addSolutionToProblem(
      String problemId, ProblemPossibleSolution updatedSolution) {
    validateSolution.apply(updatedSolution);

    return problemRepository
        .findById(problemId)
        .map(
            problem -> {
              updateOrAdd(
                  problem.getSolutions(),
                  updatedSolution,
                  ProblemPossibleSolution::getId,
                  existing -> existing.setSolutionDetail(updatedSolution.getSolutionDetail()));
              problemRepository.save(problem);
              return problem;
            });
  }

  private <T> void updateOrAdd(
      List<T> items, T newItem, Function<T, String> idGetter, Consumer<T> updateExisting) {
    String newId = idGetter.apply(newItem);
    items.stream()
        .filter(item -> idGetter.apply(item).equals(newId))
        .findFirst()
        .ifPresentOrElse(
            updateExisting,
            () -> {
              if (StringUtils.isBlank(newId) && newItem instanceof ProblemDescription) {
                ((ProblemDescription) newItem).setId(new ObjectId().toString());
              } else if (StringUtils.isBlank(newId) && newItem instanceof ProblemPossibleSolution) {
                ((ProblemPossibleSolution) newItem).setId(new ObjectId().toString());
              }
              items.add(newItem);
            });
  }
}
