package com.peopleflow.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static io.swagger.v3.oas.annotations.media.Schema.AccessMode.READ_ONLY;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    @Schema(accessMode = READ_ONLY, example = "1")
    private Long id;
    @Schema(example = "lemilivoskodi")
    private String username;
    @Schema(example = "Marko Milenkovic")
    private String fullName;
    @Schema(example = "APPROVED")
    private String state;

}


