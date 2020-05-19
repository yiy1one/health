package com.itheima.service;

import com.itheima.pojo.Order;

import java.util.Map;

/**
 * @author ShiXiaoyu
 * @date 2020-05-14 16:45
 */
public interface OrderService {
    Order add(Map map) throws Exception;

    Map findById(Integer id) throws Exception;
}
