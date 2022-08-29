package com.warehouse.service.impl;

import com.warehouse.pojo.Role;
import com.warehouse.mapper.RoleMapper;
import com.warehouse.service.IRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author sachen
 * @since 2022-06-20
 */
@EnableTransactionManagement
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {
    @Autowired
    RoleMapper rm;

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public boolean deleteRoleById(String id) {
        rm.deleteRolePermissionByRid(id);
        rm.deleteUserRoleByRid(id);
        return rm.deleteById(id) == 1;
    }

    @Override
    public List<Role> findRolesByUserId(Integer id) {
        return rm.findRolesByUserId(id);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public boolean saveRolePermissionByRid(String rid, String[] ids) {
        rm.deleteRolePermissionByRid(rid);
        if (ids==null||ids.length==0){
            return true;
        }
        return rm.saveRolePermissionByRid(rid,ids);
    }
}
