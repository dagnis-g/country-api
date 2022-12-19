package com.dagnis.countryapi.mapper;

import com.dagnis.countryapi.dto.CountryDto;
import com.dagnis.countryapi.model.CountryEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;

import java.util.TimeZone;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@ExtendWith(OutputCaptureExtension.class)
class CountryMapperTest {

    @Autowired
    private CountryMapper countryMapper;

    @Test
    void shouldMapCountryDtoToEntity() {
        var countryDto = CountryDto.builder()
                .ip("123")
                .city("Riga")
                .region("Riga")
                .country("LV")
                .location("123.123")
                .organization("big company")
                .postal("LV-1047")
                .timezone(TimeZone.getTimeZone("Europe/Riga"))
                .readme("readme text")
                .build();

        CountryEntity countryEntity = countryMapper.countryDtoToEntity(countryDto);

        assertThat(countryEntity.getIp()).isEqualTo("123");
        assertThat(countryEntity.getCountry()).isEqualTo("Latvia");
        assertThat(countryEntity.getCity()).isEqualTo("Riga");
        assertThat(countryEntity.getLocation()).isEqualTo("123.123");
    }

    @Test
    void shouldMapCountryIdAsCountryAndLog(CapturedOutput output) {
        var countryDto = CountryDto.builder()
                .ip("123")
                .city("Riga")
                .region("Riga")
                .country("ABCD")
                .location("123.123")
                .organization("big company")
                .postal("LV-1047")
                .timezone(TimeZone.getTimeZone("Europe/Riga"))
                .readme("readme text")
                .build();

        CountryEntity countryEntity = countryMapper.countryDtoToEntity(countryDto);

        assertThat(countryEntity.getIp()).isEqualTo("123");
        assertThat(countryEntity.getCountry()).isEqualTo("ABCD");
        assertThat(countryEntity.getCity()).isEqualTo("Riga");
        assertThat(countryEntity.getLocation()).isEqualTo("123.123");

        assertThat(output.getOut()).contains("Can't find country name by ID:ABCD. Saving country name by ID in database");
    }
    
}