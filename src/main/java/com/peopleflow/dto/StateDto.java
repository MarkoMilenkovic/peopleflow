package com.peopleflow.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StateDto {

    @Schema(example = "APPROVED", required = true)
    private String state;

}
