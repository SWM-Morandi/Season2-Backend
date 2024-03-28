package kr.co.morandi.backend.domain.problem.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ProblemStatus {
    //배치 작업으로 문제가 자동으로 추가됐을 때, 관리자의 검토 없이 문제가 사용되는 것을 방지하기 위해 추가했습니다.
    INIT("새롭게 추가되어 확인이 필요한 문제입니다."),
    ACTIVE("활성화되어 사용 가능한 문제입니다."),
    HOLD("홀드된 문제입니다. 문제가 활성화되기 전까지 사용할 수 없습니다."),
    INACTIVE("비활성화된 문제입니다. 문제가 활성화되기 전까지 사용할 수 없습니다.");

    private final String info;
}
