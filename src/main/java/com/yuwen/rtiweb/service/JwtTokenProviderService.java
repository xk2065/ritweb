package com.yuwen.rtiweb.service;

import com.yuwen.rtiweb.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Administrator
 */
@Service
public class JwtTokenProviderService {
    private static final String SECRET_KEY = "yourSecretKey"; // 修改为实际的密钥
    private static final long EXPIRATION_TIME = 864_000_000; // 10天

    @Autowired
    private UserMapper userMapper;
}
