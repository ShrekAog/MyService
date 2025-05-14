package com.example.myobjectserver.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * @author KORE
 */
@Data
@AllArgsConstructor
@Builder
@ToString
@TableName("resource")
public class Resource {
    //主键
    @TableId(type = IdType.AUTO)
    private Integer id;
    //用户ID
    @TableField("user_id")
    private Integer userId;
    //资源类型
    @TableField("type")
    private String type;
    //资源路径
    @TableField("path")
    private String path;
    //资源大小
    @TableField("size")
    private Integer size;
    //资源名称（包含后缀）
    @TableField("original_name")
    private String originalName;
    //mime类型
    @TableField("mime_type")
    private String mimeType;
    //是否启用
    @TableField("status")
    private Boolean status;
    //存储平台（本地或者外部oss）
    @TableField("store_type")
    private String storeType;
    @TableField("resource_path_id")
    //分类表
    private Integer resourcePathId;
    //创建时间
    @TableField("create_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createTime;
}
