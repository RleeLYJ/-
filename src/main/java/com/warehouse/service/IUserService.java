package com.warehouse.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.warehouse.pojo.Role;
import com.warehouse.pojo.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.warehouse.pojo.vo.UserVo;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author sachen
 * @since 2022-06-20
 */
public interface IUserService extends IService<User> {
    List<UserVo> findAllUserVo(QueryWrapper wrapper);

    Integer getUserMaxOrderNum();

    boolean deleteUser(String id);

    boolean resetUserPwdById(String id);

    HashMap getRoleByUserId(String id);

    boolean saveUserRole(String uid, String[] ids);
}
