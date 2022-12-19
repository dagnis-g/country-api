package com.dagnis.countryapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UniversityDto {

    @Schema(example = "LV")
    @JsonProperty("alpha_two_code")
    private String alphaTwoCode;
    @Schema(example = "Medical Academy of Latvia")
    private String name;
    @Schema(example = "[\"http://www.aml.lv/\"]")
    @JsonProperty("web_pages")
    private List<String> webPages;
    @JsonProperty("state-province")
    private String stateProvince;
    @Schema(example = "[\"http://www.rpiva.lv/\"]")
    private List<String> domains;
    @Schema(example = "Latvia")
    private String country;

}
