package com.ucsmy.eaccount.pay.dao;

import com.ucsmy.eaccount.pay.entity.UserAccountRel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * user account 映射
 *
 * @author chenqilin
 * @since 2017/9/19
 */
@Repository
public interface UserAccountRelDao {

    int save(UserAccountRel entity);
}
