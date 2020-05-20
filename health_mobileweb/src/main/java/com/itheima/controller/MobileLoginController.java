package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;

import com.itheima.constant.MessageConstant;
import com.itheima.constant.RedisMessageConstant;
import com.itheima.entity.Result;
import com.itheima.pojo.Member;
import com.itheima.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;

/**
 * @author ShiXiaoyu
 * @date 2020-05-16 17:58
 */
@RestController
@RequestMapping("login")
public class MobileLoginController {
    @Autowired
    private JedisPool jedisPool;
    @Reference
    private MemberService memberService;

    @RequestMapping("checkPhotoCode")
    public Result checkPhotoCode(@RequestParam("photoCode") String photoCode,HttpServletRequest request){
        try {
            String code = (String) request.getParameter("photoCode");
            String checkcode_server = (String) request.getSession().getAttribute("CHECKCODE_SERVER");
            if (code==null||code==""){
                return new Result(false, "请输入图片验证码");
            }else{
                if (code.equalsIgnoreCase(checkcode_server)){
                    return new Result(true,"验证成功");
                }
                return new Result(false, "图片验证码错误");
            }
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false, "未知错误");

        }



    }
    @RequestMapping("check")
    public Result check(@RequestBody Map map) {
        try {
            // 获取用户传递进来的验证码
            String validateCode = (String) map.get("validateCode");
            String telephone = (String) map.get("telephone");
            String key = telephone + RedisMessageConstant.SENDTYPE_LOGIN;
            String code = jedisPool.getResource().get(key);
            //判断验证码是否过期，以及校验是否正确
            if (code == null || !code.equals(validateCode)) {
                //提示错误信息
                return new Result(false, MessageConstant.VALIDATECODE_ERROR);
            } else {

                //通过手机号查询是否是会员
                Member member = memberService.findByTelephone(telephone);
                //判断是否是会员
                if (member == null) {
                    //如果不是会员就进行自动注册
                    member = new Member();
                    member.setPhoneNumber(telephone);
                    member.setRegTime(new Date());
                    memberService.add(member);
                }

                //校验成功
                return new Result(true, MessageConstant.LOGIN_SUCCESS);
            }


        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.LOGIN_ERROR);
        }


    }
}
