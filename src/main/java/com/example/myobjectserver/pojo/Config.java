package com.example.myobjectserver.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author 恒光
 * createTime:2025-03-21
 * version:1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Config {
    @TableId(type = IdType.AUTO)
    private Integer id;

    @TableField("key_name")
    private String keyName;

    @TableField("value")
    private String value;

    @TableField("description")
    private String description;

    @TableField("create_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createTime;

    @TableField(value = "update_time",update = "now()")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date updateTime;
}
