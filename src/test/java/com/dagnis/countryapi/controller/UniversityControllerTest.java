package com.dagnis.countryapi.controller;

import com.dagnis.countryapi.dto.UniversityDto;
import com.dagnis.countryapi.service.UniversityService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UniversityController.class)
class UniversityControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UniversityService universityService;

    @Test
    void shouldReturnUniversityDtoArray() throws Exception {
        var universityDto = UniversityDto.builder()
                .alphaTwoCode("LV")
                .name("Medical Academy of Latvia")
                .webPages(List.of("http://www.aml.lv/"))
                .stateProvince(null)
                .domains(List.of("aml.lv"))
                .country("Latvia")
                .build();

        when(universityService.getUniversitiesByCountry("Latvia")).thenReturn(new UniversityDto[]{universityDto});

        String response = mvc.perform(get("/get-universities/Latvia"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertThat(response).isEqualTo("[{\"name\":\"Medical Academy of Latvia\",\"domains\":[\"aml.lv\"],\"country\":\"Latvia\",\"alpha_two_code\":\"LV\",\"web_pages\":[\"http://www.aml.lv/\"],\"state-province\":null}]");
    }

    @Test
    void shouldReturnStatusNotFoundIfNoUniversityFoundForGivenCountry() throws Exception {
        UniversityDto[] emptyUniversityList = new UniversityDto[]{};
        when(universityService.getUniversitiesByCountry("Latvia")).thenReturn(emptyUniversityList);
        mvc.perform(get("/get-universities/FakeCountry"))
                .andExpect(status().isNotFound());
    }
}