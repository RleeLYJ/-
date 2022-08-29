package com.warehouse.pojo.vo;


import com.warehouse.pojo.Inport;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InportVo extends Inport {
    private Integer page;
    private Integer limit;
    private String[] ids;
    private String startTime;
    private String endTime;
    private String providername;
    private String goodsname;
    private String size;
}
