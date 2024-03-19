package kr.co.morandi.backend.application.usecase;

import kr.co.morandi.backend.domain.defense.dailydefense.service.DailyDefenseService;
import kr.co.morandi.backend.domain.member.model.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DailyDefenseUseCase {

    private final DailyDefenseService dailyDefenseService;

    public void startDailyTest(Member member) {
        // DailyDefense를 가져온다.

    }
}
