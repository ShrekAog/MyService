package com.example.myobjectserver.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author KORE
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName("engine")
public class Engine {
    //主键
    @TableId(type = IdType.AUTO)
    private Integer id;
    //资源id
    @TableField("resource_id")
    private Integer resourceId;
    //引擎名字
    @TableField("engine_name")
    private String engineName;
    //引擎URL路径
    @TableField("path")
    private String path;
    @TableField("is_hidden")
    private boolean isHidden;
    //资源
    @TableField(exist = false)
    private Resource resource;
}
