package com.ucsmy.eaccount.pay.dao;/*
 * Copyright (c) 2017 UCSMY.
 * All rights reserved.
 * Created on 2017/9/15

 * Contributors:
 *      - initial implementation
 */

import org.apache.ibatis.annotations.Mapper;

/**
 * 暂无描述
 *
 * @author ucs_gaokuixin
 * @since 2017/9/15
 */
@Mapper
public interface PayBaitiaoMapper {
    String getCreditLine(String accountNo);
}
