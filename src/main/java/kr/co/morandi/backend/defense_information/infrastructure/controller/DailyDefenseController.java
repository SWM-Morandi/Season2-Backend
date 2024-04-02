package kr.co.morandi.backend.defense_information.infrastructure.controller;

import kr.co.morandi.backend.defense_information.application.dto.response.DailyDefenseInfoResponse;
import kr.co.morandi.backend.defense_information.application.port.in.DailyDefenseUseCase;
import kr.co.morandi.backend.member_management.domain.model.member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

import static kr.co.morandi.backend.member_management.domain.model.member.SocialType.GOOGLE;

@RestController
@RequiredArgsConstructor
public class DailyDefenseController {

    private final DailyDefenseUseCase dailyDefenseUseCase;

    @GetMapping("/daily-defense")
    public DailyDefenseInfoResponse getDailyDefenseInfo() {
        //TODO SecurityContext에서 Member 정보 가져오기
        Member member = Member.create("", "", GOOGLE, "", "");
        return dailyDefenseUseCase.getDailyDefenseInfo(member, LocalDateTime.now());
    }


}
