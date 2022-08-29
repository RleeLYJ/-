package com.warehouse.pojo.vo;

import com.warehouse.pojo.Sales;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SalesVo extends Sales {
    private Integer page;
    private Integer limit;
    private String[] ids;
    private String startTime;
    private String endTime;
    private String customername;
    private String goodsname;
    private String size;
}
