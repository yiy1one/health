package com.itheima.jobs;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.RedisConstant;
import com.itheima.service.OderSettingService;
import com.itheima.utils.DateUtils;
import com.itheima.utils.QiniuUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisPool;

import java.util.*;

/**
 * @author ShiXiaoyu
 * @date 2020-05-11 13:37
 */
@Component
public class ClearJob {
    @Autowired
    private JedisPool jedisPool;

    @Reference
    private OderSettingService oderSettingService;

    public void clearImg(){
        //对redis中的存储图片的两个set集合进行比较，获取垃圾图片名称
        Set<String> imgSet = jedisPool.getResource().sdiff(RedisConstant.SETMEAL_PIC_RESOURCES, RedisConstant.SETMEAL_PIC_DB_RESOURCES);
        //遍历差值，获取其中的图片名称
        for (String s : imgSet) {
            //删除七牛云中的图片
            QiniuUtils.deleteFileFromQiniu(s);
            //删除redis中多余的图片
            jedisPool.getResource().srem(RedisConstant.SETMEAL_PIC_RESOURCES,s);
        }
    }

    public void clearOrderS(){
        Map<String, String> dates = DateUtils.getLastHalfYearMonth();
        String beginDay=dates.get("beginDay");
        String endDay=dates.get("endDay");
        oderSettingService.clearOrderSettingByMonth(beginDay,endDay);
    }
}
