package com.sakurabird.cangxi.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * (BackPackage)实体类
 *
 * @author vans
 * @since 2021-02-03 13:25:52
 */
@TableName("back_package")
@Data
public class BackPackage implements Serializable {
    private static final long serialVersionUID = 661553506759461889L;
    /**
     * 背包内物品id
     */
    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "背包内物品id")
    private Integer id;
    /**
     * 物品对应用户id
     */
    @ApiModelProperty(value = "物品对应用户id")
    private Integer userId;
    /**
     * 物品id
     */
    @ApiModelProperty(value = "物品id")
    private Integer goodsId;
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
     * 物品数量
     */
    @ApiModelProperty(value = "物品数量")
    private Integer nums;
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