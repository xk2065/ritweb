package com.yuwen.rtiweb.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @author Administrator
 */
@Data
@TableName("log_table")
public class Log {
    @TableId("id")
    private Long id;
    @TableField("content")
    private String content;
    @TableField("table_name")
    private String tableName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @TableField("create_time")
    private Date createTime;

    public String value() {
        return "";
    }
}
