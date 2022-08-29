package com.warehouse.service.impl;

import com.warehouse.pojo.Permission;
import com.warehouse.mapper.PermissionMapper;
import com.warehouse.service.IPermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author sachen
 * @since 2022-06-20
 */
@EnableTransactionManagement
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements IPermissionService {

    @Autowired
    PermissionMapper pm;
    @Override
    public HashMap<String, Permission> findPermissionsByRoleId(String id) {
        return pm.findPermissionsByRoleId(id);
    }
    

    @Override
    public Integer getMenuMaxOrderNum() {
        return pm.getMenuMaxOrderNum();
    }

    @Override
    public Integer getPermissionMaxOrderNum() {
        return pm.getPermissionMaxOrderNum();
    }

    @Override
    public boolean ifHasBeenUsed(String id) {
        return pm.ifHasBeenUsed(id)>0;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public boolean deletePermissionById(String id) {
        pm.deleteRolePermissionByPid(id);
        return pm.deleteById(id)==1;
    }

    @Override
    public List<Permission> findPermissionsByUserId(Integer id) {
        return pm.findPermissionsByUserId(id);
    }

    @Override
    public List<Permission> findUserPermissionsByUserId(Integer id) {
        return pm.findUserPermissionsByUserId(id);
    }
}
