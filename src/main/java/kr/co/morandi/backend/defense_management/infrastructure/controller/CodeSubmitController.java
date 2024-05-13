package kr.co.morandi.backend.defense_management.infrastructure.controller;

import kr.co.morandi.backend.defense_management.application.service.codesubmit.SQSService;
import kr.co.morandi.backend.defense_management.infrastructure.request.codesubmit.CodeRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class CodeSubmitController {

    private final SQSService sqsService;
    @PostMapping("/submit")
    public ResponseEntity<Void> submit(@RequestBody CodeRequest codeRequest) {
        sqsService.sendMessage(codeRequest);
        return ResponseEntity.ok(null);
    }
}
