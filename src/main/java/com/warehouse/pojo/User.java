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
 * @since 2022-06-20
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("sys_user")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String name;

    private String loginname;

    private String pwd;

    private String address;

    private Integer sex;

    private String remark;

    private Integer deptid;

    private String hiredate;

    /**
     * 上级领导id
     */
    private Integer mgr;

    /**
     * 是否可用，0不可用，1可用
     */
    private Integer available;

    /**
     * 排序码
     */
    private Integer ordernum;

    /**
     * 用户类型[0超级管理员，1管理员，2普通用户]
     */
    private Integer type;

    /**
     * 用户头像地址
     */
    private String imgpath;

    /**
     * 盐
     */
    private String salt;

}
