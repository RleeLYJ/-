package com.warehouse.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.warehouse.annotation.BehaviorLog;
import com.warehouse.commons.DataGridView;

import com.warehouse.commons.DataResults;
import com.warehouse.commons.ResultCode;
import com.warehouse.pojo.Dept;
import com.warehouse.pojo.Notice;
import com.warehouse.pojo.User;
import com.warehouse.pojo.vo.DeptVo;
import com.warehouse.pojo.vo.TreeNode;
import com.warehouse.pojo.vo.NoticeVo;
import com.warehouse.service.IDeptService;
import com.warehouse.service.IUserService;
import com.warehouse.utils.ResultUtils;
import com.warehouse.utils.TimeUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;


import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author sachen
 * @since 2022-06-23
 */
@RestController
@RequestMapping("/dept")
public class DeptController {

    @Autowired
    IDeptService ids;
    @Autowired
    IUserService ius;

    @RequestMapping("/loadDeptManagerLeftTreeJson")
    public DataGridView loadDeptManagerLeftTreeJson() {
        List<TreeNode> list = new ArrayList<>();
        List<Dept> deptList = ids.list();
        for (Dept dept : deptList) {
            list.add(new TreeNode(dept.getId(), dept.getPid(), dept.getName(), dept.getOpen() == 1));
        }
        return new DataGridView(list);
    }

    @BehaviorLog("查看部门")
    @PreAuthorize("hasAnyAuthority('dept:view')")
    @RequestMapping("/loadAllDept")
    public DataGridView loadAllDept(DeptVo deptVo) {
        QueryWrapper<Dept> wrapper = new QueryWrapper<>();
        //模糊查询
        wrapper.like(StringUtils.isNotEmpty(deptVo.getName()), "name", deptVo.getName());
        wrapper.like(StringUtils.isNotEmpty(deptVo.getRemark()), "remark", deptVo.getRemark());
        wrapper.like(StringUtils.isNotEmpty(deptVo.getAddress()), "address", deptVo.getAddress());
        wrapper.and(qw -> qw.eq(deptVo.getId() != null && deptVo.getId() != 0, "pid", deptVo.getId())
                .or().eq(deptVo.getId() != null && deptVo.getId() != 0, "id", deptVo.getId()));
        //mybatis分页插件
        IPage<Dept> page = ids.page(new Page<>(deptVo.getPage(), deptVo.getLimit()), wrapper);
        return new DataGridView(page.getTotal(), page.getRecords());
    }

    @BehaviorLog("增加部门")
    @PreAuthorize("hasAnyAuthority('dept:create')")
    @RequestMapping("/addDept")
    public DataResults addDept(Dept dept) {
        dept.setCreatetime(TimeUtils.getCurrentTime());
        return ResultUtils.simpleResult(ids.save(dept));
    }

    @RequestMapping("/loadDeptMaxOrderNum")
    public DataResults loadDeptMaxOrderNum() {
        return DataResults.success(ResultCode.SUCCESS, ids.getDeptMaxOrderNum());
    }

    @BehaviorLog("修改部门")
    @PreAuthorize("hasAnyAuthority('dept:update')")
    @RequestMapping("/updateDept")
    public DataResults updateDept(Dept dept) {
        return ResultUtils.simpleResult(ids.updateById(dept));
    }


    @RequestMapping("/checkDeptHasChildrenNode")
    public DataResults checkDeptHasChildrenNode(String id) {
        return DataResults.success(ResultCode.SUCCESS, ids.count(new QueryWrapper<Dept>().eq("pid", id)) > 0);
    }

    @BehaviorLog("删除部门")
    @PreAuthorize("hasAnyAuthority('dept:delete')")
    @RequestMapping("/deleteDept")
    public DataResults deleteDept(String id) {
        int count = ius.count(new QueryWrapper<User>().eq("deptid", id));
        if (count == 0) {
            return ResultUtils.simpleResult(ids.removeById(id));
        }
        return DataResults.fail(ResultCode.NO_DELETE);
    }

}
