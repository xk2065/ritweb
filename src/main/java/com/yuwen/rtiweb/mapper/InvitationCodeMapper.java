package com.yuwen.rtiweb.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yuwen.rtiweb.entity.InvitationCode;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Administrator
 */
@Repository
public interface InvitationCodeMapper extends BaseMapper<InvitationCode> {
    InvitationCode findByCode(String code);
    List<InvitationCode> findByUsed(boolean used);
}
