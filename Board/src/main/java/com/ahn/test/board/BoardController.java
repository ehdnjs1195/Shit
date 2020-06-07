package com.ahn.test.board;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ahn.test.util.PageMaker;
import com.ahn.test.util.SearchCriteria;

@Controller
public class BoardController {
	@Autowired
	private BoardService boardService;
	
    // 목록 : 기본 + 페이징 + 검색
    @RequestMapping(value = "/board/list.do", method = RequestMethod.GET)
    public String list(@ModelAttribute("criteria") SearchCriteria criteria, Model model) throws Exception {

        model.addAttribute("list", boardService.list(criteria));
        PageMaker pageMaker = new PageMaker();
        pageMaker.setCriteria(criteria);
        pageMaker.setTotalCount(boardService.listCount(criteria));
        model.addAttribute("pageMaker", pageMaker);
        model.addAttribute("totalCount", boardService.listCount(criteria));

        return "board/list.page";
    }
}
