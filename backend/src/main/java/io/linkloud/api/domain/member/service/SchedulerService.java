package io.linkloud.api.domain.member.service;

import io.linkloud.api.domain.member.model.Member;
import io.linkloud.api.domain.member.model.Role;
import io.linkloud.api.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class SchedulerService {

    private final MemberRepository memberRepository;

    // todo : 쿼리 성능 개선 필요
    // 매일 새벽 4시에 실행 (cron 표현식 참고)
    // @Scheduled(cron = "0 0 4 * * ?")
    // 사용자가 많아지면 하는걸로
    @Deprecated
    @Transactional
    public void updateMemberRoleToUser() {

        // 오늘날짜 - 3일
        LocalDateTime threeDaysAgo = LocalDateTime.now().minusDays(3);

        // 3일전에 가입하고 권한이 NEW_MEMBER인 회원 검색
        List<Member> guestMembers = memberRepository.findByCreatedAtBeforeAndRole(threeDaysAgo, Role.NEW_MEMBER);
        for (Member member : guestMembers) {
            log.info("{}님의 권한을 변경합니다.", member.getNickname());
            member.updateRoleToMember(Role.MEMBER);
            memberRepository.save(member);
        }
    }
}
