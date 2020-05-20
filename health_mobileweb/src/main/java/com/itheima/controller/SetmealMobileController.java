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

    @RequestMapping("findSetmealAll")
    public Result findSetmealAll(){
        try {
            String setmealLisString = jedisPool.getResource().get("setmealList");
            List<Setmeal> setmealList = null;
            if (setmealLisString==null){
                setmealList = setmealService.findSetmealAll();
                jedisPool.getResource().set("setmealList", JSON.toJSONString(setmealList));
            }else{
                setmealList = JSON.parseArray(setmealLisString,Setmeal.class);
            }
            return new Result(true, MessageConstant.QUERY_SETMEALLIST_SUCCESS,setmealList);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_SETMEALLIST_FAIL);
        }
    }

    @RequestMapping("findById")
    public Result findById(@RequestParam("id") String id){
        try {
            //Setmeal setmeal = setmealService.findById(id);
            String setmealDetail = jedisPool.getResource().hget("setmealDetail", id);
            Setmeal setmeal = null;
            if (setmealDetail==null){
                setmeal = setmealService.findById(id);
                jedisPool.getResource().hset("setmealDetail",id,JSON.toJSONString(setmeal));
            }else {
                setmeal  = new Gson().fromJson(setmealDetail, Setmeal.class);
            }
            return new Result(true,MessageConstant.QUERY_SETMEAL_SUCCESS,setmeal);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_SETMEAL_FAIL);
        }
    }
}
