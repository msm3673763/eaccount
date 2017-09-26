package com.ucsmy.eaccount.pay.dao;

import com.ucsmy.eaccount.pay.entity.Account;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 账户
 *
 * @author chenqilin
 * @since 2017/9/19
 */
@Repository
public interface AccountDao {

    int save(Account entity);
}
