package com.itheima.service;

import com.itheima.pojo.OrderSetting;

import java.util.List;
import java.util.Map;

/**
 * @author ShiXiaoyu
 * @date 2020-05-12 19:13
 */
public interface OderSettingService {
    void importOrderSettings(List<OrderSetting> orderSettings);

    List<Map> findOrderSettingByMonth(String date);

    void updateNumberByOrderDate(OrderSetting orderSetting);
}
