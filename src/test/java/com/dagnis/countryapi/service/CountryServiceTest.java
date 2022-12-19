package com.dagnis.countryapi.service;

import com.dagnis.countryapi.mapper.CountryMapper;
import com.dagnis.countryapi.model.CountryEntity;
import com.dagnis.countryapi.repository.CountryRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.TimeZone;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CountryServiceTest {
    @Captor
    private ArgumentCaptor<CountryEntity> countryCaptor;
    @Spy
    private ObjectMapper objectMapper;
    @Mock
    private HttpResponse<String> mockedResponse;
    @Mock
    private CountryRepository countryRepository;
    @Mock
    private CountryMapper countryMapper;
    @Mock
    private HttpClient httpClient;
    @InjectMocks
    private CountryService countryService;

    private String json = "{\"ip\":\"123\",\"city\":\"Riga\",\"region\":\"Riga\",\"country\":\"LV\",\"postal\":\"1001\",\"timezone\":\"Europe/Riga\",\"readme\":\"readme text\",\"loc\":\"1.23,1.23\",\"org\":\"Big company\"}";

    @Test
    void shouldReturnCountryDto() throws IOException, URISyntaxException, InterruptedException {
        HttpRequest request = createRequest();

        when(httpClient.send(request, HttpResponse.BodyHandlers.ofString())).thenReturn(mockedResponse);
        when(mockedResponse.statusCode()).thenReturn(200);
        when(mockedResponse.body()).thenReturn(json);

        var countryDto = countryService.getCountryByIp("123");

        assertThat(countryDto.getIp()).isEqualTo("123");
        assertThat(countryDto.getCity()).isEqualTo("Riga");
        assertThat(countryDto.getRegion()).isEqualTo("Riga");
        assertThat(countryDto.getCountry()).isEqualTo("LV");
        assertThat(countryDto.getLocation()).isEqualTo("1.23,1.23");
        assertThat(countryDto.getOrganization()).isEqualTo("Big company");
        assertThat(countryDto.getPostal()).isEqualTo("1001");
        assertThat(countryDto.getTimezone()).isEqualTo(TimeZone.getTimeZone("Europe/Riga"));
        assertThat(countryDto.getReadme()).isEqualTo("readme text");
    }

    @Test
    void shouldReturnNullIfNoCountryFoundByIpAndNoInteractionWithDatabase() throws URISyntaxException, IOException, InterruptedException {
        HttpRequest request = createRequest();

        when(httpClient.send(request, HttpResponse.BodyHandlers.ofString())).thenReturn(mockedResponse);
        when(mockedResponse.statusCode()).thenReturn(404);

        var countryDto = countryService.getCountryByIp("123");

        verify(countryRepository, times(0)).save(any());
        assertThat(countryDto).isNull();
    }

    @Test
    void shouldSaveCountryEntityInDatabase() throws URISyntaxException, IOException, InterruptedException {
        HttpRequest request = createRequest();
        var countryEntity = CountryEntity.builder()
                .ip("123")
                .city("Riga")
                .country("Latvia")
                .location("1.23,1.23")
                .build();

        when(httpClient.send(request, HttpResponse.BodyHandlers.ofString())).thenReturn(mockedResponse);
        when(mockedResponse.body()).thenReturn(json);
        when(mockedResponse.statusCode()).thenReturn(200);
        when(countryMapper.countryDtoToEntity(any())).thenReturn(countryEntity);

        countryService.getCountryByIp("123");

        verify(countryRepository).save(countryCaptor.capture());
        var savedCountryEntity = countryCaptor.getValue();
        assertThat(savedCountryEntity.getIp()).isEqualTo(countryEntity.getIp());
        assertThat(savedCountryEntity.getCity()).isEqualTo(countryEntity.getCity());
        assertThat(savedCountryEntity.getCountry()).isEqualTo(countryEntity.getCountry());
        assertThat(savedCountryEntity.getLocation()).isEqualTo(countryEntity.getLocation());
    }

    private HttpRequest createRequest() throws URISyntaxException {
        return HttpRequest.newBuilder()
                .uri(new URI("https://ipinfo.io/" + "123" + "/geo"))
                .GET()
                .build();
    }

}