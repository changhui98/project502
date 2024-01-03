package org.choongang;

import org.choongang.board.entitys.BoardData;
import org.choongang.board.repository.BoardRepository;
import org.choongang.member.repositorys.AuthoritiesRepository;
import org.choongang.member.repositorys.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ProjectApplicationTests {

	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private AuthoritiesRepository authoritiesRepository;

	@Test
	void contextLoads() {


		/*
		Member member = memberRepository.findByUserId("user01").orElse(null);

		Authorities authorities = new Authorities();
		authorities.setMember(member);

		authorities.setAuthority(Authority.ADMIN);

		authoritiesRepository.saveAndFlush(authorities);

		 */
	}
}
