package com.p.experiment.collate.with.generics.modules.tasks;

import com.p.experiment.collate.with.generics.modules.common.ExtractedDataAnalysisResult;
import com.p.experiment.collate.with.generics.modules.common.NodeDataType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.stream.Collectors;

@Service
public class TasksService {
    @Autowired
    private RestTemplate restTemplate;

    public TaskOfEP[] getTasksV5() {
        String url = "http://localhost:3003/consolidated-reporting?moduleName=tasks";
        return restTemplate.getForObject(url, TaskOfEP[].class);
    }

    public ExtractedDataAnalysisResult getAnalysisReportForExtractedTasks() {
        TaskOfEP[] tasksV5 = getTasksV5();

        return ExtractedDataAnalysisResult.builder()
                .dataType(NodeDataType.Tasks)
                .totalExtractedResult(tasksV5.length)
                .dataCountAsPerRating(Arrays.stream(tasksV5)
                        .collect(
                                Collectors.groupingBy(TaskOfEP::getRating,
                                        Collectors.counting()))
                )
                .dataCountAsPerPrivate(Arrays.stream(tasksV5)
                        .collect(
                                Collectors.partitioningBy(TaskOfEP::isPrivate,
                                        Collectors.counting())
                        )
                )
                .dataCountAsPerParentId(Arrays.stream(tasksV5)
                        .collect(
                                Collectors.groupingBy(TaskOfEP::getParentId,
                                        Collectors.counting())
                        )
                )
                .build();
    }
}
