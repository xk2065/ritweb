package com.yuwen.rtiweb.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

/**
 * @author Administrator
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("InvitationCode")
public class InvitationCode {

    @TableId("id")
    private Long id;

    @NotBlank(message = "Code cannot be blank")
    @TableField("code")
    private String code;

    @TableField("used")
    private boolean used;

    @TableField("create_time")
    private LocalDateTime createTime;

    @TableField("used_time")
    private LocalDateTime usedTime;
}
