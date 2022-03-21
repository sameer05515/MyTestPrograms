package com.p.consume.rest;

import java.util.ArrayList;
import java.util.List;

public class GitReponseList {
    public List<GitResponse> getResponses() {
        return responses;
    }

    public void setResponses(List<GitResponse> responses) {
        this.responses = responses;
    }

    private List<GitResponse> responses;

    public GitReponseList() {
        responses = new ArrayList<>();
    }
}
