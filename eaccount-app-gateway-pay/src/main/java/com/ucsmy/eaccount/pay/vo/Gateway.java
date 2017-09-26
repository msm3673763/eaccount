package com.ucsmy.eaccount.pay.vo;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

@Data
public class Gateway {
    @NotEmpty(message = "appId不能为空")
    private String appId;// pay_product_code
    @NotEmpty(message = "商品名称不能为空")
    private String productName;// product_name 商品名称
    @NotEmpty(message = "商户订单号不能为空")
    private String orderNo;// merchant_order_no 商户订单号
    private double totalAmount;// order_amount 平台交易金额
    @NotEmpty(message = "页面回调地址不能为空")
    private String returnUrl;// return_url 页面回调通知url
    @NotEmpty(message = "后台异步通知地址不能为空")
    private String notifyUrl;// notify_url 后台异步通知url
    @NotEmpty(message = "加密参数不能为空")
    private String mac;// 使用key加密防篡改
}
