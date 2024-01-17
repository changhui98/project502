package org.choongang.board.service;

import lombok.RequiredArgsConstructor;
import org.choongang.board.entitys.BoardData;
import org.choongang.board.repository.BoardDataRepository;
import org.choongang.file.service.FileDeleteService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardDeleteService {

    private final BoardDataRepository boardDataRepository;
    private final BoardInfoService boardInfoService;
    private final FileDeleteService fileDeleteService;
    private final BoardAuthService boardAuthService;

    /**
     * 게시글 삭제
     * @param seq
     */
    public void delete(Long seq){

        boardAuthService.check("delete", seq);

        BoardData data = boardInfoService.get(seq);

        String gid = data.getGid();

        boardDataRepository.delete(data);
        boardDataRepository.flush();

        // 업로드 파일 삭제
        fileDeleteService.delete(gid);
    }
}
