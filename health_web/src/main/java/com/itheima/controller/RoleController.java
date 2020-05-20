package com.itheima.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.pojo.Role;
import com.itheima.service.RoleService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("role")
public class RoleController {

    @Reference
    private RoleService roleService;


    @RequestMapping("findByPage")
    public Result findByPage(@RequestBody QueryPageBean queryPageBean) {
        try {
            PageResult pageResult = roleService.findByPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize(), queryPageBean.getQueryString());
            return new Result(true, MessageConstant.QUERY_ROLE_SUCCESS, pageResult);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_ROLE_FAIL);

        }

    }

    @PreAuthorize("hasAuthority('ROLE_ADD')")
    @RequestMapping("add")
    public Result add(@RequestParam("menuIds") Integer[] menuIds, @RequestParam("permissionIds") Integer[] permissionIds, @RequestBody Role role) {
        try {

            roleService.add(role, menuIds, permissionIds);
            return new Result(true, MessageConstant.ADD_ROLE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_ROLE_FAIL);

        }

    }

    @PreAuthorize("hasAuthority('ROLE_QUERY')")
    @RequestMapping("findById")
    public Result findById(@RequestParam("id") Integer id) {
        try {
            Map<String, Object> map = roleService.findById(id);
            return new Result(true, MessageConstant.QUERY_ROLE_SUCCESS, map);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_ROLE_FAIL);

        }
    }

    @PreAuthorize("hasAuthority('ROLE_EDIT')")
    @RequestMapping("edit")
    public Result edit(@RequestParam("menuIds") Integer[] menuIds, @RequestParam("permissionIds") Integer[] permissionIds, @RequestBody Role role) {
        try {

            roleService.edit(role, menuIds, permissionIds);
            return new Result(true, MessageConstant.EDIT_ROLE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.EDIT_ROLE_FAIL);

        }
    }

    @RequestMapping("findAll")
    private Result findAll() {
        try {
            List<Role> list = roleService.findAll();
            return new Result(true, MessageConstant.QUERY_ROLE_SUCCESS, list);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_ROLE_FAIL);

        }
    }

    @PreAuthorize("hasAuthority('ROLE_DELETE')")
    @RequestMapping("deleteById")
    public Result deleteById(@RequestParam("id") Integer id) {
        try {
            roleService.delete(id);
            return new Result(true, MessageConstant.DELETE_ROLE_SUCCESS);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return new Result(false, e.getMessage());

        }catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.DELETE_ROLE_FAIL);

        }
    }
}
