package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.pojo.User;
import com.itheima.service.UserService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("user")
public class UserController {

    @Reference
    private UserService userService;

    /**
     *
     * 在Principal中存储了当前登录用户的相关认证信息，将其配置在方法的参数位置，spring会自动将其注入进来
     *
     * @param principal
     * @return
     */
    @RequestMapping("getUsername")
    public Result getUsername(Principal principal){

        try {
            return new Result(true, MessageConstant.GET_USERNAME_SUCCESS,principal.getName());
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.GET_USERNAME_FAIL);
        }

    }

    @RequestMapping("findByPage")
    public Result findByPage(@RequestBody QueryPageBean queryPageBean) {
        try {
            PageResult pageResult = userService.findByPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize(), queryPageBean.getQueryString());
            return new Result(true, MessageConstant.QUERY_USER_SUCCESS, pageResult);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_USER_FAIL);

        }
    }

    @RequestMapping("findAll")
    public Result findAll() {
        try {
            List<User> list = userService.findAll();
            return new Result(true, MessageConstant.QUERY_USER_SUCCESS, list);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_USER_FAIL);

        }
    }

    @RequestMapping("add")
    public Result add(@RequestParam("roleIds") Integer[] roleIds , @RequestBody User user) {
        try {
            userService.add(roleIds, user);
            return new Result(true, MessageConstant.ADD_USER_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_USER_FAIL);
        }
    }


    // 编辑回显数据
    @RequestMapping("findById")
    public Result findById(@RequestParam("userId") Integer userId) {
        try {
            User user = userService.findById(userId);
            List<Integer> roleIds = userService.getRoleIdsByUserId(user.getId());
            HashMap<String, Object> map = new HashMap<>();
            map.put("userInfo", user);
            map.put("roleIds", roleIds);
            return new Result(true, MessageConstant.QUERY_USER_SUCCESS, map);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_USER_FAIL);
        }
    }

    @RequestMapping("edit")
    public Result edit(@RequestParam("roleIds") Integer[] roleIds , @RequestBody User user) {
        try {
            userService.edit(roleIds, user);
            return new Result(true, MessageConstant.EDIT_USER_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.EDIT_USER_FAIL);
        }
    }


    // 删除用户，如果是admin，无法删除
    @RequestMapping("deleteById")
    public Result deleteById(@RequestParam("id") Integer id) {
        try {
            userService.delete(id);
            return new Result(true, MessageConstant.DELETE_USER_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.DELETE_USER_FAIL);
        }
    }


    // 检查用户名是否重复
    @RequestMapping("checkUsername")
    public Result checkUsername(@RequestParam("username") String username) {

            boolean flag = userService.checkUsername(username);
            if (flag) {
                return new Result(true, "用户名正常");
            } else {
                return new Result(false, "用户名重复");
            }
    }
}
