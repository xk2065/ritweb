package com.yuwen.rtiweb.utils;

import org.jetbrains.annotations.NotNull;

import java.util.Random;

/**
 * @author Administrator
 */
public class VerificationCodeUtils {
    // 生成邮箱验证码
    @NotNull
    public static String generateEmailVerificationCode() {
        Random random = new Random();
        StringBuilder codeBuilder = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            codeBuilder.append(random.nextInt(10));
        }
        return codeBuilder.toString();
    }

    // 生成登录验证码
    @NotNull
    public static String generateLoginVerificationCode() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuilder codeBuilder = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            char randomChar = characters.charAt(random.nextInt(characters.length()));
            codeBuilder.append(randomChar);
        }
        return codeBuilder.toString();
    }

}
