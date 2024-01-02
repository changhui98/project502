package org.choongang.member.service;

import lombok.RequiredArgsConstructor;
import org.choongang.member.entitys.Member;
import org.choongang.member.repositorys.MemberRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberInfoService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Member member = memberRepository.findByEmail(username)
                        .orElseGet(()-> memberRepository.findByUserId(username).orElseThrow(MemberNotFoundException::new));


        return MemberInfo.builder()
                .email(member.getEmail())
                .userId(member.getUserId())
                .password(member.getPassword())
                .member(member)
                .build();
    }
}
