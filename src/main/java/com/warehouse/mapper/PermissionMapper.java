package com.warehouse.mapper;

import com.warehouse.pojo.Permission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.MapKey;

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
public interface PermissionMapper extends BaseMapper<Permission> {
    @MapKey("id")
    HashMap<String,Permission> findPermissionsByRoleId(String id);

    Integer getMenuMaxOrderNum();
    Integer getPermissionMaxOrderNum();
    Integer deleteRolePermissionByPid(String id);

    Integer ifHasBeenUsed(String id);
    List<Permission> findPermissionsByUserId(Integer id);
    List<Permission> findUserPermissionsByUserId(Integer id);

}
