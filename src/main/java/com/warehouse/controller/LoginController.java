package com.warehouse.controller;

import cn.hutool.captcha.LineCaptcha;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


@RestController
@RequestMapping("/login")
public class LoginController {
    Logger logger = Logger.getLogger(LoginController.class);
    @RequestMapping("/getCode")
    public void getCode(HttpServletRequest request , HttpServletResponse response) throws IOException {
        LineCaptcha lineCaptcha = new LineCaptcha(116,36,4,5);
        HttpSession session = request.getSession();
        String code = lineCaptcha.getCode();
        session.setAttribute("code",code);
        logger.info("系统生成的验证码是:"+code);
        ServletOutputStream outputStream = response.getOutputStream();
        lineCaptcha.write(outputStream);
        outputStream.close();
    }
}
