package kr.co.morandi.backend.defense_management.application.response.problemcontent;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Subtask {

    private String title;
    private List<String> conditions;
    private String tableConditionsHtml;

    @Builder
    private Subtask(String title, List<String> conditions, String tableConditionsHtml) {
        this.title = title;
        this.conditions = conditions;
        this.tableConditionsHtml = tableConditionsHtml;
    }
}
