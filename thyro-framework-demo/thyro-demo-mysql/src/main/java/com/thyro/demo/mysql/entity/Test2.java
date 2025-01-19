package com.thyro.demo.mysql.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.thyro.framework.mysql.BaseEntity;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 测试2表
 * </p>
 *
 * @since 2025-01-18
 */
@Getter
@Setter
public class Test2 extends BaseEntity {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 名称
     */
    private String name;

    /**
     * 性别 0:男 1:女 2:其他
     */
    private Integer sex;

    /**
     * 删除标识 1:是 0:否
     */
    private Integer isDeleted;

    /**
     * 创建人id
     */
    private Long createUser;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 创建人id
     */
    private Long updateUser;

    /**
     * 创建时间
     */
    private LocalDateTime updateTime;
}
