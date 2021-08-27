package com.zhuaowei.bookstore.controller;

import com.google.code.kaptcha.Producer;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * @ClassName: KaptchaController
 * @Description: TODO: add description
 * @Author: zhuaowei
 * @Date: 2021/8/23 16:24
 * @Version: 1.0
 **/
@Controller
public class KaptchaController  {
    @Resource
    private Producer kaptchaProducer;

    /** 需要传入原声的http请求响应对象 */
    @GetMapping("/verify_code")
    public void createVerifyCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 响应立即过期
        response.setDateHeader("Expires", 0);
        // 不缓存任何图片信息
        response.setHeader("Cache-Control", "no-store,no-cache, must-revalidate");
        response.setHeader("Cache-Control", "post-check=0,pre-check=0");
        response.setHeader("Pragma", "no-cache");
        /* 返回数据类型 */
        response.setContentType("image/png");
        // 生成验证码的文本
        String verifyCode = kaptchaProducer.createText();
        // 将验证码保存到请求中
        request.getSession().setAttribute("kaptchaVerifyCode", verifyCode);
        System.out.println(request.getSession().getAttribute("kaptchaVerifyCode"));

        // 创建验证码图片
        BufferedImage image = kaptchaProducer.createImage(verifyCode);
        // 获取图片的输出流
        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(image, "png", out);
        // 立即发送
        out.flush();
        out.close();
    }
}
