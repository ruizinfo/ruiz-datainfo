package io.ruiz.modules.base.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId
    private Long id;

    // 创建者
    @TableField(fill = FieldFill.INSERT)
    private Integer creator;

    // 创建时间
    @TableField(fill = FieldFill.INSERT)
    private Date createDate;

    @TableField(fill = FieldFill.UPDATE)
    private Integer updater;

    @TableField(fill = FieldFill.UPDATE)
    private Date updateDate;

    @JsonIgnore
    @TableLogic
    private int delFlag;
}
