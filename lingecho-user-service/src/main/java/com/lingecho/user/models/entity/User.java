package com.lingecho.user.models.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lingecho.common.core.BaseEntity;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * User 实体类
 * @author Hibiscus-code-generate
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("users")
@EqualsAndHashCode(callSuper = true)
public class User extends BaseEntity implements Serializable, Cloneable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 邮箱
     */
    @TableField("email")
    private String email;

    /**
     * 密码
     */
    @TableField("password")
    private String password;

    /**
     * 手机号
     */
    @TableField("phone")
    private String phone;

    /**
     * 状态
     */
    @TableField("status")
    private Integer status;

    /**
     * 角色
     */
    @TableField("role")
    private String role;

    /**
     * 上次登录时间
     */
    @TableField("last_login")
    private LocalDateTime lastLogin;

    /**
     * 上次登录的IP
     */
    @TableField("last_login_ip")
    private String lastLoginIp;

    /**
     * 账号来源
     */
    @TableField("source")
    private String source;

    /**
     * 鉴权Token
     */
    @TableField("authToken")
    private String authToken;

    /**
     * 邮件验证
     */
    @TableField("email_verified")
    private Boolean emailVerified;

    /**
     * 手机验证
     */
    @TableField("phone_verified")
    private Boolean phoneVerified;

    /**
     * 双因素认证
     */
    @TableField("two_factor_enabled")
    private Boolean twoFactorEnabled;

    /**
     * 双因素认证密钥
     */
    @TableField("two_factor_secret")
    private Boolean twoFactorSecret;

    /**
     * 邮件验证Token
     */
    @TableField("email_verify_token")
    private Boolean emailVerifyToken;

    /**
     * 手机验证Token
     */
    @TableField("phone_verify_token")
    private Boolean phoneVerifyToken;

    /**
     * 密码充值Token
     */
    @TableField("password_reset_token")
    private Boolean passwordResetToken;

    /**
     * 密码充值Token
     */
    @TableField("password_reset_expires")
    private LocalDateTime passwordResetExpires;

    /**
     * 邮箱验证过期时间
     */
    @TableField("email_verify_expires")
    private LocalDateTime emailVerifyExpires;

    /**
     * 登录次数
     */
    @TableField("login_count")
    private Integer loginCount;

    /**
     * 上次更改密码时间
     */
    @TableField("last_password_change")
    private LocalDateTime lastPasswordChange;

    /**
     * 请求注销时间
     */
    @TableField("account_deletion_request_at")
    private LocalDateTime accountDeletionRequestedAt;

    /**
     * 彻底注销时间
     */
    @TableField("account_deletion_effective_at")
    private LocalDateTime accountDeletionEffectiveAt;

    /**
     * 区域
     */
    @TableField("preferredLocale")
    private String preferredLocale;

    /**
     * 偏好时区
     */
    @TableField("preferredTimezone")
    private String preferredTimezone;

    /**
     * 主题模式
     */
    @TableField("theme_mode")
    private String themeMode;

    /**
     * WechatOpenID
     */
    @TableField("wechat_open_id")
    private String wechatOpenID;

    /**
     * WechatUnion
     */
    @TableField("wechat_union_id")
    private String wechat_union_id;

    /**
     * GithubID
     */
    @TableField("github_id")
    private String githubID;

    /**
     * Github登录
     */
    @TableField("github_login")
    private String githubLogin;

    @Override
    public User clone() {
        try {
            return (User) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
