package com.p.experiment.collate.with.generics.modules.questions;

import com.p.experiment.collate.with.generics.common.base.response.ResponseMapper;
import com.p.experiment.collate.with.generics.common.base.response.StandardResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/questions")
public class QuestionsController {
    @Autowired
    private QuestionsService questionService;

    @GetMapping("/v1")
    public String getQuestionsV1(){
        return "Question data will be shared soon";
    }
    @GetMapping("/v2")
    public String getQuestionsV2(){
        return questionService.getQuestionsV2();
    }
    @GetMapping("/v3")
    public String getQuestionsV3(){
        return questionService.getQuestionsV3();
    }

    @GetMapping("/v4")
    public String getQuestionsV4(){
        return questionService.getQuestionsV4();
    }

    @GetMapping("/v5")
    public ResponseEntity<StandardResponse<String>> getQuestionsV5(){
        String questions= questionService.getQuestionsV4();
        return ResponseMapper.createSuccessResponse(questions, HttpStatus.OK);
    }

    @GetMapping("/v6")
    public QuestionOfExtractPhase[] getQuestionsV6(){
        return questionService.getQuestionsV5();
    }
}
