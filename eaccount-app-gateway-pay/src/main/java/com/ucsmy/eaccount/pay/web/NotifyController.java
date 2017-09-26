package com.ucsmy.eaccount.pay.web;

import com.ucsmy.core.utils.JsonUtils;
import com.ucsmy.eaccount.pay.dao.PaymentOrderDao;
import com.ucsmy.eaccount.pay.entity.PaymentOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.AsyncRestTemplate;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("notify")
public class NotifyController {

    private RestTemplate template = new RestTemplate();

    @Autowired
    private PaymentOrderDao orderDao;

    @RequestMapping("test")
    public Object test(String orderNo, String outOrderNo, HttpServletRequest request) {
        System.out.println("test: " + orderNo + ", " + outOrderNo);
        return "success";
    }

    @RequestMapping("alipay")
    public Object alipayNotify(String trade_no, String out_trade_no, HttpServletRequest request) {
        System.out.println(JsonUtils.formatObjectToJson(request.getParameterMap()));

        PaymentOrder order = orderDao.findById(out_trade_no);
        order.setStatus("0");
        order.setTrxNo(trade_no);
        orderDao.update(order);
        MultiValueMap<String, String> map= new LinkedMultiValueMap<>();
        map.add("orderNo", out_trade_no);
        map.add("outOrderNo", order.getMerchantOrderNo());
        HttpEntity<MultiValueMap<String, String>> formEntity = new HttpEntity<>(map, new HttpHeaders());
        String result = template.postForObject(order.getNotifyUrl(), formEntity, String.class);
        System.out.println(result);
        return "success";
    }

}
