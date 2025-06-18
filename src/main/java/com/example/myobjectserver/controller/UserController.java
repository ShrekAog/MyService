package com.example.myobjectserver.controller;

import com.example.myobjectserver.pojo.Users;
import com.example.myobjectserver.result.R;
import com.example.myobjectserver.services.UsersService;
import com.example.myobjectserver.utils.JwtUtil;
import com.example.myobjectserver.vo.UserVo;
import io.jsonwebtoken.Claims;
import org.springframework.web.bind.annotation.*;

/**
*@author 恒光
*createTime:2024-11-29
*version:1.0
*/
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UsersService usersService;
    public UserController(UsersService usersService) {
        this.usersService = usersService;
    }

    @PostMapping("/login")
    public R<UserVo> login(@RequestBody Users users){
        Users user = usersService.login(users.getUsername(), users.getPassword());
        if(user != null){
            UserVo vo = UserVo.builder()
                    .id(user.getId())
                    .username(user.getUsername())
                    .nickName(user.getNickName())
                    .avatar(user.getAvatar())
                    .email(user.getEmail())
                    .introduction(user.getIntroduction())
                    .gender(user.getGender())
                    .userType(user.getUserType())
                    .phoneNumber(user.getPhoneNumber())
                    .token(user.getToken())
                    .build();
        return R.ok(vo);
        }
        return null;
    }

    @PostMapping("/register")
    public R<Boolean> register(@RequestBody Users users){
        return R.ok(usersService.register(users.getUsername(),users.getPassword()));
    }

    @GetMapping("/checkUsername")
    public R<Boolean> checkUsernameExist(@RequestParam("username") String username){
        return R.ok(usersService.checkUsernameExist(username));
    }

    @GetMapping("/getUserInfo")
    public R<UserVo> getUserInfo(String token){
        Claims claims = JwtUtil.parsePayload(token);
        String subject = claims.getSubject();
        return R.ok(usersService.getUserInfoByUsername(subject));
    }
    @GetMapping("/getUserInfo/{userId}")
    public R<UserVo> getUserInfoById(@PathVariable("userId") Integer userId){
        return R.ok(usersService.getUserInfoById(userId));
    }

    @PutMapping("/update")
    public R<Boolean> update(@RequestBody Users users){
        return R.ok(usersService.updateById(users));
    }
    @PostMapping(("/checkToken"))
    public R<Boolean> checkJwtIsExpired(@RequestBody String token){
        return R.ok(usersService.checkJwtIsExpired(token));
    }



}
