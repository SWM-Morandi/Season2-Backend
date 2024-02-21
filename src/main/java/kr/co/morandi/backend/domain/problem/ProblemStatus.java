package kr.co.morandi.backend.domain.problem;

import lombok.Getter;

@Getter
public enum ProblemStatus {
    INIT,
    ACTIVE,
    HOLD,
    INACTIVE
}
