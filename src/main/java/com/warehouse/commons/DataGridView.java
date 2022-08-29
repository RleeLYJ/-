package com.warehouse.commons;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DataGridView {

    private Integer code=0;
    private String msg="";


    /**
     * 返回的记录总条数
     */
    private Long count=0L;
    /**
     * 返回的记录(每页数据)
     */
    private Object data;

    public DataGridView(Long count, Object data) {
        this.count = count;
        this.data = data;
    }

    public DataGridView(Object data) {
        this.data = data;
    }
}
