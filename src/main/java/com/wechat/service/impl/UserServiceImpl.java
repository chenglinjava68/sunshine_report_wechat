package com.wechat.service.impl;

import com.wechat.dao.UserDao;
import com.wechat.dao.base.BaseDao;
import com.wechat.entity.User;
import com.wechat.service.UserService;
import com.wechat.service.base.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by zhusen on 2017/1/4.
 */
@Service
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService {
    @Autowired
    private UserDao userDao;

    @Override
    protected BaseDao<User> getBaseDao() {
        return this.userDao;
    }

}
