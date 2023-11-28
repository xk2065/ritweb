package com.yuwen.rtiweb.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yuwen.rtiweb.entity.Permission;
import com.yuwen.rtiweb.entity.Role;
import com.yuwen.rtiweb.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Administrator
 */
@Repository
public interface UserMapper extends BaseMapper<User> {
    User findByUsername(String username);
    List<Role> findRolesByUserName(String username);
    List<Permission> findPermissionsByUserName(String username);
}
