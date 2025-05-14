package com.example.myobjectserver.controller;

import com.example.myobjectserver.pojo.Engine;
import com.example.myobjectserver.result.R;
import com.example.myobjectserver.services.EngineService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 恒光
 * createTime:2024-12-04
 * version:1.0
 */
@RestController
@RequestMapping("api/engine")
public class EngineController {

    private final EngineService engineService;
    public EngineController(EngineService engineService){
        this.engineService = engineService;
    }

    @GetMapping("/list")
    public R<List<Engine>> getEngineList(){
        return R.ok(engineService.getEngineList());
    }
    @PutMapping("/put")
    public R<Boolean> updateEngine(@RequestBody Engine engine){
        return R.ok(engineService.updateById(engine));
    }
    @DeleteMapping("/del")
    public R<Boolean> deleteEngine(Integer id){
        return R.ok(engineService.removeById(id));
    }
}
