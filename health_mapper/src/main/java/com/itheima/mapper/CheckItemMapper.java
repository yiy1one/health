package com.itheima.mapper;

import com.itheima.pojo.CheckItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author ShiXiaoyu
 * @date 2020-05-06 13:00
 */
public interface CheckItemMapper {
    void add(CheckItem checkItem);

    List<CheckItem> findByCondition(@Param("queryString") String queryString);

    void deleteById(@Param("id") Integer id);

    Integer findCountByCheckitemId(@Param("id") Integer id);

    CheckItem findById(@Param("id") Integer id);

    void eidt(CheckItem checkItem);

    List<CheckItem> findALL();
}
