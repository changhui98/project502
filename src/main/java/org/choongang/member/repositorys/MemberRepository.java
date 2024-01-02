package org.choongang.member.repositorys;

import org.choongang.member.entitys.Member;
import org.choongang.member.entitys.QMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.Optional;


public interface MemberRepository extends JpaRepository<Member, Long>, QuerydslPredicateExecutor<Member>{


        Optional<Member> findByEmail(String email);

        Optional<Member> findByUserId(String userId);

        default boolean existsByEmail(String email){

                QMember member = QMember.member;

                return exists(member.email.eq(email));
        }

    }

