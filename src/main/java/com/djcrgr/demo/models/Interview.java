package com.djcrgr.demo.models;

import com.djcrgr.demo.models.params.InterviewRequestParam;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Сущность интервью")
public class Interview {

    @JsonProperty
    @Schema(description = "название интервью")
    private String name;
    @JsonProperty
    @Schema(description = "описание")
    private String description;
    @JsonProperty
    @JsonFormat(pattern="yyyy-MM-dd")
    @Schema(description = "дата начала")
    private Date startDate;
    @JsonProperty
    @JsonFormat(pattern="yyyy-MM-dd")
    @Schema(description = "дата окончания")
    private Date endDate;
    @JsonProperty
    @Schema(description = "список вопросов")
    private List<Question> questionList;
    @JsonProperty
    @Schema(description = "статус активности интервью")
    private boolean isActive;

    public Interview(InterviewRequestParam interviewRequestParam) throws ParseException {
        this.name = interviewRequestParam.getName();
        this.description = interviewRequestParam.getName();
        this.endDate = new SimpleDateFormat("yyyy-MM-dd").parse(interviewRequestParam.getEndDate());
        this.questionList = interviewRequestParam.getQuestionList();
        this.isActive = Boolean.parseBoolean(interviewRequestParam.getIsActive());
    }
}
