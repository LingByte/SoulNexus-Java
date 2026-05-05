package com.lingecho.user.models.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lingecho.common.core.BaseEntity;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

/**
 * Role 实体类
 * @author Hibiscus-code-generate
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("roles")
@EqualsAndHashCode(callSuper = true)
public class Role  extends BaseEntity implements Serializable, Cloneable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 角色名
     */
    @TableField("name")
    private String name;

    /**
     * 摘要
     */
    @TableField("slug")
    private String slug;

    /**
     * 是否系统内置
     */
    @TableField("description")
    private String description;

    /**
     * 是否是系统创建
     */
    @TableField("is_system")
    private Boolean isSystem;
}
