package com.warehouse.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.warehouse.pojo.Permission;
import com.warehouse.pojo.Role;
import com.warehouse.pojo.User;
import com.warehouse.mapper.UserMapper;
import com.warehouse.pojo.vo.UserVo;
import com.warehouse.service.IPermissionService;
import com.warehouse.service.IRoleService;
import com.warehouse.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
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
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService , UserDetailsService {

    @Autowired
    UserMapper um;
    @Autowired
    IPermissionService ips;
    @Autowired
    BCryptPasswordEncoder encoder;
    Logger logger = Logger.getLogger(UserServiceImpl.class);

    @Override
    public List<UserVo> findAllUserVo(QueryWrapper wrapper) {
        return um.findAllUserVo(wrapper);
    }

    @Override
    public Integer getUserMaxOrderNum() {
        return um.getUserMaxOrderNum();
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public boolean deleteUser(String id) {
        um.deleteUserRoleByUserId(id);
        return um.deleteById(id)==1;
    }

    @Override
    public boolean resetUserPwdById(String id) {
        String password = encoder.encode("000000");
        return um.resetUserPwdById(id,password);
    }

    @Override
    public HashMap getRoleByUserId(String id) {
        return um.getRoleByUserId(id);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public boolean saveUserRole(String uid, String[] ids) {
        um.deleteUserRoleByUserId(uid);
        if (ids==null){
            return true;
        }
        return um.addUserRoleByUid(uid,ids)==ids.length;
    }


    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = um.selectOne(new QueryWrapper<User>().eq("loginname", s));
        if(user==null){
            return null;
        }
        ArrayList<GrantedAuthority> roles = new ArrayList<>();
        List<Permission> permissions = ips.findUserPermissionsByUserId(user.getId());
        if (!permissions.isEmpty()) {
            for (Permission permission : permissions) {
                roles.add(new SimpleGrantedAuthority(permission.getPercode()));
                logger.info("当前用户拥有的权限为："+permission.getPercode());
            }
        }
        return new org.springframework.security.core.userdetails.User(user.getLoginname(),user.getPwd(),roles);
    }
}
