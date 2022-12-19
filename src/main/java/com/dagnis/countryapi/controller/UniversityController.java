package com.dagnis.countryapi.controller;

import com.dagnis.countryapi.dto.UniversityDto;
import com.dagnis.countryapi.service.UniversityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URISyntaxException;

@RequiredArgsConstructor
@RestController
public class UniversityController {

    private final UniversityService universityService;

    @GetMapping("/get-universities/{country}")
    @Operation(description = "Get list of universities in supplied country")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Data returned"),
            @ApiResponse(responseCode = "404", description = "No data found", content = @Content(schema = @Schema(hidden = true)))})
    public ResponseEntity<UniversityDto[]> getUniversitiesByCountry(@PathVariable String country) throws URISyntaxException, IOException, InterruptedException {
        UniversityDto[] universities = universityService.getUniversitiesByCountry(country);
        if (universities == null || universities.length == 0) {
            return ResponseEntity.status(404).build();
        }
        return ResponseEntity.status(200).body(universities);
    }

}
