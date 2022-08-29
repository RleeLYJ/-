package com.warehouse.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.warehouse.annotation.BehaviorLog;
import com.warehouse.commons.DataGridView;
import com.warehouse.commons.DataResults;
import com.warehouse.commons.ResultCode;
import com.warehouse.pojo.Dept;
import com.warehouse.pojo.Permission;
import com.warehouse.pojo.vo.PermissionVo;
import com.warehouse.pojo.vo.TreeNode;
import com.warehouse.service.IPermissionService;
import com.warehouse.utils.ResultUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author sachen
 * @since 2022-06-20
 */
@RestController
@RequestMapping("/permission")
public class PermissionController {

    @Autowired
    IPermissionService ips;


    @RequestMapping("/loadPermissionManagerLeftTreeJson")
    public DataGridView loadPermissionManagerLeftTreeJson(TreeNode treeNode) {
        List<TreeNode> list = new ArrayList<>();
        List<Permission> permissionList = ips.list(new QueryWrapper<Permission>().eq("type", "menu"));
        for (Permission permission : permissionList) {
            list.add(new TreeNode(permission.getId(), permission.getPid(), permission.getTitle(), permission.getOpen() == 1));
        }
        return new DataGridView(list);
    }

    @BehaviorLog("查看权限")
    @PreAuthorize("hasAnyAuthority('permission:view')")
    @RequestMapping("/loadAllPermission")
    public DataGridView loadAllPermission(PermissionVo permissionVo) {
        QueryWrapper<Permission> wrapper = new QueryWrapper<>();
        //模糊查询
        wrapper.like(StringUtils.isNotEmpty(permissionVo.getTitle()), "title", permissionVo.getTitle());
        wrapper.like(StringUtils.isNotEmpty(permissionVo.getPercode()), "percode", permissionVo.getPercode());
        wrapper.eq("type", "permission");
        wrapper.and(qw -> qw.eq(permissionVo.getId() != null && permissionVo.getId() != 0, "pid", permissionVo.getId())
                .or().eq(permissionVo.getId() != null && permissionVo.getId() != 0, "id", permissionVo.getId()));
        //mybatis分页插件
        IPage<Permission> page = ips.page(new Page<>(permissionVo.getPage(), permissionVo.getLimit()), wrapper);
        return new DataGridView(page.getTotal(), page.getRecords());
    }

    @RequestMapping("/loadPermissionMaxOrderNum")
    public DataResults loadPermissionMaxOrderNum() {
        return DataResults.success(ResultCode.SUCCESS, ips.getPermissionMaxOrderNum());
    }

    @BehaviorLog("增加权限")
    @PreAuthorize("hasAnyAuthority('permission:create')")
    @RequestMapping("/addPermission")
    public DataResults addPermission(Permission permission) {
        permission.setType("permission");
        return ResultUtils.simpleResult(ips.save(permission));
    }

    @BehaviorLog("修改权限")
    @PreAuthorize("hasAnyAuthority('permission:update')")
    @RequestMapping("/updatePermission")
    public DataResults updatePermission(Permission permission) {
        return ResultUtils.simpleResult(ips.updateById(permission));
    }

    @BehaviorLog("删除权限")
    @PreAuthorize("hasAnyAuthority('permission:delete')")
    @RequestMapping("/deletePermission")
    public DataResults deletePermission(String id) {
        return ResultUtils.simpleResult(ips.deletePermissionById(id));
    }
}
