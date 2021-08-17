package com.sakurabird.cangxi.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * (UserEquipment)实体类
 *
 * @author vans
 * @since 2021-02-01 16:31:05
 */
@TableName("user_equipment")
@Data
public class UserEquipment implements Serializable {
    private static final long serialVersionUID = -78522066338699061L;
    /**
     * 装备id
     */
    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "装备id")
    private Integer id;
    /**
     * 装备名称
     */
    @ApiModelProperty(value = "装备名称")
    private String equipmentName;
    /**
     * 攻击力
     */
    @ApiModelProperty(value = "攻击力")
    private Double attack;
    /**
     * 防御力
     */
    @ApiModelProperty(value = "防御力")
    private Double defense;
    /**
     * 幸运值
     */
    @ApiModelProperty(value = "幸运值")
    private Double lucky;
    /**
     * 速度
     */
    @ApiModelProperty(value = "速度")
    private Double speed;
    /**
     * 生命值
     */
    @ApiModelProperty(value = "生命值")
    private Double health;
    /**
     * 金币
     */
    @ApiModelProperty(value = "金币")
    private Double money;
    /**
     * 即死几率
     */
    @ApiModelProperty(value = "即死几率")
    private Double killProbability;
    /**
     * 反击几率
     */
    @ApiModelProperty(value = "反击几率")
    private Double strikeBack;
    /**
     * 连击几率
     */
    @ApiModelProperty(value = "连击几率")
    private Double doubleHit;
    /**
     * 暴击几率
     */
    @ApiModelProperty(value = "暴击几率")
    private Double criticalHit;
    /**
     * 暴击伤害
     */
    @ApiModelProperty(value = "暴击伤害")
    private Double criticalDmg;
    /**
     * 用户id
     */
    @ApiModelProperty(value = "用户id")
    private Integer userId;
    /**
     * 装备评级
     */
    @ApiModelProperty(value = "装备评级")
    private String equRank;
    /**
     * 强化等级
     */
    @ApiModelProperty(value = "强化等级")
    private Integer level;
    /**
     * 所属类别
     */
    @ApiModelProperty(value = "所属类别")
    private Integer equType;
    /**
     * 装备描述
     */
    @ApiModelProperty(value = "装备描述")
    private String equDescribe;
    /**
     * 偷窃率
     */
    @ApiModelProperty(value = "偷窃率")
    private Double thief;
    /**
     * 必中概率
     */
    @ApiModelProperty(value = "必中概率")
    private Double attackProbability;
    /**
     * 闪避率
     */
    @ApiModelProperty(value = "闪避率")
    private Double missProbability;

}