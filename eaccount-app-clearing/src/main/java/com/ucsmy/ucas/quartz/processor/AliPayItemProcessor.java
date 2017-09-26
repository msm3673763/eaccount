package com.ucsmy.ucas.quartz.processor;/*
 * Copyright (c) 2017 UCSMY.
 * All rights reserved.
 * Created on 2017/9/19

 * Contributors:
 *      - initial implementation
 */

import com.ucsmy.ucas.clearing.entity.AliPayBill;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

/**
 * 支付宝账单processor
 *
 * @author ucs_masiming
 * @since 2017/9/19
 */
@Component
public class AliPayItemProcessor implements ItemProcessor<AliPayBill, AliPayBill> {

    @Override
    public AliPayBill process(AliPayBill aliPayBill) throws Exception {
        String productName = aliPayBill.getProductName();
        System.out.println(productName);
        return null;
    }
}
