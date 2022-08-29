package com.warehouse.filter;

import com.google.gson.Gson;
import com.warehouse.commons.DataResults;
import com.warehouse.commons.ResultCode;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.security.sasl.AuthenticationException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Component
public class CodeFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        if (StringUtils.equals("/login",httpServletRequest.getRequestURI())&&StringUtils.equalsIgnoreCase("post",httpServletRequest.getMethod())){
            try {
                VerityCode(httpServletRequest);
            }catch (Exception e){
                httpServletResponse.setContentType("application/json;charset=utf-8");
                httpServletResponse.getWriter().write(new Gson().toJson(DataResults.success(ResultCode.CODE_FAIL,e.getMessage())));
                return;
            }
        }
        filterChain.doFilter(httpServletRequest,httpServletResponse);
    }
    public void VerityCode(HttpServletRequest httpServletRequest){
        HttpSession session = httpServletRequest.getSession(false);
        if(session!=null){
            String code = (String) session.getAttribute("code");
            String inputCode = httpServletRequest.getParameter("code");
            if (!StringUtils.isNotEmpty(inputCode)){
                throw new SessionAuthenticationException("验证码不能为空!");
            }else if (!inputCode.equalsIgnoreCase(code)) {
               throw new SessionAuthenticationException("验证码错误!");
            }
        }
    }
}
