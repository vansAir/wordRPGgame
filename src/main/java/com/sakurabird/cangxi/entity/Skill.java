package com.sakurabird.cangxi.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * (Skill)实体类
 *
 * @author vans
 * @since 2021-02-01 16:31:16
 */
@TableName("skill")
@Data
public class Skill implements Serializable {
    private static final long serialVersionUID = 525196525054121931L;
    /**
     * 技能id
     */
    @ApiModelProperty(value = "技能id")
    private String id;
    /**
     * 技能名称
     */
    @ApiModelProperty(value = "技能名称")
    private String skillName;
    /**
     * 技能描述
     */
    @ApiModelProperty(value = "技能描述")
    private String skillDesc;
    /**
     * 技能评级
     */
    @ApiModelProperty(value = "技能评级")
    private String skillRank;

}