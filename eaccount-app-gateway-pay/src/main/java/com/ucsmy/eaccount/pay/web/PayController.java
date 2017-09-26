package com.ucsmy.eaccount.pay.web;

import com.ucsmy.eaccount.pay.service.PayService;
import com.ucsmy.eaccount.pay.vo.Gateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping("pay")
public class PayController {

    @Autowired
    private PayService payService;

    /**
     * 选择支付方法页面
     */
    @RequestMapping("gateway")
    public String gateway(Gateway gateway, Model model) {
        model.addAttribute("pay", payService.gateway(gateway));
        return "gateway";
    }

    @RequestMapping("toPay")
    public void pay(String securityCode, String payWayCode, HttpServletResponse response) throws IOException {
        payService.toPay(securityCode, payWayCode, response);
    }
}
