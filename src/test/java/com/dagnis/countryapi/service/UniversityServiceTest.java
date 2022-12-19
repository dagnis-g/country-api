package com.dagnis.countryapi.service;

import com.dagnis.countryapi.dto.UniversityDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UniversityServiceTest {

    @Spy
    private ObjectMapper objectMapper;
    @Mock
    private HttpResponse<String> mockedResponse;
    @Mock
    private HttpClient httpClient;
    @InjectMocks
    private UniversityService universityService;

    @Test
    void shouldReturnUniversities() throws IOException, InterruptedException, URISyntaxException {
        String json = "[{\"name\":\"Medical Academy of Latvia\",\"domains\":[\"aml.lv\"],\"country\":\"Latvia\",\"alpha_two_code\":\"LV\",\"web_pages\":[\"http://www.aml.lv/\"],\"state-province\":null}]";
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("http://universities.hipolabs.com/search?country=" + "Latvia"))
                .GET()
                .build();

        when(httpClient.send(request, HttpResponse.BodyHandlers.ofString())).thenReturn(mockedResponse);
        when(mockedResponse.body()).thenReturn(json);

        UniversityDto[] universityDto = universityService.getUniversitiesByCountry("Latvia");

        assertThat(universityDto[0].getAlphaTwoCode()).isEqualTo("LV");
        assertThat(universityDto[0].getName()).isEqualTo("Medical Academy of Latvia");
        assertThat(universityDto[0].getWebPages().get(0)).isEqualTo("http://www.aml.lv/");
        assertThat(universityDto[0].getStateProvince()).isEqualTo(null);
        assertThat(universityDto[0].getDomains().get(0)).isEqualTo("aml.lv");
        assertThat(universityDto[0].getCountry()).isEqualTo("Latvia");
    }

}