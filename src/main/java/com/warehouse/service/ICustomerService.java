package com.warehouse.service;

import com.warehouse.pojo.Customer;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author sachen
 * @since 2022-06-21
 */
public interface ICustomerService extends IService<Customer> {
    boolean deleteCustomer(String id);
    boolean batchDeleteCustomer(String[] ids);
}
