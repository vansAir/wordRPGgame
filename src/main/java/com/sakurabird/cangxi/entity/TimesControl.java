package com.sakurabird.cangxi.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * (LuckDraw)实体类
 *
 * @author vans
 * @since 2021-02-03 13:53:53
 */
@TableName("times_control")
@Data
public class TimesControl implements Serializable {
    private static final long serialVersionUID = -36647829234203621L;
    /**
     * 抽奖次数id
     */
    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "抽奖次数id")
    private Integer id;
    /**
     * 用户id
     */
    @ApiModelProperty(value = "用户id")
    private Integer userId;
    /**
     * 剩余抽奖次数
     */
    @ApiModelProperty(value = "剩余抽奖次数")
    private Integer useTimes;
    /**
     * 剩余抽奖次数
     */
    @ApiModelProperty(value = "次数类别")
    private String useType;


}