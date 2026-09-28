package com.p.problem.mgmt.utils;

import com.p.problem.mgmt.service.ProblemDescriptionUpsertService;
import com.p.problem.mgmt.service.ProblemSolutionUpsertService;
import com.p.problem.mgmt.service.ProblemUpsertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Utility {
  @Autowired private ProblemUpsertService problemUpsertService;
  @Autowired private ProblemDescriptionUpsertService descriptionUpsertService;
  @Autowired private ProblemSolutionUpsertService solutionUpsertService;
}
