package org.choongang.member.service;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.choongang.commons.ListData;
import org.choongang.commons.Pagination;
import org.choongang.commons.Utils;
import org.choongang.file.entitys.FileInfo;
import org.choongang.file.service.FileInfoService;
import org.choongang.member.controllers.MemberSearch;
import org.choongang.member.entitys.Authorities;
import org.choongang.member.entitys.Member;
import org.choongang.member.entitys.QMember;
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
    private final EntityManager em;
    private final HttpServletRequest request;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Member member = memberRepository.findByEmail(username)
                        .orElseGet(()-> memberRepository.findByUserId(username).orElseThrow(() ->new UsernameNotFoundException(username)));

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

    /**
     * 회원 목록
     * @param search
     * @return
     */
    public ListData<Member> getList(MemberSearch search){

        int page = Utils.onlyPositiveNumber(search.getPage(),1); // 페이지 번호
        int limit = Utils.onlyPositiveNumber(search.getLimit(),20); // 1페이지당
        int offset = (page - 1) * limit; // 레코드 시작 위치 번호


        BooleanBuilder andBuilder = new BooleanBuilder();
        QMember member = QMember.member;

        PathBuilder<Member> pathBuilder =  new PathBuilder<>(Member.class, "member");

        List<Member> items = new JPAQueryFactory(em)
                .selectFrom(member)
                .leftJoin(member.authorities)
                .fetchJoin()
                .where(andBuilder)
                .limit(limit)
                .offset(offset)
                .orderBy(new OrderSpecifier(Order.DESC, pathBuilder.get("createdAt")))
                .fetch();

        /*
            페이징 처리 Start
         */

        int total = (int)memberRepository.count(andBuilder); // 총 레코드 갯수
        Pagination pagination = new Pagination(page, total, 10, limit, request);


        /*
            페이징 처리 End
         */

        return new ListData<>(items, pagination);
    }
}
