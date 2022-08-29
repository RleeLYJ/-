package com.warehouse.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.warehouse.pojo.Goods;
import com.warehouse.pojo.Outport;
import com.warehouse.mapper.OutportMapper;
import com.warehouse.pojo.vo.InportVo;
import com.warehouse.pojo.vo.OutportVo;
import com.warehouse.service.IGoodsService;
import com.warehouse.service.IOutportService;
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
public class OutportServiceImpl extends ServiceImpl<OutportMapper, Outport> implements IOutportService {

    @Autowired
    OutportMapper om;
    @Autowired
    IGoodsService igs;

    @Override
    public List<OutportVo> findAllOutportVo(QueryWrapper wrapper) {
        return om.findAllOutportVo(wrapper);
    }

    @Transactional(propagation =  Propagation.REQUIRED)
    @Override
    public boolean addOutput(Outport outport) {
        Goods goods = igs.getById(outport.getGoodsid());
        goods.setNumber(goods.getNumber()-outport.getNumber());
        igs.updateById(goods);
        return save(outport);
    }
}
