package com.p.experiment.collate.with.generics.modules.topics;

import com.p.experiment.collate.with.generics.modules.common.ExtractedDataAnalysisResult;
import com.p.experiment.collate.with.generics.modules.common.NodeDataType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.stream.Collectors;

@Service
public class TopicsService {
    @Autowired
    private RestTemplate restTemplate;

    public TopicOfEP[] getTopicsV5() {
        String url = "http://localhost:3003/consolidated-reporting?moduleName=topics";
        return restTemplate.getForObject(url, TopicOfEP[].class);
    }

    public ExtractedDataAnalysisResult getAnalysisReportForExtractedTopics() {
        TopicOfEP[] topicsV5 = getTopicsV5();

        return ExtractedDataAnalysisResult.builder()
                .dataType(NodeDataType.Topics)
                .totalExtractedResult(topicsV5.length)
                .dataCountAsPerRating(Arrays.stream(topicsV5)
                        .collect(
                                Collectors.groupingBy(TopicOfEP::getRating,
                                        Collectors.counting()))
                )
                .dataCountAsPerPrivate(Arrays.stream(topicsV5)
                        .collect(
                                Collectors.partitioningBy(TopicOfEP::isPrivate,
                                        Collectors.counting())
                        )
                )
                .dataCountAsPerParentId(Arrays.stream(topicsV5)
                        .collect(
                                Collectors.groupingBy(TopicOfEP::getParentId,
                                        Collectors.counting())
                        )
                )
                .build();
    }
}
