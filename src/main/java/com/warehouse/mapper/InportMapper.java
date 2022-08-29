package com.warehouse.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.warehouse.pojo.Inport;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.warehouse.pojo.vo.InportVo;
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
public interface InportMapper extends BaseMapper<Inport> {
    List<InportVo> findAllInportVo(@Param(Constants.WRAPPER)Wrapper ew);
}
