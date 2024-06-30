package kr.co.morandi.backend.judgement.application.port.out;

import kr.co.morandi.backend.judgement.domain.model.baekjoon.submit.BaekjoonSubmit;

import java.util.Optional;

public interface BaekjoonSubmitPort {
    BaekjoonSubmit save(BaekjoonSubmit submit);

    Optional<BaekjoonSubmit> findSubmitJoinFetchDetailAndRecord(Long submitId);
}
