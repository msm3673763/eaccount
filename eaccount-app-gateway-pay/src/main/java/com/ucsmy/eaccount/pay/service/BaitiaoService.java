package com.ucsmy.eaccount.pay.service;

/**
 * Created by ucs_gaokuixin on 2017/9/15.
 */
public interface BaitiaoService {
    /**
     * 返回授信用户额度
     * @param accountNo
     * @return
     */
    String getCreditLine(String accountNo);

}
