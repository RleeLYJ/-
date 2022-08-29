package com.warehouse.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.warehouse.pojo.Customer;
import com.warehouse.mapper.CustomerMapper;
import com.warehouse.pojo.Sales;
import com.warehouse.pojo.Salesback;
import com.warehouse.service.ICustomerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.warehouse.service.ISalesService;
import com.warehouse.service.ISalesbackService;
import com.warehouse.utils.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author sachen
 * @since 2022-06-21
 */
@EnableTransactionManagement
@Service
public class CustomerServiceImpl extends ServiceImpl<CustomerMapper, Customer> implements ICustomerService {
    @Autowired
    ISalesbackService isbs;
    @Autowired
    ISalesService iss;

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public boolean deleteCustomer(String id) {
        try {
            isbs.remove(new QueryWrapper<Salesback>().eq("customerid",id));
            iss.remove(new QueryWrapper<Sales>().eq("customerid",id));
            removeById(id);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public boolean batchDeleteCustomer(String[] ids) {
        for (String id : ids) {
            deleteCustomer(id);
        }
        return true;
    }
}
