package com.ucsmy.eaccount.pay.dao;

import com.ucsmy.eaccount.pay.entity.UserPayConfig;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserPayConfigDao {

    UserPayConfig findByAppId(@Param("appId") String appId);

}
