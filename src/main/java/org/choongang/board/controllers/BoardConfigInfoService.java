package org.choongang.board.controllers;

import org.choongang.board.entitys.Board;
import org.choongang.file.entitys.FileInfo;

import java.util.List;

public class BoardConfigInfoService {



    public void addBoardInfo(Board board){

        board.setHtmlTopImages(htmlTopmages);
        board.setHtmlBottmm(htmlBottomImages);

        List<FileInfo> logo1 = fileInfoService.getListDone(gid, "logo1");
        List<FileInfo> logo2 = fileInfoService.getListDone(gid, "logo1");
        List<FileInfo> logo3 = fileInfoService.getListDone(gid, "logo1");




    }
}
