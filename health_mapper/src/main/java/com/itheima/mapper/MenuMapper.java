package com.itheima.mapper;

import com.itheima.pojo.Menu;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

public interface MenuMapper {
    List<Menu> findAll();

    void add(Menu menu);

    Menu findById(Integer id);

    Integer countUse(Integer id);

    void deleteById(Integer id);


    List<Menu> getMenuByRoleIds(List<Integer> roleIds);

    List<Menu> getMenuByMenuIds(@Param("menuIds") Set<Integer> menuIds);

    void edit(Menu menu);

    List<Menu> getMenu(Integer userId);
}
