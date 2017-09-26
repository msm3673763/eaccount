package com.ucsmy.eaccount.pay.service.impl;

import com.ucsmy.core.tool.security.SecurityService;
import com.ucsmy.core.utils.EncryptUtils;
import com.ucsmy.core.utils.HibernateValidateUtils;
import com.ucsmy.core.utils.StringUtils;
import com.ucsmy.core.vo.RetMsg;
import com.ucsmy.eaccount.pay.dao.PayWayDao;
import com.ucsmy.eaccount.pay.dao.PaymentOrderDao;
import com.ucsmy.eaccount.pay.dao.UserPayConfigDao;
import com.ucsmy.eaccount.pay.dao.UserPayInfoDao;
import com.ucsmy.eaccount.pay.entity.PayWay;
import com.ucsmy.eaccount.pay.entity.PaymentOrder;
import com.ucsmy.eaccount.pay.entity.UserPayConfig;
import com.ucsmy.eaccount.pay.entity.UserPayInfo;
import com.ucsmy.eaccount.pay.service.AlipayService;
import com.ucsmy.eaccount.pay.service.PayService;
import com.ucsmy.eaccount.pay.vo.Gateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PayServiceImpl implements PayService {

    private static final String CHARSET = "UTF-8";

    @Autowired
    private UserPayConfigDao userPayConfigDao;

    @Autowired
    private PayWayDao payWayDao;

    @Autowired
    private UserPayInfoDao payInfoDao;

    @Autowired
    private AlipayService alipayService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private PaymentOrderDao paymentOrderDao;

    @Override
    public RetMsg gateway(Gateway gateway) {
        RetMsg retMsg = HibernateValidateUtils.getError(gateway);
        if(retMsg.isError()) {
            return retMsg;
        }
        UserPayConfig userPayConfig = userPayConfigDao.findByAppId(gateway.getAppId());
        if(userPayConfig == null) {
            return RetMsg.error("appId不存在");
        }
        String sign = EncryptUtils.MD5(gateway.getAppId().concat(gateway.getProductName()).concat(gateway.getOrderNo()).concat(String.valueOf(gateway.getTotalAmount())));
        System.out.println("sign: ".concat(sign));
        if(!sign.equalsIgnoreCase(gateway.getMac())) {
            return RetMsg.error("签名错误");
        }
        List<PayWay> payWays = payWayDao.findByAppId(gateway.getAppId());
        List<String> payWayCodes = new ArrayList<>();
        payWays.forEach(p -> payWayCodes.add(p.getPayWayCode()));
        Map<String, Object> data = new HashMap<>();
        data.put("userPayConfig", userPayConfig);
        data.put("order", gateway);
        data.put("payWayCodes", payWayCodes);
        String securityCode = securityService.genSecurityCode(data, SecurityService.Business.PAY);
        data.put("payWay", payWays);
        data.put("securityCode", securityCode);
        return RetMsg.success(data);
    }

    @Override
    public void toPay(String securityCode, String payWayCode, HttpServletResponse response) throws IOException {
        SecurityService.Security<Map<String, Object>> security = securityService.getSecurityCode(securityCode, SecurityService.Business.PAY);
        if(security == null) {
            response.setContentType("text/html;charset=" + CHARSET);
            error("数据不存在", response);
            return;
        }
        Gateway gateway = (Gateway) security.getData().get("order");
        UserPayConfig userPayConfig = (UserPayConfig) security.getData().get("userPayConfig");
        List payWayCodes = (List) security.getData().get("payWayCodes");
        if(!payWayCodes.contains(payWayCode)) {
            response.setContentType("text/html;charset=" + CHARSET);
            error("支付方式不存在", response);
            return;
        }
        UserPayInfo payInfo = payInfoDao.find(userPayConfig.getUserNo(), payWayCode);
        if(payInfo == null) {
            error("支付方式不存在", response);
            return;
        }
        String id = StringUtils.getUUID();
        PaymentOrder order = PaymentOrder.builder()
                .id(id)
                .productName(gateway.getProductName())
                .merchantOrderNo(gateway.getOrderNo())
                .orderAmount(gateway.getTotalAmount())
                .merchantNo(userPayConfig.getUserNo())
                .orderTime(new Timestamp(System.currentTimeMillis()))
                .orderDate(new Date(System.currentTimeMillis()))
                .returnUrl(gateway.getReturnUrl())
                .notifyUrl(gateway.getNotifyUrl())
                .payWayCode(payWayCode)
                .status("1")
                .createTime(new Timestamp(System.currentTimeMillis()))
                .build();
        paymentOrderDao.save(order);
        gateway.setOrderNo(id);
        if(payWayCode.equals(PayType.ALIPAY.value)) {
            alipayService.getPayForm(gateway, payInfo, response);
        } else {
            error("支付方式不存在", response);
        }
    }

    private enum PayType {
        ALIPAY("支付宝支付", "001");

        final String name;
        final String value;

        PayType(String name, String value) {
            this.name = name;
            this.value = value;
        }
    }

    private void error(String error, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=" + CHARSET);
        response.sendError(HttpStatus.BAD_REQUEST.value(), error);
    }
}
