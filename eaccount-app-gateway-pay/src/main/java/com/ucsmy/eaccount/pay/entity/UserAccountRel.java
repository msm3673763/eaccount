package com.ucsmy.eaccount.pay.entity;

import com.ucsmy.core.bean.BaseNode;
import lombok.Builder;
import lombok.Data;

@Data
public class UserAccountRel extends BaseNode {
    private String accountType;// 账户类型
    private String accountNo;// 账户编号
    private String userNo;// 用户编号
}