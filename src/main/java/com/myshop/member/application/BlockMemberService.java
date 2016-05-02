package com.myshop.member.application;

import com.myshop.member.domain.Member;
import com.myshop.member.domain.MemberId;
import com.myshop.member.domain.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BlockMemberService {

    private MemberRepository memberRepository;

    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public void block(String memberId) {
        Member member = memberRepository.findById(new MemberId(memberId));
        if (member == null) throw new NoMemberException();

        member.block();
    }

    @Autowired
    public void setMemberRepository(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }
}
