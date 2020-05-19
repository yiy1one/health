package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.constant.RedisConstant;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.pojo.CheckGroup;
import com.itheima.pojo.Setmeal;
import com.itheima.service.CheckGroupService;
import com.itheima.service.SetmealService;
import com.itheima.utils.QiniuUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.JedisPool;


import java.util.List;
import java.util.UUID;

/**
 * @author ShiXiaoyu
 * @date 2020-05-10 10:38
 */
@RestController
@RequestMapping("setmeal")
public class SetMealController {
    @Autowired
    private JedisPool jedisPool;

    @Reference
    private SetmealService setmealService;

    @RequestMapping("upload")
    public Result upload(@RequestParam("imgFile") MultipartFile file) {

        try {

            //获取上传文件的文件名称
            String originalFilename = file.getOriginalFilename();
            //获取后缀名
            int index = originalFilename.indexOf(".");
            String suffix = originalFilename.substring(index);
            //拼接一个唯一的文件名称
            String fileName = UUID.randomUUID() + suffix;
            //将图片上传到七牛云
            QiniuUtils.upload2Qiniu(file.getBytes(), fileName);
            //将自动上传的图片保存到redis的set集合中
            jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_RESOURCES,fileName);
            return new Result(true, MessageConstant.PIC_UPLOAD_SUCCESS, fileName);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.PIC_UPLOAD_FAIL);
        }
    }

    @RequestMapping("add")
    public Result add(@RequestParam("checkGroupIds") Integer[] checkGroupIds,
                      @RequestBody Setmeal setmeal) {

        try {
            setmealService.add(checkGroupIds, setmeal);
            return new Result(true, MessageConstant.ADD_SETMEAL_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_SETMEAL_FAIL);
        }


    }

    @RequestMapping("findPage")
    public Result findPage(@RequestBody QueryPageBean queryPageBean) {
        try {
            PageResult pageResult = setmealService.findPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize(),queryPageBean.getQueryString());
            return new Result(true,MessageConstant.QUERY_SETMEALLIST_SUCCESS,pageResult);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_SETMEALLIST_FAIL);
        }

    }

}
