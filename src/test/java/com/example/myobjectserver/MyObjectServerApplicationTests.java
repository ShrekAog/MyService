package com.example.myobjectserver;

import com.example.myobjectserver.mapper.UsersMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
class MyObjectServerApplicationTests {
    @Value("${my.upload.file-upload-path}")
    private String uploadDir;

    private String zz;

    @Autowired
    private UsersMapper usersMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Test
    void contextLoads() throws InterruptedException {

    }


}
