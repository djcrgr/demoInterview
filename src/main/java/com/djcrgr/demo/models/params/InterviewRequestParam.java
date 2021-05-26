package com.djcrgr.demo.models.params;

import com.djcrgr.demo.models.Question;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Schema(description = "Сущность запроса для изменения интервью")
public class InterviewRequestParam {
    @Schema(description = "название")
    private String name;
    @Schema(description = "описание")
    private String description;
    @Schema(description = "конечная дата")
    private String endDate;
    @Schema(description = "активность")
    private String isActive;
    @Schema(description = "список вопросов")
    private List<Question> questionList;
}
