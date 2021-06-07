package com.hhp.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author hhp
 * @since 2021-05-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("project")
public class Project implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;
    /**
     * 项目状态
     */
    private Integer status;

    /**
     * 项目名称
     */
    private String name;

    /**
     * 项目序号
     */
    private Integer serialNo;

    /**
     * 项目经理
     */
    private String manager;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 修改时间
     */
    private String updateTime;


    /**
     * 项目描述
     */
    private String description;

    /**
     *
     */
    @TableLogic
    @TableField(value = "is_deleted")
    private Boolean isDelete;
}
