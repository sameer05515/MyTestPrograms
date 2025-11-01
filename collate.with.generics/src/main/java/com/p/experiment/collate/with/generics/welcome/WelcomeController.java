package com.p.experiment.collate.with.generics.welcome;

import com.p.experiment.collate.with.generics.common.Purpose;
import com.p.experiment.collate.with.generics.modules.chatRenderer.CGPTService;
import com.p.experiment.collate.with.generics.modules.common.ExtractedDataAnalysisResult;
import com.p.experiment.collate.with.generics.modules.questions.QuestionOfExtractPhase;
import com.p.experiment.collate.with.generics.modules.questions.QuestionsService;
import com.p.experiment.collate.with.generics.modules.tasks.TasksService;
import com.p.experiment.collate.with.generics.modules.topics.TopicsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;

@Controller(value = "")
public class WelcomeController {

    @Autowired
    private QuestionsService questionService;

    @Autowired
    private TopicsService topicsService;

    @Autowired
    private TasksService tasksService;

    @Autowired
    private CGPTService cgptService;


    @GetMapping("/")
    public String welcome(Model model) {
        model.addAttribute("welcomeMessage", Purpose.welcomeToProject);
        model.addAttribute("milestone_0_details", Purpose.milestone_0_details);
        model.addAttribute("milestone_1_details", Purpose.milestone_1_details);
        return "welcome";
    }

    @GetMapping("/models")
    public String allModelsDashboard(Model model){
        return "models-dashboard";
    }

    @GetMapping("/models/v1")
    public String allModelsDashboardV1(Model model){
        return "models/dashboard";
    }

    @GetMapping("/models/questions")
    public String questionsDashboardModel(Model model){
        return "models/questions/dashboard";
    }

    @GetMapping("/models/questions/page/v1")
    public String questionsModelPageV1(Model model){
        String questions= questionService.getQuestionsV4();
        model.addAttribute("questions", questions);
        return "models/questions/page/page-v1";
    }

    @GetMapping("/models/questions/page/v2")
    public String questionsModelPageV2(Model model){
        QuestionOfExtractPhase[] questionOfExtractPhases = questionService.getQuestionsV5();
        model.addAttribute("questions", questionOfExtractPhases);
        return "models/questions/page/page-v2";
    }

    @GetMapping("/models/questions/page/v3")
    public String questionsAnalysis(Model model){
        ExtractedDataAnalysisResult analysisResult= questionService.getAnalysisReportForExtractedQuestions();
        model.addAttribute("analysisResult", analysisResult);
        return "models/questions/page/page-v3";
    }

    @GetMapping("/models/topics/page/v3")
    public String topicsAnalysis(Model model){
        ExtractedDataAnalysisResult analysisResult= topicsService.getAnalysisReportForExtractedTopics();
        model.addAttribute("analysisResult", analysisResult);
        return "models/questions/page/page-v3";
    }

    @GetMapping("/models/tasks/page/v3")
    public String tasksAnalysis(Model model){
        ExtractedDataAnalysisResult analysisResult= tasksService.getAnalysisReportForExtractedTasks();
        model.addAttribute("analysisResult", analysisResult);
        return "models/questions/page/page-v3";
    }

    /** Chat-GPT views*/
    @GetMapping("/models/cgpt-files/")
    public String goToCGPTDashboard(Model model){
        model.addAttribute("cgptFilesInfo",cgptService.getAllChatGPTFileBasicInfo());
        return "models/cgpt-files/dashboard";
    }

    @GetMapping("/models/cgpt-files/{fileUID}")
    public String goToSelectedCGPTWithConversationNames(@PathVariable String fileUID, Model model){
        model.addAttribute("cgptFile",cgptService.getChatGPTFileInfoWithConversationNames(fileUID));
        return "models/cgpt-files/conv/dashboard";
    }
}
