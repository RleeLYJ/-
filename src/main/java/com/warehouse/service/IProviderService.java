package com.warehouse.service;

import com.warehouse.pojo.Provider;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author sachen
 * @since 2022-06-21
 */
public interface IProviderService extends IService<Provider> {
    boolean deleteProviderById(String id);
    boolean batchDeleteProviderByIds(String[] ids);
}
