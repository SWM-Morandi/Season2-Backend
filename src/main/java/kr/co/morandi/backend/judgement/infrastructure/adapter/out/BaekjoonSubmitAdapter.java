package kr.co.morandi.backend.judgement.infrastructure.adapter.out;

import kr.co.morandi.backend.common.annotation.Adapter;
import kr.co.morandi.backend.judgement.application.port.out.BaekjoonSubmitPort;
import kr.co.morandi.backend.judgement.domain.model.baekjoon.submit.BaekjoonSubmit;
import kr.co.morandi.backend.judgement.infrastructure.persistence.submit.BaekjoonSubmitRepository;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@Adapter
@RequiredArgsConstructor
public class BaekjoonSubmitAdapter implements BaekjoonSubmitPort {

    private final BaekjoonSubmitRepository baekjoonSubmitRepository;
    @Override
    public BaekjoonSubmit save(BaekjoonSubmit submit) {
        return baekjoonSubmitRepository.save(submit);
    }

    @Override
    public Optional<BaekjoonSubmit> findSubmit(Long submitId) {
        return baekjoonSubmitRepository.findById(submitId);
    }
}
