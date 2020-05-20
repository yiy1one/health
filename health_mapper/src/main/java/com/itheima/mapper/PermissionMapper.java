package com.itheima.mapper;

import com.itheima.pojo.Permission;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PermissionMapper {
    List<Permission> findByCondition(@Param("queryString") String queryString);

    void add(Permission permission);

    Permission findById(Integer id);

    void edit(Permission permission);

    void deleteById(Integer id);

    Integer countUse(Integer id);

    List<Permission> findAll();
}
