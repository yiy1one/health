package com.itheima.mapper;

import com.itheima.entity.PageResult;
import com.itheima.pojo.CheckGroup;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author ShiXiaoyu
 * @date 2020-05-09 14:28
 */
public interface CheckGroupMapper {
    //新增检查组
    void add(CheckGroup checkGroup);

    //添加检查项和检查组中间表关系
    void setCheckGroupAndCheckItem(@Param("checkItemId") Integer checkItemId, @Param("id")Integer id);

    List<CheckGroup> findByCondition(@Param("queryString") String queryString);

    CheckGroup findById(@Param("id") Integer id);

    List<Integer> findCheckItemIdsByCheckGroupId(@Param("id") Integer id);

    void deleteAssociation(@Param("id") Integer id);

    void edit(CheckGroup checkGroup);

    Integer findCountById(@Param("id") Integer id);

    void deleteById(@Param("id") Integer id);

    List<CheckGroup> findAll();
}
