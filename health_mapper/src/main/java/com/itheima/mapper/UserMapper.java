package com.itheima.mapper;

import com.itheima.pojo.User;
import org.apache.ibatis.annotations.Param;

/**
 * @author ShiXiaoyu
 * @date 2020-05-17 18:47
 */
public interface UserMapper {
    User findByUsername(@Param("username") String username);
}
