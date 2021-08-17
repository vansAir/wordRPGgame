package com.sakurabird.cangxi.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * (Users)实体类
 *
 * @author vans
 * @since 2021-02-01 15:55:01
 */
@TableName("users")
@Data
public class Users implements Serializable {
    private static final long serialVersionUID = -41140902414718168L;
    /**
     * 用户id
     */
    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "用户id")
    private Integer id;
    /**
     * 用户名
     */
    @ApiModelProperty(value = "用户名")
    private String userName;
    /**
     * 密码
     */
    @ApiModelProperty(value = "密码")
    private String userPassword;
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
    /**
     * 出生地
     */
    @ApiModelProperty(value = "出生地")
    private String area;
    /**
     * 头盔
     */
    @ApiModelProperty(value = "头盔")
    private String head;
    /**
     * 衣服
     */
    @ApiModelProperty(value = "衣服")
    private String body;
    /**
     * 武器
     */
    @ApiModelProperty(value = "武器")
    private String arms;
    /**
     * 鞋子
     */
    @ApiModelProperty(value = "鞋子")
    private String shoes;
    /**
     * 饰品
     */
    @ApiModelProperty(value = "饰品")
    private String ornaments;
    /**
     * 姓名
     */
    @ApiModelProperty(value = "姓名")
    private String realName;
    /**
     * 前缀
     */
    @ApiModelProperty(value = "前缀")
    private String prefixName;
    /**
     * 技能
     */
    @ApiModelProperty(value = "技能")
    private String skill;
    /**
     * 天命
     */
    @ApiModelProperty(value = "天命")
    private String fate;

    /**
     * 天命描述
     */
    @TableField(exist=false)
    private String fateDesc;

    /**
     * 偷窃物品文本
     */
    @TableField(exist=false)
    private StringBuffer thiefThing;

    /**
     * 用户装备详情
     */
    @TableField(exist=false)
    private List<UserEquipment> equs;

}