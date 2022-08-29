package com.warehouse.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.warehouse.annotation.BehaviorLog;
import com.warehouse.commons.DataGridView;
import com.warehouse.commons.DataResults;
import com.warehouse.pojo.Loginfo;
import com.warehouse.pojo.vo.LoginfoVo;
import com.warehouse.service.ILoginfoService;
import com.warehouse.utils.ResultUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author sachen
 * @since 2022-06-22
 */
@RestController
@RequestMapping("/loginfo")
public class LoginfoController {

    @Autowired
    ILoginfoService ils;

    @BehaviorLog("查看登录日志")
    @PreAuthorize("hasAnyAuthority('loginfo:view')")
    @RequestMapping("/loadAllLoginfo")
    public DataGridView loadAllLoginfo(LoginfoVo loginfoVo) {
        QueryWrapper wrapper = new QueryWrapper<Loginfo>();
        wrapper.like(StringUtils.isNotEmpty(loginfoVo.getLoginname()), "loginname", loginfoVo.getLoginname());
        wrapper.like(StringUtils.isNotEmpty(loginfoVo.getLoginip()), "loginip", loginfoVo.getLoginip());
        wrapper.le(StringUtils.isNotEmpty(loginfoVo.getEndTime()), "logintime", loginfoVo.getEndTime());
        wrapper.ge(StringUtils.isNotEmpty(loginfoVo.getStartTime()), "logintime", loginfoVo.getStartTime());
        IPage<Loginfo> page = ils.page(new Page<Loginfo>(loginfoVo.getPage(), loginfoVo.getLimit()), wrapper);
        return new DataGridView(page.getTotal(), page.getRecords());
    }

    @BehaviorLog("删除登录日志")
    @PreAuthorize("hasAnyAuthority('loginfo:delete')")
    @RequestMapping("/deleteLoginfo")
    public DataResults deleteLoginfo(String id) {
        return ResultUtils.simpleResult(ils.removeById(id));
    }

    @BehaviorLog("批量删除登录日志")
    @PreAuthorize("hasAnyAuthority('loginfo:batchdelete')")
    @RequestMapping("/batchDeleteLoginfo")
    public DataResults batchDeleteLoginfo(String[] ids) {
        return ResultUtils.simpleResult(ils.removeByIds(Arrays.asList(ids)));
    }
}
