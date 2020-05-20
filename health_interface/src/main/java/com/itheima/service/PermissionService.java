package com.itheima.service;

import com.itheima.entity.PageResult;
import com.itheima.pojo.Permission;

public interface PermissionService {
    PageResult findByPage(Integer currentPage, Integer pageSize, String queryString);

    void add(Permission permission);
}
