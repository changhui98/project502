package org.choongang.admin.board;

import org.choongang.admin.menus.Menu;
import org.choongang.admin.menus.MenuDetail;
import org.choongang.commons.ExceptionProcessor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller("adminBoardController")
@RequestMapping("/admin/board")
public class BoardController implements ExceptionProcessor {

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

    @GetMapping
    public String list(Model model){
        commonProcess("list", model);

        return "admin/board/list";
    }

    @GetMapping("/add")
    public String add(Model model){
        commonProcess("add", model);

        return "admin/board/add";
    }

    public String save(){

        return "redirect:/admin/board";
    }

    public String posts(Model model){
        commonProcess("posts", model);

        return "admin/board/posts";
    }





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

        model.addAttribute("pageTitle", pageTitle);
    }




}
