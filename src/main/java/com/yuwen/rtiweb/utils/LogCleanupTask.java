package com.yuwen.rtiweb.utils;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.yuwen.rtiweb.entity.Log;
import com.yuwen.rtiweb.mapper.LogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author Administrator
 */
@Component
public class LogCleanupTask {
    @Autowired
    private LogMapper logMapper;

    @Scheduled(cron = "0 0 0 * * ?") // 每天凌晨执行一次
    public void cleanupLogs() {
        // 删除七天前的日志记录
        Date sevenDaysAgo = new Date(System.currentTimeMillis() - 7 * 24 * 60 * 60 * 1000);
        logMapper.delete(Wrappers.<Log>lambdaQuery().lt(Log::getCreateTime, sevenDaysAgo));
    }
}
