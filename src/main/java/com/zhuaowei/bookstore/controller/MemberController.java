package com.zhuaowei.bookstore.controller;

import com.baomidou.mybatisplus.core.conditions.query.Query;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zhuaowei.bookstore.bean.Evaluation;
import com.zhuaowei.bookstore.bean.Member;
import com.zhuaowei.bookstore.bean.MemberReadState;
import com.zhuaowei.bookstore.mapper.MemberMapper;
import com.zhuaowei.bookstore.service.MemberService;
import com.zhuaowei.bookstore.service.exception.BussinessException;
import com.zhuaowei.bookstore.util.MD5Util;
import jdk.nashorn.internal.objects.annotations.Getter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: MemberController
 * @Description: TODO: add description
 * @Author: zhuaowei
 * @Date: 2021/8/23 16:46
 * @Version: 1.0
 **/
@Controller
public class MemberController {
    @Resource
    private MemberMapper memberMapper;
    @Resource
    private MemberService memberService;

    @GetMapping("/login.html")
    public ModelAndView showLogin() {
        ModelAndView mav = new ModelAndView("/login");
        return mav;
    }


    /** 注册请求 */
    @PostMapping("/register")
    @ResponseBody
    public Map register(String vc, String username, String password, String nickname, HttpServletRequest request) {
        // 获取请求中的正确验证码
        String verifyCode = (String)request.getSession().getAttribute("kaptchaVerifyCode");
        Map map = new HashMap();
        // 验证码不匹配
        if (vc == null || verifyCode == null || !verifyCode.equalsIgnoreCase(vc)) {
            map.put("code", "V01");
            map.put("message", "验证码错误");
        } else {
            try {
                memberService.createMember(username, password, nickname);
                map.put("code", "0");
                map.put("message", "success");
            } catch (BussinessException be) {
                be.printStackTrace();
                map.put("code", "M02");
                map.put("message", be.getMessage());
            }
        }
        return map;
    }

    /** 登录请求 */
    @PostMapping("/login")
    @ResponseBody
    public Map login(String vc, String username, String password, HttpServletRequest request, HttpSession session) {
        // 获取请求中的正确验证码
        String verifyCode = (String)request.getSession().getAttribute("kaptchaVerifyCode");


        Map map = new HashMap();
        System.out.println(vc + "->" + username + "->" + password);
        if (vc == null || verifyCode == null || !verifyCode.equalsIgnoreCase(vc)) {
            map.put("code", "V01");
            map.put("message", "验证码错误");
        } else {
            try {
                Member member = memberService.checkLogin(username, password);
                session.setAttribute("loginMember", member);
                map.put("code", "0");
                map.put("message", "success");
            } catch (BussinessException be) {
                be.printStackTrace();
                map.put("code", "M02");
                map.put("message", be.getMessage());
            }
        }

        return map;
    }

    @PostMapping("/update_read_state")
    @ResponseBody
    public Map updateReadState(Integer memberId, Integer bookId, Integer readState) {
        Map map = new HashMap();
        try {
            memberService.updateMemberReadState(memberId, bookId, readState);
            map.put("code", "0");
            map.put("message", "success");
        } catch (BussinessException be) {
            be.printStackTrace();
            map.put("code", be.getCode());
            map.put("message", be.getMessage());
        }
        return map;
    }

    @PostMapping("/evaluate")
    @ResponseBody
    public Map evaluate(Integer bookId, Integer memberId, Integer score, String content) {
        Map map = new HashMap();
        try {
            Evaluation evaluation = memberService.addEvaluation(bookId, memberId, score, content);
            map.put("code", "0");
            map.put("message", "success");
            map.put("evaluation", evaluation);
        } catch (BussinessException be) {
            be.printStackTrace();
            map.put("code", be.getCode());
            map.put("message", be.getMessage());
        }
        return map;
    }

    @PostMapping("/enjoy")
    @ResponseBody
    public Map enjoy(Integer evaluationId) {
        Map map = new HashMap();
        try {
            Evaluation enjoy = memberService.enjoy(evaluationId);
            map.put("code", "0");
            map.put("message", "success");
            map.put("evaluation", enjoy);
        } catch (BussinessException be) {
            be.printStackTrace();
            map.put("code", be.getCode());
            map.put("message", be.getMessage());
        }
        return map;
    }
}
