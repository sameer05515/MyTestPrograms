package com.p.problem.mgmt.nosql.controller;

import com.p.problem.mgmt.nosql.exception.BusinessDataNotFoundException;
import com.p.problem.mgmt.nosql.model.Problem;
import com.p.problem.mgmt.nosql.model.ProblemDescription;
import com.p.problem.mgmt.nosql.model.ProblemPossibleSolution;
import com.p.problem.mgmt.nosql.service.ProblemService;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/problems")
public class ProblemController {

  private final ProblemService problemService;

  public ProblemController(ProblemService problemService) {
    this.problemService = problemService;
  }

  // Get all problems
  @GetMapping
  public List<Map<String, ? extends Serializable>> getAllProblems() {
    return problemService.getAllProblems().stream()
        .map(
            problem ->
                Map.of(
                    "id", problem.getId(),
                    "title", problem.getTitle(),
                    "status", problem.getStatus()))
        .collect(Collectors.toList());
  }

  // Get problem details by ID
  @GetMapping("/{id}")
  public ResponseEntity<Problem> getProblemById(@PathVariable String id) {
    return problemService
        .getProblemById(id)
        .map(ResponseEntity::ok)
        .orElseThrow(
            () -> new BusinessDataNotFoundException("Problem with ID " + id + " not found"));
  }

  // Upsert a problem
  @PostMapping
  public ResponseEntity<Problem> upsertProblem(@RequestBody Problem problem) {
    Problem savedProblem = problemService.upsertProblem(problem);
    return ResponseEntity.status(HttpStatus.CREATED).body(savedProblem);
  }

  // Upsert a problem description
  @PostMapping("/{problemId}/descriptions")
  public ResponseEntity<Problem> upsertDescription(
      @PathVariable String problemId, @RequestBody ProblemDescription description) {
    return problemService
        .upsertDescriptionToProblem(problemId, description)
        .map(ResponseEntity::ok)
        .orElseThrow(
            () ->
                new BusinessDataNotFoundException(
                    "Description for Problem ID " + problemId + " not found"));
  }

  // Upsert a problem solution
  @PostMapping("/{problemId}/solutions")
  public ResponseEntity<Problem> upsertSolution(
      @PathVariable String problemId, @RequestBody ProblemPossibleSolution solution) {
    return problemService
        .addSolutionToProblem(problemId, solution)
        .map(ResponseEntity::ok)
        .orElseThrow(
            () ->
                new BusinessDataNotFoundException(
                    "Solution for Problem ID " + problemId + " not found"));
  }
}
