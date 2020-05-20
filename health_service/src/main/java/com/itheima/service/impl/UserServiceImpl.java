package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itheima.entity.PageResult;
import com.itheima.mapper.UserMapper;
import com.itheima.pojo.User;
import com.itheima.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

/**
 * @author ShiXiaoyu
 * @date 2020-05-17 18:45
 */
@Service(interfaceClass = UserService.class)
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Override
    public User findByUsername(String username) {
        return userMapper.findByUsername(username);
    }

    @Override
    public PageResult findByPage(Integer currentPage, Integer pageSize, String queryString) {
        PageHelper.startPage(currentPage, pageSize);
        List<User> list = userMapper.findByCondition(queryString);
        PageInfo<User> pageInfo = new PageInfo<>(list);

        return new PageResult(pageInfo.getTotal(), pageInfo.getList());
    }

    @Override
    public List<User> findAll() {

        return userMapper.findAll();
    }

    @Override
    public User findIdByName(String username) {

        return userMapper.findIdByName(username);
    }

    @Override
    public void add(Integer[] roleIds, User user) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String newPass = encoder.encode(user.getPassword());
        user.setPassword(newPass);

        userMapper.insert(user);

        userMapper.insertRoles(user.getId(), roleIds);

    }

    @Override
    public User findById(Integer userId) {
        User user = userMapper.findById(userId);
        return user;
    }

    @Override
    public List<Integer> getRoleIdsByUserId(Integer userId) {
        List<Integer> roleIds = userMapper.getRoleIdsByUserId(userId);
        return roleIds;
    }

    @Override
    public void edit(Integer[] roleIds, User user) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String newPass = encoder.encode(user.getPassword());
        user.setPassword(newPass);

        userMapper.update(user);

        userMapper.deleteRoles(user.getId());

        userMapper.insertRoles(user.getId(), roleIds);
    }

    @Override
    public void delete(Integer id) {
        userMapper.deleteRoles(id);
        userMapper.delete(id);

    }

    @Override
    public boolean checkUsername(String username) {

        Integer count = userMapper.checkUsername(username);

        return count == 0;
    }
}
