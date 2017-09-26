package com.ucsmy.ucas.clearing.dao;

import com.ucsmy.core.dao.BasicDao;
import com.ucsmy.ucas.clearing.entity.EcCheckBatch;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface EcCheckBatchDao extends BasicDao<EcCheckBatch> {

    List<EcCheckBatch> listBy(Map<String, Object> map);
}
