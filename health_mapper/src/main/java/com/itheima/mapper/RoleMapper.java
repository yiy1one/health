package com.itheima.mapper;

import com.itheima.pojo.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

public interface RoleMapper {
    List<Role> findByCondition(@Param("queryString") String queryString);

    void add(Role role);

    void addRoleAndMenus(@Param("id") Integer id, @Param("menus") Integer[] menus);

    void addRoleAndPermission(@Param("id") Integer id,@Param("permissions") Integer[] permissions);


    Role findRoleAndPermission(Integer id);

    Role findRoleAndMenu(Integer id);

    Role findRoleById(Integer id);

    List<Integer> findMenuIds(Integer id);

    List<Integer> findPermissionIds(Integer id);

    void update(Role role);

    void deleteMenus(Integer id);

    void deletePermission(Integer id);

    List<Role> findAll();

    List<Integer> getRoleIdsByUserId(Integer userId);

    Set<Integer> getMenuIdsByRoleIds(@Param("roleIds") List<Integer> roleIds);

    Integer findRoleUse(Integer id);

    void deleteRole(Integer id);
}
