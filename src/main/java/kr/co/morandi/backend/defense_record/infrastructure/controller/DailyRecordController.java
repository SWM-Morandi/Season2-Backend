package kr.co.morandi.backend.defense_record.infrastructure.controller;

import kr.co.morandi.backend.defense_record.application.dto.DailyDefenseRankPageResponse;
import kr.co.morandi.backend.defense_record.application.port.in.DailyRecordRankUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
public class DailyRecordController {

    private final DailyRecordRankUseCase dailyRecordRankUseCase;

     @GetMapping("/daily-record/rankings")
     public ResponseEntity<DailyDefenseRankPageResponse> getDailyRecordRank(@RequestParam(value = "page", defaultValue = "0") int page,
                                                                            @RequestParam(value = "size", defaultValue = "5") int size) {

         return ResponseEntity.ok(dailyRecordRankUseCase.getDailyRecordRank(LocalDateTime.now(), page, size));
    }

}
