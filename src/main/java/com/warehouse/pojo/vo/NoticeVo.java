package com.warehouse.pojo.vo;

import com.warehouse.pojo.Notice;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NoticeVo extends Notice {
    private Integer page;
    private Integer limit;
    private String[] ids;
    private String startTime;
    private String endTime;
}
