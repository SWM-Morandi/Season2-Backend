package kr.co.morandi.backend.judgement.infrastructure.controller.cookie;

import jakarta.validation.Valid;
import kr.co.morandi.backend.common.web.MemberId;
import kr.co.morandi.backend.judgement.application.service.baekjoon.cookie.BaekjoonMemberCookieService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
public class CookieController {

    private final BaekjoonMemberCookieService baekjoonMemberCookieService;

    @PostMapping("cookie/baekjoon")
    public ResponseEntity<Void> setMemberBaekjoonCookie(@Valid @RequestBody BaekjoonMemberCookieRequest request,
                                    @MemberId Long memberId) {
        baekjoonMemberCookieService.setMemberBaekjoonCookie(request.toServiceRequest(memberId, LocalDateTime.now()));
        return ResponseEntity.ok()
                .build();
    }

}
