package com.warehouse.service.impl;

import com.warehouse.pojo.Goods;
import com.warehouse.pojo.Inport;
import com.warehouse.pojo.Provider;
import com.warehouse.mapper.ProviderMapper;
import com.warehouse.service.IGoodsService;
import com.warehouse.service.IProviderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
public class ProviderServiceImpl extends ServiceImpl<ProviderMapper, Provider> implements IProviderService {

    @Autowired
    ProviderMapper pm;
    @Autowired
    IGoodsService igs;

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public boolean deleteProviderById(String id) {
        List<Goods> goodsList = igs.findGoodsByProviderId(id);
        List<Integer> goodsIds = new ArrayList<>();
        for (Goods goods : goodsList) {
            goodsIds.add(goods.getId());
        }
        String ids = goodsIds.toString();
        System.out.println(ids);
        String[] target = ids.substring(1,ids.length()-1).split(",");
        igs.batchDeleteGoodsByIds(target);
        removeById(id);
        return true;
    }

    @Override
    public boolean batchDeleteProviderByIds(String[] ids) {
        for (String id : ids) {
            deleteProviderById(id);
        }
        return true;
    }
}
