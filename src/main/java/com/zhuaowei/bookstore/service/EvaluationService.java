package com.zhuaowei.bookstore.service;

import com.zhuaowei.bookstore.bean.Evaluation;

import java.util.List;

/**
 * @ClassName: CategoryService
 * @Description: TODO: add description
 * @Author: zhuaowei
 * @Date: 2021/8/20 11:00
 * @Version: 1.0
 **/
public interface EvaluationService {
    /**
     * 获取所有有效评论
     * @return 返回评论列表
     */
    List<Evaluation> selectByBookId(Integer bookId);
}
