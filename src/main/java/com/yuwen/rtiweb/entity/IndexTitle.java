package com.yuwen.rtiweb.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author Administrator
 */
@Data
@TableName("indextitle")
public class IndexTitle {
    @TableId("id")
    private Long id;
    @TableField("titlelist")
    private  String titlelist;
}
