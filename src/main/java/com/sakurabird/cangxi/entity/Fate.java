package com.sakurabird.cangxi.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * (Fate)实体类
 *
 * @author vans
 * @since 2021-02-01 16:38:02
 */
@Data
public class Fate implements Serializable {
    private static final long serialVersionUID = -43297474741054143L;
    /**
     * 天命id
     */
    @ApiModelProperty(value = "天命id")
    private String id;
    /**
     * 天命名称
     */
    @ApiModelProperty(value = "天命名称")
    private String fateName;
    /**
     * 天命描述
     */
    @ApiModelProperty(value = "天命名称")
    private String fateDesc;

}