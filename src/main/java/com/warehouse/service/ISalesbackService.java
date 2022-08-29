package com.warehouse.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.warehouse.pojo.Salesback;
import com.baomidou.mybatisplus.extension.service.IService;
import com.warehouse.pojo.vo.SalesbackVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author sachen
 * @since 2022-06-21
 */
public interface ISalesbackService extends IService<Salesback> {

    List<SalesbackVo> findAllSalesbackVo(QueryWrapper wrapper);
    boolean addSalesback(Salesback salesback);
}
