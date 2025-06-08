package com.example.myobjectserver.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * @author 恒光
 * createTime:2025-06-08
 * version:1.0
 */
@Data
@AllArgsConstructor
@ToString
@Builder
@TableName("note_resource")
public class NoteResource {
    @TableId(type = IdType.AUTO)
    private Integer id;
    @TableField("note_id")
    private Integer noteId;
    @TableField("image_url")
    private String imageUrl;
    @TableField("create_time")
    private Date createTime;
}
