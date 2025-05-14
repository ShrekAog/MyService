package com.example.myobjectserver.services.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.myobjectserver.mapper.ConfigMapper;
import com.example.myobjectserver.pojo.Config;
import com.example.myobjectserver.services.ConfigService;
import org.springframework.stereotype.Service;

/**
 * @author 恒光
 * createTime:2025-03-21
 * version:1.0
 */
@Service
public class ConfigServiceImpl extends ServiceImpl<ConfigMapper, Config> implements ConfigService {

    private final ConfigMapper mapper;
    public ConfigServiceImpl(ConfigMapper mapper){
        this.mapper = mapper;
    }

    @Override
    public Config getWallpaperClassify() {
        QueryWrapper<Config> wrapper = new QueryWrapper<>();
        wrapper.eq("key_name","wallpaperClassifyId");
        return mapper.selectOne(wrapper);
    }

    @Override
    public Config getLoginWallpaperConfig() {
        QueryWrapper<Config> wrapper = new QueryWrapper<>();
        wrapper.eq("key_name","loginWallpaper");
        return mapper.selectOne(wrapper);
    }

    @Override
    public Config getRegisterWallpaperConfig() {
        QueryWrapper<Config> wrapper = new QueryWrapper<>();
        wrapper.eq("key_name","registerWallpaper");
        return mapper.selectOne(wrapper);
    }

    @Override
    public Boolean updateWallpaperConfig(Integer value) {
        Config one = mapper.selectById(1);
        one.setValue(String.valueOf(value));

        return Boolean.parseBoolean(String.valueOf(mapper.updateById(one)));
    }

    @Override
    public Boolean updateLoginWallpaperConfig(Integer value) {
        Config one = mapper.selectById(2);
        one.setValue(String.valueOf(value));
        return Boolean.parseBoolean(String.valueOf(mapper.updateById(one)));
    }

    @Override
    public Boolean updateRegisterWallpaperConfig(Integer value) {
        Config one = mapper.selectById(3);
        one.setValue(String.valueOf(value));
        return Boolean.parseBoolean(String.valueOf(mapper.updateById(one)));
    }
}
