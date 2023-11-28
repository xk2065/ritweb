package com.yuwen.rtiweb.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yuwen.rtiweb.entity.Log;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Administrator
 */
@Repository
public interface LogMapper extends BaseMapper<Log> {
    List<String> getLogTableNames();
}
