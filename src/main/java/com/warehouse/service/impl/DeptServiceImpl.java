package com.warehouse.service.impl;

import com.warehouse.pojo.Dept;
import com.warehouse.mapper.DeptMapper;
import com.warehouse.service.IDeptService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author sachen
 * @since 2022-06-23
 */
@Service
public class DeptServiceImpl extends ServiceImpl<DeptMapper, Dept> implements IDeptService {

    @Autowired
    DeptMapper dm;
    @Override
    public Integer getDeptLevel(Integer deptid) {
        Integer pid = dm.selectById(deptid).getPid();
        if (pid==0){
            return 1;
        }
        return getDeptLevel(pid)+1;
    }

    @Override
    public Integer getDeptMaxOrderNum() {
        return dm.getDeptMaxOrderNum();
    }
}
