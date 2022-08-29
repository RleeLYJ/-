package com.warehouse.pojo.vo;

import com.warehouse.pojo.Permission;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PermissionVo extends Permission {
    private Integer page;
    private Integer limit;
}
