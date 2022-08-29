package com.warehouse.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author sachen
 * @since 2022-06-21
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("bus_salesback")
public class Salesback implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer customerid;

    private String paytype;

    private String salesbacktime;

    private Double salebackprice;

    private String operateperson;

    private Integer number;

    private String remark;

    private Integer goodsid;

}
