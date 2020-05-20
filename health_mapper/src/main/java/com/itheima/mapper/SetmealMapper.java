package com.itheima.mapper;

import com.itheima.pojo.Setmeal;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author ShiXiaoyu
 * @date 2020-05-10 18:35
 */
public interface SetmealMapper {
    void add(Setmeal setmeal);

    void setSetmealAndCheckGroup(@Param("checkGroupId")Integer checkGroupId, @Param("id") Integer id);

    List<Setmeal> findByCondition(@Param("queryString") String queryString);

    List<Setmeal> findSetmealAll();

    Setmeal findById(@Param("id") String id);

    List<Map> getSetmealReport();
}
