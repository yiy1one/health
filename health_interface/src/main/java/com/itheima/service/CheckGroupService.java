package com.itheima.service;

import com.itheima.entity.PageResult;
import com.itheima.pojo.CheckGroup;

import java.util.List;

/**
 * @author ShiXiaoyu
 * @date 2020-05-09 14:22
 */
public interface CheckGroupService {
    void add(Integer[] checkItemIds, CheckGroup checkGroup);

    PageResult findPage(Integer currentPage, Integer pageSize, String queryString);

    CheckGroup findById(Integer id);

    List<Integer> findCheckItemIdsByCheckGroupId(Integer id);

    void edit(Integer[] checkItemIds, CheckGroup checkGroup);

    void deleteById(Integer id);

    List<CheckGroup> findAll();
}
