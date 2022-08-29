package com.warehouse.pojo.vo;

import com.warehouse.pojo.Goods;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoodsVo extends Goods {
    private Integer page;
    private Integer limit;
    private String[] ids;
    private String providername;
}
