package com.example.myobjectserver.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author 恒光
 * createTime:2025-03-20
 * version:1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName("resource_path")
public class ResourcePath {
    @TableId(type = IdType.AUTO)
    private Integer id;
    @TableField("title") //标题
    private String title;
    @TableField("classify") //分类
    private String classify;
    @TableField("cover") //封面
    private String cover;
    @TableField("url") //链接
    private String url;
    @TableField("introduction") //简介
    private String introduction;
    @TableField("type") //类型
    private String type;
    @TableField("status") //是否启用
    private Boolean status;
    @TableField("remark") //备注
    private String remark;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createTime;

}
