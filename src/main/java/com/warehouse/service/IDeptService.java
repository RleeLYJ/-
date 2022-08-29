package com.warehouse.service;

import com.warehouse.pojo.Dept;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author sachen
 * @since 2022-06-23
 */
public interface IDeptService extends IService<Dept> {
    Integer getDeptLevel(Integer deptid);
    Integer getDeptMaxOrderNum();
}
