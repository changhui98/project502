package org.choongang.board.service.config;

import lombok.RequiredArgsConstructor;
import org.choongang.board.entitys.Board;
import org.choongang.board.repository.BoardRepository;
import org.choongang.commons.Utils;
import org.choongang.file.service.FileDeleteService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class BoardConfigDeleteService {

    private final BoardRepository boardRepository;
    private final BoardConfigInfoService configInfoService;
    private final FileDeleteService fileDeleteService;
    private final Utils utils;


    public void delete(String bid){
        Board board = configInfoService.get(bid);

        String gid = board.getGid();
        boardRepository.delete(board);
        boardRepository.flush();
        fileDeleteService.delete(gid);
    }

    public void deleteList(List<Integer>)
}
