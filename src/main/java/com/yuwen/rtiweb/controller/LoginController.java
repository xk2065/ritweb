package com.yuwen.rtiweb.controller;


import com.yuwen.rtiweb.mapper.LogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Administrator
 */
@RestController
@RequestMapping("/user/login")
public class LoginController {
    @Autowired
    private LogMapper logMapper;





}
