package com.djcrgr.demo.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "Сущность пользователя")
public class User {

    @JsonProperty
    @Schema(description = "имя пользователя")
    private String name;
    @JsonProperty
    @Schema(description = "роль пользователя")
    private boolean isAdmin;
}
