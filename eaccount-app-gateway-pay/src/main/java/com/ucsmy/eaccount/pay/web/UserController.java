package com.ucsmy.eaccount.pay.web;

import com.ucsmy.core.vo.RetMsg;
import com.ucsmy.eaccount.pay.service.UserService;
import com.ucsmy.eaccount.pay.service.impl.UserServiceImpl;
import com.ucsmy.eaccount.pay.vo.UserReg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 用户账户
 *
 * @author chenqilin
 * @since 2017/9/18
 */

@Controller
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 跳转到账户注册页示例，由具体支付方式（如白条）判断跳转逻辑
     * @param userName
     * @param model
     * @return
     */
    @RequestMapping(value = "register", produces = "text/html")
    public String register(String userName, Model model) {
        model.addAttribute("data", userService.preRegister(userName));
        return "register";
    }

    @RequestMapping("save")
    @ResponseBody
    public RetMsg register(UserReg userReg) {
        return userService.register(userReg, UserServiceImpl.AccountTypeEnum.BAITIAO);
    }
}