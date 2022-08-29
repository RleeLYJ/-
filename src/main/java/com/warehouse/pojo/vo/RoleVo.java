package com.warehouse.pojo.vo;

import com.warehouse.pojo.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleVo extends Role {
    private Integer page;
    private Integer limit;
    private String[] ids;
    private String startTime;
    private String endTime;
    private boolean LAY_CHECKED=false;
}
