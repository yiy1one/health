package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.PageResult;
import com.itheima.mapper.RoleMapper;
import com.itheima.pojo.Role;
import com.itheima.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service(interfaceClass = RoleService.class)
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleMapper roleMapper;

    @Override
    public PageResult findByPage(Integer currentPage, Integer pageSize, String queryString) {
        PageHelper.startPage(currentPage, pageSize);
        List<Role> list = roleMapper.findByCondition(queryString);
        PageInfo<Role> pageInfo = new PageInfo<>(list);

        return new PageResult(pageInfo.getTotal(), pageInfo.getList());
    }

    @Override
    public void add(Role role, Integer[] menu, Integer[] permission) {
        roleMapper.add(role);
        roleMapper.addRoleAndMenus(role.getId(), menu);
        roleMapper.addRoleAndPermission(role.getId(), permission);
    }

    @Override
    public Map<String, Object> findById(Integer id) {

        Role role = roleMapper.findRoleById(id);
        List<Integer> menuIds = roleMapper.findMenuIds(id);
        List<Integer> permissionIds = roleMapper.findPermissionIds(id);

        HashMap<String, Object> map = new HashMap<>();

        map.put("role", role);
        map.put("menuIds" , menuIds);
        map.put("permissionIds", permissionIds);
        return map;
    }

    @Override
    public void edit(Role role, Integer[] menu, Integer[] permission) {
        // 先修改角色表
        roleMapper.update(role);
        // 删除和menu相关数据
        roleMapper.deleteMenus(role.getId());
        // 删除和权限相关数据
        roleMapper.deletePermission(role.getId());
        // 添加和menu相关数据
        roleMapper.addRoleAndMenus(role.getId(), menu);
        //  添加和权限相关数据
        roleMapper.addRoleAndPermission(role.getId(), permission);
    }

    @Override
    public List<Role> findAll() {
        return roleMapper.findAll();
    }

    @Override
    public List<Integer> getRoleIdsByUserId(Integer userId) {

        return roleMapper.getRoleIdsByUserId(userId);
    }

    @Override
    public Set<Integer> getMenuIdsByRoleIds(List<Integer> roleIds) {

        return roleMapper.getMenuIdsByRoleIds(roleIds);
    }

    @Override
    public void delete(Integer id) {
        Integer count = roleMapper.findRoleUse(id);
        if (count == 0) {
            roleMapper.deleteMenus(id);
            roleMapper.deletePermission(id);

            roleMapper.deleteRole(id);
        } else {
            throw new RuntimeException(MessageConstant.ROLE_IS_QUOTED);
        }

    }
}
