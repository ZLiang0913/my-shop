package com.funtl.my.shop.web.admin.service.impl;

import com.funtl.my.shop.commons.dto.BaseResult;
import com.funtl.my.shop.commons.utils.RegexpUtils;
import com.funtl.my.shop.domain.TbUser;
import com.funtl.my.shop.web.admin.dao.TbUserDao;
import com.funtl.my.shop.web.admin.service.TbUserService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Date;
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

    @Override
    public List<TbUser> selectAll() {
        return tbUserDao.selectAll();
    }

    @Override
    public BaseResult save(TbUser tbUser) {
        BaseResult baseResult = checkParams(tbUser);
        if (baseResult.getStatus() == BaseResult.STATUS_SUCCESS){
            //验证通过

            if (tbUser.getId() == null) {
                // 新增用户
                tbUser.setPassword(DigestUtils.md5DigestAsHex(tbUser.getPassword().getBytes()));
                tbUser.setCreated(new Date());
                tbUser.setUpdated(new Date());
                tbUserDao.insert(tbUser);
            }
            else {
                // 编辑用户
                // 编辑用户时如果没有输入密码则沿用原来的密码
//                if (StringUtils.isBlank(tbUser.getPassword())) {
//                    TbUser oldTbUser = getById(tbUser.getId());
//                    tbUser.setPassword(oldTbUser.getPassword());
//                } else {
//                    // 验证密码是否符合规范，密码长度介于 6 - 20 位之间
//                    if (StringUtils.length(tbUser.getPassword()) < 6 || StringUtils.length(tbUser.getPassword()) > 20) {
//                        return BaseResult.fail("密码长度必须介于 6 - 20 位之间");
//                    }
//
//                    // 设置密码加密
//                    tbUser.setPassword(DigestUtils.md5DigestAsHex(tbUser.getPassword().getBytes()));
//                }
//                update(tbUser);
            }
        }
        return baseResult;
    }

    @Override
    public TbUser getById(Long id) {
        return tbUserDao.getById(id);
    }

    /**
     * 检查用户参数是否正确
     * @param user
     * @return
     */
    public BaseResult checkParams(TbUser user){
        BaseResult baseResult = BaseResult.success();

        if (StringUtils.isBlank(user.getEmail())){
            baseResult = BaseResult.fail("邮箱不能为空");
        }
        else if (!RegexpUtils.checkEmail(user.getEmail())){
            baseResult = BaseResult.fail("邮箱格式错误，请重新输入");
        }
        else if (StringUtils.isBlank(user.getPassword())){
            baseResult = BaseResult.fail("密码不能为空");
        }
        else if (StringUtils.isBlank(user.getUsername())){
            baseResult = BaseResult.fail("姓名不能为空");
        }
        else if (StringUtils.isBlank(user.getPhone())){
            baseResult = BaseResult.fail("手机号不能为空");
        }
        else if (!RegexpUtils.checkPhone(user.getPhone())){
            baseResult = BaseResult.fail("手机号格式错误，请重新输入");
        }

        return baseResult;
    }
}
