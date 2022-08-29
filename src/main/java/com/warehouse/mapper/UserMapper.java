package com.warehouse.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.warehouse.pojo.Role;
import com.warehouse.pojo.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.warehouse.pojo.vo.UserVo;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author sachen
 * @since 2022-06-20
 */
public interface UserMapper extends BaseMapper<User> {
    List<UserVo> findAllUserVo(@Param(Constants.WRAPPER)Wrapper wrapper);
    Integer getUserMaxOrderNum();
    Integer deleteUserRoleByUserId(String id);
    Integer addUserRoleByUid(@Param("uid") String uid,@Param("ids") String[] ids);
    boolean resetUserPwdById(@Param("id") String id,@Param("password") String password);
    @MapKey("id")
    HashMap<String,Role> getRoleByUserId(String id);
}
