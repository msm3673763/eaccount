package com.ucsmy.eaccount.pay.service;

import com.ucsmy.core.vo.RetMsg;
import com.ucsmy.eaccount.pay.vo.Gateway;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface PayService {

    RetMsg gateway(Gateway gateway);

    void toPay(String securityCode, String payWayCode, HttpServletResponse response) throws IOException;

}
