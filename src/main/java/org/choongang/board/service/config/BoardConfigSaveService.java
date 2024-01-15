package org.choongang.board.service.config;

import lombok.RequiredArgsConstructor;
import org.choongang.admin.board.controllers.RequestBoardConfig;
import org.choongang.board.entitys.Board;
import org.choongang.board.repository.BoardRepository;
import org.choongang.file.service.FileUploadService;
import org.choongang.member.Authority;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class BoardConfigSaveService {

    private final BoardRepository boardRepository;
    private final FileUploadService fileUploadService;

    public void save(RequestBoardConfig form) {
        String bid = form.getBid();
        String mode = form.getMode();
        mode = StringUtils.hasText(mode) ? mode : "add";

        Board board = boardRepository.findById(bid).orElseGet(Board::new);

        if (mode.equals("add")) {
            board.setBid(bid);
            board.setGid(form.getGid());
        }

        board.setBName(form.getBName());
        board.setActive(form.isActive());
        board.setRowsPerPage(form.getRowsPerPage());
        board.setPageCountPc(form.getPageCountPc());
        board.setPageCountMobile(form.getPageCountMobile());
        board.setUseReply(form.isUseReply());
        board.setUseComment(form.isUseComment());
        board.setUseEditor(form.isUseEditor());
        board.setUseUploadImage(form.isUseUploadImage());
        board.setUseUploadFile(form.isUseUploadFile());
        board.setLocationAfterWriting(form.getLocationAfterWriting());
        board.setSkin(form.getSkin());
        board.setCategory(form.getCategory());

        board.setListAccessType(Authority.valueOf(form.getListAccessType()));
        board.setViewAccessType(Authority.valueOf(form.getViewAccessType()));
        board.setWriteAccessType(Authority.valueOf(form.getWriteAccessType()));
        board.setReplyAccessType(Authority.valueOf(form.getReplyAccessType()));
        board.setCommentAccessType(Authority.valueOf(form.getCommentAccessType()));

        board.setHtmlTop(form.getHtmlTop());
        board.setHtmlBottom(form.getHtmlBottom());

        boardRepository.saveAndFlush(board);

        // 파일 업로드 완료 처리
        fileUploadService.processDone(board.getGid());

    }
}
