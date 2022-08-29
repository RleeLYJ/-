package com.warehouse.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.warehouse.pojo.Goods;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.warehouse.pojo.vo.GoodsVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author sachen
 * @since 2022-06-21
 */
public interface GoodsMapper extends BaseMapper<Goods> {
    List<GoodsVo> findAllGoodsVo(@Param(Constants.WRAPPER) Wrapper ew);
    List<Goods> findGoodsByIds(String[] ids);
    List<Goods> findGoodsByProviderId(String providerid);
    List<Goods> findAllWarningGoods(@Param(Constants.WRAPPER) Wrapper ew);
}
