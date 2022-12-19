package com.dagnis.countryapi.mapper;

import com.dagnis.countryapi.dto.CountryDto;
import com.dagnis.countryapi.model.CountryEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
public class CountryMapper {

    private static final Map<String, String> countryNameById = Map.of(
            "LT", "Lithuania",
            "LV", "Latvia"
    );

    public CountryEntity countryDtoToEntity(CountryDto dto) {
        var countryName = countryNameById.get(dto.getCountry());

        var countryEntity = CountryEntity.builder()
                .ip(dto.getIp())
                .city(dto.getCity())
                .location(dto.getLocation());

        //Only here because incomplete countryNameById Map
        if (countryName != null) {
            countryEntity.country(countryName);
        } else {
            log.warn("Can't find country name by ID:" + dto.getCountry() + ". Saving country name by ID in database");
            countryEntity.country(dto.getCountry());
        }

        return countryEntity.build();
    }
}
