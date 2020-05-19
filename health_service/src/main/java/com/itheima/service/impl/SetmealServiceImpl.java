package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itheima.constant.RedisConstant;
import com.itheima.entity.PageResult;
import com.itheima.mapper.SetmealMapper;
import com.itheima.pojo.Setmeal;
import com.itheima.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.JedisPool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ShiXiaoyu
 * @date 2020-05-10 18:33
 */
@Service(interfaceClass = SetmealService.class)
public class SetmealServiceImpl implements SetmealService {
    @Autowired
    private SetmealMapper setmealMapper;
    @Autowired
    private JedisPool jedisPool;

    @Override
    public void add(Integer[] checkGroupIds, Setmeal setmeal) {
        //新增套餐
        setmealMapper.add(setmeal);
        //设置中间表关系
        setSetmealAndCheckGroup(checkGroupIds,setmeal.getId());
        //判断是否上传图片，如果没有图片，将一个null保存到redis会报错。
        if (setmeal.getImg()!=null){
            //将要保存到数据库中的图片保存到redis
            jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_DB_RESOURCES,setmeal.getImg());
        }

    }

    @Override
    public PageResult findPage(Integer currentPage, Integer pageSize, String queryString) {
        //定义分页信息
        PageHelper.startPage(currentPage,pageSize);
        //查询分页数据
        List<Setmeal> list = setmealMapper.findByCondition(queryString);
        //保证分页数据
        PageInfo pageInfo = new PageInfo(list);

        //封装PageResult
        PageResult pageResult = new PageResult(pageInfo.getTotal(), pageInfo.getList());

        return pageResult;

    }

    @Override
    public List<Setmeal> findSetmealAll() {
        return setmealMapper.findSetmealAll();
    }

    @Override
    public Setmeal findById(Integer id) {
        return setmealMapper.findById(id);
    }

    @Override
    public Map getSetmealReport() {
        List<Map> list = setmealMapper.getSetmealReport();
        List<String> names = new ArrayList<>();
        for (Map map : list) {
            String name = (String) map.get("name");
            names.add(name);
        }
        Map<String, Object> map = new HashMap<>();
        map.put("setmealNames",names);
        map.put("setmealCounts",list);

        return map;

    }

    //设置中间表关系
    private void setSetmealAndCheckGroup(Integer[] checkGroupIds, Integer id) {

        if (checkGroupIds!=null && checkGroupIds.length>0){
            for (Integer checkGroupId : checkGroupIds) {
                setmealMapper.setSetmealAndCheckGroup(checkGroupId,id);
            }
        }
    }

}
