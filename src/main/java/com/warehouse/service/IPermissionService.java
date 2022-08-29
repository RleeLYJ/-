package com.warehouse.service;

import com.warehouse.pojo.Permission;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author sachen
 * @since 2022-06-20
 */
public interface IPermissionService extends IService<Permission> {
    HashMap findPermissionsByRoleId(String id);

    Integer getMenuMaxOrderNum();

    Integer getPermissionMaxOrderNum();

    boolean ifHasBeenUsed(String id);

    boolean deletePermissionById(String id);

    List<Permission> findPermissionsByUserId(Integer id);
    List<Permission> findUserPermissionsByUserId(Integer id);
}

