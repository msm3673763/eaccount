package com.ucsmy.ucas.clearing.service.impl;/*
 * Copyright (c) 2017 UCSMY.
 * All rights reserved.
 * Created on 2017/9/18

 * Contributors:
 *      - initial implementation
 */

import com.ucsmy.core.service.BasicServiceImpl;
import com.ucsmy.ucas.clearing.Enum.BatchStatusEnum;
import com.ucsmy.ucas.clearing.dao.EcCheckBatchDao;
import com.ucsmy.ucas.clearing.entity.EcCheckBatch;
import com.ucsmy.ucas.clearing.service.EcCheckBatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 暂无描述
 *
 * @author ucs_masiming
 * @since 2017/9/18
 */
@Service
public class EcCheckBatchServiceImpl extends BasicServiceImpl<EcCheckBatch, EcCheckBatchDao> implements EcCheckBatchService {

    @Autowired
    private EcCheckBatchDao ecCheckBatchDao;

    @Override
    public Boolean isChecked(String bankType, String billDate) {
        Map<String, Object> map = new HashMap<>();
        map.put("bankType", bankType);
        map.put("billDate", billDate);
        String[] status = new String[]{BatchStatusEnum.ERROR.name(), BatchStatusEnum.FAIL.name()};
        map.put("status", status);
        List<EcCheckBatch> list = ecCheckBatchDao.listBy(map);
        if (list.isEmpty()) {
            return false;
        }
        return true;
    }
}
