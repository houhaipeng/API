package com.hhp.entity;

import com.baomidou.mybatisplus.annotation.*;

import lombok.Data;

import java.time.LocalDate;

@Data
public class User {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String name;
    private Integer age;
    private String email;
//    @TableField(value = "create_time")
//    private LocalDate createTime;
//    @TableField(value = "update_time")
//    private LocalDate updateTime;
}
