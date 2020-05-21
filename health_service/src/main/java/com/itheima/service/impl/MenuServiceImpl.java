package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.PageResult;
import com.itheima.mapper.MenuMapper;
import com.itheima.pojo.Menu;
import com.itheima.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service(interfaceClass = MenuService.class)
public class MenuServiceImpl implements MenuService {
    @Autowired
    private MenuMapper menuMapper;

    @Override
    public List<Menu> findAll() {
        List<Menu> list = menuMapper.findAll();


        return list;
    }

    @Override
    public void add(Menu menu) {
        menuMapper.add(menu);
    }

    @Override
    public Menu findById(Integer id) {

        return menuMapper.findById(id);
    }

    @Override
    public void deleteById(Integer id) {
        Integer count = menuMapper.countUse(id);
        if (count == 0) {

            menuMapper.deleteById(id);
        } else {
            throw new RuntimeException(MessageConstant.MENU_IS_QUOTED);
        }
    }



    @Override
    public List<Menu> getMenuByRoleIds(List<Integer> roleIds) {
        List<Menu> list = menuMapper.getMenuByRoleIds(roleIds);
        return null;
    }

    @Override
    public List<Menu> getMenuByMenuIds(Set<Integer> menuIds) {
        List<Menu> list = menuMapper.getMenuByMenuIds(menuIds);
        // 原本的list数据是一维的，现在封装为2维数据
        // 创建主目录list
        ArrayList<Menu> mainMenus = new ArrayList<>();
        // 创建主目录的id Set
        Set<Integer> mainMenuIds = new HashSet<>();


        // 第一次循环子目录list， 把主目录的id放入set，去重
        for (Menu menu : list) {
            Integer parentMenuId = menu.getParentMenuId();
            mainMenuIds.add(parentMenuId);
        }

        // 循环主目录id的set，添加主目录到目录list
        for (Integer mainMenuId : mainMenuIds) {
            mainMenus.add(this.findById(mainMenuId));
        }

        // 双层循环，把子目录装入主目录的children列表
        for (Menu menu : list) {
            for (Menu mainMenu : mainMenus) {
                if (menu.getParentMenuId().equals(mainMenu.getId())) {
                    mainMenu.getChildren().add(menu);
                }
            }
        }

        return mainMenus;
    }

    @Override
    public void edit(Menu menu) {
        menuMapper.edit(menu);
    }
}
