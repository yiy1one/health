package com.itheima.service;

import com.itheima.entity.PageResult;
import com.itheima.pojo.Permission;

import java.util.List;

public interface PermissionService {
    PageResult findByPage(Integer currentPage, Integer pageSize, String queryString);

    void add(Permission permission);

    Permission findById(Integer id);

    void edit(Permission permission);

    void deleteById(Integer id);

    List<Permission> findAll();
}
