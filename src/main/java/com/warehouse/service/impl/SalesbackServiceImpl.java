package com.warehouse.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.warehouse.pojo.Goods;
import com.warehouse.pojo.Salesback;
import com.warehouse.mapper.SalesbackMapper;
import com.warehouse.pojo.vo.SalesbackVo;
import com.warehouse.service.IGoodsService;
import com.warehouse.service.ISalesbackService;
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
public class SalesbackServiceImpl extends ServiceImpl<SalesbackMapper, Salesback> implements ISalesbackService {

    @Autowired
    SalesbackMapper sbm;
    @Autowired
    IGoodsService igs;

    @Override
    public List<SalesbackVo> findAllSalesbackVo(QueryWrapper wrapper) {
        return sbm.findAllSalesbackVo(wrapper);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public boolean addSalesback(Salesback salesback) {
        Goods goods = igs.getById(salesback.getGoodsid());
        //退回数量
        goods.setNumber(goods.getNumber()+salesback.getNumber());
        igs.updateById(goods);
        return save(salesback);
    }


}
