package com.sakurabird.cangxi.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * (Goods)实体类
 *
 * @author vans
 * @since 2021-02-03 13:26:00
 */
@TableName("goods")
@Data
public class Goods implements Serializable {
    private static final long serialVersionUID = 581199331149063739L;
    /**
     * 物品id
     */
    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "物品id")
    private Integer id;
    /**
     * 物品名称
     */
    @ApiModelProperty(value = "物品名称")
    private String goodsName;
    /**
     * 物品评级
     */
    @ApiModelProperty(value = "物品评级")
    private String goodsRank;
    /**
     * 物品描述
     */
    @ApiModelProperty(value = "物品描述")
    private String goodsDesc;
    /**
     * 是否可以主动使用
     */
    @ApiModelProperty(value = "是否可以主动使用")
    private boolean canUse;

    /**
     * 是否可以被偷窃
     */
    @ApiModelProperty(value = "是否可以被偷窃")
    private boolean canThief;

}