package com.zhuaowei.bookstore.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhuaowei.bookstore.bean.Book;
import com.zhuaowei.bookstore.mapper.BookMapper;
import com.zhuaowei.bookstore.service.BookService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @ClassName: BookServiceImpl
 * @Description: TODO: add description
 * @Author: zhuaowei
 * @Date: 2021/8/18 11:47
 * @Version: 1.0
 **/
@Service("bookService")
@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
public class BookServiceImpl implements BookService {
    @Resource
    private BookMapper bookMapper;

    @Override
    public IPage<Book> paging(Integer categotyId, String sort, Integer page, Integer count) {
        // 查询的分页
        Page<Book> p = new Page<Book>(page, count);
        // 不写默认查询全部
        QueryWrapper<Book> queryWrapper = new QueryWrapper<Book>();

        if (categotyId != null && categotyId != -1) {
            queryWrapper.eq("category_id", categotyId);
        }
        if (sort != null) {
            queryWrapper.orderByDesc(sort);
        }

        Page<Book> pageObject = bookMapper.selectPage(p, queryWrapper);
        return pageObject;
    }

    @Override
    public Book getBookById(Integer bookId) {
        Book book = bookMapper.selectById(bookId);
        return book;
    }

    /**
     * 插入图书到数据库
     * @param book
     * @return 返回图书对象 有id
     */
    @Override
    @Transactional
    public Book createBook(Book book) {
        bookMapper.insert(book);
        return book;
    }

    @Override
    @Transactional
    public Book updateBook(Book book) {
        bookMapper.updateById(book);
        return book;
    }

    @Override
    @Transactional
    public void deleteBook(Integer bookId) {
        bookMapper.deleteById(bookId);
    }
}
