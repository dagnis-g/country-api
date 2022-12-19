package com.dagnis.countryapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.TimeZone;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CountryDto {

    @Schema(example = "119.207.232.0")
    private String ip;
    @Schema(example = "Riga")
    private String city;
    @Schema(example = "Riga")
    private String region;
    @Schema(example = "LV")
    private String country;
    @Schema(example = "123.123,234.657")
    @JsonProperty("loc")
    private String location;
    @Schema(example = "Big Company")
    @JsonProperty("org")
    private String organization;
    @Schema(example = "LV-1047")
    private String postal;
    private TimeZone timezone;
    @Schema(example = "readme text")
    private String readme;

}
