package com.itheima.mapper;

import com.itheima.pojo.Permission;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PermissionMapper {
    List<Permission> findByCondition(@Param("queryString") String queryString);

    void add(Permission permission);
}
