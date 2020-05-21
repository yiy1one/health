package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.Result;
import com.itheima.pojo.Setmeal;
import com.itheima.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

import java.util.List;
import java.util.Map;

/**
 * @author ShiXiaoyu
 * @date 2020-05-13 14:36
 */
@RestController
@RequestMapping("setmeal")
public class SetmealMobileController {

    @Reference
    private SetmealService setmealService;

    @Autowired
    private JedisPool jedisPool;

    //套餐列表查询
    @RequestMapping("findSetmealAll")
    public Result findSetmealAll(){
        try {
            String setmealListString = jedisPool.getResource().get("setmealList");
            List<Setmeal> setmealList = null;

            //如果redis查询结果为空，查询数据库，添加redis
            if (setmealListString==null){
                setmealList = setmealService.findSetmealAll();
                if (setmealList!=null) {
                    // 把数据转为json字符串后添加进数据库
                    jedisPool.getResource().setnx("setmealList", JSON.toJSONString(setmealList));
                }
            }else{
                //如果redis查询结果不为空，序列化查询对象
                setmealList = JSON.parseArray(setmealListString,Setmeal.class);
            }
            return new Result(true, MessageConstant.QUERY_SETMEALLIST_SUCCESS,setmealList);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_SETMEALLIST_FAIL);
        }
    }

    //查询套餐详情
    @RequestMapping("findById")
    public Result findById(@RequestParam("id") String id){
        try {
            String setmealDetail = jedisPool.getResource().hget("setmealDetail", id);
            Setmeal setmeal = null;
            if (setmealDetail==null){
                setmeal = setmealService.findById(id);
                if (setmeal!=null){
                    // 把查询到的套餐详情存入redis的setmealDetail
                    jedisPool.getResource().hsetnx("setmealDetail",id,JSON.toJSONString(setmeal));
                    // 把查询到的套餐被查询计数置为 1
                    jedisPool.getResource().hsetnx("setmealDetail-count",id,"1");
                }
            }else {
                //如果redis查询结果不为空，计数器自增
                jedisPool.getResource().hincrBy("setmealDetail-count",id,1);
                //复杂对象用Gson反序列化
                setmeal  = new Gson().fromJson(setmealDetail, Setmeal.class);
            }
            return new Result(true,MessageConstant.QUERY_SETMEAL_SUCCESS,setmeal);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_SETMEAL_FAIL);
        }
    }
}
