package com.itheima.controller;

import com.itheima.constant.MessageConstant;
import com.itheima.entity.Result;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("user")
public class UserController {

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
}
