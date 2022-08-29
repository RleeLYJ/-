package com.warehouse.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.warehouse.pojo.Sales;
import com.baomidou.mybatisplus.extension.service.IService;
import com.warehouse.pojo.vo.SalesVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author sachen
 * @since 2022-06-21
 */
public interface ISalesService extends IService<Sales> {

    List<SalesVo> findAllSalesVo(QueryWrapper wrapper);

    boolean addSales(Sales sales);
}
