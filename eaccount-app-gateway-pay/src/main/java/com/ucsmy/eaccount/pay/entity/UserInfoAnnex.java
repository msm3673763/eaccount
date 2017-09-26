package com.ucsmy.eaccount.pay.entity;

import com.ucsmy.core.bean.BaseNode;
import lombok.Data;

@Data
public class UserInfoAnnex extends BaseNode {
    private String userNo;// 用户编号
    private String idcardNo;// 身份证号
    private String userName;// 用户名
    private String mobile;// 手机号
}