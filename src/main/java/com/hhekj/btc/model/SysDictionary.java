package com.hhekj.btc.model;

import lombok.Data;

/**
 * anther : hux
 * datetime : 2019/11/8 14:21
 * description : 系统配置
 */
@Data
public class SysDictionary {
    private Integer id;

    /**
     * 名称
     */
    private String name;

    /**
     * 值
     */
    private String value;

    /**
     * 备注
     */
    private String description;

}