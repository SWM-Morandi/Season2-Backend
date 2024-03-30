package kr.co.morandi.backend.common.exception.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.FieldError;

import java.util.List;

@Getter
@AllArgsConstructor
@Builder
public class ErrorResponse {
    private String code;
    private String message;
}