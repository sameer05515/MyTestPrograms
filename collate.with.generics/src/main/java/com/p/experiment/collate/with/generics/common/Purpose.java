package com.p.experiment.collate.with.generics.common;

import java.util.Arrays;
import java.util.List;

public class Purpose {
    public static final String welcomeToProject = """
            ================
            Welcome to our project [Project started on- 22-Oct-2024, Completed on- TBD]
                Purpose: create an application, where adding a new field is easy.
            
            [Completed]: Milestone 0: Set-up initial project
            [Planned]: Milestone 1: fetch existing topics, tasks and Questions from mongoDB, via invoking existing APIs
            [Planned]: Milestone 2: Find common fields in topics, tasks and Questions and put them in a common superclass
            [Planned]: Milestone 3: Fetch data from one mongodb and store them in another, such that
                - [Planned]: If ETL operation is performed second time then,
                    - Only new records should be saved
                    - Existing records should be updated
                - [Planned]: For simplicity, we can pass existing topics via APIs, or read them through RestTemplates
                    - We will go with RestTemplates approach.
            
            ================
            """;
    public static final String milestone_0_details = """
            [Completed]: Milestone 0: Set-up initial project
                Metadata:
                    - [Started 22-Oct-2024, 9:00 AM, Completed: 22-Oct-2024, 12:12 PM]
            
                [22-Oct-2024, 9:00 AM]: [Activity: Done]: - created project with below initial dependencies, from 'spring initializer'
                    - Spring Web (org.springframework.boot:spring-boot-starter-web) , Why - To be mentioned (TBM)
                    - Spring Data MongoDB (org.springframework.boot:spring-boot-starter-data-mongodb), Why- TBM
            
                [22-Oct-2024, 9:15 AM]: [Activity: Done]: - Created below java file
                    - `src/main/java/com/p/experiment/collate/with/generics/common/Purpose.java`
                        - Purpose: to store welcome-message, project-purpose and milestone-details
                    - `src/main/java/com/p/experiment/collate/with/generics/common/StatusConstants.java`
                        - Purpose: An Enum to store Status constants
            
                [22-Oct-2024, 11:00 AM]: [Activity: Done]: Added `thymeleaf` dependency
                    - Thymeleaf (org.springframework.boot:spring-boot-starter-thymeleaf)
                        - Purpose: to render welcome-message and milestone-details on welcome page
            
                [22-Oct-2024, 12:12 PM]: [Activity: Done]: Marking this milestone done.
                    Note: Any further UI or bug-fix will be mentioned in sub-sequent milestone details
            
            """;
    public static final String milestone_1_details = """
            [Planned]: Milestone 1: fetch existing topics, tasks and Questions from mongoDB, via invoking existing APIs
                Metadata
                    - [Started: 22-Oct-2024, 12:26 PM, Completed: - ]
            
                [22-Oct-2024, 12:35 PM]: [Activity: Done]: Added `devtools` dependency
                    - Devtools (org.springframework.boot: spring-boot-devtools)
                        - Purpose: To perform fast application restarts, LiveReload, and configurations for enhanced development experience.
            
                [22-Oct-2024, 12:45 PM]: [Activity: In-Progress]: Added code to fetch questions and show on questions.html page
            
                Roadblocks:
                    -
            """;

    private static final List<MilestoneDetails> milestonesDetails
            = Arrays.asList(
            MilestoneDetails.builder()
                    .id("milestone-0")
                    .details(milestone_0_details)
                    .startedOn("22-Oct-2024")
                    .completedOn("-")
                    .status(CWGConstants.MILESTONE_STATUS_IN_PROGRESS)
                    .build(),

            MilestoneDetails.builder()
                    .id("milestone-1")
                    .details(milestone_1_details)
                    .startedOn("-")
                    .completedOn("-")
                    .status(CWGConstants.MILESTONE_STATUS_PLANNED)
                    .build()
    );
};


