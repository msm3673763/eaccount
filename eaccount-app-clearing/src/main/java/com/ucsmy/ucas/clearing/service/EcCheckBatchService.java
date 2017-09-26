package com.ucsmy.ucas.clearing.service;

import com.ucsmy.core.service.BasicService;
import com.ucsmy.ucas.clearing.entity.EcCheckBatch;

public interface EcCheckBatchService extends BasicService<EcCheckBatch> {

    Boolean isChecked(String bankType, String billDate);
}
