package com.zhuaowei.bookstore.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhuaowei.bookstore.bean.Book;

public interface BookService {
    /**
     * 分页查询
     * @param page 查询位置
     * @param count 查询数量
     * @return
     */
    IPage<Book> paging(Integer categotyId, String sort, Integer page, Integer count);

    Book getBookById(Integer bookId);

    Book createBook(Book book);

    Book updateBook(Book book);

    void deleteBook(Integer bookId);
}
