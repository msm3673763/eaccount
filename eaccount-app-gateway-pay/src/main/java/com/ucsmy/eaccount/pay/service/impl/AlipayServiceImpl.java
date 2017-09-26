package com.ucsmy.eaccount.pay.service.impl;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.ucsmy.core.utils.JsonUtils;
import com.ucsmy.eaccount.pay.entity.UserPayInfo;
import com.ucsmy.eaccount.pay.service.AlipayService;
import com.ucsmy.eaccount.pay.vo.AlipayOrder;
import com.ucsmy.eaccount.pay.vo.Gateway;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;

@Service
public class AlipayServiceImpl implements AlipayService {

    private static final String URL = "https://openapi.alipaydev.com/gateway.do";
    private static final String PRODUCT_CODE = "FAST_INSTANT_TRADE_PAY";// 销售产品码，与支付宝签约的产品码名称。 注：目前仅支持FAST_INSTANT_TRADE_PAY
    private static final String FORMAT = "json";
    private static final String CHARSET = "UTF-8";
    private static final String SIGN_TYPE = "RSA2";

    private static final String notify_url = "http://wenzz.iok.la:21829/notify/alipay";// 通知地址
    private static final String callBack_url = "http://wenzz.iok.la:21829/callBack/alipay";// 回跳地址

    @Override
    public void getPayForm(Gateway gateway, UserPayInfo payInfo, HttpServletResponse response) throws IOException {
        AlipayClient alipayClient = new DefaultAlipayClient(URL, payInfo.getAppId(), payInfo.getRsaPrivateKey(), FORMAT, CHARSET, payInfo.getRsaPublicKey(), SIGN_TYPE);
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();// 创建API对应的request
        alipayRequest.setReturnUrl(callBack_url);
        alipayRequest.setNotifyUrl(notify_url);// 在公共参数中设置回跳和通知地址
        // String order = String.valueOf(System.currentTimeMillis());
        alipayRequest.setBizContent(JsonUtils.formatObjectToJson(AlipayOrder.builder()
                .tradeNo(gateway.getOrderNo())
                .productCode(PRODUCT_CODE)
                .totalAmount(gateway.getTotalAmount())
                .subject(gateway.getProductName())
                .body(gateway.getProductName())
                .passBackParams(URLEncoder.encode("订单号：".concat(gateway.getOrderNo()), CHARSET))
                .build()));
        String form = "";
        try {
            form = alipayClient.pageExecute(alipayRequest).getBody(); // 调用SDK生成表单
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        response.setContentType("text/html;charset=" + CHARSET);
        response.getWriter().write(form);// 直接将完整的表单html输出到页面
        response.getWriter().flush();
        response.getWriter().close();
    }
}
