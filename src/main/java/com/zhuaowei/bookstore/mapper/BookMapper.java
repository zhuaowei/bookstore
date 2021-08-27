package com.zhuaowei.bookstore.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhuaowei.bookstore.bean.Book;

public interface BookMapper extends BaseMapper<Book> {
    /**
     * 更新图书评论数和分数
     */
    void updateEvaluation();
}
