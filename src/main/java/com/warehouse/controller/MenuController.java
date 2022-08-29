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
import com.warehouse.pojo.User;
import com.warehouse.pojo.vo.PermissionVo;
import com.warehouse.pojo.vo.TreeNode;
import com.warehouse.service.IPermissionService;
import com.warehouse.utils.ResultUtils;
import com.warehouse.utils.TreeNodeUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/menu")
public class MenuController {

    @Autowired
    IPermissionService ips;

    @RequestMapping("/loadMenuManagerLeftTreeJson")
    public DataGridView loadMenuManagerLeftTreeJson() {
        List<TreeNode> list = new ArrayList<>();
        List<Permission> permissionList = ips.list(new QueryWrapper<Permission>().eq("type", "menu"));
        for (Permission permission : permissionList) {
            list.add(new TreeNode(permission.getId(), permission.getPid(), permission.getTitle(), permission.getOpen() == 1));
        }
        return new DataGridView(list);
    }

    @BehaviorLog("查看菜单")
    @PreAuthorize("hasAnyAuthority('menu:view')")
    @RequestMapping("/loadAllMenu")
    public DataGridView loadAllMenu(PermissionVo permissionVo) {
        QueryWrapper<Permission> wrapper = new QueryWrapper<>();
        //模糊查询
        wrapper.like(StringUtils.isNotEmpty(permissionVo.getTitle()), "title", permissionVo.getTitle());
        wrapper.eq("type", "menu");
        wrapper.eq(permissionVo.getId() != null && permissionVo.getId() != 0, "pid", permissionVo.getId());
        //mybatis分页插件
        IPage<Permission> page = ips.page(new Page<>(permissionVo.getPage(), permissionVo.getLimit()), wrapper);
        return new DataGridView(page.getTotal(), page.getRecords());
    }

    @RequestMapping("/loadMenuMaxOrderNum")
    public DataResults loadMenuMaxOrderNum() {
        return DataResults.success(ResultCode.SUCCESS, ips.getMenuMaxOrderNum());
    }

    @BehaviorLog("增加菜单")
    @PreAuthorize("hasAnyAuthority('menu:create')")
    @RequestMapping("/addMenu")
    public DataResults addMenu(Permission permission) {
        permission.setType("menu");
        return ResultUtils.simpleResult(ips.save(permission));
    }

    @BehaviorLog("修改菜单")
    @PreAuthorize("hasAnyAuthority('menu:update')")
    @RequestMapping("/updateMenu")
    public DataResults updateMenu(Permission permission) {
        return ResultUtils.simpleResult(ips.updateById(permission));
    }

    @RequestMapping("/checkMenuHasChildrenNode")
    public DataResults checkMenuHasChildrenNode(String id) {
        return DataResults.success(ResultCode.SUCCESS, ips.count(new QueryWrapper<Permission>().eq("pid", id)) > 0);
    }

    @BehaviorLog("删除菜单")
    @PreAuthorize("hasAnyAuthority('menu:delete')")
    @RequestMapping("/deleteMenu")
    public DataResults deleteMenu(String id) {
        return ResultUtils.simpleResult(ips.removeById(id));
    }

    @RequestMapping("/loadIndexLeftMenuJson")
    public DataGridView loadIndexLeftMenuJson(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        List<Permission> permissionList = ips.findPermissionsByUserId(user.getId());
        List<TreeNode> treeNodes = new ArrayList<TreeNode>();
        for (Permission p : permissionList) {
            Integer id = p.getId();
            Integer pid = p.getPid();
            String title = p.getTitle();
            String icon = p.getIcon();
            String href = p.getHref();
            Boolean spread = p.getOpen() == 1;
            treeNodes.add(new TreeNode(id, pid, title, icon, href, spread));
        }

        //构造层级关系
        List<TreeNode> result = TreeNodeUtils.build(treeNodes, 1);
        return new DataGridView(result);
    }
}
