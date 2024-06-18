package kr.co.morandi.backend.judgement.infrastructure.controller.cookie;

import jakarta.validation.constraints.NotEmpty;
import kr.co.morandi.backend.judgement.application.request.cookie.BaekjoonMemberCookieServiceRequest;

import java.time.LocalDateTime;

public record BaekjoonMemberCookieRequest(
    @NotEmpty(message = "Cookie 값은 비어 있을 수 없습니다.")
    String cookie
) {
    public BaekjoonMemberCookieServiceRequest toServiceRequest(Long memberId, LocalDateTime nowDateTime) {
        return new BaekjoonMemberCookieServiceRequest(cookie, memberId, nowDateTime);
    }
}
