package com.yuwen.rtiweb.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * @author Administrator
 */
@Service
public class EmailService {

    private final JavaMailSender javaMailSender;
    private final SpringTemplateEngine templateEngine;

    @Value("${spring.mail.username}")
    private String senderEmail;

    public EmailService(JavaMailSender javaMailSender, SpringTemplateEngine templateEngine) {
        this.javaMailSender = javaMailSender;
        this.templateEngine = templateEngine;
    }

    public void sendEmailWithVerificationCode(String to, String subject, String verificationCode) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");

        try {
            helper.setTo(to);
            helper.setFrom(senderEmail);
            helper.setSubject(subject);

            // 使用 Thymeleaf 模板引擎填充 HTML 内容中的占位符
            String htmlContent = getHtmlContentWithVerificationCode(verificationCode);
            helper.setText(htmlContent, true);

            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            // 处理邮件发送异常
            e.printStackTrace();
        }
    }

    private String getHtmlContentWithVerificationCode(String verificationCode) {
        // 创建 Thymeleaf 上下文
        Context context = new Context();
        context.setVariable("verificationCode", verificationCode);

        // 使用 Thymeleaf 模板引擎渲染 HTML 内容
        return templateEngine.process("emailTemplate", context);
    }
}
