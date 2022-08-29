package com.warehouse.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.warehouse.pojo.*;
import com.warehouse.mapper.GoodsMapper;
import com.warehouse.pojo.vo.GoodsVo;
import com.warehouse.service.*;
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
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods> implements IGoodsService {
    @Autowired
    GoodsMapper gm;

    @Autowired
    IInportService iis;
    @Autowired
    IOutportService ios;
    @Autowired
    ISalesbackService isbs;
    @Autowired
    ISalesService iss;

    @Override
    public List<GoodsVo> findAllGoodsVo(QueryWrapper wq) {
        return gm.findAllGoodsVo(wq);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public boolean batchDeleteGoodsByIds(String[] ids) {
        for (String id : ids) {
            deleteGoodsById(id);
        }
        return true;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public boolean deleteGoodsById(String id) {
        iis.remove(new QueryWrapper<Inport>().eq("goodsid",id));
        ios.remove(new QueryWrapper<Outport>().eq("goodsid",id));
        iss.remove(new QueryWrapper<Sales>().eq("goodsid",id));
        isbs.remove(new QueryWrapper<Salesback>().eq("goodsid",id));
        removeById(id);
        return true;
    }

    @Override
    public List<Goods> findGoodsByIds(String[] ids) {
        return gm.findGoodsByIds(ids);
    }

    @Override
    public List<Goods> findGoodsByProviderId(String providerid) {
        return gm.findGoodsByProviderId(providerid);
    }

    @Override
    public List<Goods> findAllWarningGoods(QueryWrapper wrapper) {
        return gm.findAllWarningGoods(wrapper);
    }
}
