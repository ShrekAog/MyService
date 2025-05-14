package com.example.myobjectserver.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.myobjectserver.pojo.Resource;
import com.example.myobjectserver.result.R;
import com.example.myobjectserver.services.ResourceService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author 恒光
 * createTime:2024-12-02
 * version:1.0
 */
@RestController
@RequestMapping("/api/resource")
public class ResourceController {

    private final ResourceService resourceService;
    public ResourceController(ResourceService resourceService) {this.resourceService = resourceService;}

    @PostMapping("/upload")
    public R<Integer> uploadImage(@RequestParam("file") MultipartFile file,
                               @RequestParam("type") String type,@RequestParam(value = "classify",required = false) Integer classify) throws IOException {
        return R.ok(resourceService.uploadResource(file,type,classify));
    }
    @GetMapping("/getOne")
    public R<Resource> getResourceById(@RequestParam("id") Integer id){
        return R.ok(resourceService.getById(id));
    }
    @GetMapping("/list")
    public R<Map<String,Object>> getResourceList(@RequestParam("current") Integer current, @RequestParam("pageSize") Integer pageSize){
        return R.ok(resourceService.getResourceList(current,pageSize));
    }
    @GetMapping("/byList")
    public R<List<Resource>> getResourceList(){
        return R.ok(resourceService.list(new QueryWrapper<Resource>().eq("status",1)));
    }
    @PutMapping("/updateStatus")
    public R<Boolean> updateResource(@RequestBody Resource resource){
        return R.ok(resourceService.updateById(resource));
    }
    @DeleteMapping("/delete")
    public R<Boolean> deleteResourceById(@RequestParam("id") Integer id){
        return R.ok(resourceService.deleteResourceById(id));
    }
    @DeleteMapping("/deleteList")
    public R<Boolean> deleteResourceList(@RequestBody List<Integer> ids){
        return R.ok(resourceService.deleteResourceByIdList(ids));
    }





}
