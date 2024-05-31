package kr.co.morandi.backend.defense_management.infrastructure.baekjoon;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum BaekjoonSubmitVisuability {
    OPEN("open"),//공개
    CLOSE("close"),//비공개
    ONLY_ACCEPTED("onlyaccepted");//맞았을 때만 공개

    private final String submitVisibilityCode;

    public static String getSubmitVisibilityCode(String submitVisibility) {
        return BaekjoonSubmitVisuability.valueOf(submitVisibility.toUpperCase()).getSubmitVisibilityCode();
    }

}
