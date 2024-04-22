package kr.co.morandi.backend.defense_information.application.service;

import kr.co.morandi.backend.defense_information.application.dto.response.DailyDefenseInfoResponse;
import kr.co.morandi.backend.defense_information.application.mapper.dailydefense.DailyDefenseInfoMapper;
import kr.co.morandi.backend.defense_information.application.port.in.DailyDefenseUseCase;
import kr.co.morandi.backend.defense_information.application.port.out.dailydefense.DailyDefensePort;
import kr.co.morandi.backend.defense_information.domain.model.dailydefense.DailyDefense;
import kr.co.morandi.backend.defense_record.application.port.out.dailyrecord.DailyRecordPort;
import kr.co.morandi.backend.defense_record.domain.model.dailydefense_record.DailyRecord;
import kr.co.morandi.backend.member_management.application.port.out.member.MemberPort;
import kr.co.morandi.backend.member_management.domain.model.member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

import static kr.co.morandi.backend.defense_information.domain.model.defense.DefenseType.DAILY;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DailyDefenseUseCaseImpl implements DailyDefenseUseCase {

    private final MemberPort memberPort;
    private final DailyDefensePort dailyDefensePort;
    private final DailyRecordPort dailyRecordPort;

    @Override
    public DailyDefenseInfoResponse getDailyDefenseInfo(Long memberId, LocalDateTime requestDateTime) {
        final DailyDefense dailyDefense = dailyDefensePort.findDailyDefense(DAILY, requestDateTime.toLocalDate());

        if(memberId != null) {
            final Member member = memberPort.findMemberById(memberId);
            Optional<DailyRecord> maybeDailyRecord = dailyRecordPort.findDailyRecord(member, requestDateTime.toLocalDate());
            if(maybeDailyRecord.isPresent()) {
                DailyRecord dailyRecord = maybeDailyRecord.get();
                return DailyDefenseInfoMapper.ofAttempted(dailyDefense, dailyRecord);
            }
        }

        return DailyDefenseInfoMapper.fromNonAttempted(dailyDefense);
    }
}
