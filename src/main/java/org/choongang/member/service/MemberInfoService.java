package org.choongang.member.service;

import lombok.RequiredArgsConstructor;
import org.choongang.file.entitys.FileInfo;
import org.choongang.file.service.FileInfoService;
import org.choongang.member.entitys.Authorities;
import org.choongang.member.entitys.Member;
import org.choongang.member.repositorys.MemberRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberInfoService implements UserDetailsService {

    private final MemberRepository memberRepository;
    private final FileInfoService fileInfoService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Member member = memberRepository.findByEmail(username)
                        .orElseGet(()-> memberRepository.findByUserId(username).orElseThrow(MemberNotFoundException::new));

        List<SimpleGrantedAuthority> authorities = null;
        List<Authorities> tmp = member.getAuthorities();
        if(tmp != null){
            authorities = tmp.stream()
                    .map(s -> new SimpleGrantedAuthority(s.getAuthority().name()))
                    .toList();
        }

        // 프로필 이미지 처리 Start
        List<FileInfo> files = fileInfoService.getListDone(member.getGid());
        if(files != null && !files.isEmpty()){
            member.setProfileImage(files.get(0));
        }


        // 프로필 이미지 처리 End


        return MemberInfo.builder()
                .email(member.getEmail())
                .userId(member.getUserId())
                .password(member.getPassword())
                .member(member)
                .authorities(authorities)
                .build();
    }
}
