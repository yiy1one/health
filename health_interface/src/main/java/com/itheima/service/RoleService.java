package com.itheima.service;

import com.itheima.entity.PageResult;
import com.itheima.pojo.Role;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface RoleService {
    PageResult findByPage(Integer currentPage, Integer pageSize, String queryString);

    void add(Role role, Integer[] menu, Integer[] permission);

    Map<String, Object> findById(Integer id);

    void edit(Role role, Integer[] menu, Integer[] permission);

    List<Role> findAll();

    List<Integer> getRoleIdsByUserId(Integer id);

    Set<Integer> getMenuIdsByRoleIds(List<Integer> roleIds);

    void delete(Integer id);
}
