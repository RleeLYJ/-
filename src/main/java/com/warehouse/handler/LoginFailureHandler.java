package com.warehouse.handler;

import com.google.gson.Gson;
import com.warehouse.commons.DataResults;
import com.warehouse.commons.ResultCode;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class LoginFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        httpServletResponse.setContentType("application/json;charset=utf-8");
        DataResults results = DataResults.success(ResultCode.LOGIN_FAIL,"登录失败");
        httpServletResponse.getWriter().write(new Gson().toJson(results));
    }
}
