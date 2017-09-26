package com.ucsmy.eaccount.pay.dao;

import com.ucsmy.eaccount.pay.entity.PayWay;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PayWayDao {

    List<PayWay> findByAppId(@Param("appId") String appId);

}
