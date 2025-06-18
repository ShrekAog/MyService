package com.example.myobjectserver;

import com.example.myobjectserver.mapper.UsersMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
class MyObjectServerApplicationTests {
    @Value("${my.upload.file-upload-path}")
    private String uploadDir;
    @Value("${my.security.publicPath}")
    private String publicPath;

    @Autowired
    private UsersMapper usersMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Test
    void contextLoads() {
        String[] array = Arrays.stream(publicPath.split(",")).map(String::trim).toArray(String[]::new);
        System.out.println();
    }



}
