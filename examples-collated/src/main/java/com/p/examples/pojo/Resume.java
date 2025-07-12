package com.p.examples.pojo;

import lombok.Data;

import java.util.List;

@Data
public class Resume {
    private String uniqueId;
    private String introduction;
    private ProcessedDetails processedDetails;
    private List<Company> companies;
    private List<Education> educations;

    @Data
    public static class ProcessedDetails {
        private Metadata metadata;
        private String textType;

        @Data
        public static class Metadata {
            private List<String> summarizedIntroduction;
            private PersonalDetails personalDetails;

            @Data
            public static class PersonalDetails {
                private String name;
            }
        }
    }

    @Data
    public static class Company {
        private String name;
        private ProcessedDetails processedDetails;
        private List<Project> projects;
        private String uniqueId;

        @Data
        public static class ProcessedDetails {
            private Metadata metadata;
            private String textType;

            @Data
            public static class Metadata {
                private String company;
                private List<String> aboutCompany;
                private List<String> domainOfCompany;
                private String companyWebsiteURL;
                private String overAllTenure;
                private String lastDesignation;
                private List<String> highlights;
                private List<EmploymentHistory> employmentHistories;
                private List<String> references;

                @Data
                public static class EmploymentHistory {
                    private String tenure;
                    private String employeeCode;
                    private String lastWorkingDay;
                    private String dateOfJoining;
                    private String designation;
                    private String reasonForJoining;
                    private List<ReasonForChange> reasonForChange;
                    private String joiningCTC;
                    private String exitCTC;

                    @Data
                    public static class ReasonForChange {
                        private String actual;
                        private String forHR;
                    }
                }
            }
        }

        @Data
        public static class Project {
            private String name;
            private ProcessedDetails processedDetails;
            private String uniqueId;

            @Data
            public static class ProcessedDetails {
                private Metadata metadata;
                private String textType;

                @Data
                public static class Metadata {
                    private String project;
                    private String tenure;
                    private List<String> description;
                    private List<String> techStack;
                    private List<RoleResponsibility> rolesAndResponsibilities;

                    @Data
                    public static class RoleResponsibility {
                        private String role;
                        private List<String> responsibilities;
                    }
                }
            }
        }
    }

    @Data
    public static class Education {
        private String uniqueId;
        private String name;
        private ProcessedDetails processedDetails;

        @Data
        public static class ProcessedDetails {
            private Metadata metadata;
            private String textType;

            @Data
            public static class Metadata {
                private String name;
            }
        }
    }
}
