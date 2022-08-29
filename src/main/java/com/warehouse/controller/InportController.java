package com.warehouse.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.warehouse.annotation.BehaviorLog;
import com.warehouse.commons.DataGridView;
import com.warehouse.commons.DataResults;
import com.warehouse.pojo.Inport;
import com.warehouse.pojo.User;
import com.warehouse.pojo.vo.InportVo;
import com.warehouse.service.IInportService;
import com.warehouse.utils.PageUtils;
import com.warehouse.utils.ResultUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author sachen
 * @since 2022-06-21
 */
@RestController
@RequestMapping("/inport")
public class InportController {

    @Autowired
    IInportService iis;

    @BehaviorLog("查看进货")
    @PreAuthorize("hasAnyAuthority('inport:view')")
    @RequestMapping("/loadAllInport")
    public DataGridView loadAllInport(InportVo inportVo) {
        QueryWrapper wrapper = new QueryWrapper<>();
        wrapper.eq(inportVo.getProviderid() != null && inportVo.getProviderid() != 0, "bus_inport.providerid", inportVo.getProviderid());
        wrapper.eq(inportVo.getGoodsid() != null && inportVo.getGoodsid() != 0, "bus_inport.goodsid", inportVo.getGoodsid());
        wrapper.le(StringUtils.isNotEmpty(inportVo.getEndTime()), "bus_inport.inporttime", inportVo.getEndTime());
        wrapper.ge(StringUtils.isNotEmpty(inportVo.getStartTime()), "bus_inport.inporttime", inportVo.getStartTime());
        wrapper.orderByAsc("bus_inport.id");
        List<InportVo> voList = iis.findAllInportVo(wrapper);
        List<InportVo> pageList = PageUtils.getPageList(voList, inportVo.getPage(), inportVo.getLimit());
        return new DataGridView((long) voList.size(), pageList);

    }

    @BehaviorLog("删除进货")
    @PreAuthorize("hasAnyAuthority('inport:delete')")
    @RequestMapping("/deleteInport")
    public DataResults deleteInport(String id) {
        return ResultUtils.simpleResult(iis.removeById(id));
    }

    @BehaviorLog("增加进货")
    @PreAuthorize("hasAnyAuthority('inport:create')")
    @RequestMapping("/addInport")
    public DataResults addInport(Inport inport, HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        inport.setOperateperson(user.getName());
        return ResultUtils.simpleResult(iis.addInport(inport));
    }

    @BehaviorLog("修改进货")
    @PreAuthorize("hasAnyAuthority('inport:update')")
    @RequestMapping("/updateInport")
    public DataResults updateInport(Inport inport) {
        return ResultUtils.simpleResult(iis.updateById(inport));
    }


}
