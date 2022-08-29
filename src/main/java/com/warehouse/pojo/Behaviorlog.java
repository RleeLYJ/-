package com.warehouse.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author sachen
 * @since 2022-06-30
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@TableName("sys_behaviorlog")
public class Behaviorlog implements Serializable {

    private static final long serialVersionUID = 1L;
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String type;
    private String status;
    private String operatorname;

    private String title;

    private String time;

    private String methodname;

    private String args;

    private String returntype;

    private String exception;

}
