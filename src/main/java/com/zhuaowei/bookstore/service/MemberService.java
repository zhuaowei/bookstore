package com.zhuaowei.bookstore.service;

import com.zhuaowei.bookstore.bean.Evaluation;
import com.zhuaowei.bookstore.bean.Member;
import com.zhuaowei.bookstore.bean.MemberReadState;

/**
 * @ClassName: CategoryService
 * @Description: TODO: add description
 * @Author: zhuaowei
 * @Date: 2021/8/20 11:00
 * @Version: 1.0
 **/
public interface MemberService {
    /**
     * 创建用户接口
     * @param username 用户名
     * @param password 用户密码
     * @param nickname 用户昵称
     * @return 返回member对象
     */
    Member createMember(String username, String password, String nickname);

    /**
     * 检查登录是否正确
     * @param username
     * @param password
     * @return
     */
    Member checkLogin(String username, String password);


    /**
     * 获取会员阅读状态
     * @param memberId
     * @param bookId
     * @return
     */
    MemberReadState selectMemberReadState(Integer memberId, Integer bookId);

    /**
     * 更新阅读状态
     * @param memberId
     * @param bookId
     * @param readState
     * @return
     */
    MemberReadState updateMemberReadState(Integer memberId, Integer bookId, Integer readState);

    /**
     * 添加新短评
     * @param bookId
     * @param memberId
     * @param score
     * @param content
     * @return
     */
    Evaluation addEvaluation(Integer bookId, Integer memberId, Integer score, String content);

    /**
     * 给评论点赞
     * @param evaluationId
     * @return
     */
    Evaluation enjoy(Integer evaluationId);
}
