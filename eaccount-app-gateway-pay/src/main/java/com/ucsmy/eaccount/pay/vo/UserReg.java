package com.ucsmy.eaccount.pay.vo;

import com.ucsmy.eaccount.pay.service.impl.UserServiceImpl;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

@Data
public class UserReg {
    @NotEmpty(message = "身份证号不能为空")
    private String idcardNo;// 身份证号
    @NotEmpty(message = "支付密码不能为空")
    private String payPwd;// 支付密码
    private String payPwdConfirm;// 二次确认支付密码
    @NotEmpty(message = "安全码不能为空")
    private String securityCode;// 安全码
    private String userName;// 用户名
    private UserServiceImpl.AccountTypeEnum type;// 账户类型
}