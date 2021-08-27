package com.zhuaowei.bookstore.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zhuaowei.bookstore.bean.Evaluation;
import com.zhuaowei.bookstore.bean.Member;
import com.zhuaowei.bookstore.bean.MemberReadState;
import com.zhuaowei.bookstore.mapper.EvaluationMapper;
import com.zhuaowei.bookstore.mapper.MemberMapper;
import com.zhuaowei.bookstore.mapper.MemberReadStateMapper;
import com.zhuaowei.bookstore.service.MemberService;
import com.zhuaowei.bookstore.service.exception.BussinessException;
import com.zhuaowei.bookstore.util.MD5Util;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * @ClassName: MemberServiceImpl
 * @Description: TODO: add description
 * @Author: zhuaowei
 * @Date: 2021/8/23 20:54
 * @Version: 1.0
 **/
@Service("memberService")
@Transactional
public class MemberServiceImpl implements MemberService {
    @Resource
    private MemberMapper memberMapper;
    @Resource
    private MemberReadStateMapper memberReadStateMapper;
    @Resource
    private EvaluationMapper evaluationMapper;

    @Override
    public Member createMember(String username, String password, String nickname) {
        QueryWrapper<Member> queryWrapper = new QueryWrapper();
        queryWrapper.eq("username", username);
        List<Member> members = memberMapper.selectList(queryWrapper);
        if (members.size() > 0) {
            throw new BussinessException("M01", "用户已存在");
        }
        Member member = new Member();
        member.setUsername(username);
        int salt = (new Random().nextInt(1000) + 1000);
        member.setPassword(MD5Util.Md5Digest(password, salt));
        member.setSalt(salt);
        member.setNickname(nickname);
        member.setCreateTime(new Date());
        memberMapper.insert(member);
        return member;
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    public Member checkLogin(String username, String password) {
        QueryWrapper<Member> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        Member member = memberMapper.selectOne(queryWrapper);
        if (member == null) {
            throw new BussinessException("M03", "用户不存在");
        } else {
            int salt = member.getSalt();
            String md5Digest = MD5Util.Md5Digest(password, salt);
            if (!md5Digest.equals(member.getPassword())) {
                throw new BussinessException("M04", "密码错误");
            }
        }
        return member;
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    public MemberReadState selectMemberReadState(Integer memberId, Integer bookId) {
        QueryWrapper<MemberReadState> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("member_id", memberId);
        queryWrapper.eq("book_id", bookId);
        MemberReadState memberReadState = memberReadStateMapper.selectOne(queryWrapper);

        return memberReadState;
    }

    @Override
    public MemberReadState updateMemberReadState(Integer memberId, Integer bookId, Integer readState) {
        QueryWrapper<MemberReadState> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("member_id", memberId);
        queryWrapper.eq("book_id", bookId);
        MemberReadState memberReadState = memberReadStateMapper.selectOne(queryWrapper);
        if (memberReadState == null) {
            /* 如果没有添加过阅读状态 */
            memberReadState = new MemberReadState();
            memberReadState.setMemberId(memberId);
            memberReadState.setBookId(bookId);
            memberReadState.setReadState(readState);
            memberReadState.setCreateTime(new Date());
            System.out.println(memberReadState);
            memberReadStateMapper.insert(memberReadState);
        } else {
            memberReadState.setReadState(readState);
            System.out.println(memberReadState);
            memberReadStateMapper.updateById(memberReadState);
        }
        return memberReadState;
    }

    @Override
    public Evaluation addEvaluation(Integer bookId, Integer memberId, Integer score, String content) {
        Evaluation evaluation = new Evaluation();
        evaluation.setContent(content);
        evaluation.setScore(score);
        evaluation.setMemberId(memberId);
        evaluation.setBookId(bookId);
        evaluation.setEnjoy(0);
        evaluation.setCreateTime(new Date());
        evaluation.setState("enable");
        evaluationMapper.insert(evaluation);

        return evaluation;
    }

    @Override
    public Evaluation enjoy(Integer evaluationId) {
        Evaluation evaluation = evaluationMapper.selectById(evaluationId);
        evaluation.setEnjoy(evaluation.getEnjoy() + 1);
        evaluationMapper.updateById(evaluation);
        return evaluation;
    }
}


