package kr.co.morandi.backend.defense_management.domain.model.tempcode.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import kr.co.morandi.backend.common.exception.MorandiException;
import kr.co.morandi.backend.defense_management.domain.error.LanguageErrorCode;
import kr.co.morandi.backend.judgement.domain.error.SubmitErrorCode;
import kr.co.morandi.backend.judgement.domain.model.baekjoon.result.ResultType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Language {
    JAVA("JAVA", """
        public class Main {
            public static void main(String[] args) {
                System.out.println("Hello World");
            }
        }
        """),
     CPP("CPP","""
        #include <iostream>
        using namespace std;
        
        int main() {
            cout << "Hello World" << endl;
            return 0;
        }
        """),
    PYTHON("PYTHON","""
            print("Hello World")
        """);

    private final String value;
    private final String initialCode;

    @JsonValue
    public String getValue() {
        return value;
    }
    @JsonCreator
    public static Language from(String value) {
        for (Language language : values()) {
            if(language.getValue().equals(value)) {
                return language;
            }
        }
        throw new MorandiException(LanguageErrorCode.LANGUAGE_NOT_FOUND);
    }
}
