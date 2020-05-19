package com.itheima.controller;

import com.itheima.constant.MessageConstant;
import com.itheima.constant.RedisMessageConstant;
import com.itheima.entity.Result;
import com.itheima.utils.SMSUtils;
import com.itheima.utils.ValidateCodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

/**
 * @author ShiXiaoyu
 * @date 2020-05-14 16:19
 */
@RestController
@RequestMapping("validateCode")
public class ValidateCodeController {
    @Autowired
    private JedisPool jedisPool;

    @RequestMapping("send2telephone")
    public Result send2Telephone(@RequestParam("telephone")String telephone){

        try {
           /* //通过工具类随机获取一个验证码
            Integer code = ValidateCodeUtils.generateValidateCode(4);
            //调用工具类，发送验证码到手机
            SMSUtils.sendShortMessage(SMSUtils.VALIDATE_CODE,telephone,code.toString());
            //设置一个key，用来保存验证码
            String key = telephone+ RedisMessageConstant.SENDTYPE_ORDER;
            //将验证码保存到redis中保存10分钟
            jedisPool.getResource().setex(key,60*10,code.toString());*/
            Integer code = ValidateCodeUtils.generateValidateCode(4);
            SMSUtils.sendShortMessage(SMSUtils.VALIDATE_CODE,telephone,code.toString());
            String key = telephone + RedisMessageConstant.SENDTYPE_ORDER;
            jedisPool.getResource().setex(key,60*10,code.toString());

            return new Result(true, MessageConstant.SEND_VALIDATECODE_SUCCESS);

        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.SEND_VALIDATECODE_FAIL);
        }
    }
    @RequestMapping("send2Login")
    public Result send2Login(@RequestParam("telephone")String telephone){
        try {
            Integer code = ValidateCodeUtils.generateValidateCode(4);
            SMSUtils.sendShortMessage(SMSUtils.VALIDATE_CODE,telephone,code.toString());
            String key = telephone + RedisMessageConstant.SENDTYPE_LOGIN;
            jedisPool.getResource().setex(key,60*10,code.toString());
            return new Result(true, MessageConstant.SEND_VALIDATECODE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.SEND_VALIDATECODE_FAIL);
        }

    }

}
