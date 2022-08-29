package com.warehouse.pojo.vo;

import com.warehouse.pojo.Dept;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeptVo extends Dept {
    private Integer page;
    private Integer limit;
    private String[] ids;
}
