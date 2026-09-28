package com.example.query;

import graphql.kickstart.tools.GraphQLQueryResolver;
import org.springframework.stereotype.Component;

// import com.coxautodev.graphql.tools.GraphQLQueryResolver;

@Component
public class Query implements GraphQLQueryResolver {

  public String firstQuery() {
    return "First Query";
  }

  public String secondQuery() {
    return "Second Query";
  }
}
