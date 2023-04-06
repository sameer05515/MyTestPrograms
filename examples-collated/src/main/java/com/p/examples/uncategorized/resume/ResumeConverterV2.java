package com.p.examples.uncategorized.resume;

import com.google.gson.Gson;
import com.p.examples.pojo.Resume;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * A utility class for converting and processing resume data from JSON files.
 * It reads a resume JSON file, processes the data, and generates HTML content
 * representing the resume details. It also extracts and prints technical stacks
 * from the resume data.
 */
public class ResumeConverterV2 {

    private static final Gson gson = new Gson();
    private static final String JSON_FILE_PATH = "src/main/resources/resume.json";
    private static final String RESULT_FILE_PATH = "src/main/resources/result.html";

    /**
     * The entry point of the application.
     * Reads a resume JSON file, processes the data, generates HTML content,
     * writes the HTML content to a file, and prints the technical stacks.
     *
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        try {
            Resume resume = readJsonFile(JSON_FILE_PATH);
            printResumeDetails(resume);
            String htmlContent = generateHtmlContent(resume);
            writeResultToFile(htmlContent);
            List<String> techStacks = extractTechStacks(resume);
            System.out.println(techStacks);
        } catch (IOException e) {
            System.err.println("Error processing the resume: " + e.getMessage());
        }
    }

    /**
     * Prints the resume details including unique ID and introduction, and
     * formats and prints the details of companies and their projects.
     *
     * @param resume The {@link Resume} object to be processed.
     */
    private static void printResumeDetails(Resume resume) {
        System.out.println("uniqueId : " + resume.getUniqueId() + " introduction : " + resume.getIntroduction());

        String result = resume.getCompanies().stream()
                .map(ResumeConverterV2::formatCompanyProjects)
                .reduce("\n\t==========Companies", (acc, str) -> acc + "\n\t-----\n\t" + str) + "\n==========\n";

        System.out.println(result);
    }

    /**
     * Formats the company projects into a readable string.
     *
     * @param company The {@link Resume.Company} object to be processed.
     * @return A formatted string representing the company and its projects.
     */
    private static String formatCompanyProjects(Resume.Company company) {
        String projects = company.getProjects().stream()
                .map(project -> project.getName())
                .reduce("\n\t\t==========Projects", (acc, str) -> acc + "\n\t\t" + str);

        return company.getName() + projects;
    }

    /**
     * Generates HTML content representing the resume details.
     *
     * @param resume The {@link Resume} object to be processed.
     * @return A string containing HTML content for the resume.
     */
    private static String generateHtmlContent(Resume resume) {
        Function<Resume.Company, String> companyDetailsExtractor = company -> String.format("""
                <div class="divBorder">
                    <span><b>uniqueId:-</b> %s </span> <br/>
                    <span><b>Name:-</b> %s </span>
                    %s
                </div>
                """, company.getUniqueId(), company.getName(), generateCompanyHtml(company));

        Function<Resume, String> resumeDetailsExtractor = res -> String.format("""
                <div class="divBorder">
                    <span><b>uniqueId:-</b> %s </span> <br/>
                    <span><b>introduction:-</b> %s </span>
                    %s
                </div>
                """, res.getUniqueId(), res.getIntroduction(),
                res.getCompanies() != null ? res.getCompanies().stream()
                        .map(companyDetailsExtractor)
                        .collect(Collectors.joining("\n")) : "");

        return String.format("""
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
                """, resumeDetailsExtractor.apply(resume));
    }

    /**
     * Formats the details of each company into HTML.
     *
     * @param company The {@link Resume.Company} object to be processed.
     * @return A string containing HTML content for the company and its projects.
     */
    private static String generateCompanyHtml(Resume.Company company) {
        Function<Resume.Company.Project, String> projectDetailsExtractor = project -> String.format("""
                <div>
                    <span><b>Project Name:</b> %s</span>
                </div>
                """, project.getName());

        String projectsHtml = company.getProjects().stream()
                .map(projectDetailsExtractor)
                .collect(Collectors.joining("\n"));

        return String.format("""
                <div class="divBorder">
                    <span><b>uniqueId:-</b> %s </span> <br/>
                    <span><b>Name:-</b> %s </span>
                    %s
                </div>
                """, company.getUniqueId(), company.getName(), projectsHtml);
    }

    /**
     * Extracts a list of unique technical stacks from all projects in the resume.
     *
     * @param resume The {@link Resume} object to be processed.
     * @return A list of unique technical stacks.
     */
    private static List<String> extractTechStacks(Resume resume) {
        return resume.getCompanies().stream()
                .flatMap(company -> company.getProjects().stream())
                .flatMap(project -> project.getProcessedDetails().getMetadata() != null
                        && project.getProcessedDetails().getMetadata().getTechStack() != null
                        ? project.getProcessedDetails().getMetadata().getTechStack().stream()
                        : List.<String>of().stream())
                .distinct()
                .collect(Collectors.toList());
    }

    /**
     * Reads a JSON file and converts it into a {@link Resume} object.
     *
     * @param filename The path to the JSON file.
     * @return A {@link Resume} object representing the data in the JSON file.
     * @throws IOException If an I/O error occurs while reading the file.
     */
    public static Resume readJsonFile(String filename) throws IOException {
        try (var reader = Files.newBufferedReader(Paths.get(filename))) {
            return gson.fromJson(reader, Resume.class);
        }
    }

    /**
     * Writes the result to an HTML file.
     *
     * @param result The content to be written to the file.
     */
    public static void writeResultToFile(String result) {
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(RESULT_FILE_PATH))) {
            writer.write(result);
            System.out.println("Result has been written to result.html.");
        } catch (IOException e) {
            System.err.println("Error writing result to file: " + e.getMessage());
        }
    }
}
