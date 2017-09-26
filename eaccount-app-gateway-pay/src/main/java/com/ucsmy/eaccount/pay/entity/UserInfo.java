package com.ucsmy.eaccount.pay.entity;

import com.ucsmy.core.bean.BaseNode;
import lombok.Data;

@Data
public class UserInfo extends BaseNode {
    private String userType;// 用户类型

    private String salt;// 加盐

    private String payPwd;// 支付密码

    private String status;// 状态

    private String loginName;// 登录名

    private String userName;// 用户姓名

    private String accountNo;// 账户编号

    private String password;// 登录密码

    private String userNo;// 用户编号

    private java.util.Date createTime;// 创建时间
}