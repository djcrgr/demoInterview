package com.djcrgr.demo.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Сущность вопроса")
public class Question {

    @JsonProperty
    @Schema(description = "текст вопроса")
    private String questionText;
    @JsonProperty
    @Schema(description = "тип вопроса")
    private String questionType;
}
