package org.choongang.board.controllers;

import lombok.RequiredArgsConstructor;
import org.choongang.board.entitys.BoardData;
import org.choongang.board.repository.BoardRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardRepository boardRepository;


    @ResponseBody
    @GetMapping("/test")
    public void test(){

        BoardData data = boardRepository.findById(1L).orElse(null);
        /*
        BoardData data = new BoardData();
        data.setSubject("제목");
        data.setContent("내용");
        boardRepository.saveAndFlush(data);

         */
    }

    @GetMapping("/test2")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseBody
    public void test2(){
        System.out.println("test2!");

    }


}
