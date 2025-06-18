package com.example.myobjectserver.controller;

import com.example.myobjectserver.pojo.UsersRouter;
import com.example.myobjectserver.result.R;
import com.example.myobjectserver.services.UsersRouterService;
import com.example.myobjectserver.services.UsersService;
import com.example.myobjectserver.vo.RouterVo;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 恒光
 * createTime:2024-11-30
 * version:1.0
 */
@RestController
@RequestMapping("/api/users/router")
public class UsersRouterController {

    private final UsersRouterService service;
    public UsersRouterController(UsersRouterService service) {
        this.service = service;
    }
    /**
     *获取用户类型查询对应的路由菜单
     * @param userType 用户类型
     * @return 路由菜单
     */
    @GetMapping("/getRouterListByUserType/{userType}")
    public R<List<RouterVo>> getRouters(@PathVariable("userType") Integer userType) {
        List<UsersRouter> userRouters = service.getUserRoutersById(userType);
        return R.ok(service.getUserRoutersVoByList(userRouters));
    }
    @GetMapping("/getRouterList")
    public R<List<UsersRouter>> getRouterList(){
        return R.ok(service.getRoutersList());
    }

    @PutMapping("/addRouter")
    public R<Integer> addRouter(@RequestBody UsersRouter router){
        return R.ok(service.addRouter(router));
    }

    @DeleteMapping("/removeRouter")
    public R<Integer> removeRouterById(@RequestParam("id") Integer id){
        return R.ok(service.removeRouterById(id));
    }

    @PutMapping("/updateRouter")
    public R<Integer> updateRouter(@RequestBody UsersRouter router){
        return R.ok(service.updateRouter(router));
    }

}
