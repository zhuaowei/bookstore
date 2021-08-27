package com.zhuaowei.bookstore.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhuaowei.bookstore.bean.Book;
import com.zhuaowei.bookstore.bean.Evaluation;
import com.zhuaowei.bookstore.bean.Member;
import com.zhuaowei.bookstore.bean.MemberReadState;
import com.zhuaowei.bookstore.service.BookService;
import com.zhuaowei.bookstore.service.EvaluationService;
import com.zhuaowei.bookstore.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @ClassName: BookController
 * @Description: TODO: add description
 * @Author: zhuaowei
 * @Date: 2021/8/18 16:32
 * @Version: 1.0
 **/
@Controller
public class BookController {
    @Resource
    private BookService bookService;
    @Resource
    private EvaluationService evaluationService;
    @Resource
    private MemberService memberService;

    @GetMapping("/")
    public ModelAndView showIndex() {
        ModelAndView mav = new ModelAndView("/bookstore");
        IPage<Book> pageObject = bookService.paging(-1, "evaluation_score", 1, 10);
        mav.addObject("json", pageObject);
        return mav;
    }

    @GetMapping("/books")
    @ResponseBody
    public IPage<Book> selectBooks(Integer categoryId, String sort, Integer page) {
        if (page == null) {
            page = 1;
        }
        IPage<Book> pageObject = bookService.paging(categoryId, sort, page, 10);
        return pageObject;
    }

    @GetMapping("/detail")
    @ResponseBody
    public ModelAndView getDetailBook(Integer bookId, HttpSession session) {
        ModelAndView mav = new ModelAndView("/detail");
        // 获取图书信息
        Book book = bookService.getBookById(bookId);
        // 获取状态
        Member member = (Member)session.getAttribute("loginMember");
        if (member != null) {
            MemberReadState memberReadState = memberService.selectMemberReadState(member.getMemberId(), bookId);
            mav.addObject("readState", memberReadState);
        }
        // 获取评论信息
        List<Evaluation> evaluationList = evaluationService.selectByBookId(bookId);

        mav.addObject("book", book);
        mav.addObject("evaluationList", evaluationList);
        return mav;
    }
}
