package com.itheima.security;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.pojo.Permission;
import com.itheima.pojo.Role;
import com.itheima.pojo.User;
import com.itheima.service.UserService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author ShiXiaoyu
 * @date 2020-05-17 18:37
 */
@Component
public class SpringSecurityUserService implements UserDetailsService {
    @Reference
    private UserService userService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //通过用户名查询用信息
        User user = userService.findByUsername(username);
        List<GrantedAuthority> list = new ArrayList<>();
        Set<Role> roles = user.getRoles();
        for (Role role : roles) {
            list.add(new SimpleGrantedAuthority(role.getKeyword()));
            Set<Permission> permissions = role.getPermissions();
            for (Permission permission : permissions) {
                list.add(new SimpleGrantedAuthority(permission.getKeyword()));
            }
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(),user.getPassword(),list);
    }
}
