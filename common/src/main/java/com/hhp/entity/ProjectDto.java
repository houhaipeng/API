package com.hhp.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProjectDto {
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
     * 项目描述
     */
    private String description;
}
