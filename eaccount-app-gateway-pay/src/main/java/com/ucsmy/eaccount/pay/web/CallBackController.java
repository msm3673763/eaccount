package com.ucsmy.eaccount.pay.web;

import com.ucsmy.eaccount.pay.dao.PaymentOrderDao;
import com.ucsmy.eaccount.pay.entity.PaymentOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("callBack")
public class CallBackController {

    @Autowired
    private PaymentOrderDao orderDao;

    @RequestMapping("alipay")
    public Object alipay(String trade_no, String out_trade_no) {
        PaymentOrder order = orderDao.findById(out_trade_no);
        return "redirect:".concat(order.getReturnUrl()).concat("?order_no=").concat(out_trade_no).concat("&out_order_no=").concat(order.getMerchantOrderNo());
    }

}
