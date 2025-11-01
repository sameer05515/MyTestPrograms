package com.p.java.playground.api.array.controller;

import com.p.java.playground.api.array.service.ArrayService;
import com.p.java.playground.api.base.response.ResponseMapper;
import com.p.java.playground.api.base.response.StandardResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * The RandomArrayController class provides a REST API to generate a random array of integers.
 */
@RestController
@RequestMapping("/arrays")
public class ArrayController {

    private final ArrayService arrayService;

    /**
     * Constructor for RandomArrayController.
     *
     * @param arrayService The service to generate random arrays.
     */
    @Autowired
    public ArrayController(ArrayService arrayService) {
        this.arrayService = arrayService;
    }

    /**
     * Endpoint to generate a random array of integers with the specified length.
     *
     * @param length The length of the array to generate. Must be a positive integer.
     * @return A JSON array of integers with the specified length.
     */
    @Operation(summary = "Generate a Random Array",
            description = "Generates a random array of integers with the specified length.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful operation"),
                    @ApiResponse(responseCode = "400", description = "Invalid length parameter")
            })
    @GetMapping("/get-random-array")
    public ResponseEntity<StandardResponse<int[]>> getRandomArray(
            @Parameter(description = "The length of the array to generate. Must be a positive integer.",
                    example = "10")
            @RequestParam(value = "length", defaultValue = "10") int length) {
        int[] array = arrayService.generateRandomArray(length);

        return ResponseMapper.createSuccessResponse(array, HttpStatus.OK);
    }

    /**
     * Endpoint to find the next index in an array of given length and current index.
     *
     * @param arrayLength  The length of the array.
     * @param currentIndex The current index in the array.
     * @return A ResponseEntity containing the next index.
     */
    @Operation(
            summary = "Find the next index in an array",
            description = "This endpoint calculates the next index in an array given its current length and index. " +
                    "It ensures the calculated index is within valid bounds.",
            parameters = {
                    @Parameter(name = "arrayLength", description = "The length of the array", required = true, example = "10"),
                    @Parameter(name = "currentIndex", description = "The current index in the array", required = true, example = "5")
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
    @GetMapping("/next-index")
    public ResponseEntity<StandardResponse<Integer>> getNextIndex(
            @RequestParam int arrayLength,
            @RequestParam int currentIndex) {
        int nextIndex = arrayService.findNextIndex(arrayLength, currentIndex);
        return ResponseMapper.createSuccessResponse("Successfully found next index.", nextIndex, HttpStatus.OK);
    }
}
