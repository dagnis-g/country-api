package com.dagnis.countryapi.service;

import com.dagnis.countryapi.dto.CountryDto;
import com.dagnis.countryapi.mapper.CountryMapper;
import com.dagnis.countryapi.model.CountryEntity;
import com.dagnis.countryapi.repository.CountryRepository;
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
public class CountryService {

    private final CountryRepository countryRepository;
    private final ObjectMapper objectMapper;
    private final CountryMapper countryMapper;
    private final HttpClient httpClient;

    public CountryDto getCountryByIp(String ip) throws IOException, URISyntaxException, InterruptedException {
        var countryDto = fetchCountryByIpFromApi(ip);
        if (countryDto == null) {
            return null;
        }

        CountryEntity countryEntity = countryMapper.countryDtoToEntity(countryDto);
        countryRepository.save(countryEntity);

        return countryDto;
    }

    private CountryDto fetchCountryByIpFromApi(String ip) throws URISyntaxException, IOException, InterruptedException {
        log.info("Making GET Request for IP: {}", ip);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("https://ipinfo.io/" + ip + "/geo"))
                .GET()
                .build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        log.info("Response for IP: {}", response.body());

        if (response.statusCode() != 200) {
            return null;
        }
        return objectMapper.readValue(response.body(), CountryDto.class);
    }
}
