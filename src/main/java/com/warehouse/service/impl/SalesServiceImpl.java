package com.warehouse.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.warehouse.pojo.Goods;
import com.warehouse.pojo.Sales;
import com.warehouse.mapper.SalesMapper;
import com.warehouse.pojo.vo.SalesVo;
import com.warehouse.service.IGoodsService;
import com.warehouse.service.ISalesService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
public class SalesServiceImpl extends ServiceImpl<SalesMapper, Sales> implements ISalesService {

    @Autowired
    SalesMapper sm;
    @Autowired
    IGoodsService igs;
    @Override
    public List<SalesVo> findAllSalesVo(QueryWrapper wrapper) {
        return sm.findAllSalesVo(wrapper);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public boolean addSales(Sales sales) {
        Goods goods = igs.getById(sales.getGoodsid());
        //卖出数量
        goods.setNumber(goods.getNumber()-sales.getNumber());
        igs.updateById(goods);
        return save(sales);
    }
}
