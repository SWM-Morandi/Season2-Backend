package kr.co.morandi.backend.defense_information.infrastructure.controller;

import kr.co.morandi.backend.common.web.MemberId;
import kr.co.morandi.backend.defense_information.application.dto.response.DailyDefenseInfoResponse;
import kr.co.morandi.backend.defense_information.application.port.in.DailyDefenseUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
public class DailyDefenseController {

    private final DailyDefenseUseCase dailyDefenseUseCase;

    @GetMapping("/daily-defense")
    public DailyDefenseInfoResponse getDailyDefenseInfo(@MemberId Long memberId) {

        return dailyDefenseUseCase.getDailyDefenseInfo(memberId, LocalDateTime.now());
    }


}
