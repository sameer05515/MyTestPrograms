package com.p.examples.uncategorized.resume;

import com.google.gson.Gson;
import com.p.examples.pojo.Resume;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ResumeConverterV1 {

    private static final Gson gson = new Gson();

    public static void main(String[] args) {
        Resume resume = null;
        try {
            resume = readJsonFile("src/main/resources/resume.json");
            //System.out.println(resume); // Print the converted object
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Print resume uniqueId and introduction
        System.out.println("uniqueId : " + resume.getUniqueId() + " introduction : " + resume.getIntroduction());

        // Print all company names and unique id, its projects
        String result = resume.getCompanies().stream()
                .map(company -> /*company.getUniqueId() + ": " +*/ company.getName() + company.getProjects().stream()
                        .map(project -> /*project.getUniqueId() + ": " +*/ project.getName())
                        .reduce("\n\t\t==========Projects", (acc, str) -> acc.isEmpty() ? str : acc + "\n\t\t" + str))
                .reduce("\n\t==========Companies", (acc, str) -> acc.isEmpty() ? str : acc + "\n\t-----\n\t" + str) + "\n==========\n";
//        writeResultToFile(result);
//        System.out.println(result);

        // Printing same resume, company, project details in functional way
        Function<Resume.Company, String> companyDetailsExtractor = (comp) -> """
                <div class="divBorder">
                    <span><b>uniqueId:-</b> %s </span> <br/>
                    <span><b>Name:-</b> %s </span>
                </div>
                """.formatted(comp.getUniqueId(), comp.getName());

        Function<Resume, String> resumeDetailsExtractor = (res) -> """
                <div class="divBorder">
                    <span><b>uniqueId:-</b> %s </span> <br/>
                    <span><b>introduction:-</b> %s </span>
                    %s
                </div>
                """.formatted(res.getUniqueId(), res.getIntroduction()
                , res.getCompanies() != null ? res.getCompanies().stream().map(c -> companyDetailsExtractor.apply(c)).reduce("", (acc, str) -> acc.isEmpty() ? str : acc + "\n" + str) : "");

        Function<Resume, String> htmlDetailsFormatter = (res) -> """
                <!DOCTYPE html>
                <html lang="en">
                <head>
                    <meta charset="UTF-8">
                    <meta name="viewport" content="width=device-width, initial-scale=1.0">
                    <title>My Resume details</title>
                    <style>
                        .divBorder{
                            border: 1px solid yellow; padding-left: 10px; margin-bottom: 5px;
                        }
                    </style>
                </head>
                <body>
                    %s
                </body>
                </html>
                """.formatted(resumeDetailsExtractor.apply(res));
        writeResultToFile(htmlDetailsFormatter.apply(resume));


        // collect techStack lists for a company
        Function<Resume.Company, List<String>> companyTechStacksExtractor= company -> company.getProjects().stream()
                .flatMap(project -> project.getProcessedDetails().getMetadata()!=null && project.getProcessedDetails().getMetadata().getTechStack()!=null?project.getProcessedDetails().getMetadata().getTechStack().stream(): new ArrayList<String>().stream())
                /*.distinct()*/
                .collect(Collectors.toList());
        List<String> tSacks=resume.getCompanies().stream().flatMap(company -> companyTechStacksExtractor.apply(company).stream())/*.distinct()*/.collect(Collectors.toList());
        System.out.println(tSacks);

    }

    public static Resume readJsonFile(String filename) throws IOException {
        try (Reader reader = Files.newBufferedReader(Paths.get(filename))) {
            return gson.fromJson(reader, Resume.class);
        }
    }

    public static void writeResultToFile(String result) {
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get("src/main/resources/result.html"))) {
            writer.write(result);
            System.out.println("Result has been written to result.html.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
