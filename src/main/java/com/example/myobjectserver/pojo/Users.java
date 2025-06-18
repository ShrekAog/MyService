package com.example.myobjectserver.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * @author 恒光
 * createTime:2024-11-29
 * version:1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Users implements UserDetails{
    @TableId(type = IdType.AUTO)
    private Integer id;
    @TableField("username")
    //用户名
    private String username;
    @TableField("nick_name")
    private String nickName;
    @TableField("password")
    //用户密码
    private String password;
    @TableField("phone_number")
    //电话号码
    private String phoneNumber;
    @TableField("email")
    //邮箱
    private String email;
    @TableField("user_status")
    //是否启用该账号
    private Integer userStatus;
    @TableField("gender")
    //性别
    private Integer gender;
    @TableField("avatar")
    //头像路径
    private String avatar;
    @TableField("introduction")
    //个性签名(自我介绍)
    private String introduction;
    @TableField("user_type")
    //用户类型
    private Integer userType;
    @TableField("create_time")
    //创建时间
    private Date createTime;
    @TableField("update_time")
    //修改时间
    private Date updateTime;
    @TableField("update_by")
    //修改人
    private String updateBy;
    @TableField("deleted")
    //是否已删除(0否1是)
    private Integer deleted;

    @TableField(exist = false)
    //用户权限列表
    Collection<? extends GrantedAuthority> authorities;
    @TableField(exist = false)
    //token
    String token;

  /*  public Users(String username, String password, List<GrantedAuthority> authorities) {
        this.username = username;
        this.password = password;
        this.authorities = authorities;
    }*/


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
