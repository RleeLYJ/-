package com.warehouse.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.warehouse.annotation.BehaviorLog;
import com.warehouse.commons.ResultCode;
import com.warehouse.pojo.Customer;
import com.warehouse.pojo.Sales;
import com.warehouse.pojo.Salesback;
import com.warehouse.pojo.vo.CustomerVo;
import com.warehouse.service.ICustomerService;
import com.warehouse.service.ISalesService;
import com.warehouse.service.ISalesbackService;
import com.warehouse.commons.DataGridView;
import com.warehouse.commons.DataResults;
import com.warehouse.utils.ResultUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author sachen
 * @since 2022-06-21
 */
@RestController
@RequestMapping("/customer")
public class CustomerController {
    @Autowired
    ICustomerService ics;

    @RequestMapping("/loadAllCustomerForSelect")
    public DataResults loadAllCustomerForSelect() {
        return DataResults.success(ResultCode.SUCCESS, ics.list());
    }

    @BehaviorLog("查看客户")
    @PreAuthorize("hasAnyAuthority('customer:view')")
    @RequestMapping("/loadAllCustomer")
    public DataGridView loadAllCustomer(CustomerVo customerVo) {
        QueryWrapper wrapper = new QueryWrapper<Customer>();
        wrapper.like(StringUtils.isNotEmpty(customerVo.getCustomername()), "customername", customerVo.getCustomername());
        wrapper.like(StringUtils.isNotEmpty(customerVo.getConnectionpersion()), "connectionpersion", customerVo.getConnectionpersion());
        wrapper.like(StringUtils.isNotEmpty(customerVo.getPhone()), "phone", customerVo.getPhone());
        IPage<Customer> page = ics.page(new Page<Customer>(customerVo.getPage(), customerVo.getLimit()), wrapper);
        return new DataGridView(page.getTotal(), page.getRecords());
    }

    @BehaviorLog("修改客户")
    @PreAuthorize("hasAnyAuthority('customer:update')")
    @RequestMapping("/updateCustomer")
    public DataResults updateCustomer(Customer customer) {
        return ResultUtils.simpleResult(ics.updateById(customer));
    }

    @BehaviorLog("新增客户")
    @PreAuthorize("hasAnyAuthority('customer:create')")
    @RequestMapping("/addCustomer")
    public DataResults addCustomer(Customer customer) {
        return ResultUtils.simpleResult(ics.save(customer));
    }

    @BehaviorLog("删除客户")
    @PreAuthorize("hasAnyAuthority('customer:delete')")
    @RequestMapping("/deleteCustomer")
    @Transactional
    public DataResults deleteCustomer(String id) {
        return ResultUtils.simpleResult(ics.deleteCustomer(id));
    }

    @BehaviorLog("批量删除客户")
    @PreAuthorize("hasAnyAuthority('customer:batchdelete')")
    @RequestMapping("/batchDeleteCustomer")
    public DataResults batchDeleteCustomer(String[] ids) {
        return ResultUtils.simpleResult(ics.batchDeleteCustomer(ids));
    }
}
