package com.warehouse.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.warehouse.annotation.BehaviorLog;
import com.warehouse.commons.DataGridView;
import com.warehouse.pojo.Provider;
import com.warehouse.pojo.vo.ProviderVo;
import com.warehouse.service.IProviderService;
import com.warehouse.commons.DataResults;
import com.warehouse.commons.ResultCode;
import com.warehouse.utils.ResultUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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
@RequestMapping("/provider")
public class ProviderController {
    @Autowired
    IProviderService ips;

    @RequestMapping("/loadAllProviderForSelect")
    public DataResults loadAllProviderForSelect() {
        return DataResults.success(ResultCode.SUCCESS, ips.list());
    }

    @BehaviorLog("查看供应商")
    @PreAuthorize("hasAnyAuthority('provider:view')")
    @RequestMapping("/loadAllProvider")
    public DataGridView loadAllProvider(ProviderVo providerVo) {
        QueryWrapper wrapper = new QueryWrapper<Provider>();
        wrapper.like(StringUtils.isNotEmpty(providerVo.getProvidername()), "providername", providerVo.getProvidername());
        wrapper.like(StringUtils.isNotEmpty(providerVo.getConnectionperson()), "connectionperson", providerVo.getConnectionperson());
        wrapper.like(StringUtils.isNotEmpty(providerVo.getPhone()), "phone", providerVo.getPhone());
        IPage page = ips.page(new Page<Provider>(), wrapper);
        return new DataGridView(page.getTotal(), page.getRecords());
    }

    @BehaviorLog("增加供应商")
    @PreAuthorize("hasAnyAuthority('provider:create')")
    @RequestMapping("/addProvider")
    public DataResults addProvider(Provider provider) {
        return ResultUtils.simpleResult(ips.save(provider));
    }

    @BehaviorLog("修改供应商")
    @PreAuthorize("hasAnyAuthority('provider:update')")
    @RequestMapping("/updateProvider")
    public DataResults updateProvider(Provider provider) {
        return ResultUtils.simpleResult(ips.updateById(provider));
    }

    @BehaviorLog("删除供应商")
    @PreAuthorize("hasAnyAuthority('provider:delete')")
    @RequestMapping("/deleteProvider")
    public DataResults deleteProvider(String id) {
        return ResultUtils.simpleResult(ips.deleteProviderById(id));
    }

    @BehaviorLog("批量删除供应商")
    @PreAuthorize("hasAnyAuthority('provider:batchdelete')")
    @RequestMapping("/batchDeleteProvider")
    public DataResults batchDeleteProvider(String[] ids) {
        return ResultUtils.simpleResult(ips.batchDeleteProviderByIds(ids));
    }
}
