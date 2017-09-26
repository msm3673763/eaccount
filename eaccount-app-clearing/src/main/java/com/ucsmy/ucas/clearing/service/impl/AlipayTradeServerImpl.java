package com.ucsmy.ucas.clearing.service.impl;

import com.alipay.api.AlipayApiException;
import com.ucsmy.core.utils.AliPayUtils;
import com.ucsmy.core.utils.DateUtils;
import com.ucsmy.ucas.clearing.service.TradeService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.Date;

/**
 * 暂无描
 *
 * @author ucs_guichang
 * @since 2017/9/18
 */

@Log4j2
@Service
public class AlipayTradeServerImpl implements TradeService {


    @Override
    public File downloadTradeFile(Date date) {
        String sDate = DateUtils.dateToString(date, "yyyy-MM-dd");

        // 请求对账单url
        String downloadUrl;
        try {
            downloadUrl = AliPayUtils.queryDownloadUrl(sDate);
        } catch (AlipayApiException e) {
            log.error("请求支付宝对账单url失败（" + sDate + "）");
            e.printStackTrace();
            return null;
        }

        // 下载对账文件
        String zipName = sDate + "_" + System.currentTimeMillis() + ".zip";
        String zipPath;
        try {
            zipPath = AliPayUtils.downLoadFromUrl(downloadUrl, zipName, sDate);
        } catch (IOException e) {
            log.error("下载支付宝对账文件失败（" + sDate + "）");
            e.printStackTrace();
            return null;
        }

        // 解压
        String dateDir;
        try {
            dateDir = AliPayUtils.unZip(zipPath, sDate);
        } catch (IOException e) {
            log.error("解压文件失败");
            e.printStackTrace();
            return null;
        }

        File dir = new File(dateDir);
        for(File f:dir.listFiles()) {
            if(f.isFile() && f.getName().endsWith("csv") && f.getName().indexOf("汇总")==-1)
                return f;
        }
        return null;
    }

    public static void main(String[] args) {
        AlipayTradeServerImpl alip = new AlipayTradeServerImpl();
        File file = alip.downloadTradeFile(DateUtils.getDate(2017, 8, 18));
        System.out.println(file.getPath());
    }
}