package com.ucsmy.eaccount.pay.dao;

import com.ucsmy.eaccount.pay.entity.UserInfo;
import com.ucsmy.eaccount.pay.entity.UserInfoAnnex;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 用户账户
 *
 * @author chenqilin
 * @since 2017/9/18
 */
@Repository
public interface UserInfoDao {

    /**
     * 根据user_info_annex查找用户信息
     * @param entity 用户附加信息表，作为查询条件
     * @return
     */
    UserInfo findUserInfo(@Param("entity") UserInfoAnnex entity);

    /**
     * 保存
     * @param entity
     * @return
     */
    int save(UserInfo entity);
}
