package com.itheima.service;

import com.itheima.entity.PageResult;
import com.itheima.pojo.Menu;

import java.util.List;
import java.util.Set;

public interface MenuService {
    List<Menu> findAll();

    void add(Menu menu);

    Menu findById(Integer id);

    void deleteById(Integer id);



    List<Menu> getMenuByRoleIds(List<Integer> roleIds);

    List<Menu> getMenuByMenuIds(Set<Integer> menuIds);

    void edit(Menu menu);

    List<Menu> getMenu(Integer id);
}
