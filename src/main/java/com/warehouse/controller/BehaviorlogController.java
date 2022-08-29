package com.warehouse.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.warehouse.annotation.BehaviorLog;
import com.warehouse.commons.DataGridView;
import com.warehouse.commons.DataResults;
import com.warehouse.pojo.Behaviorlog;
import com.warehouse.pojo.Loginfo;
import com.warehouse.pojo.vo.BehaviorlogVo;
import com.warehouse.pojo.vo.LoginfoVo;
import com.warehouse.service.IBehaviorlogService;
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
 * @since 2022-06-30
 */
@RestController
@RequestMapping("/behaviorlog")
public class BehaviorlogController {
    @Autowired
    IBehaviorlogService ibs;

    @BehaviorLog("查看行为日志")
    @PreAuthorize("hasAnyAuthority('behaviorlog:view')")
    @RequestMapping("/loadAllBehaviorlog")
    public DataGridView loadAllBehaviorlog(BehaviorlogVo behaviorlogVo) {
        QueryWrapper wrapper = new QueryWrapper<Behaviorlog>();
        wrapper.like(StringUtils.isNotEmpty(behaviorlogVo.getOperatorname()), "operatorname", behaviorlogVo.getOperatorname());
        wrapper.like(StringUtils.isNotEmpty(behaviorlogVo.getTitle()), "title", behaviorlogVo.getTitle());
        wrapper.eq(StringUtils.isNotEmpty(behaviorlogVo.getStatus()), "status", behaviorlogVo.getStatus());
        wrapper.eq(StringUtils.isNotEmpty(behaviorlogVo.getType()), "type", behaviorlogVo.getType());
        wrapper.le(StringUtils.isNotEmpty(behaviorlogVo.getEndTime()), "time", behaviorlogVo.getEndTime());
        wrapper.ge(StringUtils.isNotEmpty(behaviorlogVo.getStartTime()), "time", behaviorlogVo.getStartTime());
        IPage<Behaviorlog> page = ibs.page(new Page<Behaviorlog>(behaviorlogVo.getPage(), behaviorlogVo.getLimit()), wrapper);
        return new DataGridView(page.getTotal(), page.getRecords());
    }

    @BehaviorLog("删除行为日志")
    @PreAuthorize("hasAnyAuthority('behaviorlog:delete')")
    @RequestMapping("/deleteBehaviorlog")
    public DataResults deleteBehaviorlog(String id) {
        return ResultUtils.simpleResult(ibs.removeById(id));
    }

    @BehaviorLog("批量删除行为日志")
    @PreAuthorize("hasAnyAuthority('behaviorlog:batchdelete')")
    @RequestMapping("/batchDeleteBehaviorlog")
    public DataResults batchDeleteBehaviorlog(String[] ids) {
        return ResultUtils.simpleResult(ibs.removeByIds(Arrays.asList(ids)));
    }
}
