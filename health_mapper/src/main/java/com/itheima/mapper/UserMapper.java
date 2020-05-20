package com.itheima.mapper;

import com.itheima.pojo.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author ShiXiaoyu
 * @date 2020-05-17 18:47
 */
public interface UserMapper {
    User findByUsername(@Param("username") String username);

    List<User> findByCondition(@Param("queryString")String queryString);

    List<User> findAll();

    User findIdByName(@Param("username") String username);

    void insert(User user);

    void insertRoles(@Param("userId") Integer id,@Param("roleIds") Integer[] roleIds);

    User findById(Integer userId);

    List<Integer> getRoleIdsByUserId(Integer userId);

    void update(User user);

    void deleteRoles(Integer id);

    void delete(Integer id);

    Integer checkUsername(String username);
}
