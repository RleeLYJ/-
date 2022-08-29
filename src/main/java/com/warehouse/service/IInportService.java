package com.warehouse.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.warehouse.pojo.Inport;
import com.baomidou.mybatisplus.extension.service.IService;
import com.warehouse.pojo.vo.InportVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author sachen
 * @since 2022-06-21
 */
public interface IInportService extends IService<Inport> {
    List<InportVo> findAllInportVo(QueryWrapper wrapper);
    boolean addInport(Inport inport);
}
