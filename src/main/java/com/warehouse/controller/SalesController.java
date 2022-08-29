package com.warehouse.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.warehouse.annotation.BehaviorLog;
import com.warehouse.commons.DataGridView;
import com.warehouse.commons.DataResults;
import com.warehouse.pojo.Sales;
import com.warehouse.pojo.User;
import com.warehouse.pojo.vo.SalesVo;
import com.warehouse.service.ISalesService;
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
@RequestMapping("/sales")
public class SalesController {

    @Autowired
    ISalesService iss;

    @BehaviorLog("查看商品销售")
    @PreAuthorize("hasAnyAuthority('sales:view')")
    @RequestMapping("/loadAllSales")
    public DataGridView loadAllSales(SalesVo salesVo) {
        QueryWrapper wrapper = new QueryWrapper<Sales>();
        wrapper.eq(salesVo.getCustomerid() != null && salesVo.getCustomerid() != 0, "bus_sales.customerid", salesVo.getCustomerid());
        wrapper.eq(salesVo.getGoodsid() != null && salesVo.getGoodsid() != 0, "bus_sales.goodsid", salesVo.getGoodsid());
        wrapper.le(StringUtils.isNotEmpty(salesVo.getEndTime()), "bus_sales.salestime", salesVo.getEndTime());
        wrapper.ge(StringUtils.isNotEmpty(salesVo.getStartTime()), "bus_sales.salestime", salesVo.getStartTime());
        wrapper.orderByAsc("bus_sales.id");
        List<SalesVo> voList = iss.findAllSalesVo(wrapper);
        List<SalesVo> pageList = PageUtils.getPageList(voList, salesVo.getPage(), salesVo.getLimit());
        return new DataGridView((long) voList.size(), pageList);
    }

    @BehaviorLog("删除商品销售")
    @PreAuthorize("hasAnyAuthority('sales:delete')")
    @RequestMapping("/deleteSales")
    public DataResults deleteSales(String id) {
        return ResultUtils.simpleResult(iss.removeById(id));
    }

    @BehaviorLog("修改商品销售")
    @PreAuthorize("hasAnyAuthority('sales:update')")
    @RequestMapping("/updateSales")
    public DataResults updateSales(Sales sales) {
        return ResultUtils.simpleResult(iss.updateById(sales));
    }

    @BehaviorLog("增加商品销售")
    @PreAuthorize("hasAnyAuthority('sales:create')")
    @RequestMapping("/addSales")
    public DataResults addSales(Sales sales, HttpServletRequest request) {
        sales.setSalestime(TimeUtils.getCurrentTime());
        User user = (User) request.getSession().getAttribute("user");
        sales.setOperateperson(user.getName());
        return ResultUtils.simpleResult(iss.addSales(sales));
    }

}
