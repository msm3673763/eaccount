package com.ucsmy.eaccount.pay.dao;

import com.ucsmy.eaccount.pay.entity.PaymentOrder;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentOrderDao {
    int save(PaymentOrder order);

    int update(PaymentOrder order);

    PaymentOrder findById(@Param("id") String id);
}
