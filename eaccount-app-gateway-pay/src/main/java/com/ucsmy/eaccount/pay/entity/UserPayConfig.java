package com.ucsmy.eaccount.pay.entity;

import lombok.Data;

@Data
public class UserPayConfig {
    private String id;
    private String appId;
    private String productName;
    private String userNo;
    private String payKey;
    private String paySecret;
    private String status;
}
