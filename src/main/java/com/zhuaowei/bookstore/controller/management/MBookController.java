package com.zhuaowei.bookstore.controller.management;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhuaowei.bookstore.bean.Book;
import com.zhuaowei.bookstore.service.BookService;
import com.zhuaowei.bookstore.service.exception.BussinessException;
import org.apache.ibatis.annotations.Param;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: MBookController
 * @Description: 管理图书
 * @Author: zhuaowei
 * @Date: 2021/8/26 15:34
 * @Version: 1.0
 **/
@Controller
@RequestMapping("/management/book")
public class MBookController {

    @Resource
    private BookService bookService;
    /** 管理首页 */
    @GetMapping("/index.html")
    public ModelAndView showBook() {
        return new ModelAndView("/management/book");
    }
    /** 上传图片 */
    @PostMapping("/upload")
    @ResponseBody
    public Map upload(@RequestParam("img") MultipartFile file, HttpServletRequest request) throws IOException {
        // 上传路径
        String uploadPath = request.getServletContext().getResource("/").getPath() + "/upload/";
        // 图片名字
        String fileName = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
        // 扩展名
        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        // 保存到 upload 目录
        file.transferTo(new File(uploadPath + fileName + suffix));
        Map map = new HashMap();
        map.put("errno", 0);
        map.put("data", new String[] {"/upload/" + fileName + suffix});

        return map;
    }


    @PostMapping("/create")
    @ResponseBody
    public Map createBook(Book book) {
        Map map = new HashMap();
        try {
            book.setEvaluationScore(1f);
            book.setEvaluationQuantity(0);
            Document doc = Jsoup.parse(book.getDescription());
            Element img = doc.select("img").first();
            String src = img.attr("src");
            book.setCover(src);
            bookService.createBook(book);
            map.put("code", 0);
            map.put("message", "success");
        } catch (BussinessException be) {
            map.put("code", be.getCode());
            map.put("message", be.getMessage());
        }
        return map;
    }
    @PostMapping("/update")
    @ResponseBody
    public Map updateBook(Book book) {
        Map map = new HashMap();
        try {
            Document doc = Jsoup.parse(book.getDescription());
            Element img = doc.select("img").first();
            String src = img.attr("src");
            book.setCover(src);
            bookService.updateBook(book);
            map.put("code", 0);
            map.put("message", "success");
        } catch (BussinessException be) {
            map.put("code", be.getCode());
            map.put("message", be.getMessage());
        }
        return map;
    }

    @GetMapping("/list")
    @ResponseBody
    public IPage<Book> list(Integer page, Integer limit) {
        if (page == null) {
            page = 1;
        }
        if (limit == null) {
            limit = 10;
        }
        IPage<Book> pageObject = bookService.paging(null, null, page, limit);
        return pageObject;
    }

    @GetMapping("/modify")
    @ResponseBody
    public Map modify(Integer bookId) {
        Map map = new HashMap();
        Book book = bookService.getBookById(bookId);
        map.put("code", 0);
        map.put("message", "success");
        map.put("book", book);
        return map;
    }

    @PostMapping("/delete")
    @ResponseBody
    public Map delete(Integer bookId) {
        Map map = new HashMap();
        bookService.deleteBook(bookId);
        map.put("code", 0);
        map.put("message", "success");
        return map;
    }

}
