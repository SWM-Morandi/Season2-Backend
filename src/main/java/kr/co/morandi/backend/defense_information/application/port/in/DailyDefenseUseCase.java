package kr.co.morandi.backend.defense_information.application.port.in;

import kr.co.morandi.backend.defense_information.application.dto.response.DailyDefenseInfoResponse;
import kr.co.morandi.backend.member_management.domain.model.member.Member;

import java.time.LocalDateTime;

public interface DailyDefenseUseCase {
    DailyDefenseInfoResponse getDailyDefenseInfo(Member member, LocalDateTime requestDateTime);
    void getDailyDefenseTopRank();
}
