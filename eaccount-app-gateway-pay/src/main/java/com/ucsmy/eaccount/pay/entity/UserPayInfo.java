package com.ucsmy.eaccount.pay.entity;

import lombok.Data;

@Data
public class UserPayInfo {
    private String id;
    private String appId;
    private String appSectet;
    private String merchantId;
    private String userNo;
    private String partnerKey;// 商户key
    private String payWayCode;
    private String rsaPrivateKey;
    private String rsaPublicKey;
}
