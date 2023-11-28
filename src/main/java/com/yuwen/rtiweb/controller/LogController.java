package com.yuwen.rtiweb.controller;

import com.alibaba.fastjson2.JSONObject;
import com.yuwen.rtiweb.entity.Log;
import com.yuwen.rtiweb.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Administrator
 */
@RestController
@RequestMapping("/admin/logs")
public class LogController {
    @Autowired
    private LogService logService;

    @GetMapping("/categories")
    public JSONObject getLogCategories() {
        List<String> tableNames = logService.getLogTableNames();
        JSONObject result = new JSONObject();
        for (String tableName : tableNames) {
            List<Log> logs = logService.getLogsByTableName(tableName);
            result.put(tableName, logs);
        }
        return result;
    }
    @GetMapping("/{tableName}")
    public List<Log> getLogsByTableName(@PathVariable String tableName) {
        return logService.getLogsByTableName(tableName);
    }

}
