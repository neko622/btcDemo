package com.hhekj.btc.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;



@TableName("user_message")
@Data
public class User {

    // 用户id
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @JsonIgnore
    // 用户账号类型 0：普通账号 1：初始账号(主账号)
    private Integer auth;
    // 上级id
    private Integer pid;
    // 头像
    private String avatar;
    // 用户名
    private String username;
    //用户昵称
    private String nickname;
    // 平台id
    private String idCard;
    // 盐
    @JsonIgnore
    private String salt;
    // 密码
    @JsonIgnore
    private String password;
    // 手机号
    private String phonenum;
    // 邮箱
    private String eMail;
    // 注册时间
    private String createTime;
    // 身份认证 0:未认证 1:提交资料,审核中 2：审核通过 3：审核不通过
    private Integer identityAuthentication;
    // 认证不通过备注
    private String identityRemark;
    // 身份证正面
    private String cardObverse;
    // 身份证反面
    private String cardReverse;
    // 国家/地区
    private String address;
    // 真实姓名
    private String realname;
    // 身份证号
    private String idNumber;
    // 资金密码
    @JsonIgnore
    private String moneyPassword;
    // 邀请码
    private String inviteCode;
    // 谷歌验证是否已经绑定 1:已经绑定 0：未绑定
    private Integer googleStatus;
    // 谷歌验证秘钥
    private String googleSecret;
    // 谷歌验证qrCode
    private String googleQrCode;
    // 状态 1：正常 2：禁用
    @JsonIgnore
    private Integer status;
    // 是否删除 1：删除
    @JsonIgnore
    private Integer isdel;

    // ==========非数据库字段集========= //

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @TableField(exist = false)
    private String token;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @TableField(exist = false)
    private String download;



    @JsonInclude(JsonInclude.Include.NON_NULL)
    @TableField(exist = false)
    private String paymentTypes;

    // 是否设定过资金密码
    @TableField(exist = false)
    private Integer moneyPasswordSet;

}
