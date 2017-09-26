package com.ucsmy.eaccount.pay.entity;

import lombok.Data;

@Data
public class PayWay {
    private String id;
    private String payWayCode;
    private String payWayName;
    private String payTypeCode;
    private String payTypeName;
    private String appId;
    private String status;
    private int sorts;
    private double payRate;
}
