package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.PageResult;
import com.itheima.mapper.CheckGroupMapper;
import com.itheima.pojo.CheckGroup;
import com.itheima.service.CheckGroupService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author ShiXiaoyu
 * @date 2020-05-09 14:26
 */
@Service(interfaceClass = CheckGroupService.class)
public class CheckGroupServiceImpl implements CheckGroupService {

    @Autowired
    private CheckGroupMapper checkGroupMapper;

    @Override
    public void add(Integer[] checkItemIds, CheckGroup checkGroup) {
        //新增检查组
        checkGroupMapper.add(checkGroup);
        //设置检查项和检查组的中间表
        setCheckGroupAndCheckItem(checkItemIds, checkGroup.getId());
    }

    @Override
    public PageResult findPage(Integer currentPage, Integer pageSize, String queryString) {
//使用分页插件定义当前页和页面大小
        PageHelper.startPage(currentPage,pageSize);
        //条件查询检查组数据
        List<CheckGroup> checkGroupList = checkGroupMapper.findByCondition(queryString);
        //将数据进行包装
        PageInfo<CheckGroup> pageInfo = new PageInfo<>(checkGroupList);

        //将分页数据封装到PageResult
        return new PageResult(pageInfo.getTotal(),pageInfo.getList());

    }

    @Override
    public CheckGroup findById(Integer id) {
        return checkGroupMapper.findById(id);
    }

    @Override
    public List<Integer> findCheckItemIdsByCheckGroupId(Integer id) {
        return checkGroupMapper.findCheckItemIdsByCheckGroupId(id);
    }

    //新增检查组和检查项的关系
    private void setCheckGroupAndCheckItem(Integer[] checkItemIds, Integer id) {
        //设置检查项和检查组的中间表
        if (checkItemIds != null && checkItemIds.length > 0) {
            for (Integer checkItemId : checkItemIds) {
                //循环设置中间表关系
                checkGroupMapper.setCheckGroupAndCheckItem(checkItemId, id);
            }
        }
    }

    @Override
    public void edit(Integer[] checkItemIds, CheckGroup checkGroup) {
        //删除中间表关系
        checkGroupMapper.deleteAssociation(checkGroup.getId());
        //更新中间表表关系(调用之前的方法即可)
        setCheckGroupAndCheckItem(checkItemIds,checkGroup.getId());
        //更新检查组信息
        checkGroupMapper.edit(checkGroup);

    }

    @Override
    public void deleteById(Integer id) {
        //查询当前检查组有没有被套餐引用
        Integer count = checkGroupMapper.findCountById(id);
        if (count>0){
            throw new RuntimeException(MessageConstant.CHECKGROUP_IS_QUOTED);
        }
        //通过id删除检查项和检查组中间表关系
        checkGroupMapper.deleteAssociation(id);
        //通过id删除检查组
        checkGroupMapper.deleteById(id);

    }

    @Override
    public List<CheckGroup> findAll() {
        return checkGroupMapper.findAll();
    }
}