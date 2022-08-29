package com.warehouse.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.warehouse.annotation.BehaviorLog;
import com.warehouse.commons.DataGridView;
import com.warehouse.commons.DataResults;
import com.warehouse.pojo.Sales;
import com.warehouse.pojo.Salesback;
import com.warehouse.pojo.vo.SalesVo;
import com.warehouse.pojo.vo.SalesbackVo;
import com.warehouse.service.ISalesService;
import com.warehouse.service.ISalesbackService;
import com.warehouse.utils.PageUtils;
import com.warehouse.utils.ResultUtils;
import com.warehouse.utils.TimeUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

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
@RequestMapping("/salesback")
public class SalesbackController {

    @Autowired
    ISalesbackService isbs;
    @Autowired
    ISalesService iss;

    @BehaviorLog("销售退货")
    @PreAuthorize("hasAnyAuthority('salesback:create')")
    @RequestMapping("/addSalesback")
    public DataResults addSalesback(Integer id, Integer number, String remark) {
        Sales sales = iss.getById(id);
        Salesback salesback = new Salesback(null, sales.getCustomerid(), sales.getPaytype(),
                TimeUtils.getCurrentTime(), sales.getSaleprice(), "匿名", number, remark, sales.getGoodsid());
        return ResultUtils.simpleResult(isbs.addSalesback(salesback));
    }

    @BehaviorLog("查看销售退货")
    @PreAuthorize("hasAnyAuthority('salesback:view')")
    @RequestMapping("/loadAllSalesback")
    public DataGridView loadAllSalesback(SalesbackVo salesbackVo) {
        QueryWrapper wrapper = new QueryWrapper<Sales>();
        wrapper.eq(salesbackVo.getCustomerid() != null && salesbackVo.getCustomerid() != 0, "bus_salesback.customerid", salesbackVo.getCustomerid());
        wrapper.eq(salesbackVo.getGoodsid() != null && salesbackVo.getGoodsid() != 0, "bus_salesback.goodsid", salesbackVo.getGoodsid());
        wrapper.le(StringUtils.isNotEmpty(salesbackVo.getEndTime()), "bus_salesback.salesbacktime", salesbackVo.getEndTime());
        wrapper.ge(StringUtils.isNotEmpty(salesbackVo.getStartTime()), "bus_salesback.salesbacktime", salesbackVo.getStartTime());
        wrapper.orderByAsc("bus_salesback.id");
        List<SalesbackVo> voList = isbs.findAllSalesbackVo(wrapper);
        List<SalesbackVo> pageList = PageUtils.getPageList(voList, salesbackVo.getPage(), salesbackVo.getLimit());
        return new DataGridView((long) voList.size(), pageList);
    }

    @BehaviorLog("删除销售退货")
    @PreAuthorize("hasAnyAuthority('salesback:delete')")
    @RequestMapping("/deleteSalesback")
    public DataResults deleteSalesback(String id) {
        return ResultUtils.simpleResult(isbs.removeById(id));
    }
}
