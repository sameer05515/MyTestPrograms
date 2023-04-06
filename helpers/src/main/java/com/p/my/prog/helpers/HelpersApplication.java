package com.p.my.prog.helpers;

import com.p.my.prog.helpers.service.StringReplacer;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@SpringBootApplication
public class HelpersApplication {

    public static void main(String[] args) {
        SpringApplication.run(HelpersApplication.class, args);
    }

    String[] topics = {"Java", "Spring", "Spring boot"};

    @Autowired
    private StringReplacer stringReplacer;

    @Bean
    ApplicationRunner appStarted() {
        return args -> {
            Arrays.stream(topics).forEach(t->prepareQuestions(t));
        };
    }

    private void prepareQuestions(String t) {
        List<GroupQ> groupQList= prepareWhs();
        System.out.println("========================== "+t+" =======");
        groupQList.stream().forEach( gq->
                {
                    System.out.println(gq.name);
                    gq.questions.stream().forEach(
                            q-> System.out
                                    .println(
                                            "\t"+stringReplacer
                                                    .replaceTopic(q, t)
                                    )
                    );
                }
        );
    }


    private List<GroupQ> prepareWhs() {
        return Arrays.asList(
                GroupQ.builder()
                        .name("What")
                        .questions(Arrays.asList(
								"What is the definition of this #topic#? or What is #topic#?",
								"What are the key components or concepts within this #topic#?",
								"What are the primary uses or applications of this #topic#?",
								"What are the main challenges or issues associated with this #topic#?"))
                        .build(),
				GroupQ.builder()
						.name("Why")
						.questions(Arrays.asList(
                                "Why is this #topic# important or relevant?",
                                "Why are certain methods or approaches used within this #topic#?",
                                "Why does this #topic# matter in the broader context of the field?",
                                "Why did certain versions introduce specific changes or features?",
                                "Why are certain buzzwords popular in the context of this #topic#?"
                        ))
						.build(),
                GroupQ.builder()
                        .name("Who")
                        .questions(Arrays.asList(
                                "Who are the main contributors or experts in this field?",
                                "Who can benefit from understanding this #topic#?",
                                "Who are the primary stakeholders involved in this #topic#?",
                                "Who were the key developers or organizations behind each major version?",
                                "Who coined or popularized the buzzwords associated with this #topic#?"
                        ))
                        .build(),
                GroupQ.builder()
                        .name("When")
                        .questions(Arrays.asList(
                                "When did this #topic# emerge or become significant?",
                                "When are the critical moments or milestones in the development of this #topic#?",
                                "When should certain approaches or methods be applied within this #topic#?",
                                "When were the significant versions or updates released for #topic#?",
                                "When did #topic# become popular?"
                        ))
                        .build(),
                GroupQ.builder()
                        .name("Where")
                        .questions(Arrays.asList(
                                "Where is this #topic# most relevant or commonly applied?",
                                "Where can you find resources or further information on this #topic#?",
                                "Where are the key research institutions or companies working on this #topic#?",
                                "Where can you find the changelogs or release notes for the different versions?",
                                "Where are the discussions or forums where these buzzwords are frequently used?"
                        ))
                        .build(),
                GroupQ.builder()
                        .name("How")
                        .questions(Arrays.asList(
                                "How does this #topic# work in practice?",
                                "How can you apply the concepts of this #topic# to real-world scenarios?",
                                "How has this #topic# evolved over time?",
                                "How do the features or functionalities differ between versions?",
                                "How do the buzzwords relate to the underlying concepts or technologies in this #topic#?"
                        ))
                        .build(),
                GroupQ.builder()
                        .name("Which")
                        .questions(Arrays.asList(
                                "Which version is most stable or recommended for use?",
                                "Which buzzwords should you be aware of to stay current in the field?"
                        ))
                        .build(),
                GroupQ.builder()
                        .name("What if")
                        .questions(Arrays.asList(
                                "What if you need to migrate from one version to another?",
                                "What if the buzzword is just a hype and lacks substantial backing?"
                        ))
                        .build(),
                GroupQ.builder()
                        .name("What are")
                        .questions(Arrays.asList(
                                "What are the backward compatibility considerations for each version?",
                                "What are the misconceptions or myths around the buzzwords in this #topic#?"
                        ))
                        .build(),
                GroupQ.builder()
                        .name("How has")
                        .questions(Arrays.asList(
                                "How has the community or market response been to different versions?",
                                "How has the usage of buzzwords evolved over time in this #topic#?"
                        ))
                        .build(),
                GroupQ.builder()
                        .name("miscellaneous")
                        .questions(Arrays.asList(
                                "What are alternatives of #topic#?"
                        ))
                        .build()
        );
    }

}

@Data
@Builder
class GroupQ {
    String name;
    List<String> questions;
}
