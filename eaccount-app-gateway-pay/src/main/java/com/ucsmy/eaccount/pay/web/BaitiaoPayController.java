package com.ucsmy.eaccount.pay.web;/*
 * Copyright (c) 2017 UCSMY.
 * All rights reserved.
 * Created on 2017/9/15

 * Contributors:
 *      - initial implementation
 */

import com.ucsmy.core.exception.ServiceException;
import com.ucsmy.core.vo.RetMsg;
import com.ucsmy.eaccount.pay.service.BaitiaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 暂无描述
 *
 * @author ucs_gaokuixin
 * @since 2017/9/15
 */
@RestController
@RequestMapping(value = "baitiaoPay")
public class BaitiaoPayController {
    @Autowired
    BaitiaoService baitiaoService;

    /**
     * 返回授信额度
     * @param accountNo
     */
    @GetMapping("getCreditLine")
    public RetMsg getCreditLine(String accountNo) {
        if(StringUtils.isEmpty(accountNo)){
            throw new ServiceException("请求账户编号不能为空");
        }
        String creditLine = baitiaoService.getCreditLine(accountNo);
       return  RetMsg.success(creditLine);
    }

}
