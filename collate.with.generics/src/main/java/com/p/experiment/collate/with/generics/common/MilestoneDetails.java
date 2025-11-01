package com.p.experiment.collate.with.generics.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MilestoneDetails {
    private String id;
    private String details;
    private String startedOn;
    private String completedOn;
    private CWGConstants status;
}