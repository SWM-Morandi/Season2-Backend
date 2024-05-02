package kr.co.morandi.backend.common;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CodeRequest {

    private String code;
    private String language;
    private String input;
}
