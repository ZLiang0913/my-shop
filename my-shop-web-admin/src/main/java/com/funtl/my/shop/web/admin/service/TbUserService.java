package com.funtl.my.shop.web.admin.service;

import com.funtl.my.shop.commons.dto.BaseResult;
import com.funtl.my.shop.domain.TbUser;

import java.util.List;

public interface TbUserService {

    /**
     * 用户登录
     * @param email 邮箱
     * @param password 密码
     * @return 用户信息
     */
    public TbUser getByEmail(String email, String password);

    /**
     * 查询所有用户
     * @return
     */
    public List<TbUser> selectAll();

    public BaseResult save(TbUser tbUser);
}
