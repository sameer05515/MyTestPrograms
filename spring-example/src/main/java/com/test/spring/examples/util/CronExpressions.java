package com.test.spring.examples.util;

public enum CronExpressions {
        EVERY_DAY_AT_8_AM(
                        "Every Day At 8 AM",
                        "0 0 8 ? * * *"),
        EVERY_ONE_HOUR(
                        "Every One Hour",
                        "0 0 0/1 ? * * *"),
        EVERY_FIRST_DAY_OF_MONTH(
                        "Every First Day Of Month",
                        "0 0 0 1 * ? *"),

        EVERY_7_SECONDS_START_AT_00_SEC(
                        "Every 7 seconds starting at :00 second after the minute",
                        "0/7 * * * * ?"),

        EVERY_5_SECONDS_START_AT_01_SEC(
                        "Every 7 seconds starting at :01 second after the minute",
                        "1/5 * * * * ?");

        private String expression;

        private String label;

        CronExpressions(String label, String expression) {
                this.expression = expression;
                this.label = label;
        }

        public String getExpression() {
                return expression;
        }

        public String getLabel() {
                return label;
        }
}