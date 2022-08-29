package com.warehouse.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.warehouse.pojo.Outport;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.warehouse.pojo.vo.OutportVo;
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
public interface OutportMapper extends BaseMapper<Outport> {
    List<OutportVo> findAllOutportVo(@Param(Constants.WRAPPER)Wrapper wrapper);
}
