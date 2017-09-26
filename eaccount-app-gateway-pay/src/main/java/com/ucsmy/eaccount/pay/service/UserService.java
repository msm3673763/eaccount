package com.ucsmy.eaccount.pay.service;

import com.ucsmy.core.vo.RetMsg;
import com.ucsmy.eaccount.pay.entity.UserInfo;
import com.ucsmy.eaccount.pay.entity.UserInfoAnnex;
import com.ucsmy.eaccount.pay.service.impl.UserServiceImpl;
import com.ucsmy.eaccount.pay.vo.UserReg;

/**
 * 用户账户
 *
 * @author chenqilin
 * @since 2017/9/18
 */

public interface UserService {

    /**
     * 根据user_info_annex查找用户信息
     * @param userInfoAnnex 用户附加信息表，作为查询条件
     * @return
     */
    UserInfo findUserInfo(UserInfoAnnex userInfoAnnex);

    /**
     * 生成注册页面需要的预备信息
     * @param userName 用户名，从下单接口传过来
     * @return
     */
    RetMsg preRegister(String userName);

    /**
     * 用户注册
     * @param userReg
     * @param type
     * @return
     */
    RetMsg register(UserReg userReg, UserServiceImpl.AccountTypeEnum type);
}
