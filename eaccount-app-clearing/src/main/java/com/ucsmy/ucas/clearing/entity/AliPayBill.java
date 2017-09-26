package com.ucsmy.ucas.clearing.entity;/*
 * Copyright (c) 2017 UCSMY.
 * All rights reserved.
 * Created on 2017/9/19

 * Contributors:
 *      - initial implementation
 */

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 支付宝账单Bean
 *
 * @author ucs_masiming
 * @since 2017/9/19
 */
@Data
public class AliPayBill {

    private String tradeNo;//支付宝交易号
    private String orderNo;//商户订单号
    private String businessType;//业务类型
    private String productName;//商品名称
    private Date createTime;//创建时间
    private Date endTime;//完成时间
    private String storeNo;//门店编号
    private String storeName;//门店名称
    private String operator;//操作员
    private String clientNo;//终端号
    private String counterpartAccount;//对方账户
    private BigDecimal orderAmount;//订单金额
    private BigDecimal merchantIncome;//商家实收
    private BigDecimal alipayRedPacket;//支付宝红包
    private BigDecimal jfTreasure;//集分宝
    private BigDecimal alipayOffer;//支付宝优惠
    private BigDecimal merchantOffer;//商家优惠
    private BigDecimal volume;//卷核销金额
    private String volumeName;//卷名称
    private BigDecimal merchantRed;//商家红包消费金额
    private BigDecimal cardPayAmount;//卡消费金额
    private String refundBatchNo;//退款批次号
    private BigDecimal serviceCharge;//服务费
    private BigDecimal subRun;//分润
    private String remark;//备注
}
