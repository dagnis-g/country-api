package com.dagnis.countryapi.controller;

import com.dagnis.countryapi.dto.CountryDto;
import com.dagnis.countryapi.service.CountryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.TimeZone;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CountryController.class)
class CountryControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CountryService countryService;

    @Test
    void shouldReturnCountryDto() throws Exception {
        var countryDto = CountryDto.builder()
                .ip("123")
                .city("Riga")
                .region("Riga")
                .country("Latvia")
                .location("123.123")
                .organization("big company")
                .postal("LV-1047")
                .timezone(TimeZone.getTimeZone("Europe/Riga"))
                .readme("readme text")
                .build();

        when(countryService.getCountryByIp("123")).thenReturn(countryDto);

        String response = mvc.perform(get("/get-country/123"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertThat(response).isEqualTo("{\"ip\":\"123\",\"city\":\"Riga\",\"region\":\"Riga\",\"country\":\"Latvia\",\"postal\":\"LV-1047\",\"timezone\":\"Europe/Riga\",\"readme\":\"readme text\",\"loc\":\"123.123\",\"org\":\"big company\"}");
    }

    @Test
    void shouldReturnStatusNotFoundIfNoCountryFoundByIp() throws Exception {
        when(countryService.getCountryByIp("123")).thenReturn(null);

        mvc.perform(get("/get-country/123"))
                .andExpect(status().isNotFound());
    }
}