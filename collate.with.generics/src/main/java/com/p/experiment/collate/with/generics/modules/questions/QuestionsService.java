package com.p.experiment.collate.with.generics.modules.questions;

import com.p.experiment.collate.with.generics.modules.common.ExtractedDataAnalysisResult;
import com.p.experiment.collate.with.generics.modules.common.NodeDataType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.stream.Collectors;

@Service
public class QuestionsService {

    @Autowired
    private RestTemplate restTemplate;

    public String getQuestionsV2() {
        return "Question data will be shared soon";
    }

    public String getQuestionsV3() {
        String url = "http://localhost:3003/consolidated-reporting";
        // You can use .getForObject or other methods based on your requirement
        return restTemplate.getForObject(url, String.class);
    }

    public String getQuestionsV4() {
        String url = "http://localhost:3003/consolidated-reporting?moduleName=questions";
        // You can use .getForObject or other methods based on your requirement
        return restTemplate.getForObject(url, String.class);
    }

    public QuestionOfExtractPhase[] getQuestionsV5() {
        String url = "http://localhost:3003/consolidated-reporting?moduleName=questions";
//        Question[] questions = restTemplate.getForObject(url, Question[].class);
        return restTemplate.getForObject(url, QuestionOfExtractPhase[].class);
    }

    public ExtractedDataAnalysisResult getAnalysisReportForExtractedQuestions() {
        QuestionOfExtractPhase[] questions = getQuestionsV5();

        return ExtractedDataAnalysisResult.builder()
                .dataType(NodeDataType.Questions)
                .totalExtractedResult(questions.length)
                .dataCountAsPerRating(Arrays.stream(questions)
                        .collect(
                                Collectors.groupingBy(QuestionOfExtractPhase::getRating,
                                        Collectors.counting()))
                )
                .dataCountAsPerPrivate(Arrays.stream(questions)
                        .collect(
                                Collectors.partitioningBy(QuestionOfExtractPhase::isPrivate,
                                        Collectors.counting())
                        )
                )
                .dataCountAsPerParentId(Arrays.stream(questions)
                        .collect(
                                Collectors.groupingBy(QuestionOfExtractPhase::getParentId,
                                        Collectors.counting())
                        )
                )
                .build();
    }
}
