package com.p.problem.mgmt.nosql.repository;

import com.p.problem.mgmt.nosql.model.Problem;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProblemRepository extends MongoRepository<Problem, String> {}
