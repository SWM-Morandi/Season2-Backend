package kr.co.morandi.backend.judgement.application.request.cookie;


import java.time.LocalDateTime;

public record BaekjoonMemberCookieServiceRequest(
    String cookie,
    Long memberId,
    LocalDateTime nowDateTime
) {}
