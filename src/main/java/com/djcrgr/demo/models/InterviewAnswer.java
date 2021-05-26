package com.djcrgr.demo.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Сущность ответа на интервью")
public class InterviewAnswer {

    @JsonProperty
    @Schema(description = "Id пользователя")
    private Integer userId;
    @JsonProperty
    @Schema(description = "Id интревью")
    private Integer interviewId;
    @JsonProperty
    @Schema(description = "список ответов")
    private List<Answer> answers;
}
