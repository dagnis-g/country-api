package com.dagnis.countryapi.controller;

import com.dagnis.countryapi.dto.CountryDto;
import com.dagnis.countryapi.service.CountryService;
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
public class CountryController {

    private final CountryService countryService;

    @GetMapping("/get-country/{ip}")
    @Operation(description = "Get details about country from supplied IP address")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Data returned"),
            @ApiResponse(responseCode = "404", description = "No data found", content = @Content(schema = @Schema(hidden = true)))})
    public ResponseEntity<CountryDto> getCountryByIp(@PathVariable String ip) throws IOException, InterruptedException, URISyntaxException {
        var countryDto = countryService.getCountryByIp(ip);
        if (countryDto == null) {
            return ResponseEntity.status(404).build();
        }
        return ResponseEntity.status(200).body(countryDto);
    }

}
