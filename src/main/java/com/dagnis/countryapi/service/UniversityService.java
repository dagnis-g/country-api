package com.dagnis.countryapi.service;

import com.dagnis.countryapi.dto.UniversityDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
@RequiredArgsConstructor
@Slf4j
public class UniversityService {

    private final ObjectMapper objectMapper;
    private final HttpClient httpClient;

    public UniversityDto[] getUniversitiesByCountry(String country) throws URISyntaxException, IOException, InterruptedException {
        log.info("Making Universities GET Request for country: {}", country);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("http://universities.hipolabs.com/search?country=" + country))
                .GET()
                .build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        log.info("Universities Response for country - {}: {}", country, response.body());

        return objectMapper.readValue(response.body(), UniversityDto[].class);
    }
}
