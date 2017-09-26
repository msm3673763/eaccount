package com.ucsmy.eaccount.pay.entity;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.sql.Timestamp;

@Data
public class PaymentOrder {
    private String id;
    private String productName;// 商品名称
    private String merchantOrderNo;// 商户订单号
    private double orderAmount;// 平台交易金额
    private String merchantName;// 商家名称
    private String merchantNo;// 商家编号
    private Timestamp orderTime;// 下单时间
    private Date orderDate;// 下单日期
    private String orderIp;// 下单ip
    private String returnUrl;// 页面回调通知url
    private String notifyUrl;// 后台异步通知url
    private String payWayCode;
    private String payWayName;
    private String trxNo;// 平台流水号
    private String status;
    private Timestamp createTime;

    public PaymentOrder() {
    }

    @Builder
    public PaymentOrder(String id, String productName, String merchantOrderNo, double orderAmount, String merchantName, String merchantNo, Timestamp orderTime, Date orderDate, String orderIp, String returnUrl, String notifyUrl, String payWayCode, String payWayName, String trxNo, String status, Timestamp createTime) {
        this.id = id;
        this.productName = productName;
        this.merchantOrderNo = merchantOrderNo;
        this.orderAmount = orderAmount;
        this.merchantName = merchantName;
        this.merchantNo = merchantNo;
        this.orderTime = orderTime;
        this.orderDate = orderDate;
        this.orderIp = orderIp;
        this.returnUrl = returnUrl;
        this.notifyUrl = notifyUrl;
        this.payWayCode = payWayCode;
        this.payWayName = payWayName;
        this.trxNo = trxNo;
        this.status = status;
        this.createTime = createTime;
    }
}
