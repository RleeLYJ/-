package com.warehouse.pojo.vo;

import com.warehouse.pojo.Provider;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProviderVo extends Provider {
    private Integer page;
    private Integer limit;
    private String[] ids;

}
