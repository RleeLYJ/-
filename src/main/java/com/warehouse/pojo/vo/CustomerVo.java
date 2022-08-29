package com.warehouse.pojo.vo;

import com.warehouse.pojo.Customer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerVo extends Customer {
    private Integer page;
    private Integer limit;
    private String[] ids;
}
