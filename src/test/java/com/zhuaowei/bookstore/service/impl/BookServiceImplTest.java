package com.zhuaowei.bookstore.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhuaowei.bookstore.bean.Book;
import com.zhuaowei.bookstore.service.BookService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import java.util.List;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class BookServiceImplTest {
    @Resource
    BookService bookService;

    @Test
    public void pagging() {
        IPage<Book> pageObject = bookService.paging(2, "evaluation_quantity", 2, 10);
        List<Book> list =  pageObject.getRecords();
        System.out.println(list.size());

    }

    @Test
    public void testGetBookById() {
        Book book = bookService.getBookById(17);
        System.out.println(book);
    }
}