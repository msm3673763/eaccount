package com.ucsmy.ucas.clearing.entity;/*
 * Copyright (c) 2017 UCSMY.
 * All rights reserved.
 * Created on 2017/9/18

 * Contributors:
 *      - initial implementation
 */

import com.ucsmy.core.bean.BaseNode;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 对账批次表
 *
 * @author ucs_masiming
 * @since 2017/9/18
 */
@Data
public class EcCheckBatch extends BaseNode {

    private String id;
    private int version;
    private Date createTime;
    private String editor;
    private String creater;
    private Date editTime;
    private String status;
    private String remark;
    private String accountCheckBatchNo;

    // 账单时间(账单交易发生时间)
    private String billDate;

    // 账单类型(默认全部是交易成功)
    private String billType;

    // 批次处理状态, 已处理, 未处理
    private String handleStatus;

    // 银行类型 WEIXIN ALIPAY
    private String bankType;

    // 所有差错总单数
    private int mistakeCount;

    // 待处理的差错总单数
    private int unhandleMistakeCount;

    // 平台总交易单数
    private Integer tradeCount;

    // 银行总交易单数
    private int bankTradeCount;

    // 平台交易总金额
    private BigDecimal tradeAmount;

    // 银行交易总金额
    private BigDecimal bankTradeAmount;

    // 平台退款总金额
    private BigDecimal refundAmount;

    // 银行退款总金额
    private BigDecimal bankRefundAmount;

    // 银行总手续费, 单位元
    private BigDecimal bankFee;

    // 原始对账文件存放地址
    private String orgCheckFilePath;

    // 解析后文件存放地址
    private String releaseCheckFilePath;

    // 解析状态
    private String releaseStatus;

    /** 解析检查失败的描述信息 */
    private String checkFailMsg;

    /** 银行返回的错误信息 */
    private String bankErrMsg;
}
