package com.yuwen.rtiweb.service;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.yuwen.rtiweb.annotation.LogAnnotation;
import com.yuwen.rtiweb.entity.Log;
import com.yuwen.rtiweb.mapper.LogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author Administrator
 */
@LogAnnotation("日志服务")
@Service
public class LogService {
    @Autowired
    private LogMapper logMapper;

    public void saveLog(Log log){
        logMapper.insert(log);
    }
    public List<Log> getLogsByTimeRange(Date startTime, Date endTime){
        return logMapper.selectList(
                Wrappers.<Log>lambdaQuery()
                        .ge(Log::getCreateTime, startTime)
                        .le(Log::getCreateTime, endTime)
        );
    }
    public List<String> getLogTableNames() {
        return logMapper.getLogTableNames();
    }
    public List<Log> getLogsByTableName(String tableName){
        return logMapper.selectList(
                Wrappers.<Log>lambdaQuery()
                        .eq(Log::getTableName, tableName)
        );
    }
}
