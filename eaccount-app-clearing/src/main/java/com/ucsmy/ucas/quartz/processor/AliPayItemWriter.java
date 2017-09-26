package com.ucsmy.ucas.quartz.processor;/*
 * Copyright (c) 2017 UCSMY.
 * All rights reserved.
 * Created on 2017/9/19

 * Contributors:
 *      - initial implementation
 */

import com.ucsmy.ucas.clearing.entity.AliPayBill;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 支付宝账单处理writer类
 *
 * @author ucs_masiming
 * @since 2017/9/19
 */
@Component
public class AliPayItemWriter implements ItemWriter<AliPayBill> {

    @Override
    public void write(List<? extends AliPayBill> list) throws Exception {

    }
}
