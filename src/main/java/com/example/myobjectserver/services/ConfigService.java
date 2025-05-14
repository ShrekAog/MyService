package com.example.myobjectserver.services;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.myobjectserver.pojo.Config;

/**
 * @author 恒光
 * createTime:2025-03-21
 * version:1.0
 */
public interface ConfigService extends IService<Config> {

    Config getWallpaperClassify();
    Config getLoginWallpaperConfig();
    Config getRegisterWallpaperConfig();


    Boolean updateWallpaperConfig(Integer value);
    Boolean updateLoginWallpaperConfig(Integer value);
    Boolean updateRegisterWallpaperConfig(Integer value);
}
