package com.lingecho.user.models.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lingecho.common.core.BaseEntity;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

/**
 * User 实体类
 * @author Hibiscus-code-generate
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("user_profiles")
@EqualsAndHashCode(callSuper = true)
public class UserProfile extends BaseEntity implements Serializable, Cloneable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 名字
     */
    private String firstName;

    /**
     * 姓氏
     */
    private String lastName;

    /**
     * 显示名称
     */
    private String displayName;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 邮件通知
     */
    private Boolean emailNotifications;

    /**
     * 推送通知
     */
    private Boolean pushNotification;

    /**
     * 完成度
     */
    private int profileComplete;

    /**
     * 性别
     */
    private String gender;

    /**
     * 城市
     */
    private String city;

    @Override
    public UserProfile clone() {
        try {
            return (UserProfile) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
