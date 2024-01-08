package org.choongang.member.service;

import lombok.RequiredArgsConstructor;
import org.choongang.member.Authority;
import org.choongang.member.controllers.JoinValidator;
import org.choongang.member.controllers.RequestJoin;
import org.choongang.member.entitys.Authorities;
import org.choongang.member.entitys.Member;
import org.choongang.member.repositorys.AuthoritiesRepository;
import org.choongang.member.repositorys.MemberRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;

@Service
@RequiredArgsConstructor
@Transactional
public class JoinService {

    private final MemberRepository memberRepository;
    private final AuthoritiesRepository authoritiesRepository;
    private final JoinValidator validator;
    private final PasswordEncoder encoder;

    public void process(RequestJoin form, Errors errors){
        validator.validate(form, errors);
        if(errors.hasErrors()){
            return;
        }

        String hash = encoder.encode(form.getPassword());

        Member member= new Member();
        member.setEmail(form.getEmail());
        member.setName(form.getName());
        member.setPassword(hash);
        member.setUserId(form.getUserId());
        member.setGid(form.getGid());

        process(member);

        // 회원 가입시에는 일반 사용자 권한 부여(user)
        Authorities authorities = new Authorities();
        authorities.setMember(member);
        authorities.setAuthority(Authority.USER);
        authoritiesRepository.saveAndFlush(authorities);

    }

    public void process(Member member){
        memberRepository.saveAndFlush(member);
    }

}
