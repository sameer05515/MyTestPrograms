package com.p.java.playground.api.salary.controller;

import com.p.java.playground.api.base.response.StandardResponse;
import com.p.java.playground.api.salary.enums.ExchangeRates;
import com.p.java.playground.api.salary.service.SalaryCalculationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/salaries")
public class SalaryCalculationController {

    private final SalaryCalculationService salaryCalculationService;

    @Autowired
    public SalaryCalculationController(SalaryCalculationService salaryCalculationService) {
        this.salaryCalculationService = salaryCalculationService;
    }

    /**
     * Endpoint to calculate and retrieve freelancer wage details.
     *
     * @param salary         The annual salary.
     * @param daysInYear     Total days in a year.
     * @param leavesInYear   Number of leaves in a year.
     * @param workingHour    Working hours in a day.
     * @param minCoeff       Minimum coefficient.
     * @param maxCoeff       Maximum coefficient.
     * @return A map containing the wage details in different currencies.
     */
    @Operation(
            summary = "Find the next index in an array",
            description = "This endpoint calculates the next index in an array given its current length and index. " +
                    "It ensures the calculated index is within valid bounds.",
            parameters = {
                    @Parameter(name = "salary", description = "salary", required = true, example = "3100000"),
                    @Parameter(name = "daysInYear", description = "daysInYear", required = true, example = "365"),
                    @Parameter(name = "leavesInYear", description = "leavesInYear", required = true, example = "0"),
                    @Parameter(name = "workingHour", description = "workingHour", required = true, example = "24"),
                    @Parameter(name = "minCoeff", description = "minCoeff", required = true, example = "2"),
                    @Parameter(name = "maxCoeff", description = "maxCoeff", required = true, example = "6")
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Next index calculated successfully",
                            content = @Content(schema = @Schema(implementation = StandardResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid input parameters",
                            content = @Content(schema = @Schema(implementation = StandardResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Internal server error",
                            content = @Content(schema = @Schema(implementation = StandardResponse.class)))
            }
    )
    @GetMapping("/calculate-wages")
    public ResponseEntity<Map<String, Object>> getFreelancerWages(
            @Parameter(description = "Salary Must be a positive integer.",
                    example = "3100000")
            @RequestParam(value = "salary", defaultValue = "0") double salary,
            @RequestParam double daysInYear,
            @RequestParam double leavesInYear,
            @RequestParam double workingHour,
            @RequestParam double minCoeff,
            @RequestParam double maxCoeff) {

        double oneHourMinFreelancerWage = salaryCalculationService.calculateHourlyFreelancerWage(salary, daysInYear, leavesInYear, workingHour, minCoeff);
        double oneHourMaxFreelancerWage = salaryCalculationService.calculateHourlyFreelancerWage(salary, daysInYear, leavesInYear, workingHour, maxCoeff);

        Map<String, Object> response = new HashMap<>();
        for (ExchangeRates rate : ExchangeRates.values()) {
            response.put(rate.name() + "_oneHourMinFreelancerWage", salaryCalculationService.convertToCurrency(oneHourMinFreelancerWage, rate));
            response.put(rate.name() + "_oneHourMaxFreelancerWage", salaryCalculationService.convertToCurrency(oneHourMaxFreelancerWage, rate));
            response.put(rate.name() + "_oneDayMinFreelancerWage", salaryCalculationService.convertToCurrency(oneHourMinFreelancerWage, rate) * workingHour);
            response.put(rate.name() + "_oneDayMaxFreelancerWage", salaryCalculationService.convertToCurrency(oneHourMaxFreelancerWage, rate) * workingHour);
        }

        return ResponseEntity.ok(response);
    }
}
