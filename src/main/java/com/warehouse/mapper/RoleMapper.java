package com.warehouse.mapper;

import com.warehouse.pojo.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author sachen
 * @since 2022-06-20
 */
public interface RoleMapper extends BaseMapper<Role> {

    Integer deleteRolePermissionByRid(String id);
    Integer deleteUserRoleByRid(String id);
    List<Role> findRolesByUserId(Integer id);
    boolean saveRolePermissionByRid(@Param("rid") String rid, @Param("ids") String[] ids);
}
