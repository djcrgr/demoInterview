package com.djcrgr.demo.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Сущность ответа пользователя")
public class Answer {

    @Schema(description = "Ответ пользователя")
    @JsonProperty
    private String answerText;
}
