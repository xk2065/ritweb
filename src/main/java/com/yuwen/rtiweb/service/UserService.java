package com.yuwen.rtiweb.service;

import com.yuwen.rtiweb.entity.InvitationCode;
import com.yuwen.rtiweb.entity.Role;
import com.yuwen.rtiweb.entity.User;
import com.yuwen.rtiweb.mapper.UserMapper;
import com.yuwen.rtiweb.utils.ValidationUtils;
import com.yuwen.rtiweb.utils.VerificationCodeUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.concurrent.TimeUnit;

/**
 * @author Administrator
 */
@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private EmailService emailService;

    @Autowired
    private InvitationCodeService invitationCodeService;

    @Autowired
    private StringRedisTemplate redisTemplate;

    private static final String IP_REQUEST_KEY_PREFIX = "ip_request:";
    private static final String IP_LOGIN_REQUEST_KEY_PREFIX = "ip_login_request:";

    // 注册用户业务逻辑
    public User registerUser(String username, String password, String confirmPassword,
                             String email, String emailVerificationCode, String captchaCode, String invitationCode) throws Exception {
        // 校验密码是否符合要求
        validatePassword(password, confirmPassword);
        // 校验邮箱验证码
        validateEmailVerificationCode(email, emailVerificationCode);
        // 校验登录验证码（防止机器人操作）
        validateCaptchaCode(captchaCode);
        // 校验邀请码
        validateInvitationCode(invitationCode);
        // 注册成功后，保存用户信息
        User newUser = new User();
        newUser.setUserName(username);
        newUser.setUserPwd(new BCryptPasswordEncoder().encode(password));
        newUser.setUserEmail(email);
        newUser.setGameName("DefaultGameName"); // 你可以设置默认游戏名称，如果有的话
        newUser.setRoles(Collections.singletonList(new Role(null, "USER"))); // 设置默认角色为USER
        newUser.setAvatar("default_avatar.jpg"); // 设置默认头像
        newUser.setPersonalIntroduction("Welcome to my profile!"); // 设置默认个人介绍
        newUser.setAge(18); // 设置默认年龄
        newUser.setOrganization("Gaming Guild"); // 设置默认组织
        newUser.setExperience(0); // 设置默认经验
        newUser.setLevel(1); // 设置默认等级
        newUser.setContribution(0); // 设置默认贡献度
        return newUser;
    }

    // 校验密码是否符合要求
    private void validatePassword(String password, String confirmPassword) throws Exception {
        if (!ValidationUtils.isPasswordValid(password) || !ValidationUtils.arePasswordsEqual(password, confirmPassword)) {
            throw new Exception("Invalid password");
        }
    }

    // 校验邮箱验证码
    private void validateEmailVerificationCode(String email, @NotNull String verificationCode) throws Exception {
        // 根据实际情况，从缓存或数据库中获取之前发送的验证码
        String savedCode = redisTemplate.opsForValue().get("email_verification_code:" + email);
        if (!verificationCode.equals(savedCode)) {
            throw new Exception("Invalid email verification code");
        }
        // 验证通过后从缓存中删除验证码
        redisTemplate.delete("email_verification_code:" + email);
    }

    // 校验登录验证码（防止机器人操作）
    private void validateCaptchaCode(@NotNull String captchaCode) throws Exception {
        // 根据实际情况，从缓存或数据库中获取之前生成的验证码
        String savedCode = redisTemplate.opsForValue().get("captcha_code");
        if (!captchaCode.equalsIgnoreCase(savedCode)) {
            throw new Exception("Invalid captcha code");
        }
        // 验证通过后从缓存中删除验证码
        redisTemplate.delete("captcha_code");
    }
    // 发送邮箱验证码
    public void sendEmailVerificationCode(String email,String ipAddress) throws Exception {
        // 检查IP请求频率
        checkIPRequestFrequency(ipAddress);

        String verificationCode = VerificationCodeUtils.generateEmailVerificationCode();
        // 将验证码保存到 Redis 缓存，有效期设置为 5 分钟
        redisTemplate.opsForValue().set("email_verification_code:" + email, verificationCode, 5, TimeUnit.MINUTES);

        // 记录IP请求时间戳
        String ipRequestKey = IP_REQUEST_KEY_PREFIX + ipAddress;
        redisTemplate.opsForValue().set(ipRequestKey, String.valueOf(System.currentTimeMillis()), 5, TimeUnit.MINUTES);

        // 发送邮件
        emailService.sendEmailWithVerificationCode(email, "Your Verification Code", "Your verification code is: " + verificationCode);
    }

    private void checkIPRequestFrequency(String ipAddress) throws Exception {
        String ipRequestKey = IP_REQUEST_KEY_PREFIX + ipAddress;
        String ipRequestTimestamp = redisTemplate.opsForValue().get(ipRequestKey);
        if (ipRequestTimestamp != null) {
            long lastTime = Long.parseLong(ipRequestTimestamp);
            long currentTime = System.currentTimeMillis();
            long elapsedTime = currentTime - lastTime;
            if (elapsedTime < TimeUnit.MINUTES.toMillis(5)) {
                throw new Exception("Request frequency limit exceeded. Please try again later.");
            }
        }
    }

    // 生成并发送登录验证码（防止机器人操作）
    public String generateAndSendLoginVerificationCode(String email, String  ipAddress) throws Exception {
        // 检查IP登录请求频率
        checkIPLoginRequestFrequency(ipAddress);
        String loginVerificationCode = VerificationCodeUtils.generateLoginVerificationCode();
        // 将验证码保存到 Redis 缓存，有效期设置为 5 分钟
        redisTemplate.opsForValue().set("captcha_code", loginVerificationCode, 5, TimeUnit.MINUTES);
        // 记录IP登录请求次数
        incrementIPLoginRequestCount(ipAddress);
        emailService.sendEmailWithVerificationCode(email, "Your Login Verification Code", "Your login verification code is: " + loginVerificationCode);
        return loginVerificationCode;
    }

    private void checkIPLoginRequestFrequency(String ipAddress) throws Exception {
        String ipLoginRequestKey = IP_LOGIN_REQUEST_KEY_PREFIX + ipAddress;
        String requestCount = redisTemplate.opsForValue().get(ipLoginRequestKey);
        if (requestCount != null) {
            int count = Integer.parseInt(requestCount);
            if (count >= 15) {
                throw new Exception("Login verification code request limit exceeded. Please try again later.");
            }
        }
    }
    // 增加IP登录请求次数
    private void incrementIPLoginRequestCount(String ipAddress) {
        String ipLoginRequestKey = IP_LOGIN_REQUEST_KEY_PREFIX + ipAddress;
        redisTemplate.opsForValue().increment(ipLoginRequestKey, 1);
        // 设置过期时间为5分钟
        redisTemplate.expire(ipLoginRequestKey, 5, TimeUnit.MINUTES);
    }

    // 校验邀请码
    private void validateInvitationCode(String invitationCode) throws Exception {
        InvitationCode code = invitationCodeService.getByCode(invitationCode);
        if (code == null) {
            throw new Exception("Invalid invitation code");
        }
        if (code.isUsed()) {
            throw new Exception("Invitation code has already been used");
        }
    }
}
