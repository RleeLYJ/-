package com.warehouse.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.warehouse.annotation.BehaviorLog;
import com.warehouse.commons.DataGridView;
import com.warehouse.commons.DataResults;
import com.warehouse.commons.ResultCode;
import com.warehouse.pojo.Permission;
import com.warehouse.pojo.Role;
import com.warehouse.pojo.vo.RoleVo;
import com.warehouse.pojo.vo.TreeNode;
import com.warehouse.service.IPermissionService;
import com.warehouse.service.IRoleService;
import com.warehouse.utils.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
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
@RequestMapping("/role")
public class RoleController {
    @Autowired
    IRoleService irs;
    @Autowired
    IPermissionService ips;
    @BehaviorLog("查看角色")
    @PreAuthorize("hasAnyAuthority('role:view')")
    @RequestMapping("/loadAllRole")
    public DataGridView loadAllRole(RoleVo roleVo) {
        QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
        //模糊查询
        queryWrapper.like(StringUtils.isNotEmpty(roleVo.getName()), "name", roleVo.getName());
        queryWrapper.like(StringUtils.isNotEmpty(roleVo.getRemark()), "remark", roleVo.getRemark());
        queryWrapper.le(StringUtils.isNotEmpty(roleVo.getEndTime()), "createtime", roleVo.getEndTime());
        queryWrapper.ge(StringUtils.isNotEmpty(roleVo.getStartTime()), "createtime", roleVo.getStartTime());
        queryWrapper.eq(roleVo.getAvailable() != null, "available", roleVo.getAvailable());
        //mybatis分页插件
        IPage<Role> page = irs.page(new Page<>(roleVo.getPage(), roleVo.getLimit()), queryWrapper);
        return new DataGridView(page.getTotal(), page.getRecords());
    }
    @BehaviorLog("增加角色")
    @PreAuthorize("hasAnyAuthority('role:create')")
    @RequestMapping("/addRole")
    public DataResults addRole(Role role) {
        role.setCreatetime(TimeUtils.getCurrentTime());
        return ResultUtils.simpleResult(irs.save(role));
    }
    @BehaviorLog("删除角色")
    @PreAuthorize("hasAnyAuthority('role:delete')")
    @RequestMapping("/deleteRole")
    public DataResults deleteRole(String id) {
        return ResultUtils.simpleResult(irs.deleteRoleById(id));
    }
    @BehaviorLog("批量删除角色")
    @PreAuthorize("hasAnyAuthority('role:batchdelete')")
    @RequestMapping("/batchDeleteRole")
    public DataResults batchDeleteRole(String[] ids) {
        for (String id : ids) {
            deleteRole(id);
        }
        return ResultUtils.simpleResult(true);
    }
    @BehaviorLog("修改角色")
    @PreAuthorize("hasAnyAuthority('role:update')")
    @RequestMapping("/updateRole")
    public DataResults updateRole(Role role) {
        return ResultUtils.simpleResult(irs.updateById(role));
    }

    /**
     * SELECT sys_permission.* FROM sys_permission INNER JOIN sys_role_permission on sys_permission.id = sys_role_permission.pid INNER JOIN sys_role on sys_role.id = sys_role_permission.rid where sys_role.id=10;
     */
    //todo:提高效率
    @RequestMapping("/initPermissionByRoleId")
    public DataGridView initPermissionByRoleId(String roleId) {
        List<TreeNode> list = new ArrayList<>();
        HashMap<String, Permission> permissionHashMap = ips.findPermissionsByRoleId(roleId);
        List<Permission> allPermissions = ips.list();
        for (Permission permission : allPermissions) {
            list.add(new TreeNode(permission.getId(), permission.getPid(), permission.getTitle(), permission.getOpen() == 1,
                    permissionHashMap.containsKey(permission.getId()) ? "1" : "0"));
        }
        return new DataGridView(list);
    }

    @BehaviorLog("角色授权")
    @PreAuthorize("hasAnyAuthority('role:selectPermission')")
    @RequestMapping("/saveRolePermission")
    public DataResults saveRolePermission(String rid, String[] ids) {
        return ResultUtils.simpleResult(irs.saveRolePermissionByRid(rid, ids));
    }
}
