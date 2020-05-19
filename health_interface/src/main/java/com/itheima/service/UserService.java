package com.itheima.service;

import com.itheima.pojo.User;

/**
 * @author ShiXiaoyu
 * @date 2020-05-17 18:41
 */
public interface UserService {
    User findByUsername(String username);
}
