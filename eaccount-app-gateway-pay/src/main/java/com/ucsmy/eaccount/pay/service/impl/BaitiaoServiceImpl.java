package com.ucsmy.eaccount.pay.service.impl;/*
 * Copyright (c) 2017 UCSMY.
 * All rights reserved.
 * Created on 2017/9/15

 * Contributors:
 *      - initial implementation
 */

import com.ucsmy.eaccount.pay.dao.PayBaitiaoMapper;
import com.ucsmy.eaccount.pay.service.BaitiaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 暂无描述
 *
 * @author ucs_gaokuixin
 * @since 2017/9/15
 */
@Service
public class BaitiaoServiceImpl  implements BaitiaoService {

    @Autowired
    PayBaitiaoMapper payBaitiaoMapper ;

    @Override
    public String getCreditLine(String accountNo) {
        return payBaitiaoMapper.getCreditLine(accountNo);
    }

}
