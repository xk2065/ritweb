package com.yuwen.rtiweb.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Administrator
 */
@Data
@TableName("rituser")
@AllArgsConstructor
@NoArgsConstructor
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "用户id", example = "1")
    @TableField("id")
    private Integer id;

    @ApiModelProperty(value = "用户名", example = "yuwen")
    @TableField("user_name")
    private String userName;

    @ApiModelProperty(value = "用户密码", example = "123456")
    @TableField("user_pwd")
    private String userPwd;

    @ApiModelProperty(value = "用户邮箱", example = "yuwen@163.com")
    @TableField("user_email")
    private String userEmail;

    @ApiModelProperty(value = "游戏名称", example = "yule")
    @TableField("game_name")
    private String gameName;

    @ApiModelProperty(value = "角色名称", example = "管理员")
    @TableField(exist = false)
    private List<Role> roles;

    @ApiModelProperty(value = "permission", example = "1")
    @TableField(exist = false)
    private List<Permission> permissions;
    @ApiModelProperty(value = "头像", example = "avatar.jpg")
   @TableField(value = "avatar")
    private String avatar;

    @ApiModelProperty(value = "个人介绍", example = "I love gaming!")
   @TableField(value = "personal_introduction")
    private String personalIntroduction;

    @ApiModelProperty(value = "年龄", example = "25")
   @TableField(value = "age")
    private Integer age;

    @ApiModelProperty(value = "组织", example = "Gaming Guild")
   @TableField(value = "organization")
    private String organization;

    @ApiModelProperty(value = "经验", example = "5000")
   @TableField(value = "experience")
    private Integer experience;

    @ApiModelProperty(value = "等级", example = "5")
   @TableField(value = "level")
    private Integer level;

    @ApiModelProperty(value = "贡献度", example = "100")
   @TableField(value = "contribution")
    private Integer contribution;

    public Integer id() {
        return id;
    }

    public String userName() {
        return userName;
    }

    public String userPwd() {
        return userPwd;
    }

    public String userEmail() {
        return userEmail;
    }

    public String gameName() {
        return gameName;
    }

    public List<Role> roles() {
        return roles;
    }

    public List<Permission> permissions() {
        return permissions;
    }

    public String avatar() {
        return avatar;
    }

    public String personalIntroduction() {
        return personalIntroduction;
    }

    public Integer age() {
        return age;
    }

    public String organization() {
        return organization;
    }

    public Integer experience() {
        return experience;
    }

    public Integer level() {
        return level;
    }

    public Integer contribution() {
        return contribution;
    }

    public User setId(Integer id) {
        this.id = id;
        return this;
    }


    public User setUserPwd(String userPwd) {
        this.userPwd = userPwd;
        return this;
    }

    public User setUserEmail(String userEmail) {
        this.userEmail = userEmail;
        return this;
    }

    public User setGameName(String gameName) {
        this.gameName = gameName;
        return this;
    }

    public User setRoles(List<Role> roles) {
        this.roles = roles;
        return this;
    }

    public User setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
        return this;
    }

    public User setAvatar(String avatar) {
        this.avatar = avatar;
        return this;
    }

    public User setPersonalIntroduction(String personalIntroduction) {
        this.personalIntroduction = personalIntroduction;
        return this;
    }

    public User setAge(Integer age) {
        this.age = age;
        return this;
    }

    public User setOrganization(String organization) {
        this.organization = organization;
        return this;
    }

    public User setExperience(Integer experience) {
        this.experience = experience;
        return this;
    }

    public User setLevel(Integer level) {
        this.level = level;
        return this;
    }

    public User setContribution(Integer contribution) {
        this.contribution = contribution;
        return this;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // 将用户拥有的角色转换为 GrantedAuthority 对象
        List<GrantedAuthority> authorities = roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName()))
                .collect(Collectors.toList());

        // 将用户拥有的权限转换为 GrantedAuthority 对象
        authorities.addAll(permissions.stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getName()))
                .collect(Collectors.toList()));

        return authorities;
    }


    @Override
    public String getPassword() {
        return userPwd;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // 根据实际需求进行调整
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // 根据实际需求进行调整
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // 根据实际需求进行调整
    }

    @Override
    public boolean isEnabled() {
        return true; // 根据实际需求进行调整
    }
}
