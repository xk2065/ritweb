package com.yuwen.rtiweb.controller;

import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Administrator
 */
public class SecureController {
    /**
     * 查询 用户信息，登录后携带JWT才能访问
     */
    @RequestMapping("/secure/getUserInfo")
    public String login(HttpServletRequest request) {
        Integer id = (Integer) request.getAttribute("id");
        String userName = request.getAttribute("userName").toString();
        String password = request.getAttribute("password").toString();
        return "当前用户信息id=" + id + ",userName=" + userName + ",password=" + password;
    }
}
