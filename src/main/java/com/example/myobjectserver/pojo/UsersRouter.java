package com.example.myobjectserver.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.util.Date;
import java.util.List;

/**
 * @author KORE
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@TableName("users_router")
public class UsersRouter {
    @TableId(type = IdType.AUTO)
    //主键
    private Integer id;

    @TableField("user_id")
    //用户id
    private Integer userId;

    @TableField("path")
    //路由路径
    private String path;

    @TableField("icon_path")
    //路由图片
    private String iconPath;

    @TableField("text")
    //路由名称
    private String text;

    @TableField("level")
    //路由级别
    private Byte level;
    //访问权限
    @TableField("permission_id")
    private Integer permissionId;

    @TableField("sort_Order")
    //排序序号
    private Byte sortOrder;

    @TableField("is_hidden")
    //是否隐藏，1：隐藏，0：不隐藏
    private Byte isHidden;

    @TableField("create_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    //创建时间
    private Date createTime;

    @TableField(value = "update_time",update = "now()")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    //修改时间
    private Date updateTime;
    @TableField("parent_id")
    //父路由id，根路由为0
    private Byte parentId;

    @TableField("update_by")
    //修改人
    private String updateBy;
    @TableField(exist = false)
    //子菜单
    List<UsersRouter> children;
}
