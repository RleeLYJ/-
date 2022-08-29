package com.warehouse.pojo.vo;

import com.warehouse.pojo.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserVo extends User {
    private Integer page;
    private Integer limit;
    private String deptname;
    private String leadername;
}
