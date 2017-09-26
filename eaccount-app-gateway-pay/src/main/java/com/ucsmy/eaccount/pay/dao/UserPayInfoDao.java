package com.ucsmy.eaccount.pay.dao;

import com.ucsmy.eaccount.pay.entity.UserPayInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserPayInfoDao {
    UserPayInfo find(@Param("userNo") String userNo, @Param("payWayCode") String payWayCode);
}
