package com.funtl.my.shop.web.admin.dao;

import com.funtl.my.shop.domain.TbUser;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TbUserDao {

    /**
     * 根据邮箱查询用户信息
     * @param email
     * @return
     */
    TbUser getByEmail(String email);

    /**
     * 查询所有用户
     * @return
     */
    public List<TbUser> selectAll();

    /**
     * 新增用户
     * @return
     */
    public void insert(TbUser tbUser);

    TbUser getById(Long id);
}
