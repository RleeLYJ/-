package com.warehouse.handler;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.gson.Gson;
import com.warehouse.commons.DataResults;
import com.warehouse.commons.ResultCode;
import com.warehouse.pojo.Loginfo;
import com.warehouse.pojo.vo.LoginfoVo;
import com.warehouse.service.IInportService;
import com.warehouse.service.ILoginfoService;
import com.warehouse.service.IUserService;
import com.warehouse.utils.TimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.crypto.Data;
import java.io.IOException;

@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {
    @Autowired
    IUserService ius;
    @Autowired
    ILoginfoService ils;
    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        httpServletResponse.setContentType("application/json;charset=utf-8");
        User user = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
        String username = user.getUsername();
        com.warehouse.pojo.User one = ius.getOne(new QueryWrapper<com.warehouse.pojo.User>().eq("loginname", username));
        httpServletRequest.getSession().setAttribute("user",one);
        Loginfo loginfo = new Loginfo(null,one.getName()+"-"+one.getLoginname(),httpServletRequest.getRemoteAddr(), TimeUtils.getCurrentTime());
        ils.save(loginfo);
        DataResults results = DataResults.success(ResultCode.SUCCESS,"登录成功");
        httpServletResponse.getWriter().write(new Gson().toJson(results));
    }
}
