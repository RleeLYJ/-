package com.warehouse.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.warehouse.pojo.Outport;
import com.baomidou.mybatisplus.extension.service.IService;
import com.warehouse.pojo.vo.InportVo;
import com.warehouse.pojo.vo.OutportVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author sachen
 * @since 2022-06-21
 */
public interface IOutportService extends IService<Outport> {
    List<OutportVo> findAllOutportVo(QueryWrapper wrapper);
    boolean addOutput(Outport outport);
}
