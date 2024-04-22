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
        /*
        * 비로그인 상태인 경우
        * */
        if(memberId == null) {
            return DailyDefenseInfoMapper.fromNonAttempted(dailyDefense);
        }
        /*
        * 로그인 상태인 경우
        * */
        final Member member = memberPort.findMemberById(memberId);
        Optional<DailyRecord> maybeDailyRecord = dailyRecordPort.findDailyRecord(member, requestDateTime.toLocalDate());

        /*
        * 시험 기록이 존재하는 경우
        * */
        if(maybeDailyRecord.isPresent()) {
            DailyRecord dailyRecord = maybeDailyRecord.get();
            return DailyDefenseInfoMapper.ofAttempted(dailyDefense, dailyRecord);
        }
        /*
        * 시험 응시 기록이 없는 경우
        * */
        return DailyDefenseInfoMapper.fromNonAttempted(dailyDefense);
    }
}
