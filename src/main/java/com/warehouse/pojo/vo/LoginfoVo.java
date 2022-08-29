package com.warehouse.pojo.vo;

import com.warehouse.pojo.Loginfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginfoVo extends Loginfo {
    private Integer page;
    private Integer limit;
    private String[] ids;
    private String startTime;
    private String endTime;
}
