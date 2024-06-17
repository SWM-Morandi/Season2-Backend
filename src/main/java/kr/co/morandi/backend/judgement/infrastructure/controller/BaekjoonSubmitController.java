package kr.co.morandi.backend.judgement.infrastructure.controller;

import jakarta.validation.Valid;
import kr.co.morandi.backend.common.web.MemberId;
import kr.co.morandi.backend.judgement.application.usecase.submit.BaekjoonSubmitUsecase;
import kr.co.morandi.backend.judgement.infrastructure.controller.request.BaekjoonJudgementRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BaekjoonSubmitController {

    private final BaekjoonSubmitUsecase baekjoonSubmitUsecase;

    @PostMapping("/submit")
    public ResponseEntity<Void> submit(@Valid @RequestBody BaekjoonJudgementRequest request,
                                    @MemberId Long memberId) {

        baekjoonSubmitUsecase.judgement(request.toServiceRequest(memberId));
        return ResponseEntity.ok()
                .build();
    }
}
