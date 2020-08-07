package com.funtl.my.shop.web.admin.service.impl;

import com.funtl.my.shop.domain.TbUser;
import com.funtl.my.shop.web.admin.dao.TbUserDao;
import com.funtl.my.shop.web.admin.service.TbUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.List;

@Service
public class TbUserServiceImpl implements TbUserService {
    private static final Logger logger = LoggerFactory.getLogger(TbUserServiceImpl.class);
    @Autowired
    private TbUserDao tbUserDao;


    @Override
    public TbUser getByEmail(String email, String password) {
        TbUser user = tbUserDao.getByEmail(email);

        if (user != null) {
            // 对明文密码加密
            String md5Password = DigestUtils.md5DigestAsHex(password.getBytes());
            if (!md5Password.equals(user.getPassword())){
                return null;
            }

        }
        return user;
    }
}
