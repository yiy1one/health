package com.itheima.service;

import com.itheima.entity.PageResult;
import com.itheima.pojo.CheckItem;

import java.util.List;

/**
 * @author ShiXiaoyu
 * @date 2020-05-06 12:27
 */
public interface CheckItemService {
    void add(CheckItem checkItem);

    PageResult findPage(Integer currentPage, Integer pageSize, String queryString);

    void deleteById(Integer id);

    CheckItem findById(Integer id);

    void edit(CheckItem checkItem);

    List<CheckItem> findAll();

}
