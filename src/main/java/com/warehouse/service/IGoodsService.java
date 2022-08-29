package com.warehouse.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.warehouse.pojo.Goods;
import com.baomidou.mybatisplus.extension.service.IService;
import com.warehouse.pojo.vo.GoodsVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author sachen
 * @since 2022-06-21
 */
public interface IGoodsService extends IService<Goods> {
    List<GoodsVo> findAllGoodsVo(QueryWrapper wq);
    boolean batchDeleteGoodsByIds(String[] ids);
    boolean deleteGoodsById(String id);
    List<Goods> findGoodsByIds(String[] ids);
    List<Goods> findGoodsByProviderId(String providerid);
    List<Goods> findAllWarningGoods(QueryWrapper wrapper);
}
