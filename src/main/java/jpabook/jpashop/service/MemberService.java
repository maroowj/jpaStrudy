package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true) // 단순 조회 메소드에는 readOnly = true (조회를 최적화 해줌)
@RequiredArgsConstructor // final이 있는 필드만 생성자를 만들어 줌
public class MemberService {

    private final MemberRepository memberRepository;

    /**
     * 회원가입
     * **/
    @Transactional // readOnly = false (상위 @Transactional 어노테이션 보다 우선권을 가짐)
    public Long join(Member member) {
       validateDuplicateMember(member); // 중복 회원 검증
       memberRepository.save(member);
       return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if(!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다."); //EXCEPTION
        }
    }

    // 회원 전체 조회
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    // 회원 단건 조회
    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }
}
