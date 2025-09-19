package com.example.myobjectserver.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.myobjectserver.dto.ResourceDTO;
import com.example.myobjectserver.dto.ResourceDTOWrapper;
import com.example.myobjectserver.pojo.Resource;
import com.example.myobjectserver.result.R;
import com.example.myobjectserver.services.ResourceService;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class ResourceController {

    private final ResourceService resourceService;
    public ResourceController(ResourceService resourceService) {this.resourceService = resourceService;}

    @PostMapping("/upload")
    public R<Integer> uploadImage(@RequestParam("file") MultipartFile file,
                               @RequestParam("type") String type,@RequestParam("classify") Integer classify) throws IOException {
        return R.ok(resourceService.uploadResource(file,type,classify));
    }

    @PostMapping("/uploadList")
    public R<List<Resource>> uploadImageList(ResourceDTOWrapper wrapper) throws IOException {
        return R.ok(resourceService.uploadResource(wrapper));
    }

    @GetMapping("/getOne")
    public R<Resource> getResourceById(@RequestParam("id") Integer id){
        return R.ok(resourceService.getById(id));
    }

    @GetMapping("/getResourceVideo/{id}")
    public R<List<Resource>> getResourceVideoListById(@PathVariable Integer id){
        return R.ok(resourceService.getResourceVideoListByUserId(id));
    }

    @GetMapping("/list")
    public R<Map<String,Object>> getResourceList(@RequestParam("current") Integer current, @RequestParam("pageSize") Integer pageSize){
        return R.ok(resourceService.getResourceList(current,pageSize));
    }

    @GetMapping("/getResourceListByClassifyId")
    public R<Map<String,Object>> getResourceListByClassifyId(@RequestParam Integer classifyId,
                                                         @RequestParam Integer current,
                                                         @RequestParam Integer pageSize){
        return R.ok(resourceService.getResourceListByClassifyId(classifyId,current,pageSize));
    }

    @GetMapping("/getResourceCount/{classifyId}")
    public R<Integer> getResourceCountByClassifyId(@PathVariable Integer classifyId){
        return R.ok(resourceService.getResourceCount(classifyId));
    }

    @GetMapping("/byList")
    public R<List<Resource>> getResourceList(){
        return R.ok(resourceService.list(
                new LambdaQueryWrapper<Resource>()
                        .eq(Resource::getStatus,1)
                        .ne(Resource::getType,"icon")
                        .last("ORDER BY RAND() LIMIT 10")
        ));
    }

    @PutMapping("/updateStatus")
    public R<Boolean> updateResource(@RequestBody Resource resource){
        return R.ok(resourceService.updateById(resource));
    }

    @DeleteMapping("/delete")
    public R<Integer> deleteResourceById(@RequestParam Integer id,@RequestParam Integer userId){
        return R.ok(resourceService.deleteResourceById(id,userId));
    }

    @DeleteMapping("/deleteList")
    public R<Boolean> deleteResourceList(@RequestBody List<Integer> ids){
        return R.ok(resourceService.deleteResourceByIdList(ids));
    }





}
