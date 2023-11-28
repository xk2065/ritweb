package com.yuwen.rtiweb.service;

import com.yuwen.rtiweb.entity.InvitationCode;
import com.yuwen.rtiweb.mapper.InvitationCodeMapper;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

/**
 * @author Administrator
 */
@Service
public class InvitationCodeService {

    private static final String CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    private final InvitationCodeMapper invitationCodeMapper;

    @Autowired
    public InvitationCodeService(InvitationCodeMapper invitationCodeMapper) {
        this.invitationCodeMapper = invitationCodeMapper;
    }

    public InvitationCode generateInvitationCode() {
        String generatedCode = generateRandomCode();

        InvitationCode invitationCode = new InvitationCode();
        invitationCode.setCode(generatedCode);
        invitationCode.setUsed(false);
        invitationCode.setCreateTime(LocalDateTime.now());

        invitationCodeMapper.insert(invitationCode);

        return invitationCode;
    }

    @NotNull
    private String generateRandomCode() {
        Random random = new Random();
        StringBuilder codeBuilder = new StringBuilder();

        // 生成六位邀请码
        for (int i = 0; i < 6; i++) {
            char randomChar = CHARACTERS.charAt(random.nextInt(CHARACTERS.length()));

            // 检查相邻字符是否相同，如果相同重新生成
            if (i > 0 && randomChar == codeBuilder.charAt(i - 1)) {
                i--;
            } else {
                codeBuilder.append(randomChar);
            }
        }

        return codeBuilder.toString();
    }

    public InvitationCode getByCode(String code) {
        return invitationCodeMapper.findByCode(code);
    }

    public void markCodeAsUsed(@NotNull InvitationCode code) {
        code.setUsed(true);
        code.setUsedTime(LocalDateTime.now());
        invitationCodeMapper.updateById(code);
    }

    public List<InvitationCode> getAllUnusedCodes() {
        return invitationCodeMapper.findByUsed(false);
    }
}
