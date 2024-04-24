package kr.co.morandi.backend.defense_information.application.port.in;

import kr.co.morandi.backend.defense_information.application.dto.response.DailyDefenseInfoResponse;

import java.time.LocalDateTime;

public interface DailyDefenseUseCase {
    DailyDefenseInfoResponse getDailyDefenseInfo(Long memberId, LocalDateTime requestDateTime);
}
