package kr.co.morandi.backend.judgement.infrastructure.controller.cookie;

import jakarta.validation.constraints.NotNull;
import kr.co.morandi.backend.judgement.application.request.cookie.BaekjoonMemberCookieServiceRequest;

import java.time.LocalDateTime;

public record BaekjoonMemberCookieRequest(
    @NotNull
    String cookie
) {
    public BaekjoonMemberCookieServiceRequest toServiceRequest(Long memberId, LocalDateTime nowDateTime) {
        return new BaekjoonMemberCookieServiceRequest(cookie, memberId, nowDateTime);
    }
}
