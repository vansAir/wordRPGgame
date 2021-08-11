package com.sakurabird.cangxi.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * (Equipment)实体类
 *
 * @author vans
 * @since 2021-02-01 16:31:25
 */
@TableName("equipment")
@Data
public class Equipment implements Serializable {
    private static final long serialVersionUID = 301182702269803993L;
    /**
     * 装备id
     */
    @ApiModelProperty(value = "装备id")
    private String id;
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
     * 装备评级
     */
    @ApiModelProperty(value = "装备评级")
    private String equRank;
    /**
     * 所属类别  1-头盔  2-衣服 3-鞋子 4-武器 5-饰品
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