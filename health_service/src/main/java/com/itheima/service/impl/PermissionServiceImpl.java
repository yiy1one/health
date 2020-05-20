package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.PageResult;
import com.itheima.mapper.PermissionMapper;
import com.itheima.pojo.Permission;
import com.itheima.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;



@Service(interfaceClass = PermissionService.class)
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private PermissionMapper permissionMapper;

    @Override
    public PageResult findByPage(Integer currentPage, Integer pageSize, String queryString) {
        PageHelper.startPage(currentPage, pageSize);
        List<Permission> list = permissionMapper.findByCondition(queryString);
        PageInfo<Permission> pageInfo = new PageInfo<>(list);

        return new PageResult(pageInfo.getTotal(), pageInfo.getList());
    }

    @Override
    public void add(Permission permission) {
        permissionMapper.add(permission);
    }

    @Override
    public Permission findById(Integer id) {

        return permissionMapper.findById(id);
    }

    @Override
    public void edit(Permission permission) {
        permissionMapper.edit(permission);
    }

    @Override
    public void deleteById(Integer id) {
        Integer count = permissionMapper.countUse(id);
        if (count == 0) {

            permissionMapper.deleteById(id);
        } else {
            throw new RuntimeException(MessageConstant.PERMISSION_IS_QUOTED);
        }
    }

    @Override
    public List<Permission> findAll() {
        return permissionMapper.findAll();
    }
}
