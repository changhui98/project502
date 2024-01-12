package org.choongang.admin.board.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.choongang.admin.menus.Menu;
import org.choongang.admin.menus.MenuDetail;
import org.choongang.board.entitys.Board;
import org.choongang.board.service.config.BoardConfigInfoService;
import org.choongang.board.service.config.BoardConfigSaveService;
import org.choongang.commons.ExceptionProcessor;
import org.choongang.commons.ListData;
import org.choongang.commons.Pagination;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller("adminBoardController")
@RequestMapping("/admin/board")
@RequiredArgsConstructor
public class BoardController implements ExceptionProcessor {

    private final BoardConfigSaveService configSaveService;
    private final BoardConfigInfoService configInfoService;
    private final BoardConfigValidator configValidator;

    // 주 메뉴 코드
    @ModelAttribute("menuCode")
    public String getMenuCode(){
        return "board";
    }

    // 서브 메뉴
    @ModelAttribute("subMenus")
    public List<MenuDetail> getSubMenus(){

        return Menu.getMenus("board");
    }

    /**
     * 게시판 목록
     *
     * @param model
     * @return
     */
    @GetMapping
    public String list(@ModelAttribute BoardSearch search, Model model){
        commonProcess("list", model);

        ListData<Board> data = configInfoService.getList(search);

        List<Board> items = data.getItems();
        Pagination pagination = data.getPagination();

        model.addAttribute("items", items);
        model.addAttribute("pagination", pagination);

        return "admin/board/list";
    }

    /**
     * 게시판 등록
     *
     * @param config
     * @param model
     * @return
     */
    @GetMapping("/add")
    public String add(@ModelAttribute RequestBoardConfig config, Model model){
        commonProcess("add", model);

        return "admin/board/add";
    }

    /**
     * 게시판 등록/수정 처리
     *
     * @param config
     * @param errors
     * @param model
     * @return
     */
    @PostMapping("/save")
    public String save(@Valid RequestBoardConfig config, Errors errors, Model model){
        String mode = config.getMode();

        commonProcess(mode, model);

        configValidator.validate(config, errors);

        if(errors.hasErrors()){
            return "admin/board/" + mode;
        }

        configSaveService.save(config);

        return "redirect:/admin/board";
    }

    /**
     * 게시글 관리
     *
     * @param model
     * @return
     */
    public String posts(Model model){
        commonProcess("posts", model);

        return "admin/board/posts";
    }

    /**
     * 공통 처리
     *
     * @param mode
     * @param model
     */
    private void commonProcess(String mode, Model model){
        String pageTitle = "게시판 목록";
        mode = StringUtils.hasText(mode) ? mode : "list";

        if(mode.equals("add")){
            pageTitle = "게시판 등록";

        } else if(mode.equals("edit")){
            pageTitle = "게시판 수정";

        } else if(mode.equals("posts")){
            pageTitle = "게시글 관리";
        }

        List<String> addCommonScript = new ArrayList<>();
        List<String> addScript = new ArrayList<>();

        if(mode.equals("add") || mode.equals("edit")) { // 게시판 등록 또는 수정
            addCommonScript.add("ckeditor5/ckeditor");
            addCommonScript.add("fileManager");

            addScript.add("board/form");
        }

        model.addAttribute("pageTitle", pageTitle);
        model.addAttribute("subMenuCode", mode);
        model.addAttribute("addCommonScript",addCommonScript);
        model.addAttribute("addScript", addScript);
    }
}