package org.choongang.member.controllers;

import lombok.RequiredArgsConstructor;
import org.choongang.member.repositorys.MemberRepository;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class JoinValidator implements Validator {

    private final MemberRepository memberRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(RequestJoin.class);
    }

    @Override
    public void validate(Object target, Errors errors) {


        /**
         * 이메일 아이디 중복여부 체크
         *  1. 비밀번호 복잡성 체크 - 대소문자1개 각각 포함,
         *  숫자 1개 이상 포함,
         *  특수문자도 1개 이상 포함
         *  비밀번호, 비밀번호 확인 일치 여부 체크
         */
        RequestJoin form = (RequestJoin)target;

        String email = form.getEmail();
        String userId = form.getUserId();
        String password = form.getPassword();
        String confirmPassword = form.getConfirmPassword();

        // 1. 이메일, 아이디 중복 여부 체크





    }


}
