package com.ucsmy.eaccount.pay.service;

import com.ucsmy.eaccount.pay.entity.UserPayInfo;
import com.ucsmy.eaccount.pay.vo.Gateway;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface AlipayService {

    void getPayForm(Gateway gateway, UserPayInfo payInfo, HttpServletResponse response) throws IOException;

}
