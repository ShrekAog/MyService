package com.example.myobjectserver.controller;

import com.example.myobjectserver.pojo.Users;
import com.example.myobjectserver.pojo.UsersRouter;
import com.example.myobjectserver.result.R;
import com.example.myobjectserver.services.UsersService;
import com.example.myobjectserver.utils.JwtUtil;
import com.example.myobjectserver.vo.UserVo;
import com.example.myobjectserver.vo.UsersRoutersVo;
import io.jsonwebtoken.Claims;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    /**
     *获取用户类型查询对应的路由菜单
     * @param userType 用户类型
     * @return 路由菜单
     */
    @GetMapping("/routers/{userType}")
    public R<List<UsersRoutersVo>> getRouters(@PathVariable("userType") Integer userType) {
        List<UsersRouter> userRouters = usersService.getUserRoutersById(userType);
        return R.ok(usersService.getUserRoutersVoByList(userRouters));
    }

    /**
     *获取路由对象去进行修改
     * @param router 路由对象
     * @return 修改成功数量 1
     */
    @PutMapping("/routers/updateRouter")
    public R<Integer> updateRouter(@RequestBody UsersRouter router){
        return R.ok(usersService.updateRouters(router));
    }

    @PostMapping("/login")
    public R<Map<String,Object>> login(@RequestBody Users users){
        HashMap<String, Object> map = new HashMap<>();
        Users user = usersService.login(users.getUsername(), users.getPassword());
        if(user != null){
            map.put("token",user.getToken());
        }
        return R.ok(map);
    }

    @PostMapping("/register")
    public R<Boolean> register(@RequestBody Users users){
        return R.ok(usersService.register(users.getUsername(),users.getPassword()));
    }

    @GetMapping("/checkUsername")
    public R<Boolean> checkUsernameExist(@RequestParam("username") String username){
        return R.ok(usersService.checkUsernameExist(username));
    }

    /**
     * 根据token解析获取类型
     * @param token token
     * @return 用户类型 1admin 2user
     */
    @GetMapping("/parseUserToken")
    public R<String> parseUserToken(String token){
        Claims claims = JwtUtil.parsePayload(token);
        return R.ok((String) claims.get("type"));
    }
    /**
     * 根据token解析获取用户名
     * @param token token
     * @return 用户名 username
     */
    @GetMapping("/getUserInfo")
    public R<UserVo> getUserInfo(String token){
        Claims claims = JwtUtil.parsePayload(token);
        String subject = claims.getSubject();
        return R.ok(usersService.getUserInfoByUsername(subject));
    }
    @PutMapping("/update")
    public R<Boolean> update(@RequestBody Users users){
        return R.ok(usersService.updateById(users));
    }



}
