package org.choongang.board.service;

import jakarta.persistence.EntityManager;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.choongang.board.entitys.BoardData;
import org.choongang.board.repository.BoardDataRepository;
import org.choongang.board.service.config.BoardConfigInfoService;
import org.choongang.commons.Utils;
import org.choongang.file.service.FileInfoService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardInfoService {

    private final EntityManager em;
    private final BoardDataRepository boardDataRepository;
    private final BoardConfigInfoService configInfoService;

    private final FileInfoService fileInfoService;
    private final HttpServletRequest request;

    private final Utils utils;


    public BoardData get(Long seq){
        BoardData boardData = boardDataRepository.findById(seq).orElseThrow(BoardDataNotFoundException::new);

        addBoardData(boardData);

        return boardData;
    }

}
