package com.yuwen.rtiweb.controller;

import com.alibaba.fastjson2.JSONObject;
import com.yuwen.rtiweb.annotation.LogAnnotation;
import com.yuwen.rtiweb.entity.Email;
import com.yuwen.rtiweb.entity.Register;
import com.yuwen.rtiweb.entity.User;
import com.yuwen.rtiweb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Administrator
 */
@RestController
@RequestMapping("/api/user")
public class RegisterController {
    @Autowired
    private UserService userService;

    @LogAnnotation("用户注册")
    @PostMapping("/register")
    public String registerUser(@RequestBody Register register) {
        try {
            String username = register.getUsername();
            String password = register.getPassword();
            String confirmPassword = register.getConfirmPassword();
            String email = register.getEmail();
            String emailVerificationCode = register.getEmailVerificationCode();
            String captchaCode = register.getCaptchaCode();
            String invitationCode = register.getInvitationCode();

            User user = userService.registerUser(username, password, confirmPassword, email, emailVerificationCode, captchaCode, invitationCode);

            // Return success message and OK status code
            JSONObject response = new JSONObject();
            response.put("message", "注册成功");
            response.put("status", "OK");
            response.put("user", user);
            return response.toJSONString();
        } catch (Exception e) {
            JSONObject errorResponse = new JSONObject();
            errorResponse.put("error", e.getMessage());
            return errorResponse.toJSONString();
        }
    }
    @PostMapping("/emailCode")
    public String sendEmailVerificationCode(@RequestBody Email emailRequest) {
        try {
            String email = emailRequest.getEmail();
            String ipAddress = emailRequest.getIpAddress();

            userService.sendEmailVerificationCode(email, ipAddress);

            // Return success message and OK status code
            JSONObject response = new JSONObject();
            response.put("message", "Email verification code sent successfully");
            response.put("status", "OK");
            return response.toJSONString();
        } catch (Exception e) {
            // Handle failure to send email verification code
            JSONObject errorResponse = new JSONObject();
            errorResponse.put("error", e.getMessage());
            return errorResponse.toJSONString();
        }
    }

    @PostMapping("/senSignCode")
    public String sendLoginVerificationCode(@RequestBody Email emailRequest) {
        try {
            String email = emailRequest.getEmail();
            String ipAddress = emailRequest.getIpAddress();

            String loginVerificationCode = userService.generateAndSendLoginVerificationCode(email, ipAddress);
            JSONObject response = new JSONObject();
            response.put("message", "Login verification code sent successfully");
            response.put("status", "OK");
            response.put("loginVerificationCode", loginVerificationCode);
            return response.toJSONString();
        } catch (Exception e) {
            // Handle failure to send login verification code
            JSONObject errorResponse = new JSONObject();
            errorResponse.put("error", e.getMessage());
            return errorResponse.toJSONString();
        }
    }


}