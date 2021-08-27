package com.zhuaowei.bookstore.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zhuaowei.bookstore.bean.Book;
import com.zhuaowei.bookstore.bean.Evaluation;
import com.zhuaowei.bookstore.bean.Member;
import com.zhuaowei.bookstore.mapper.BookMapper;
import com.zhuaowei.bookstore.mapper.EvaluationMapper;
import com.zhuaowei.bookstore.mapper.MemberMapper;
import com.zhuaowei.bookstore.service.EvaluationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ClassName: EvaluationServiceImpl
 * @Description: TODO: add description
 * @Author: zhuaowei
 * @Date: 2021/8/20 16:24
 * @Version: 1.0
 **/
@Service("evaluationService")
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class EvaluationServiceImpl implements EvaluationService {
    @Resource
    private EvaluationMapper evaluationMapper;
    @Resource
    private MemberMapper memberMapper;
    @Resource
    private BookMapper bookMapper;

    @Override
    public List selectByBookId(Integer bookId) {
        QueryWrapper queryWrapper = new QueryWrapper();
        // 根据图书id获取它的所有评论
        queryWrapper.eq("book_id", bookId);
        // 获取所有有效评论
        queryWrapper.eq("state", "enable");
        queryWrapper.orderByDesc("create_time");
        List<Evaluation> evaluationList = evaluationMapper.selectList(queryWrapper);
        Book book = bookMapper.selectById(bookId);
        for (Evaluation evaluation : evaluationList) {
            Member member = memberMapper.selectById(evaluation.getMemberId());
            evaluation.setMember(member);
            evaluation.setBook(book);
        }
        return evaluationList;
    }
}
