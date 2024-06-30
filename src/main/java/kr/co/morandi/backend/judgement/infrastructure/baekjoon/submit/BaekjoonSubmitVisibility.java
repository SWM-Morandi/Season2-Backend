package kr.co.morandi.backend.judgement.infrastructure.baekjoon.submit;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum BaekjoonSubmitVisibility {
    OPEN("open"),//공개
    CLOSE("close"),//비공개
    ONLY_ACCEPTED("onlyaccepted");//맞았을 때만 공개

    private final String submitVisibilityCode;

    public static String getSubmitVisibilityCode(String submitVisibility) {
        return BaekjoonSubmitVisibility.valueOf(submitVisibility.toUpperCase()).getSubmitVisibilityCode();
    }

}
