package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.constant.RedisMessageConstant;
import com.itheima.entity.Result;
import com.itheima.pojo.Order;
import com.itheima.service.OrderService;
import com.itheima.utils.SMSUtils;
import com.itheima.utils.ValidateCodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

import java.util.Map;

/**
 * @author ShiXiaoyu
 * @date 2020-05-14 16:41
 */
@RestController
@RequestMapping("order")
public class OrderMobileController {
    @Autowired
    private JedisPool jedisPool;
    @Reference
    private OrderService orderService;
    @RequestMapping("findById")
    public Result findById(Integer id){

        try {
            Map map = orderService.findById(id);
            return new Result(true,MessageConstant.QUERY_ORDER_SUCCESS,map);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_ORDER_FAIL);
        }

    }

    @RequestMapping("add")
    public Result add (@RequestBody Map map){

        try {
            //获取手机号码
            String telephone = (String) map.get("telephone");
            //设置redis的key
            String key = telephone+ RedisMessageConstant.SENDTYPE_ORDER;
            //从缓存中获取验证码
            String code = jedisPool.getResource().get(key);

            //获取用户输入的验证码
            String validateCode = (String) map.get("validateCode");
            //判断验证码是否过期，以及输入的验证码是否正确
            if(code==null || !code.equals(validateCode)){
                //如果为验证码过期，或者验证码校验错误
                return new Result(false, MessageConstant.VALIDATECODE_ERROR);
            }else{ //验证码通过校验
                //设置预约类型
                //开始预约
                Order order = orderService.add(map);
                //预约成功后发送短信通知
                SMSUtils.sendShortMessage(SMSUtils.ORDER_NOTICE,telephone, ValidateCodeUtils.generateValidateCode(4).toString());
                //预约成功响应order对象
                return new Result(true,MessageConstant.ADD_ORDER_SUCCESS,order);
            }
        } catch (RuntimeException  e) {
            e.printStackTrace();
            return new Result(false,e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.ADD_ORDER_FAIL);
        }
    }

}
