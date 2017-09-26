package com.ucsmy.ucas.clearing.service;

import java.io.File;
import java.util.Date;

/**
 * 交易
 *
 * @author ucs_guichang
 * @since 2017/9/18
 */

public interface TradeService {

    /**
     * 下载对账文件
     * @param date 时间
     * @return 对账文件
     */
    public File downloadTradeFile(Date date);

}