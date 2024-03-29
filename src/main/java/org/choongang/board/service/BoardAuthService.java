package org.choongang.board.service;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.choongang.board.entitys.Board;
import org.choongang.board.entitys.BoardData;
import org.choongang.board.service.config.BoardConfigInfoService;
import org.choongang.commons.Utils;
import org.choongang.commons.exceptions.AlertException;
import org.choongang.commons.exceptions.UnAuthorizedException;
import org.choongang.member.Authority;
import org.choongang.member.MemberUtil;
import org.choongang.member.entitys.Member;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class BoardAuthService {

    private final BoardConfigInfoService boardConfigInfoService;
    private final BoardInfoService infoService;
    private final HttpSession session;
    private final PasswordEncoder passwordEncoder;
    private final MemberUtil memberUtil;

    /**
     * 게시글 관련 권한 체크
     *
     * @param mode : update, delete
     * @param seq : 게시글 번호
     */
    public void check(String mode, Long seq){
        if(memberUtil.isAdmin()) { // 관리자는 체크 불필요
            return;
        }

        BoardData data = infoService.get(seq);

        if((mode.equals("update") && !data.isEditable()) || (mode.equals("delete") && !data.isDeletable())) {

            Member member = data.getMember();

            // 비회원 -> 비밀번호 확인 필요
            if(member == null) {
                session.setAttribute("mode", mode);
                session.setAttribute("seq", seq);
                throw new GuestPasswordCheckException();
            }

            // 회원인 경우 -> alert -> back
            throw new UnAuthorizedException();
        }
    }

    public void validate(String password){

        if(!StringUtils.hasText(password)){
            throw new AlertException(Utils.getMessage("NotBlank.requestBoard.guestPw"), HttpStatus.BAD_REQUEST);
        }

        String mode = (String)session.getAttribute("mode");
        Long seq = (Long)session.getAttribute("seq");
        mode = StringUtils.hasText(mode) ? mode : "update";

        String key = null;
        if(mode.equals("update") || mode.equals("delete")) { // 비회원 게시글

            BoardData data = infoService.get(seq);

            boolean match = passwordEncoder.matches(password, data.getGuestPw());
            if(!match) {
                throw new AlertException(Utils.getMessage("Mismatch.password"), HttpStatus.BAD_REQUEST);
            }

            key = "guest_confirmed_" + seq;

        }else if(mode.equals("comment_update") || mode.equals("comment_delete")) { // 비회원 댓글


            key = "guest_comment_confirmed_" + seq;
        }
        // 비밀번호 인증 성공 처리
        session.setAttribute(key, true);

        session.removeAttribute("mode");
        session.removeAttribute("seq");

    }

    /**
     * 글쓰기, 글보기, 글목록 접근 권한 체크
     * @param mode
     * @param bid
     */
    public void accessCheck(String mode, String bid){
        Board board = boardConfigInfoService.get(bid);
        accessCheck(mode, board);

    }

    public void accessCheck(String mode, Board board) {
        if(memberUtil.isAdmin()){ // 관리자는 체크 불필요
            return;
        }

        if(!board.isActive()){ // 미노출 게시판
            throw new UnAuthorizedException();
        }

        boolean accessible = false;
        Authority target = Authority.ALL;
        if (mode.equals("write") || mode.equals("update")) { // 글쓰기 페이지
            target = board.getWriteAccessType();

        } else if (mode.equals("view")) { // 글 보기 페이지
            target = board.getViewAccessType();

        } else if (mode.equals("list")) { // 글 목록 페이지
            target = board.getListAccessType();
        }

        if(target == Authority.ALL) { // 전체 접근 가능
            accessible = true;
        }

        if(target == Authority.USER && memberUtil.isLogin()) { // 회원 + 관리자
            accessible = true;
        }

        if(target == Authority.ADMIN && memberUtil.isAdmin()) { // 관리자
            accessible = true;
        }

        if(!accessible){ // 접근 불가 페이지
            throw new UnAuthorizedException();

        }

    }
}
