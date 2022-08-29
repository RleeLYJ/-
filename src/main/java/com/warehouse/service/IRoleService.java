package com.warehouse.service;

import com.warehouse.pojo.Role;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author sachen
 * @since 2022-06-20
 */
public interface IRoleService extends IService<Role> {
    boolean deleteRoleById(String id);
    List<Role> findRolesByUserId(Integer id);
    boolean saveRolePermissionByRid(String rid, String[] ids);
}
