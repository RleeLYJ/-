package com.warehouse.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.gson.Gson;
import com.warehouse.annotation.BehaviorLog;
import com.warehouse.commons.DataGridView;
import com.warehouse.commons.DataResults;
import com.warehouse.commons.ResultCode;
import com.warehouse.pojo.Notice;
import com.warehouse.pojo.Role;
import com.warehouse.pojo.User;
import com.warehouse.pojo.vo.GoodsVo;
import com.warehouse.pojo.vo.RoleVo;
import com.warehouse.pojo.vo.UserVo;
import com.warehouse.service.IRoleService;
import com.warehouse.service.IUserService;
import com.warehouse.utils.PageUtils;
import com.warehouse.utils.ResultUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.crypto.Data;
import java.io.IOException;
import java.util.ArrayList;
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
@RequestMapping("/user")
public class UserController {
    @Autowired
    IUserService ius;
    @Autowired
    IRoleService irs;
    @Autowired
    BCryptPasswordEncoder encoder;

    @BehaviorLog("查看用户")
    @PreAuthorize("hasAnyAuthority('user:view')")
    @RequestMapping("/loadAllUser")
    public DataGridView loadAllUser(UserVo userVo) {
        QueryWrapper<UserVo> wrapper = new QueryWrapper<>();
        //模糊查询
        wrapper.like(StringUtils.isNotEmpty(userVo.getName()), "b.name", userVo.getName());
        wrapper.like(StringUtils.isNotEmpty(userVo.getAddress()), "b.address", userVo.getAddress());
        wrapper.eq(userVo.getDeptid() != null, "b.deptid", userVo.getDeptid());
        wrapper.orderByAsc("b.id");
        List<UserVo> voList = ius.findAllUserVo(wrapper);
        List<UserVo> pageList = PageUtils.getPageList(voList, userVo.getPage(), userVo.getLimit());
        return new DataGridView((long) voList.size(), pageList);
    }

    @BehaviorLog("增加用户")
    @PreAuthorize("hasAnyAuthority('user:create')")
    @RequestMapping("/addUser")
    public DataResults addUser(User user) {
        user.setType(1);
        String password = encoder.encode("000000");
        user.setPwd(password);
        return ResultUtils.simpleResult(ius.save(user));
    }

    @RequestMapping("/loadUserMaxOrderNum")
    public DataResults loadUserMaxOrderNum() {
        return DataResults.success(ResultCode.SUCCESS, ius.getUserMaxOrderNum());
    }

    @RequestMapping("/loadUserById")
    public DataResults loadUserById(String id) {
        return DataResults.success(ResultCode.SUCCESS, ius.getById(id));
    }

    @RequestMapping("/loadUsersByDeptId")
    public DataResults loadUsersByDeptId(String deptId) {
        return DataResults.success(ResultCode.SUCCESS, ius.list(new QueryWrapper<User>().eq("deptid", deptId)));
    }

    @BehaviorLog("修改用户")
    @PreAuthorize("hasAnyAuthority('user:update')")
    @RequestMapping("/updateUser")
    public DataResults updateUser(User user) {
        return ResultUtils.simpleResult(ius.updateById(user));
    }

    @RequestMapping("/queryMgrByUserId")
    public DataResults queryMgrByUserId(String userId) {
        List<User> list = ius.list(new QueryWrapper<User>().eq("mgr", userId));
        if (list.size() > 0) {
            return DataResults.fail(ResultCode.NO_DELETE);
        }
        return ResultUtils.simpleResult(true);
    }

    @BehaviorLog("删除用户")
    @PreAuthorize("hasAnyAuthority('user:delete')")
    @RequestMapping("/deleteUser/{userId}")
    public DataResults deleteUser(@PathVariable("userId") String id) {
        return ResultUtils.simpleResult(ius.deleteUser(id));
    }

    @BehaviorLog("重置用户密码")
    @PreAuthorize("hasAnyAuthority('user:resetPwd')")
    @RequestMapping("/resetPwd/{userId}")
    public DataResults resetPwd(@PathVariable("userId") String id) {
        return ResultUtils.simpleResult(ius.resetUserPwdById(id));
    }

    @RequestMapping("/initRoleByUserId")
    public DataGridView initRoleByUserId(String id) {
        HashMap<String, Role> roleByUserId = ius.getRoleByUserId(id);
        List<Role> roleList = irs.list();
        List<RoleVo> list = new ArrayList<>();
        for (Role role : roleList) {
            RoleVo roleVo = new RoleVo();
            roleVo.setId(role.getId());
            roleVo.setName(role.getName());
            roleVo.setRemark(role.getRemark());
            if (roleByUserId.containsKey(role.getId())) {
                roleVo.setLAY_CHECKED(true);
            }
            list.add(roleVo);
        }
        return new DataGridView(list);
    }

    @BehaviorLog("分配用户角色")
    @PreAuthorize("hasAnyAuthority('user:selectRole')")
    @RequestMapping("/saveUserRole")
    public DataResults saveUserRole(String uid, String[] ids) {
        return ResultUtils.simpleResult(ius.saveUserRole(uid, ids));
    }

    @RequestMapping("/logout")
    public void logout(HttpServletResponse response) throws IOException {
        response.getWriter().write(new Gson().toJson(DataResults.success(ResultCode.SUCCESS, "注销成功")));
    }

    @RequestMapping("/changePassword")
    public DataResults changePassword(String oldPassword, String newPwdOne, String newPwdTwo, String name) {
        if (!StringUtils.equals(newPwdOne, newPwdTwo)) {
            return DataResults.fail(ResultCode.REPASSWORD_ERROR);
        } else {
            User user = ius.getOne(new QueryWrapper<User>().eq("loginname", name));
            if (!encoder.matches(oldPassword, user.getPwd())) {
                return DataResults.fail(ResultCode.PASSWORD_ERROR);
            }
            String newPassword = encoder.encode(newPwdOne);
            User updateUser = new User();
            updateUser.setId(user.getId());
            updateUser.setPwd(newPassword);
            ius.updateById(updateUser);
            return DataResults.success(ResultCode.SUCCESS);
        }
    }

    @RequestMapping("/updateUserInfo")
    public DataResults updateUserInfo(User user, HttpServletRequest request) {
        boolean b = ius.updateById(user);
        if (b) {
            User newUser = ius.getById(user.getId());
            request.getSession().setAttribute("user", newUser);
        }
        return ResultUtils.simpleResult(b);
    }
}
