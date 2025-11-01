package com.p.problem.mgmt.controller;

import com.p.problem.mgmt.entity.Problem;
import com.p.problem.mgmt.entity.ProblemDescription;
import com.p.problem.mgmt.entity.ProblemPossibleSolution;
import com.p.problem.mgmt.service.ProblemDescriptionUpsertService;
import com.p.problem.mgmt.service.ProblemService;
import com.p.problem.mgmt.service.ProblemSolutionUpsertService;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/problems")
public class ProblemRestController {

  private final ProblemService problemService;
  private final ProblemDescriptionUpsertService descriptionService;
  private final ProblemSolutionUpsertService solutionService;

  public ProblemRestController(
      ProblemService problemService,
      ProblemDescriptionUpsertService descriptionService,
      ProblemSolutionUpsertService solutionService) {
    this.problemService = problemService;
    this.descriptionService = descriptionService;
    this.solutionService = solutionService;
  }

  // 1. Upsert a problem
  @PostMapping("/upsert")
  public ResponseEntity<Problem> upsertProblem(
      @RequestParam(required = false) Long id,
      @RequestParam String title,
      @RequestParam Problem.Status status) {
    Problem problem = problemService.upsertProblem(id, title, status);
    return ResponseEntity.ok(problem);
  }

  // 2. Upsert a description for the given problem
  @PostMapping("/{problemId}/descriptions/upsert")
  public ResponseEntity<ProblemDescription> upsertDescription(
      @PathVariable Long problemId,
      @RequestParam(required = false) Long id,
      @RequestParam String description) {
    ProblemDescription desc = descriptionService.upsertDescription(id, problemId, description);
    return ResponseEntity.ok(desc);
  }

  // 3. Upsert a solution for the given problem
  @PostMapping("/{problemId}/solutions/upsert")
  public ResponseEntity<ProblemPossibleSolution> upsertSolution(
      @PathVariable Long problemId,
      @RequestParam(required = false) Long id,
      @RequestParam String solution) {
    ProblemPossibleSolution sol = solutionService.upsertSolution(id, problemId, solution);
    return ResponseEntity.ok(sol);
  }

  // 4. Fetch all problems (id, title, and status only)
  @GetMapping
  public ResponseEntity<List<Map<String, ? extends Serializable>>> getAllProblems() {
    List<Map<String, ? extends Serializable>> problems =
        problemService.getAllProblems().stream()
            .map(
                problem ->
                    Map.of(
                        "id",
                        problem.getId(),
                        "title",
                        problem.getTitle(),
                        "status",
                        problem.getStatus()))
            .collect(Collectors.toList());
    return ResponseEntity.ok(problems);
  }

  // 5. Fetch problem details for a given id
  @GetMapping("/{id}")
  public ResponseEntity<Problem> getProblemDetails(@PathVariable Long id) {
    Optional<Problem> problem = problemService.getProblemById(id);
    return problem.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
  }
}
