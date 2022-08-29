package com.warehouse.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.warehouse.pojo.Sales;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.warehouse.pojo.vo.SalesVo;
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
public interface SalesMapper extends BaseMapper<Sales> {
    List<SalesVo> findAllSalesVo(@Param(Constants.WRAPPER)Wrapper wrapper);
}
