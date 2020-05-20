package com.itheima.service;

import com.itheima.entity.PageResult;
import com.itheima.pojo.User;

import java.util.List;

/**
 * @author ShiXiaoyu
 * @date 2020-05-17 18:41
 */
public interface UserService {
    User findByUsername(String username);

    PageResult findByPage(Integer currentPage, Integer pageSize, String queryString);

    List<User> findAll();

    User findIdByName(String username);

    void add(Integer[] roleIds, User user);

    User findById(Integer userId);

    List<Integer> getRoleIdsByUserId(Integer id);

    void edit(Integer[] roleIds, User user);

    void delete(Integer id);

    boolean checkUsername(String username);
}
