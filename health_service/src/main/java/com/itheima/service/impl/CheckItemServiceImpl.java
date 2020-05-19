package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itheima.entity.PageResult;
import com.itheima.mapper.CheckItemMapper;
import com.itheima.pojo.CheckItem;
import com.itheima.service.CheckItemService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author ShiXiaoyu
 * @date 2020-05-06 12:57
 */
//@Service
@Service(interfaceClass = CheckItemService.class)
public class CheckItemServiceImpl implements CheckItemService {
    @Autowired
    private CheckItemMapper checkItemMapper;

    @Override
    public void add(CheckItem checkItem) {
        checkItemMapper.add(checkItem);
    }

    @Override
    public PageResult findPage(Integer currentPage, Integer pageSize, String queryString) {
        PageHelper.startPage(currentPage, pageSize);
        List<CheckItem> list = checkItemMapper.findByCondition(queryString);
        //将查询出来的数据进行包装
        PageInfo<CheckItem> pageInfo = new PageInfo<>(list);

        PageResult pageResult = new PageResult(pageInfo.getTotal(), pageInfo.getList());

        return pageResult;

    }

    @Override
    public void deleteById(Integer id) {

        Integer count = checkItemMapper.findCountByCheckitemId(id);
        checkItemMapper.deleteById(id);
    }

    @Override
    public CheckItem findById(Integer id) {
        CheckItem checkItem = checkItemMapper.findById(id);
        return checkItem;
    }

    @Override
    public void edit(CheckItem checkItem) {
        checkItemMapper.eidt(checkItem);
    }

    @Override
    public List<CheckItem> findAll() {
        return checkItemMapper.findALL();
    }
}
