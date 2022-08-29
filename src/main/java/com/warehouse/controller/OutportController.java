package com.warehouse.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.warehouse.annotation.BehaviorLog;
import com.warehouse.commons.DataGridView;
import com.warehouse.commons.DataResults;
import com.warehouse.commons.ResultCode;
import com.warehouse.pojo.Inport;
import com.warehouse.pojo.Outport;
import com.warehouse.pojo.User;
import com.warehouse.pojo.vo.InportVo;
import com.warehouse.pojo.vo.OutportVo;
import com.warehouse.service.IInportService;
import com.warehouse.service.IOutportService;
import com.warehouse.utils.PageUtils;
import com.warehouse.utils.ResultUtils;
import com.warehouse.utils.TimeUtils;
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
@RequestMapping("/outport")
public class OutportController {
    @Autowired
    IOutportService ios;
    @Autowired
    IInportService iis;

    @BehaviorLog("增加退货")
    @PreAuthorize("hasAnyAuthority('outport:create')")
    @RequestMapping("/addOutport")
    public DataResults addOutport(String id, Integer number, String remark, HttpServletRequest request) {
        //查询进货信息
        Inport inport = iis.getById(id);
        User user = (User) request.getSession().getAttribute("user");
        Outport outport = new Outport(null, inport.getProviderid(), inport.getPaytype(), TimeUtils.getCurrentTime()
                , user.getName(), inport.getInportprice(), number, remark, inport.getGoodsid());
        return ResultUtils.simpleResult(ios.addOutput(outport));
    }

    @BehaviorLog("查看退货")
    @PreAuthorize("hasAnyAuthority('outport:view')")
    @RequestMapping("/loadAllOutport")
    public DataGridView loadAllOutport(OutportVo outportVo) {
        QueryWrapper wrapper = new QueryWrapper<OutportVo>();
        wrapper.eq(outportVo.getProviderid() != null && outportVo.getProviderid() != 0, "bus_outport.providerid", outportVo.getProviderid());
        wrapper.eq(outportVo.getGoodsid() != null && outportVo.getGoodsid() != 0, "bus_outport.goodsid", outportVo.getGoodsid());
        wrapper.le(StringUtils.isNotEmpty(outportVo.getEndTime()), "bus_outport.inporttime", outportVo.getEndTime());
        wrapper.ge(StringUtils.isNotEmpty(outportVo.getStartTime()), "bus_outport.inporttime", outportVo.getStartTime());
        wrapper.orderByAsc("bus_goods.id");
        List<OutportVo> voList = ios.findAllOutportVo(wrapper);
        List<OutportVo> pageList = PageUtils.getPageList(voList, outportVo.getPage(), outportVo.getLimit());
        return new DataGridView((long) voList.size(), pageList);
    }

    @BehaviorLog("删除退货")
    @PreAuthorize("hasAnyAuthority('outport:delete')")
    @RequestMapping("/deleteOutport")
    public DataResults deleteOutport(String id) {
        return ResultUtils.simpleResult(ios.removeById(id));
    }
}
