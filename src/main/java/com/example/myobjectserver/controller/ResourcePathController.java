package com.example.myobjectserver.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.myobjectserver.pojo.Resource;
import com.example.myobjectserver.pojo.ResourcePath;
import com.example.myobjectserver.result.R;
import com.example.myobjectserver.services.ResourcePathService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 恒光
 * createTime:2025-03-20
 * version:1.0
 */
@RestController
@RequestMapping("/api/classify")
public class ResourcePathController {

    private final ResourcePathService resourcePathService;
    public ResourcePathController(ResourcePathService resourcePathService){
        this.resourcePathService = resourcePathService;
    }

    @GetMapping("/classifyList")
    public R<List<ResourcePath>> getList(){
        return R.ok(resourcePathService.list());
    }
    @GetMapping("/getStatusList")
    public R<List<ResourcePath>> getStatusList(){
        return R.ok(resourcePathService.list(new QueryWrapper<ResourcePath>().eq("status",1)));
    }
    @GetMapping("getClassifyListById/{id}")
    public R<List<Resource>> getListById(@PathVariable Integer id){
        return R.ok(resourcePathService.getListById(id));
    }

    @PostMapping("/insert")
    public R<Boolean> addPath(@RequestBody ResourcePath resourcePath){
        return R.ok(resourcePathService.save(resourcePath));
    }
    @PutMapping("/update")
    public R<Boolean> updatePath(@RequestBody ResourcePath resourcePath){
        return R.ok(resourcePathService.updateById(resourcePath));
    }
    @DeleteMapping("/delete")
    public R<Boolean> deletePath(Integer id){
        return R.ok(resourcePathService.removeById(id));
    }

}
