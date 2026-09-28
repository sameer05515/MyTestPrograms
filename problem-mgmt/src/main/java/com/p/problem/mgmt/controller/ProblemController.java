package com.p.problem.mgmt.controller;

import com.p.problem.mgmt.entity.Problem;
import com.p.problem.mgmt.service.ProblemDescriptionUpsertService;
import com.p.problem.mgmt.service.ProblemService;
import com.p.problem.mgmt.service.ProblemSolutionUpsertService;
import java.util.Optional;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ProblemController {

  private final ProblemService problemService;
  private final ProblemDescriptionUpsertService descriptionService;
  private final ProblemSolutionUpsertService solutionService;

  public ProblemController(
      ProblemService problemService,
      ProblemDescriptionUpsertService descriptionService,
      ProblemSolutionUpsertService solutionService) {
    this.problemService = problemService;
    this.descriptionService = descriptionService;
    this.solutionService = solutionService;
  }

  @GetMapping("/")
  public String index() {
    return "index";
  }

  @GetMapping("/problems")
  public String getAllProblems(Model model) {
    model.addAttribute("problems", problemService.getAllProblems());
    return "problems";
  }

  @GetMapping("/problems/{id}")
  public String getProblemDetails(@PathVariable Long id, Model model) {
    Optional<Problem> problem = problemService.getProblemById(id);
    if (problem.isPresent()) {
      model.addAttribute("problem", problem.get());
    } else {
      return "redirect:/problems";
    }
    return "problem-details";
  }

  @PostMapping("/problems/upsert")
  public String upsertProblem(
      @RequestParam(required = false) Long id,
      @RequestParam String title,
      @RequestParam Problem.Status status) {
    problemService.upsertProblem(id, title, status);
    return "redirect:/problems";
  }

  @PostMapping("/problems/descriptions/upsert")
  public String upsertDescription(
      @RequestParam(required = false) Long id,
      @RequestParam Long problemId,
      @RequestParam String description) {
    descriptionService.upsertDescription(id, problemId, description);
    return "redirect:/problems/" + problemId;
  }

  @PostMapping("/problems/solutions/upsert")
  public String upsertSolution(
      @RequestParam(required = false) Long id,
      @RequestParam Long problemId,
      @RequestParam String solution) {
    solutionService.upsertSolution(id, problemId, solution);
    return "redirect:/problems/" + problemId;
  }

  @GetMapping("/problems/edit/{id}")
  public String editProblem(@PathVariable Long id, Model model) {
    Optional<Problem> problem = problemService.getProblemById(id);
    if (problem.isPresent()) {
      model.addAttribute("problem", problem.get());
      return "edit-problem";
    } else {
      return "redirect:/problems";
    }
  }
}
