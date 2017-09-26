package com.ucsmy.eaccount.pay.dao;

import com.ucsmy.eaccount.pay.entity.UserInfoAnnex;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 用户信息附加表
 *
 * @author chenqilin
 * @since 2017/9/19
 */
@Repository
public interface UserInfoAnnexDao {

    int save(UserInfoAnnex entity);
}
