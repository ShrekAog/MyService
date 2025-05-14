package com.example.myobjectserver.controller;

import com.example.myobjectserver.pojo.Config;
import com.example.myobjectserver.result.R;
import com.example.myobjectserver.services.ConfigService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 恒光
 * createTime:2025-03-21
 * version:1.0
 */
@RestController
@RequestMapping("/api/config")
public class ConfigController {

    private final ConfigService configService;
    public ConfigController(ConfigService configService){
        this.configService = configService;
    };

    @GetMapping("/list")
    public R<List<Config>> getConfigList(){
        return R.ok(configService.list());
    }

    @GetMapping("/wallpaperList")
    public R<Config> getWallpaperClassify(){
        return R.ok(configService.getWallpaperClassify());
    }

    @GetMapping("/getLoginWallpaperConfig")
    public R<Config> getLoginWallpaperConfig(){
        return R.ok(configService.getLoginWallpaperConfig());
    }

    @GetMapping("/getRegisterWallpaperConfig")
    public R<Config> getRegisterWallpaperConfig(){
        return R.ok(configService.getRegisterWallpaperConfig());
    }

    @PutMapping("/updateWallpaperConfig")
    public R<Boolean> updateWallpaper(@RequestParam("value") Integer value){
        return R.ok(configService.updateWallpaperConfig(value));
    }
    @PutMapping("/updateLoginWallpaperConfig")
    public R<Boolean> updateLoginWallpaperConfig(@RequestParam("value") Integer value){
        return R.ok(configService.updateLoginWallpaperConfig(value));
    }
    @PutMapping("/updateRegisterWallpaperConfig")
    public R<Boolean> updateRegisterWallpaperConfig(@RequestParam("value") Integer value){
        return R.ok(configService.updateRegisterWallpaperConfig(value));
    }
}
