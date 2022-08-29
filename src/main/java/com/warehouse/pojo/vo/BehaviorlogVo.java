package com.warehouse.pojo.vo;

import com.warehouse.pojo.Behaviorlog;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BehaviorlogVo extends Behaviorlog {
    private Integer page;
    private Integer limit;
    private String[] ids;
    private String startTime;
    private String endTime;
}
