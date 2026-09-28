package com.p.experiment.collate.with.generics.modules.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ExtractedDataAnalysisResult {
    public NodeDataType dataType;
    public int totalExtractedResult;
    @Builder.Default
    public Map<Integer, Long> dataCountAsPerRating= new HashMap<>();

    @Builder.Default
    public Map<Boolean, Long> dataCountAsPerPrivate= new HashMap<>();

    @Builder.Default
    public Map<String, Long> dataCountAsPerParentId= new HashMap<>();
}
