package com.warehouse.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.warehouse.pojo.Goods;
import com.warehouse.pojo.Inport;
import com.warehouse.mapper.InportMapper;
import com.warehouse.pojo.vo.InportVo;
import com.warehouse.service.IGoodsService;
import com.warehouse.service.IInportService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.warehouse.utils.TimeUtils;
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
public class InportServiceImpl extends ServiceImpl<InportMapper, Inport> implements IInportService {

    @Autowired
    InportMapper im;
    @Autowired
    IGoodsService igs;

    @Override
    public List<InportVo> findAllInportVo(QueryWrapper wrapper) {
        return im.findAllInportVo(wrapper);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public boolean addInport(Inport inport) {
        Goods goods = igs.getById(inport.getGoodsid());
        goods.setNumber(goods.getNumber()+inport.getNumber());
        igs.updateById(goods);
        inport.setInporttime(TimeUtils.getCurrentTime());
        return save(inport);
    }
}
