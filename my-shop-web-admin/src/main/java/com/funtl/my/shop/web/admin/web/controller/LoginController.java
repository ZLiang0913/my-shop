package com.funtl.my.shop.web.admin.web.controller;

import com.funtl.my.shop.commons.constant.ConstantUtils;
import com.funtl.my.shop.domain.TbUser;
import com.funtl.my.shop.web.admin.commons.utils.CookieUtils;
import com.funtl.my.shop.web.admin.service.TbUserService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.*;

@Controller
public class LoginController{
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    private static final String COOKIE_NAME_SESSION_INFO = "userinfo";

    @Autowired
    private TbUserService tbUserService;

    @RequestMapping(value = {"","/login"},method = RequestMethod.GET)
    public String loginGet(HttpServletRequest req, Model model){
        String cookieValue = CookieUtils.getCookieValue(req, COOKIE_NAME_SESSION_INFO);
        if (StringUtils.isNotBlank(cookieValue)) {
            String[] split = cookieValue.split(":");
            model.addAttribute("email",split[0]);
            model.addAttribute("password",split[1]);
            model.addAttribute("rememberme",true);
        }
        return "login";
    }

    /**
     * 登录逻辑
     * @param email
     * @param password
     * @param rememberme
     * @param model
     * @param req
     * @param resp
     * @return
     */
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public String loginPost(@RequestParam("email") String email, @RequestParam("password") String password, @RequestParam(required = false) String rememberme, Model model,HttpServletRequest req, HttpServletResponse resp){

        TbUser user = tbUserService.getByEmail(email, password);
        logger.info("user,{}",user);

        boolean isRemember = (rememberme == null) ? false : true;
        if (!isRemember) {
            CookieUtils.deleteCookie(req,resp,COOKIE_NAME_SESSION_INFO);
        }

        if (user == null) {
            model.addAttribute("message","邮箱或密码错误！");
            return loginGet(req,model);
        } else {
            if (isRemember) {
                //用户信息存储一周
                CookieUtils.setCookie(req,resp,COOKIE_NAME_SESSION_INFO,String.format("%s:%s",email,password),7*24*60*60);
            }
            req.getSession().setAttribute(ConstantUtils.SESSION_USER,user);
            return "main";
        }
    }

    @RequestMapping(value = "/logout",method = RequestMethod.GET)
    public String logout(HttpServletRequest request){
        request.getSession().invalidate();
        return "login";
    }
}
