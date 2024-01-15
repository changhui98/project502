package org.choongang.board.service.config;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.choongang.admin.board.controllers.BoardSearch;
import org.choongang.admin.board.controllers.RequestBoardConfig;
import org.choongang.board.entitys.Board;
import org.choongang.board.repository.BoardRepository;
import org.choongang.commons.ListData;
import org.choongang.commons.Utils;
import org.choongang.file.entitys.FileInfo;
import org.choongang.file.service.FileInfoService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardConfigInfoService {

    private final BoardRepository boardRepository;
    private final FileInfoService fileInfoService;
    private HttpServletRequest request;

    /**
     * 게시판 설정 조회
     *
     * @param bid
     * @return
     */
    public Board get(String bid){
        Board board = boardRepository.findById(bid).orElseThrow(BoardNotFoundException::new);

        addBoardInfo(board);

        return board;
    }

    public RequestBoardConfig getForm(String bid){
        Board board = get(bid);

        RequestBoardConfig form = new ModelMapper().map(board, RequestBoardConfig.class);
        form.setListAccessType(board.getListAccessType().name());
        form.setViewAccessType(board.getViewAccessType().name());
        form.setWriteAccessType(board.getWriteAccessType().name());
        form.setReplyAccessType(board.getReplyAccessType().name());
        form.setCommentAccessType(board.getCommentAccessType().name());

        form.setMode("edit");

        return form;
    }

    /**
     * 게시판 설정 추가 정보
     *  - 에디터 첨부 파일 목록
     *
     * @param board
     */
    public void addBoardInfo(Board board){
        String gid = board.getGid();

        List<FileInfo> htmlTopImages = fileInfoService.getListDone(gid, "html_top");
        List<FileInfo> htmlBottomImages = fileInfoService.getListDone(gid,"html_bottom");

        board.setHtmlTopImages(htmlTopImages);
        board.setHtmlBottomImages(htmlBottomImages);

        List<FileInfo> logo1 = fileInfoService.getListDone(gid,"logo1");
        List<FileInfo> logo2 = fileInfoService.getListDone(gid,"logo2");
        List<FileInfo> logo3 = fileInfoService.getListDone(gid,"logo3");

        if(logo1 != null && !logo1.isEmpty()){
            board.setLogo1(logo1.get(0));
        }

        if(logo2 != null && !logo2.isEmpty()){
            board.setLogo2(logo2.get(0));
        }

        if(logo3 != null && !logo3.isEmpty()){
            board.setLogo3(logo3.get(0));
        }
    }

    public ListData<Board> getList(BoardSearch search){
        ing page = Utils.onlyPositiveNumber(search.getPage() ,1);
    }



    public void addBoardInfo(Board board){

        board.setHtmlTopImages(htmlTopmages);
        board.setHtmlBottmm(htmlBottomImages);

        List<FileInfo> logo1 = fileInfoService.getListDone(gid, "logo1");
        List<FileInfo> logo2 = fileInfoService.getListDone(gid, "logo1");
        List<FileInfo> logo3 = fileInfoService.getListDone(gid, "logo1");




    }
}
