package com.ucsmy.eaccount.pay.service.impl;

import com.ucsmy.core.tool.security.SecurityService;
import com.ucsmy.core.utils.*;
import com.ucsmy.core.vo.RetMsg;
import com.ucsmy.eaccount.pay.dao.AccountDao;
import com.ucsmy.eaccount.pay.dao.UserAccountRelDao;
import com.ucsmy.eaccount.pay.dao.UserInfoAnnexDao;
import com.ucsmy.eaccount.pay.dao.UserInfoDao;
import com.ucsmy.eaccount.pay.entity.Account;
import com.ucsmy.eaccount.pay.entity.UserAccountRel;
import com.ucsmy.eaccount.pay.entity.UserInfo;
import com.ucsmy.eaccount.pay.entity.UserInfoAnnex;
import com.ucsmy.eaccount.pay.service.UserService;
import com.ucsmy.eaccount.pay.vo.UserReg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;

import java.security.Key;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 用户账户
 *
 * @author chenqilin
 * @since 2017/9/18
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserInfoDao userInfoDao;

    @Autowired
    private AccountDao accountDao;

    @Autowired
    private UserInfoAnnexDao userInfoAnnexDao;

    @Autowired
    private UserAccountRelDao userAccountRelDao;

    @Autowired
    private SecurityService securityService;

    @Override
    public UserInfo findUserInfo(UserInfoAnnex userInfoAnnex) {
        return null;
    }

    @Override
    public RetMsg preRegister(String userName) {
        if (StringUtils.isEmpty(userName)) {
            return RetMsg.error("参数错误");
        }
        // TODO 密码公钥密钥设置，目前使用securityCode存储rsa密钥对
        KeyPair keyPair = RSAUtils.generateKeyPair();
        Map<String, Object> data = new HashMap<>();
        data.put("userName", userName);
        data.put("privateKey", Base64Utils.encode(keyPair.getPrivate().getEncoded()));
        String securityCode = securityService.genSecurityCode(data, SecurityService.Business.REG);
        Map<String, Object> publicKeyMap = new HashMap<>();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        publicKeyMap.put("modulus", Base64Utils.encodeToString(publicKey.getModulus().toByteArray()));
        publicKeyMap.put("exponent", Base64Utils.encodeToString(publicKey.getPublicExponent().toByteArray()));
        Map<String, Object> pageData = new HashMap<>();
        pageData.put("publicKey", publicKeyMap);
        pageData.put("securityCode", securityCode);
        return RetMsg.success(pageData);
    }

    @Override
    public RetMsg register(UserReg userReg, AccountTypeEnum type) {
        RetMsg retMsg = HibernateValidateUtils.getError(userReg);
        if (retMsg.isError()) {
            return retMsg;
        }
        // 检查securityCode
        SecurityService.Security<Map<String, Object>> data = securityService.getSecurityCode(userReg.getSecurityCode(), SecurityService.Business.REG);
        String userName = (String) data.getData().get("userName");
        if (StringUtils.isEmpty(userName)) {
            return RetMsg.error("数据不存在");
        }
        userReg.setUserName(userName);
        // 检查是否重复
        UserInfoAnnex queryEntity = new UserInfoAnnex();
        queryEntity.setUserName(userReg.getUserName());
        queryEntity.setIdcardNo(userReg.getIdcardNo());
        UserInfo existUserInfo = userInfoDao.findUserInfo(queryEntity);
        if (existUserInfo != null && StringUtils.isNotEmpty(existUserInfo.getId())) {
            return RetMsg.error("该用户已注册");
        }
        // 用户信息
        UserInfo userInfo = createUserInfo(userReg);
        // 密码解密
        byte[] privateKey = (byte[]) data.getData().get("privateKey");
        String password = RSAUtils.decrypt(privateKey, userInfo.getPassword());
        String payPassword = RSAUtils.decrypt(privateKey, userInfo.getPayPwd());
        String confirmPassword = RSAUtils.decrypt(privateKey, userReg.getPayPwdConfirm());
        if (StringUtils.isEmpty(password) || StringUtils.isEmpty(payPassword) || StringUtils.isEmpty(confirmPassword)) {
            return RetMsg.error("保存失败");
        }
        if (!confirmPassword.equals(payPassword)) {
            return RetMsg.error("两次输入的密码不一致");
        }
        userInfo.setPassword(password);
        userInfo.setPayPwd(payPassword);
        passwordHandle(userInfo);
        userInfoDao.save(userInfo);
        // 账号
        Account account = createAccount(userInfo, type);
        accountDao.save(account);
        // 用户-账号关联
        UserAccountRel userAccountRel = createUserAccountRel(account);
        userAccountRelDao.save(userAccountRel);
        // 用户附加信息
        UserInfoAnnex userInfoAnnex = createUserInfoAnnex(userInfo, userReg);
        userInfoAnnexDao.save(userInfoAnnex);
        return RetMsg.success();
    }

    /**
     * 密码加盐处理
     */
    private void passwordHandle(UserInfo entity) {
        entity.setSalt(StringUtils.getRandom(6));
        entity.setPassword(StringUtils.passwordEncrypt(entity.getUserNo(), entity.getPassword(), entity.getSalt()));
        entity.setPayPwd(StringUtils.passwordEncrypt(entity.getAccountNo(), entity.getPayPwd(), entity.getSalt()));
    }

    /**
     * 创建用户信息
     * @param userReg
     * @return
     */
    private UserInfo createUserInfo(UserReg userReg) {
        UserInfo entity = new UserInfo();
        entity.setId(SerialNumberGenerator.generate(SerialNoPerfixEnum.USERINFO_ID.value));
        entity.setUserName(userReg.getUserName());
        entity.setLoginName(userReg.getUserName());
        entity.setAccountNo(SerialNumberGenerator.generate(SerialNoPerfixEnum.ACCOUNT_NO.value));
        entity.setUserNo(SerialNumberGenerator.generate(SerialNoPerfixEnum.USER_NO.value));
        entity.setCreateTime(new Date());
        entity.setPassword(userReg.getPayPwd());
        entity.setPayPwd(userReg.getPayPwd());
        entity.setUserType(UserTypeEnum.USER.value);
        entity.setStatus(StringUtils.CommStatus.INUSE.value);
        return entity;
    }

    /**
     * 创建账户
     * @param userInfo
     * @return
     */
    private Account createAccount(UserInfo userInfo, AccountTypeEnum type) {
        Account entity = new Account();
        entity.setId(SerialNumberGenerator.generate(SerialNoPerfixEnum.ACCOUT_ID.value));
        entity.setId(SerialNumberGenerator.generate(SerialNoPerfixEnum.ACCOUNT_NO.value));
        entity.setCreateTime(userInfo.getCreateTime());
        entity.setEditTime(userInfo.getCreateTime());
        entity.setVersion(1L);
        entity.setRemark("");
        entity.setStatus(userInfo.getStatus());
        entity.setAccountNo(userInfo.getAccountNo());
        entity.setAccountType(AccountTypeEnum.BAITIAO.value);
        entity.setBalance((short) 0);
        entity.setUnbalance((short) 0);
        entity.setSecurityMoney((short) 0);
        entity.setTotalIncome((short) 0);
        entity.setTotalExpend((short) 0);
        entity.setTodayIncome((short) 0);
        entity.setTodayExpend((short) 0);
        entity.setSettAmount((short) 0);
        entity.setUserNo(userInfo.getUserNo());
        entity.setCreditLine((short) 0);
        return entity;
    }

    /**
     * 创建用户-账户映射entity
     * @param account
     * @return
     */
    private UserAccountRel createUserAccountRel(Account account) {
        UserAccountRel entity = new UserAccountRel();
        entity.setAccountType(account.getAccountType());
        entity.setAccountNo(account.getAccountNo());
        entity.setUserNo(account.getUserNo());
        entity.setId(StringUtils.getUUID());
        return entity;
    }

    /**
     * 用户附加信息
     * @param userInfo
     * @param userReg
     * @return
     */
    private UserInfoAnnex createUserInfoAnnex(UserInfo userInfo, UserReg userReg) {
        UserInfoAnnex entity = new UserInfoAnnex();
        entity.setId(StringUtils.getUUID());
        entity.setUserNo(userInfo.getUserNo());
        BeanUtil.copyPropertiesIgnoreNull(userReg, entity);
        return entity;
    }

    private enum SerialNoPerfixEnum {
        USERINFO_ID("2001"),
        USER_NO("2002"),
        ACCOUNT_NO("2003"),
        ACCOUT_ID("2004");

        public String value;

        SerialNoPerfixEnum(String value) {
            this.value = value;
        }
    }

    public enum UserTypeEnum {
        EMPLOYER("employer"),
        USER("user"),
        MERCHANT("merchant");

        public String value;

        UserTypeEnum(String value) {
            this.value = value;
        }
    }

    public enum AccountTypeEnum {

        /* 白条账户 */
        BAITIAO("baitiao"),
        /* 资金账户 */
        FUND("fund");

        public String value;

        AccountTypeEnum(String value) {
            this.value = value;
        }
    }

}